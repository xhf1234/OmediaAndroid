package org.tsinghua.omedia.tool;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;

import android.net.http.AndroidHttpClient;

/**
 * 封装了HTTP的GET操作和POST操作
 * sample :
 * 
        try {
            String json = HttpExecutor.httpGet("http://166.111.137.72:10086/omedis/login.do")
                    .addParam("username", "xuhongfeng")
                    .addParam("password", "123456")
                    .exec();
        } catch (IOException e) {
            //处理异常
        }
 *
 * @author xuhongfeng
 */
public class HttpExecutor {
    private static Logger logger = Logger.getLogger(HttpExecutor.class);
    
    private static enum METHOD_TYPE {POST, GET};
    
    private String url;
    private METHOD_TYPE type;
    private Map<String,String> params = new HashMap<String, String>();
    
    private HttpExecutor(){};
    
    public static HttpExecutor httpGet(String url) {
        HttpExecutor executor = new HttpExecutor();
        executor.url = url;
        executor.type = METHOD_TYPE.GET;
        return executor;
    }
    
    public static HttpExecutor httpPost(String url) {
        HttpExecutor executor = new HttpExecutor();
        executor.url = URLEncoder.encode(url);
        executor.type = METHOD_TYPE.POST;
        return executor;
    }
    
    public HttpExecutor addParam(String key, String value) {
        params.put(URLEncoder.encode(key), URLEncoder.encode(value));
        return this;
    }
    public HttpExecutor addParam(String key, long value) {
        return addParam(key, String.valueOf(value));
    }
    
    public String exec() throws IOException {
        switch(type) {
        case GET :
            return new String(innerHttpGet().getBytes("ISO-8859-1"), "utf8");
        case POST :
            return new String(innerHttpPost().getBytes("ISO-8859-1"), "utf8");
        default :  throw new IOException("unknow http type");
        }
    }
    
    private String innerHttpGet() throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append(url);
        Set<String> keys = params.keySet();
        boolean isFirst = true;
        for(String key:keys) {
            if(isFirst) {
                sb.append("?");
                isFirst = false;
            } else {
                sb.append("&");
            }
            sb.append(key);
            sb.append("=");
            sb.append(params.get(key));
        }
        String uri = sb.toString();
        logger.info("http get,uri = " + uri);
        HttpGet request = new HttpGet(uri);
        AndroidHttpClient client = AndroidHttpClient.newInstance("apache-client");
        try {
            HttpResponse response = client.execute(request);
            if(response.getStatusLine().getStatusCode()  != HttpStatus.SC_OK) {
                throw new IOException("http status code = " + response.getStatusLine().getStatusCode());
            }
            return IOUtils.toString(response.getEntity().getContent());
        } finally {
            client.close();
        }
    }

    private String innerHttpPost() throws IOException {
        HttpPost request = new HttpPost(url);
        List<NameValuePair> datas = new ArrayList<NameValuePair>();
        Set<String> keys = params.keySet();
        for(String key:keys) {
            String value = params.get(key);
            datas.add(new BasicNameValuePair(key,value));
        }
        request.setEntity(new UrlEncodedFormEntity(datas));
        AndroidHttpClient client = AndroidHttpClient.newInstance("apache-client");
        try {
            HttpResponse response = client.execute(request);
            return IOUtils.toString(response.getEntity().getContent());
        } finally {
            client.close();
        }
    }
}
