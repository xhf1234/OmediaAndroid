package org.tsinghua.omedia.service;

import java.io.IOException;

import org.tsinghua.omedia.consts.OmediaConst;
import org.tsinghua.omedia.consts.UrlConst;
import org.tsinghua.omedia.form.LoginForm;
import org.tsinghua.omedia.tool.HttpExecutor;

/**
 * 封装HTTP请求相关的逻辑
 * @author xuhongfeng
 *
 */
public class HttpService extends BaseService {
    private static HttpService me;
    
    //singleton
    private HttpService(){}
    
    public static HttpService getInstance() {
        if(me != null) return me;
        synchronized (HttpService.class) {
            if(me == null) {
                me = new HttpService();
            }
        }
        return me;
    }
    
    public String login(LoginForm form) throws IOException {
        return HttpExecutor.httpGet(UrlConst.LoginUrl)
                .addParam("username", form.getUsername())
                .addParam("password", form.getPassword())
                .addParam("omediaVersion", OmediaConst.OmediaVersion)
                .exec();
    }
}
