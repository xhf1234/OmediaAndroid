package org.tsinghua.omedia.form;


import java.net.SocketTimeoutException;
import java.util.Map;

import org.tsinghua.omedia.R;
import org.tsinghua.omedia.activity.OmediaActivityIntf;
import org.tsinghua.omedia.consts.ResultCode;
import org.tsinghua.omedia.data.JsonObject;
import org.tsinghua.omedia.tool.JsonUtils;
import org.tsinghua.omedia.tool.Logger;

import android.os.AsyncTask;

/**
 * 对表单进行处理
 * 异步执行
 * @author xuhongfeng
 *
 */
public abstract class FormProcessor<F extends AbstractForm> {
    private static final Logger logger = Logger.getLogger(FormProcessor.class);
    
    private static final int RESULT_OK = 1;
    private static final int RESULT_VALIDATE_FAILED = RESULT_OK+1;
    private static final int RESULT_EXCEPTION = RESULT_VALIDATE_FAILED+1;

    //表单
    protected F form;
    private OmediaActivityIntf activity;
    private JsonObject result;
    private int resultCode;
    
    private String validateMsg;
    private Throwable throwable;
    
    public FormProcessor(OmediaActivityIntf activity, F form) {
        this.form = form;
        this.activity = activity;
    }

    public void exec() {
        new AsyncTask<Void, Void, Integer>(){
            
            @Override
            protected void onPostExecute(Integer r) {
                switch(r) {
                case RESULT_VALIDATE_FAILED:
                    onValidateFailed(validateMsg);
                    break;
                case RESULT_OK:
                    if(resultCode == ResultCode.SERVER_ERROR) {
                        activity.showAlertDialog(R.string.server_error);
                    } else {
                        onProcessSuccess(result, resultCode);
                    }
                    break;
                case RESULT_EXCEPTION:
                    onExceptionCatched(throwable);
                    break;
                default :
                    logger.error("unknow result code");
                }
            }

            @Override
            protected Integer doInBackground(Void... arg0) {
                try {
                    //前端验证
                    validateMsg = form.validate();
                    if(validateMsg != null) {
                        //前端验证不通过
                        return RESULT_VALIDATE_FAILED;
                    }
                    //前端验证通过
                    String jsonResult = onProcessForm(form);
                    Map<String, Object> values = JsonUtils.read(jsonResult);
                    result = new JsonObject(values);
                    resultCode = result.getInt("result");
                } catch (Exception e) {
                    //处理异常
                    throwable = e;
                    return RESULT_EXCEPTION;
                }
                return RESULT_OK;
            }
        }.execute();
    }
    
    /**
     * 处理异常
     * 在主线程中执行
     * @param e
     */
    protected void onExceptionCatched(Throwable e) {
        logger.error("Form Processor Exception", e);
        if(e instanceof SocketTimeoutException) {
            activity.showAlertDialog(R.string.error_conn_timeout);
        } else {
            activity.showAlertDialog(e.getMessage());
        }
    }
    
    /**
     * 对表单进行逻辑处理
     * 在异步线程中执行
     */
    protected abstract String onProcessForm(F form) throws Exception;
    
    /**
     * 响应表单的处理结果
     * 在主线程中执行
     * @param result
     */
    protected abstract void onProcessSuccess(JsonObject result, int resultCode);
    
    /**
     * 表单验证失败的回调函数
     * 在主线程中执行
     * @param msg
     */
    protected void onValidateFailed(String msg) {
        activity.showAlertDialog(msg);
    }
}
