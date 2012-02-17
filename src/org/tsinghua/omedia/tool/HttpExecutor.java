package org.tsinghua.omedia.tool;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;

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
    
    private static enum METHOD_TYPE {POST, GET, MULTIPART};
    
    private String url;
    private METHOD_TYPE type;
    private Map<String,String> params = new HashMap<String, String>();
    private List<Part> multipart = new ArrayList<Part>();;
    
    private HttpExecutor(){};
    
    public static HttpExecutor httpGet(String url) {
        HttpExecutor executor = new HttpExecutor();
        executor.url = url;
        executor.type = METHOD_TYPE.GET;
        return executor;
    }
    
    public static HttpExecutor httpPost(String url) {
        HttpExecutor executor = new HttpExecutor();
        executor.url = url;
        executor.type = METHOD_TYPE.POST;
        return executor;
    }
    
    public static HttpExecutor postMultipart(String url) {
        HttpExecutor executor = new HttpExecutor();
        executor.url = url;
        executor.type = METHOD_TYPE.MULTIPART;
        return executor;
    }
    
    public HttpExecutor addParam(String key, String value) {
        if(this.type == METHOD_TYPE.MULTIPART) {
            StringPart part = new StringPart(key, value);
            multipart.add(part);
        } else {
            params.put(URLEncoder.encode(key), URLEncoder.encode(value));
        }
        return this;
    }
    public HttpExecutor addParam(String key, long value) {
        return addParam(key, String.valueOf(value));
    }
    
    public HttpExecutor addFilePart(String fileName, File file) throws FileNotFoundException {
        if(this.type != METHOD_TYPE.MULTIPART) {
            logger.error("not a multipart type");
            throw new RuntimeException();
        }
        FilePart part = new FilePart(fileName, file);
        multipart.add(part);
        return this;
    }
    
    public String exec() throws IOException {
        switch(type) {
        case GET :
            return new String(innerHttpGet().getBytes("ISO-8859-1"), "utf8");
        case POST :
            return new String(innerHttpPost().getBytes("ISO-8859-1"), "utf8");
        case MULTIPART:
            innerMultipart();
            return null;
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
        GetMethod request = new GetMethod(uri);
        HttpClient client = HttpClientFactory.getNewInstance();
        int statusCode = client.executeMethod(request);
        if(statusCode  != HttpStatus.SC_OK) {
            throw new IOException("http status code = " + statusCode);
        }
        return request.getResponseBodyAsString();
    }

    private String innerHttpPost() throws IOException {
        PostMethod post = new PostMethod(url);
        List<NameValuePair> datas = new ArrayList<NameValuePair>();
        Set<String> keys = params.keySet();
        for(String key:keys) {
            String value = params.get(key);
            datas.add(new NameValuePair(key,value));
        }
        post.addParameters(datas.toArray(new NameValuePair[0]));
        HttpClient client = HttpClientFactory.getNewInstance();
        client.executeMethod(post);
        return post.getResponseBodyAsString();
    }
    
    private void innerMultipart() throws IOException {
        Part[] parts = multipart.toArray(new Part[0]);
        PostMethod post = new PostMethod(url);
        MultipartRequestEntity entity = new MultipartRequestEntity(parts, post.getParams());
        post.setRequestEntity(entity);
        HttpClient client = HttpClientFactory.getNewInstance();
        client.executeMethod(post);
    }
}
