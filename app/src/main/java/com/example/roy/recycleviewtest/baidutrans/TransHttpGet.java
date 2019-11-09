package com.example.roy.recycleviewtest.baidutrans;

import com.example.roy.recycleviewtest.util.LogUtil;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class TransHttpGet {
    protected static final int SOCKET_TIMEOUT=10000;//10s
    protected static final String GET="GET";

    public static String get(String host,Map<String,String > params) {
        //设置SSLContext
        SSLContext sslContext = null;
        try {
            sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new TrustManager[]{myX509TrustManager}, null);

            String sendUrl = getUrlWithQueryString(host, params);

            URL uri = null;//创建URL对象
            uri = new URL(sendUrl);

            HttpURLConnection conn = (HttpURLConnection) uri.openConnection();
            conn.setConnectTimeout(SOCKET_TIMEOUT);
            conn.setRequestMethod(GET);
            int statusCode = conn.getResponseCode();
            if (statusCode != 52000) {
                LogUtil.d("百度翻译返回错误码：" + statusCode);
            }
            //读取服务器的数据
            InputStream is = conn.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            StringBuilder builder = new StringBuilder();
            String line = null;
            while ((line = br.readLine()) != null) {
                builder.append(line);
            }
            String text = builder.toString();

            close(br);//关闭数据流
            close(is);//关闭数据流
            conn.disconnect();//断开连接
            return text;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }return null;
    }
    /**示例http://api.fanyi.baidu.com/api/trans/vip/translate?q=apple&from=en&to=zh&appid=2015063000000001&salt=1435660288&sign=f89f9594663708c1605f3d736d01d2d4
     * 如上拼接数据
     * @param url
     * @param params
     * @return
     */
    public static String getUrlWithQueryString(String url, Map<String,String> params){
        if (params==null)
            return url;
        StringBuilder builder=new StringBuilder(url);
        if (url.contains("?")){
            builder.append("&");
        }else
            builder.append("?");
        int i=0;
        for (String key:params.keySet()) {
            String value=params.get(key);
            if(value==null)
                continue;//过滤空的key,调出
            if(i!=0){
                builder.append('&');
            }
            builder.append(key);
            builder.append('=');
            builder.append(encode(value));
            i++;
        }
        return builder.toString();
    }
    protected static void close(Closeable closeable){
        if(closeable!=null){
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * 对输入的字符串进行URL编码, 即转换为%20这种形式  URLEncoder.encode()
     * @param input 原文
     * @return      URL编码. 如果编码失败, 则返回原文
     */
    public static String encode(String input){
        if(input==null)
            return "";
        try {
            return URLEncoder.encode(input,"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return input;
    }

    private static TrustManager myX509TrustManager=new X509TrustManager() {
        @Override
        public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

        }

        @Override
        public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }
    };
}
