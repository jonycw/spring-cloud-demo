package com.mint.chat.controller;

import com.mint.chat.config.WeChatProperties;
import com.mint.chat.constants.Result;
import com.mint.chat.constants.ResultUtil;
import com.mint.chat.model.wx.publicno.AccessTokenDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @Author: cw
 * @Date: 2020/12/30 14:19
 * @Description:
 */

@RestController
@Api("微信公众号基本接口")
@Slf4j
public class PublicNoBaseController {

    private final WeChatProperties weChatProperties;
    private final RestTemplate restTemplate;

    @Autowired
    public PublicNoBaseController(WeChatProperties weChatProperties, RestTemplate restTemplate) {
        this.weChatProperties = weChatProperties;
        this.restTemplate = restTemplate;
    }

    @ApiOperation("微信公众号网页授权")
    @GetMapping("/wx/public/getAccessToken")
    public Result wxPublicGetAccessToken(){
        String accessTokenUrl= weChatProperties.getPublicAccessToken();
        String appId = weChatProperties.getPublicAppId();
        String secret = weChatProperties.getPublicAppSecret();

        String tokenUrl = accessTokenUrl +"?appid=" + appId + "&secret=" + secret + "&grant_type=client_credential";
        log.info("获取access_token请求地址:[tokenUrl:{}]",tokenUrl);
        ResponseEntity<AccessTokenDto> responseEntity = restTemplate.getForEntity(tokenUrl, AccessTokenDto.class);
        AccessTokenDto accessTokenDto = responseEntity.getBody();
        return ResultUtil.SUCCESS(accessTokenDto);
    }

}

