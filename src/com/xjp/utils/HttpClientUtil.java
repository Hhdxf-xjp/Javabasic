package com.xjp.utils;

import org.apache.commons.io.IOUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

import javax.net.ssl.SSLContext;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * http请求工具类
 * @author lilinbing
 * @version 2017年4月1日
 * @see HttpClientUtil
 * @since
 */
public class HttpClientUtil {
    /**
     * 说明：日志实例 功能：打印日志
     */
    private static Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);

    /**
     *
     * Description: <br>
     *  处理post请求
     * @param
     * @param
     * @return
     * @see
     */
    public static String doPost(String urlr){
        String body = null;
        try{
            // Configure and open a connection to the site you will send the request
            URL url = new URL(urlr);
            URLConnection urlConnection = url.openConnection();
            // 设置doOutput属性为true表示将使用此urlConnection写入数据
            urlConnection.setDoOutput(true);
            // 定义待写入数据的内容类型，我们设置为application/x-www-form-urlencoded类型
            urlConnection.setRequestProperty("content-type", "application/x-www-form-urlencoded");

            try(InputStream inputStream = urlConnection.getInputStream();){
                String encoding = urlConnection.getContentEncoding();
                body = IOUtils.toString(inputStream, encoding);

            }
        }catch(IOException e){
            e.printStackTrace();
        }
        return body;
    }

    /**
     *
     * Description: <br>
     *  处理post请求
     * @param url
     * @param param
     * @return
     * @see
     */
    public static String doPost(String url, Map<String, String> param) {
        String resultString = "";
        try {
            // 创建Http Post请求
            HttpPost httpPost = new HttpPost(url);
            // 创建参数列表
            if (param != null) {
                List<NameValuePair> paramList = new ArrayList<>();
                for (String key : param.keySet()) {
                    paramList.add(new BasicNameValuePair(key, param.get(key)));
                }
                // 模拟表单
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList,StandardCharsets.UTF_8.toString());
                httpPost.setEntity(entity);
            }
            try(CloseableHttpClient httpClient = HttpClients.createDefault();
                CloseableHttpResponse response = httpClient.execute(httpPost);){
                resultString = EntityUtils.toString(response.getEntity(),StandardCharsets.UTF_8.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultString;
    }

    /**
     *
     * Description: <br>
     *  处理get请求
     * @param url
     * @param param
     * @return
     * @see
     */
    public static String doGet(String url,String param) {
        String result = "";
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();

            try(InputStream inputStream = connection.getInputStream();){
                String encoding = connection.getContentEncoding();
                result = IOUtils.toString(inputStream, encoding);
            }
        } catch (Exception e) {
            logger.error("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        return result;
    }

    /**
     *
     * Description: <br>
     *  处理get请求
     * @param url
     * @param param
     * @return
     * @see
     */
    public static String doGet(String url, Map<String, String> param) {

        // 创建Httpclient对象


        String resultString = "";
        try {
            // 创建uri
            URIBuilder builder = new URIBuilder(url);
            if (param != null) {
                for (String key : param.keySet()) {
                    builder.addParameter(key, param.get(key));
                }
            }
            URI uri = builder.build();

            // 创建http GET请求
            HttpGet httpGet = new HttpGet(uri);

            try(CloseableHttpClient httpclient = HttpClients.createDefault();
                CloseableHttpResponse response = httpclient.execute(httpGet);){
                // 判断返回状态是否为200
                if (response.getStatusLine().getStatusCode() == 200) {
                    resultString = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8.toString());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultString;
    }


    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url
     *            发送请求的 URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, String param) {
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);

            /**
             * 获取URLConnection对象对应的输出流
             */
            try( PrintWriter out = new PrintWriter(conn.getOutputStream());
                    InputStreamReader is = new InputStreamReader(conn.getInputStream());
                    BufferedReader in = new BufferedReader(is);){
                // 发送请求参数
                out.print(param);
                // flush输出流的缓冲
                out.flush();

                String line;
                while ((line = in.readLine()) != null) {
                    result += line;
                }
            }
        } catch (Exception e) {
            logger.error("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        return result;
    }
    /**
     *
     * Description: <br>
     *  处理get请求
     * @param url
     * @param
     * @return
     * @see
     */
    public static String doGetWithSSl(String url,String host) {
        String resultString = "";
        try {
            // 创建uri
            URIBuilder builder = new URIBuilder(url);
            URI uri = builder.build();

            // 创建http GET请求
            HttpGet httpGet = new HttpGet(uri);

            httpGet.addHeader("Connection", "keep-alive");
            httpGet.addHeader("Accept", "*/*");
            httpGet.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            httpGet.addHeader("Host", host);
            httpGet.addHeader("X-Requested-With", "XMLHttpRequest");
            httpGet.addHeader("Cache-Control", "max-age=0");

            try(CloseableHttpClient httpclient = buildHttpClient(60*1000,60*1000);
                CloseableHttpResponse response = httpclient.execute(httpGet);){
                // 判断返回状态是否为200
                if (response.getStatusLine().getStatusCode() == 200) {
                    resultString = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8.toString());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultString;
    }
    public static String doPostJsonWithSSl(String url, String json,String host) {
        String resultString = "";
        try {
            // 创建Http Post请求
            HttpPost httpPost = new HttpPost(url);
            // 创建请求内容
            StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
            httpPost.setEntity(entity);

            httpPost.addHeader("Connection", "keep-alive");
            httpPost.addHeader("Accept", "*/*");
            httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            httpPost.addHeader("Host", host);
            httpPost.addHeader("X-Requested-With", "XMLHttpRequest");
            httpPost.addHeader("Cache-Control", "max-age=0");

            try(CloseableHttpClient httpclient = buildHttpClient(60*1000,60*1000);
                CloseableHttpResponse response = httpclient.execute(httpPost); ){
                resultString = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8.toString());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultString;
    }

//    public static String uploadFile(File file, String remoteUrl) {
//        String result = "";
//        try {
//            //String fileName = file.getOriginalFilename();
//            //String fileName = file.getName();
//            HttpPost httpPost = new HttpPost(remoteUrl);
//            /*MultipartEntityBuilder builder = MultipartEntityBuilder.create();
//            builder.addBinaryBody("file", new FileInputStream(file), ContentType.MULTIPART_FORM_DATA, file.getName());// 文件流
//            builder.addTextBody("filename", file.getName());// 类似浏览器表单提交，对应input的name和value
//            HttpEntity entity = builder.build();
//            httpPost.setEntity(entity);*/
//
//            FileBody bin = new FileBody(file);
//            // 相当于<input type="file" name="file"/>
//            HttpEntity reqEntity = MultipartEntityBuilder.create().addPart("media", bin).build();
//
//            httpPost.setEntity(reqEntity);
//
//            try(CloseableHttpClient httpClient = HttpClients.createDefault();
//                CloseableHttpResponse response = httpClient.execute(httpPost); ){
//                HttpEntity responseEntity = response.getEntity();
//                if (responseEntity != null) {
//                    // 将响应内容转换为字符串
//                    result = EntityUtils.toString(responseEntity, Charset.forName(StandardCharsets.UTF_8.toString()));
//                }
//            }
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return result;
//    }
    /**
     * ssl访问HttpClient
     * @param rqTimeout
     * @param rdTimeout
     * @return
     */
    public static CloseableHttpClient buildHttpClient(int rqTimeout, int rdTimeout) {
        CloseableHttpClient httpClient = null;
        try {
            HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
            requestFactory.setConnectionRequestTimeout(rqTimeout);
            requestFactory.setReadTimeout(rdTimeout);
            TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true;
            SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(null, acceptingTrustStrategy).build();
            SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext);
            httpClient = HttpClients.custom().setSSLSocketFactory(csf).build();
        } catch (KeyManagementException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (KeyStoreException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return httpClient;

    }
}
