package main.http;

import main.facad.Windows;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.http.*;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 网络请求(Get,Post)工具类
 *
 * @Author: Mr.Han
 * @Description:
 * @Date: Create  on  15:26 2018/12/18
 * @Version: v 0.1
 */
public class HttpTool {

    public static final String COOKIE = "cookies";

    public static final String RESP = "response";

    /**
     * 发送Post请求
     *
     * @param url          请求url地址,需要https 或 http 开头指定协议
     * @param stringEntity 字符串格式参数
     * @return 数据包装类RespData
     */
    public static RespData post(String url, String stringEntity, int readTimeOut) {
        HttpPost httpPost = new HttpPost(url);
        CookieStore cookieStore = new BasicCookieStore();
        CloseableHttpClient httpclient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
        RespData vo = null;
        try {
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectionRequestTimeout(5000).setConnectTimeout(2500)
                    .setSocketTimeout(readTimeOut).build();
            httpPost.setConfig(requestConfig);
            HttpEntity entity;
            entity = new StringEntity(stringEntity, Consts.UTF_8);
            httpPost.setEntity(entity);
            HttpResponse httpResponse = httpclient.execute(httpPost);
            vo = new RespData(responseConvert(httpResponse), cookieStore.getCookies());
            vo.setCookies(cookieStore.getCookies());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            httpPost.abort();
        }
        return vo;
    }

    /**
     * 发送Post请求
     *
     * @param url    请求url地址,需要https 或 http 开头指定协议
     * @param params 请求参数的Map集合,键值都要为String
     * @return 数据包装类RespData
     */
    public static RespData post(String url, Map<String, String> params, int readTimeOut) {
        HttpPost httpPost = new HttpPost(url);
        CookieStore cookieStore = new BasicCookieStore();
        CloseableHttpClient httpclient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
        RespData vo = null;
        try {
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectionRequestTimeout(5000).setConnectTimeout(2500)
                    .setSocketTimeout(readTimeOut).build();
            httpPost.setConfig(requestConfig);
            List<NameValuePair> formparams = new ArrayList<NameValuePair>();
            Set<String> key = params.keySet();
            for (String k : key) {
                formparams.add(new BasicNameValuePair(k, params.get(k)));
            }
            UrlEncodedFormEntity entity;
            entity = new UrlEncodedFormEntity(formparams, Consts.UTF_8);
            httpPost.setEntity(entity);
            HttpResponse httpResponse = httpclient.execute(httpPost);
            vo = new RespData(responseConvert(httpResponse), cookieStore.getCookies());
            vo.setCookies(cookieStore.getCookies());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            httpPost.abort();
        }
        return vo;
    }

    /**
     * 发送Get请求
     *
     * @param url         请求url地址,需要https 或 http 开头指定协议
     * @param inputParams 请求参数的Map集合,键值都要为String
     * @param readTimeOut 超时时间
     * @return 数据包装类RespData
     * @throws IOException
     * @throws HttpException
     */
    public static RespData get(String url, Map<String, String> inputParams,
                               int readTimeOut) throws IOException, HttpException {
        CookieStore cookieStore = new BasicCookieStore();
        CloseableHttpClient httpclient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
        RespData vo = null;
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        Set<String> set = inputParams.keySet();
        for (String s : set) {
            params.add(new BasicNameValuePair(s, inputParams.get(s)));
        }
        url = url + "?" + URLEncodedUtils.format(params, "UTF-8");
        HttpGet httpGet = new HttpGet(url);
        try {
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectionRequestTimeout(5000).setConnectTimeout(2500)
                    .setSocketTimeout(readTimeOut).build();
            httpGet.setConfig(requestConfig);
            HttpResponse httpResponse = httpclient.execute(httpGet);
            vo = new RespData(responseConvert(httpResponse), cookieStore.getCookies());
            vo.setCookies(cookieStore.getCookies());
            return vo;
        } catch (HttpException e) {
            throw e;
        } finally {
            httpGet.abort();
        }
    }

    /**
     * 发送Get请求
     *
     * @param url         请求url地址,需要https 或 http 开头指定协议
     * @param inputParams 请求参数的Map集合,键值都要为String
     * @param header      请求头部header集合,键值都要为String
     * @param readTimeOut 超时时间
     * @return 数据包装类RespData
     * @throws IOException
     * @throws HttpException
     */
    public static RespData get(String url, Map<String, String> inputParams, Map<String, String> header,
                               int readTimeOut) throws IOException, HttpException {
        CookieStore cookieStore = new BasicCookieStore();
        CloseableHttpClient httpclient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
        RespData vo = null;
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        Set<String> set = inputParams.keySet();
        for (String s : set) {
            params.add(new BasicNameValuePair(s, inputParams.get(s)));
        }
        url = url + "?" + URLEncodedUtils.format(params, "UTF-8");
        HttpGet httpGet = new HttpGet(url);
        try {
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectionRequestTimeout(5000).setConnectTimeout(2500)
                    .setSocketTimeout(readTimeOut).build();
            httpGet.setConfig(requestConfig);

            for (Map.Entry<String, String> entry : header.entrySet()) {
                httpGet.addHeader(entry.getKey(), entry.getValue());
            }
            HttpResponse httpResponse = httpclient.execute(httpGet);
            vo = new RespData(responseConvert(httpResponse), cookieStore.getCookies());
            vo.setCookies(cookieStore.getCookies());
            return vo;
        } catch (HttpException e) {
            throw e;
        } finally {
            httpGet.abort();
        }
    }

    /**
     * 解析返回Response
     *
     * @param httpResponse
     * @return
     * @throws IOException
     * @throws HttpException
     */
    private static String responseConvert(HttpResponse httpResponse) throws IOException, HttpException {
        String result = "";
        HttpEntity entity = httpResponse.getEntity();
        if (entity != null) {
            byte[] content = EntityUtils.toByteArray(entity);
            ContentType contentType = ContentType.getOrDefault(entity);
            Charset charset = contentType.getCharset();
            if (charset == null) {
                charset = HTTP.DEF_CONTENT_CHARSET;
            }
            result = new String(content, charset.name());
        }
        int statusCode = httpResponse.getStatusLine().getStatusCode();
        if (statusCode != HttpStatus.SC_OK) {
            Windows.warn("Request dont't success ! responseStatus = " + statusCode);
            throw new HttpException();
        }
        return result;
    }

    public static class RespData {
        private String response;
        private List<Cookie> cookies;

        public RespData() {
        }

        public RespData(String response, List<Cookie> cookies) {
            this.response = response;
            this.cookies = cookies;
        }

        public String getResponse() {
            return response;
        }

        public void setResponse(String response) {
            this.response = response;
        }

        public List<Cookie> getCookies() {
            return cookies;
        }

        public void setCookies(List<Cookie> cookies) {
            this.cookies = cookies;
        }
    }
}
