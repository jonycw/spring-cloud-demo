package com.mint.chat.controller;

import com.mint.chat.config.WeChatProperties;
import com.mint.chat.model.wx.publicno.AccessTokenDto;
import com.mint.chat.model.wx.publicno.UserInfoDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;

/**
 * @Author: cw
 * @Date: 2020/12/30 11:27
 * @Description:
 */

@RestController
@Api("微信公众号网页接口")
@Slf4j
public class PublicNoPageController {

    private final WeChatProperties weChatProperties;
    private final RestTemplate restTemplate;

    @Autowired
    public PublicNoPageController(WeChatProperties weChatProperties, RestTemplate restTemplate) {
        this.weChatProperties = weChatProperties;
        this.restTemplate = restTemplate;
    }

    @ApiOperation("微信公众号网页授权")
    @GetMapping("/wx/public/oauth")
    public void wxPublicLogin(HttpServletResponse response) {
        String appId = weChatProperties.getPublicAppId();
        String baseOauth = weChatProperties.getPublicPageBaseOauth();

        String redirectUrl = "http://test.mint.com:8088/wx/public/oauth/back";
        try {
            // 请求用户授权
            String url = baseOauth +
                    "?appid=" + appId +
                    "&redirect_uri=" + URLEncoder.encode(redirectUrl,"UTF-8")+
                    "&response_type=code" +
                    "&scope=snsapi_base" +
                    "&state=123" +
                    "#wechat_redirect";
            log.info("微信第三方授权请求:[url:{}]",url);
            response.sendRedirect(url);
        } catch (UnsupportedEncodingException uee) {
            uee.printStackTrace();
            // 重定向错误页面
        } catch (IOException ioe) {
            ioe.printStackTrace();
            // 重定向错误页面
        }
    }


    @ApiOperation("微信公众号网页授权登录后回调")
    @GetMapping("/wx/public/oauth/back")
    public void wxPublicBack(String code,String state) {
        log.info("微信授权用户同意后:[code:{},state:{}]",code,state);
        String appId = weChatProperties.getPublicAppId();
        String appSecret = weChatProperties.getPublicAppSecret();
        String baseAccessTokenUrl = weChatProperties.getPublicPageAccessToken();
        String basePublicUserInfoUrl = weChatProperties.getPublicPageUserInfo();
        // 获取access_token
        String tokenUrl = baseAccessTokenUrl +"?appid=" + appId + "&secret=" + appSecret + "&code=" + code + "&grant_type=authorization_code";
        log.info("获取access_token请求地址:[tokenUrl:{}]",tokenUrl);
        ResponseEntity<AccessTokenDto> responseEntity = restTemplate.getForEntity(tokenUrl, AccessTokenDto.class);

        log.info("获取access_token返回结果:[AccessTokenDto:{}]",responseEntity.getBody());
        String accessToken = responseEntity.getBody().getAccess_token();
        String openId = responseEntity.getBody().getOpenid();


        // 请求获取userInfo
        String infoUrl = basePublicUserInfoUrl +
                "?access_token=" + accessToken +
                "&openid=" + openId +
                "&lang=zh_CN";
        log.info("获取用户信息请求地址:[infoUrl:{}]",infoUrl);
        ResponseEntity<UserInfoDto> infoDtoResponseEntity = restTemplate.getForEntity(infoUrl, UserInfoDto.class);
        UserInfoDto userInfoDto = infoDtoResponseEntity.getBody();
        log.info("获取用户信息返回结果:[userInfoDto:{}]",userInfoDto);
    }

}

