package org.tsinghua.omedia.serverAPI;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.tsinghua.omedia.activity.OmediaActivityIntf;
import org.tsinghua.omedia.annotation.form.HttpParam;
import org.tsinghua.omedia.consts.ResultCode;
import org.tsinghua.omedia.data.JsonObject;
import org.tsinghua.omedia.data.Jsonable;
import org.tsinghua.omedia.datasource.DataSource;
import org.tsinghua.omedia.form.AbstractForm;
import org.tsinghua.omedia.form.FormProcessor;
import org.tsinghua.omedia.tool.HttpExecutor;

/**
 * 
 * @author xuhongfeng
 *
 * @param <F>
 */
public abstract class AbstractServerAPI<F extends AbstractForm> implements ServerAPI {
    protected F form;
    protected OmediaActivityIntf omediaActivity;
    protected DataSource dataSource = DataSource.getInstance();
    //API 的 url
    private String url;
    
    private Map<Integer, ResultCodeListener<? extends Jsonable> > resultCodeListener =
            new HashMap<Integer, ResultCodeListener<? extends Jsonable> >();
    
    protected AbstractServerAPI(F form, OmediaActivityIntf omediaActivity) {
        this.form = form;
        this.url = getUrl();
        this.omediaActivity = omediaActivity;
        initResultCodeListener();
    }
    
    protected abstract String getUrl();

    @Override
    public void call() {
        new FormProcessor<F>(omediaActivity, form) {

            @SuppressWarnings("unchecked")
            @Override
            protected String onProcessForm(F form) throws Exception {
                HttpExecutor executor = HttpExecutor.httpGet(url);
                Class<? extends AbstractForm> clazz = form.getClass();
                while(true) {
                    Field[] fields = clazz.getDeclaredFields();
                    for(Field f:fields) {
                        f.setAccessible(true);
                        if(f.isAnnotationPresent(HttpParam.class)) {
                            HttpParam anno = f.getAnnotation(HttpParam.class);
                            String name = anno.name();
                            Object value = f.get(form);
                            executor.addParam(name, value.toString());
                        }
                    }
                    if(clazz.getSuperclass() == AbstractForm.class) {
                        break;
                    }
                    clazz = (Class<? extends AbstractForm>) clazz.getSuperclass();
                }
                return executor.exec();
            }

            @Override
            protected void onExceptionCatched(Throwable e) {
                onStop();
                super.onExceptionCatched(e);
            }



            @Override
            protected void onProcessSuccess(JsonObject jsonResult, int resultCode) {
                onStop();
                ResultCodeListener<? extends Jsonable> listener = resultCodeListener.get(resultCode);
                if(listener != null) {
                    listener.exec(jsonResult);
                } else if(resultCode == ResultCode.TOKEN_WRONG){
                    omediaActivity.tokenWrong();
                } else {
                    omediaActivity.showAlertDialog("unknow resultCode:"+resultCode);
                }
            }
        }.exec();
    }
    
    protected void onStop() {
        
    }
    
    /**
     * 注册一个resultCode的监听器。
     * resultCode是指ServerAPI的返回码
     * 当ServerAPI返回该API则执行该ResultCodeListener
     * @param resultCode
     * @param listener
     */
    protected void registerResultCodeListener(int resultCode, ResultCodeListener<? extends Jsonable> listener) {
        resultCodeListener.put(resultCode, listener);
    }
    
    /**
     * 初始化ResultCodeListener
     */
    protected abstract void initResultCodeListener();
}
