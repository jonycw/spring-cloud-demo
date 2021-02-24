package com.mint.other.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.*;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

import javax.net.ssl.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 基于HttpClient实现的Http请求工具
 * @author cw
 */
@Component
@Slf4j
public class HttpRequestUtils {

    private String logPath = "/client-http.log";

    /** 连接池 */
    private static PoolingHttpClientConnectionManager connManager;
    /** 编码 */
    private static final String ENCODING = "UTF-8";
    /** 出错返回结果 */
    private static final int MAX_COUNT = 3;
    private static final Integer TIME_OUT = 10000;

    /*
      初始化连接池管理器,配置SSL
     */
    static {
        try {
            // 创建ssl上下文对象
            SSLContext sslContext = getSSLContext();

            // 注册
            Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                    .register("http", PlainConnectionSocketFactory.INSTANCE)
                    .register("https", new SSLConnectionSocketFactory(sslContext))
                    .build();

            // ssl注册到连接池
            connManager = new PoolingHttpClientConnectionManager(registry);
            // 连接池最大连接数
            connManager.setMaxTotal(300);
            // 每个路由最大连接数
            connManager.setDefaultMaxPerRoute(20);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取客户端连接对象
     * @return CloseableHttpClient
     */
    private CloseableHttpClient getHttpClient() {
        // 配置请求参数
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(TIME_OUT).
                        setConnectTimeout(TIME_OUT).
                        setSocketTimeout(TIME_OUT).
                        build();
        // 配置超时回调机制
        HttpRequestRetryHandler retryHandler = (exception, executionCount, context) -> {
            // 如果已经重试了3次，就放弃
            if (executionCount >= MAX_COUNT) {
                return false;
            }
            // 如果服务器丢掉了连接，那么就重试
            if (exception instanceof NoHttpResponseException) {
                return true;
            }
            // 不要重试SSL握手异常
            if (exception instanceof SSLHandshakeException) {
                return false;
            }
            // 超时
            if (exception instanceof InterruptedIOException) {
                return true;
            }
            // 目标服务器不可达
            if (exception instanceof UnknownHostException) {
                return false;
            }
            // ssl握手异常
            if (exception instanceof SSLException) {
                return false;
            }
            HttpClientContext clientContext = HttpClientContext.adapt(context);
            HttpRequest request = clientContext.getRequest();
            // 如果请求是幂等的，就再次尝试
            if (!(request instanceof HttpEntityEnclosingRequest)) {
                return true;
            }
            return false;
        };

        return HttpClients.custom()
                .setConnectionManager(connManager)
                .setDefaultRequestConfig(requestConfig)
                .setRetryHandler(retryHandler)
                .build();
    }

    /**
     * 获取SSL上下文对象,用来构建SSL Socket连接
     * @return SSLContext
     * @throws KeyManagementException KeyManagementException
     * @throws NoSuchAlgorithmException NoSuchAlgorithmException
     */
    private static SSLContext getSSLContext() throws KeyManagementException, NoSuchAlgorithmException {
        SSLContext sslContext = SSLContext.getInstance("SSLv3");
        X509TrustManager x509TrustManager = new X509TrustManager() {
            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }
            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            }
            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            }
        };
        sslContext.init(null, new TrustManager[] {x509TrustManager}, null);

        return sslContext;
    }

    /**
     * post请求,支持SSL
     *
     * @param url 请求地址
     * @param headers 请求头信息
     * @param jsonParams 请求参数
     * @return 响应信息
     */
    public String httpPost(String url, String jsonParams, Map<String, String> headers) {
        // 创建post请求
        HttpPost httpPost = new HttpPost(url);

        // 添加请求头信息
        if (null != headers) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                httpPost.addHeader(entry.getKey(), entry.getValue().toString());
            }
        }

        // 添加请求参数信息
        httpPost.setHeader("Content-Type","application/json");
        HttpEntity entity = new StringEntity(jsonParams, ContentType.create("application/json", "utf-8"));
        httpPost.setEntity(entity);

        return getResult(httpPost);
    }


    /**
     * 表单方式请求数据
     * @param url url
     * @param contents contents
     * @param headers headers
     * @return string
     */
    public String httpForm(String url, Map<String, Object> contents, Map<String, String> headers) {
        // 创建post请求
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded");

        // 添加请求头信息
        if (null != headers) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                httpPost.addHeader(entry.getKey(), entry.getValue());
            }
        }

        // 设置传递内容
        List<NameValuePair> nameValuePairs = new ArrayList<>();
        if (contents != null && contents.size() > 0) {
            for (Map.Entry<String, Object> entry: contents.entrySet()) {
                nameValuePairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
            }
            try {
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                WriteLogsUtil.write(logPath, e.getMessage());
                return "";
            }
        }

        return getResult(httpPost);
    }


    /**
     * get请求,支持SSL
     * @param url 请求地址
     * @param headers 请求头信息
     * @return 响应信息
     */
    public String httpGet(String url, Map<String, Object> headers) {
        // 创建post请求
        HttpGet httpGet = new HttpGet(url);

        // 添加请求头信息
        if (null != headers) {
            for (Map.Entry<String, Object> entry : headers.entrySet()) {
                httpGet.addHeader(entry.getKey(), entry.getValue().toString());
            }
        }

        return getResult(httpGet);
    }

    /**
     * get请求,支持SSL
     * @param url 请求地址
     * @param headers 请求头信息
     * @return 响应信息
     */
    public String httpGetString(String url, Map<String, String> headers) {
        // 创建post请求
        HttpGet httpGet = new HttpGet(url);

        // 添加请求头信息
        if (null != headers) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                httpGet.addHeader(entry.getKey(), entry.getValue());
            }
        }

        return getResult(httpGet);
    }

    /**
     * get请求,支持SSL
     * @param url 请求地址
     * @return 响应信息
     */
    public InputStream httpImageStreamGet(String url) {
        // 创建post请求
        HttpGet httpGet = new HttpGet(url);
        return getUrlStream(httpGet);
    }


    /**
     * http请求
     * @param httpRequest httpRequest
     * @return string
     */
    private String getResult(HttpRequestBase httpRequest) {
        // 响应结果
        String result = null;
        CloseableHttpResponse response = null;
        try {
            // 获取连接客户端 发起请求
            CloseableHttpClient httpClient = getHttpClient();
            response = httpClient.execute(httpRequest);
            int responseCode = response.getStatusLine().getStatusCode();

            // 正确响应
            if (HttpStatus.SC_OK == responseCode) {
                // 获得响应实体
                HttpEntity entity = response.getEntity();
                result = EntityUtils.toString(entity, ENCODING);
            }else{
                WriteLogsUtil.write(logPath, "curl数据返回错误，错误码：" + responseCode );
            }
        } catch (Exception e) {
            WriteLogsUtil.write(logPath, "curl数据返回错误，错误码：" + e.getMessage() );
        } finally {
            if (null != response) {
                try {
                    response.close();
                } catch (IOException e) {
                    WriteLogsUtil.write(logPath, "curl关闭连接失败：" + e.getMessage() );
                }
            }
        }

        return result;
    }

    /**
     * http获取网络资源资料inputStream
     * @param httpRequest httpRequest
     * @return InputStream 非null
     */
    private InputStream getUrlStream(HttpRequestBase httpRequest) {
        // 响应结果
        CloseableHttpResponse response = null;
        InputStream inputStream = null;
        try {
            // 获取连接客户端 发起请求
            CloseableHttpClient httpClient = getHttpClient();
            response = httpClient.execute(httpRequest);
            int responseCode = response.getStatusLine().getStatusCode();

            // 正确响应
            if (HttpStatus.SC_OK == responseCode) {
                // 获得响应实体
                inputStream = response.getEntity().getContent();
            }else{
                WriteLogsUtil.write(logPath, "curl数据返回错误，错误码：" + responseCode );
            }
        } catch (Exception e) {
            WriteLogsUtil.write(logPath, "curl数据返回错误，错误码：" + e.getMessage() );
        } finally {
            if (null != response) {
                try {
                    response.close();
                } catch (IOException e) {
                    WriteLogsUtil.write(logPath, "curl关闭连接失败：" + e.getMessage() );
                }
            }
        }

        return inputStream;
    }


    /**
     * Map转换成NameValuePair List集合
     *
     * @param params map
     * @return NameValuePair List集合
     */
    private static List<NameValuePair> covertParams2NVPS(Map<String, Object> params) {

        List<NameValuePair> paramList = new LinkedList<>();
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            paramList.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
        }

        return paramList;
    }
}