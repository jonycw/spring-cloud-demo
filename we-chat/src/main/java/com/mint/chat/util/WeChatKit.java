package com.mint.chat.util;

import com.alibaba.fastjson.JSONObject;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * @Author: cw
 * @Date: 2021/2/25 9:25
 * @Description:
 */
public class WeChatKit {

    public static String generateNonceStr() {
        long time = System.currentTimeMillis();
        Random random = new Random(time);
        String str = time + "_" + random.nextInt(10);
        return MD5Kit.encodeByMD5(str);
    }

    public static String httpGet(String urlget) {
        String result = "";

        try {
            URL url = new URL(urlget);
            HttpURLConnection httpurlConnection = (HttpURLConnection) url
                    .openConnection();
            httpurlConnection.setDoOutput(true);
            httpurlConnection.setDoInput(true);
            httpurlConnection.setConnectTimeout(10000);
            httpurlConnection.setRequestMethod("GET");
            httpurlConnection.connect();
            InputStreamReader bis = new InputStreamReader(
                    httpurlConnection.getInputStream(), "utf-8");
            int c = 0;
            while ((c = bis.read()) != -1) {
                result = result + (char) c;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Http request failed.");
            result = null;
        }

        return result;
    }

    public static String postXML(String urlStr, String xmlStr) {
        try {
            URL url = new URL(urlStr);
            URLConnection con = url.openConnection();
            con.setDoOutput(true);
            con.setRequestProperty("Pragma:", "no-cache");
            con.setRequestProperty("Cache-Control", "no-cache");
            con.setRequestProperty("Content-Type", "text/xml");

            OutputStreamWriter out = new OutputStreamWriter(
                    con.getOutputStream());
            out.write(xmlStr);
            out.flush();
            out.close();
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));

            String result = "";
            String line = "";
            for (line = br.readLine(); line != null; line = br.readLine()) {
                result += line;
            }
            return result;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String buildParamXML(Map<String, String> params) {
        Element root = DocumentHelper.createElement("xml");

        String[] keyArray = params.keySet().toArray(new String[0]);
        for (String key : keyArray) {
            String value = params.get(key);
            root.addElement(key).setText(value);
        }

        String xmlStr = root.asXML();
        xmlStr = xmlStr.replaceAll("><", ">\r\n\t<");
        xmlStr = xmlStr.replace("\t</xml>", "</xml>");
        return xmlStr;
    }

    public static JSONObject xmlResultToJSON(String xmlStr) {
        try {
            Document document = DocumentHelper.parseText(xmlStr);
            Element resultRoot = document.getRootElement();
            List<Element> elements = resultRoot.elements();

            JSONObject json = new JSONObject();
            for (Element element : elements) {
                String key = element.getName();
                String value = element.getText();
                json.put(key, value);
            }

            return json;
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        return null;
    }
}

