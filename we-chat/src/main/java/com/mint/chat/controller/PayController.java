package com.mint.chat.controller;

import com.alibaba.fastjson.JSONObject;
import com.mint.chat.config.WeChatConfig;
import com.mint.chat.util.WeChatKit;
import com.mint.chat.util.WeChatPayRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: cw
 * @Date: 2021/2/24 16:09
 * @Description:
 */

@RestController
@Api("微信支付接口")
@Slf4j
public class PayController {
//
//    /**
//     * @Description 微信浏览器内微信支付/公众号支付(JSAPI)
//     * @param request
//     * @param
//     * @return Map
//     */
//    @GetMapping(value = "/pay")
//    public @ResponseBody
//    Map<String, String> orders(HttpServletRequest request, HttpServletResponse response) {
//        try {
//
//            String openId = "用户的openid";
//
//            // 拼接统一下单地址参数
//            Map<String, String> paraMap = new HashMap<String, String>();
//            // 获取请求ip地址
//            String ip = request.getHeader("x-forwarded-for");
//            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//                ip = request.getHeader("Proxy-Client-IP");
//            }
//            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//                ip = request.getHeader("WL-Proxy-Client-IP");
//            }
//            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//                ip = request.getRemoteAddr();
//            }
//            if (ip.indexOf(",") != -1) {
//                String[] ips = ip.split(",");
//                ip = ips[0].trim();
//            }
//
//            paraMap.put("appid", "wx26715a192f317eaf"); // 商家平台ID
//            paraMap.put("body", "南京学而思-支付测试"); // 商家名称-销售商品类目、String(128)
//            paraMap.put("mch_id", "1247278301"); // 商户ID
//            paraMap.put("nonce_str", WXPayUtil.generateNonceStr()); // UUID
//            paraMap.put("openid", openId);
//            paraMap.put("out_trade_no", UUID.randomUUID().toString().replaceAll("-", ""));// 订单号,每次都不同
//            paraMap.put("spbill_create_ip", ip);
//            paraMap.put("total_fee", "1"); // 支付金额，单位分
//            paraMap.put("trade_type", "JSAPI"); // 支付类型
//            paraMap.put("notify_url", "用户支付完成后，你想微信调你的哪个接口");// 此路径是微信服务器调用支付结果通知路径随意写
//            String sign = WXPayUtil.generateSignature(paraMap, AuthUtil.PATERNERKEY);
//            paraMap.put("sign", sign);
//            String xml = WXPayUtil.mapToXml(paraMap);// 将所有参数(map)转xml格式
//
//            // 统一下单 https://api.mch.weixin.qq.com/pay/unifiedorder
//            String unifiedorder_url = "https://api.mch.weixin.qq.com/pay/unifiedorder";
//
//            System.out.println("xml为：" + xml);
//
//            // String xmlStr = HttpRequest.sendPost(unifiedorder_url,
//            // xml);//发送post请求"统一下单接口"返回预支付id:prepay_id
//
//            String xmlStr = HttpRequest.httpsRequest(unifiedorder_url, "POST", xml);
//
//            System.out.println("xmlStr为：" + xmlStr);
//
//            // 以下内容是返回前端页面的json数据
//            String prepay_id = "";// 预支付id
//            if (xmlStr.indexOf("SUCCESS") != -1) {
//                Map<String, String> map = WXPayUtil.xmlToMap(xmlStr);
//                prepay_id = (String) map.get("prepay_id");
//            }
//
//            Map<String, String> payMap = new HashMap<String, String>();
//            payMap.put("appId", AuthUtil.APPID);
//            payMap.put("timeStamp", WXPayUtil.getCurrentTimestamp() + "");
//            payMap.put("nonceStr", WXPayUtil.generateNonceStr());
//            payMap.put("signType", "MD5");
//            payMap.put("package", "prepay_id=" + prepay_id);
//            String paySign = WXPayUtil.generateSignature(payMap, AuthUtil.PATERNERKEY);
//            payMap.put("paySign", paySign);
//            //将这个6个参数传给前端
//            return payMap;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//
//
//
//    /**
//     * @Title: callBack
//     * @Description: 支付完成的回调函数
//     * @param:
//     * @return:
//     */
//    @GetMapping("/notify")
//    public String callBack(HttpServletRequest request, HttpServletResponse response) {
//        // System.out.println("微信支付成功,微信发送的callback信息,请注意修改订单信息");
//        InputStream is = null;
//        try {
//
//            is = request.getInputStream();// 获取请求的流信息(这里是微信发的xml格式所有只能使用流来读)
//            String xml = WXPayUtil.InputStream2String(is);
//            Map<String, String> notifyMap = WXPayUtil.xmlToMap(xml);// 将微信发的xml转map
//
//            System.out.println("微信返回给回调函数的信息为："+xml);
//
//            if (notifyMap.get("result_code").equals("SUCCESS")) {
//                String ordersSn = notifyMap.get("out_trade_no");// 商户订单号
//                String amountpaid = notifyMap.get("total_fee");// 实际支付的订单金额:单位 分
//                BigDecimal amountPay = (new BigDecimal(amountpaid).divide(new BigDecimal("100"))).setScale(2);// 将分转换成元-实际支付金额:元
//
//                /*
//                 * 以下是自己的业务处理------仅做参考 更新order对应字段/已支付金额/状态码
//                 */
//                System.out.println("===notify===回调方法已经被调！！！");
//
//            }
//
//            // 告诉微信服务器收到信息了，不要在调用回调action了========这里很重要回复微信服务器信息用流发送一个xml即可
//            response.getWriter().write("<xml><return_code><![CDATA[SUCCESS]]></return_code></xml>");
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (is != null) {
//                try {
//                    is.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//
//        return null;
//    }

    /**
     * 获取本机IP地址
     * @return IP
     */
    private static String getRemortIP(HttpServletRequest request) {
        if (request.getHeader("x-forwarded-for") == null) {
            return request.getRemoteAddr();
        }
        return request.getHeader("x-forwarded-for");
    }


    @GetMapping("/order/pay")
    public JSONObject unifiedOrder(String orderId, Integer totalFee,
                                   String title, String body, String notifyUrl, String clientIP) {
        String requestUri = "https://api.mch.weixin.qq.com/pay/unifiedorder";

        WeChatPayRequest payRequest = new WeChatPayRequest();
        payRequest.addPara("appid", WeChatConfig.AppID);
        payRequest.addPara("mch_id", WeChatConfig.MchID);
        payRequest.addPara("attach", "支付测试");
        payRequest.addPara("body", "JSAPI支付测试");
        payRequest.addPara("nonce_str", WeChatKit.generateNonceStr());
        payRequest.addPara("notify_url", "http://localhost:");
        payRequest.addPara("out_trade_no", "1415659990");
        payRequest.addPara("spbill_create_ip", clientIP);
        payRequest.addPara("total_fee", Integer.toString(1));
        payRequest.addPara("trade_type", "JSAPI");

        String result = payRequest.doRequest(requestUri,
                WeChatPayRequest.SIGN_MD5);
        JSONObject json = WeChatKit.xmlResultToJSON(result);

        return json;
    }

    public JSONObject queryOrder(String orderId) {
        String requestUri = "https://api.mch.weixin.qq.com/pay/orderquery";

        WeChatPayRequest payRequest = new WeChatPayRequest();
        payRequest.addPara("appid", WeChatConfig.AppID);
        payRequest.addPara("mch_id", WeChatConfig.MchID);
        payRequest.addPara("nonce_str", WeChatKit.generateNonceStr());
        payRequest.addPara("out_trade_no", orderId);

        String result = payRequest.doRequest(requestUri,
                WeChatPayRequest.SIGN_MD5);
        JSONObject json = WeChatKit.xmlResultToJSON(result);

        return json;
    }

    public JSONObject closeOrder(String orderId) {
        String requestUri = "https://api.mch.weixin.qq.com/pay/orderquery";

        WeChatPayRequest payRequest = new WeChatPayRequest();
        payRequest.addPara("appid", WeChatConfig.AppID);
        payRequest.addPara("mch_id", WeChatConfig.MchID);
        payRequest.addPara("nonce_str", WeChatKit.generateNonceStr());
        payRequest.addPara("out_trade_no", orderId);

        String result = payRequest.doRequest(requestUri,
                WeChatPayRequest.SIGN_MD5);
        JSONObject json = WeChatKit.xmlResultToJSON(result);

        return json;
    }


}

