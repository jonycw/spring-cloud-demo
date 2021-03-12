package com.mint.chat.util;

import com.mint.chat.config.WeChatConfig;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Author: cw
 * @Date: 2021/2/25 9:22
 * @Description:
 */
public class WeChatPayRequest {

    public static final int SIGN_SHA1 = 0;
    public static final int SIGN_MD5 = 1;

    private Map<String, String> params = new HashMap<String, String>();

    public void addPara(String key, String value) {
        params.put(key, value);
    }

    public String doRequest(String requestUri, int signType) {
        String sign = createSign(signType);
        String xml = buildParamsXML(sign);
        String result = WeChatKit.postXML(requestUri, xml);
        return result;
    }

    public String createSign(int signType) {
        switch (signType) {
            case SIGN_SHA1:
                return createSHA1Sign();
            case SIGN_MD5:
                return createMD5Sign();
        }

        return null;
    }

    public String buildParamsXML(String sign) {
        Map<String, String> paramMap = new LinkedHashMap<String, String>();
        String[] keyArray = params.keySet().toArray(new String[0]);
        Arrays.sort(keyArray);
        for (String key : keyArray) {
            String value = params.get(key);
            paramMap.put(key, value);
        }
        paramMap.put("sign", sign);

        return WeChatKit.buildParamXML(paramMap);
    }

    private String createSHA1Sign() {
        String codes = combineParams();
        String sign = MD5Kit.encode("SHA1", codes);
        return sign;
    }

    private String createMD5Sign() {
        String codes = combineParams();
        codes += "&key=" + WeChatConfig.APIKey;
        String sign = MD5Kit.encodeByMD5(codes).toUpperCase();
        return sign;
    }

    private String combineParams() {
        String[] keyArray = params.keySet().toArray(new String[0]);
        Arrays.sort(keyArray);

        StringBuilder stringBuilder = new StringBuilder();
        for (String key : keyArray) {
            String value = params.get(key);
            stringBuilder.append(key).append("=").append(value).append("&");
        }

        int len = stringBuilder.length();
        return stringBuilder.substring(0, len - 1);
    }

}

