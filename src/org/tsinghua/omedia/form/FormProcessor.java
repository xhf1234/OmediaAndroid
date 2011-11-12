package org.tsinghua.omedia.form;


import org.tsinghua.omedia.tool.Logger;

import android.os.AsyncTask;

/**
 * 对表单进行处理
 * 异步执行
 * @author xuhongfeng
 *
 */
public abstract class FormProcessor<F extends AbstractForm, T> {
    private static final Logger logger = Logger.getLogger(FormProcessor.class);
    
    private static final int RESULT_OK = 1;
    private static final int RESULT_VALIDATE_FAILED = RESULT_OK+1;
    private static final int RESULT_EXCEPTION = RESULT_VALIDATE_FAILED+1;

    //表单
    protected F form;
    
    private String validateMsg;
    private Throwable throwable;
    private T result;
    
    public FormProcessor(F form) {
        this.form = form;
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
                    onProcessSuccess(result);
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
                    result = onProcessForm(form);
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
     * 默认行为是打log,子类可重写该方法
     * 在主线程中执行
     * @param e
     */
    protected void onExceptionCatched(Throwable e) {
        logger.error("Form Processor Exception", e);
    }
    
    /**
     * 对表单进行逻辑处理
     * 在异步线程中执行
     */
    protected abstract T onProcessForm(F form) throws Exception;
    
    /**
     * 响应表单的处理结果
     * 在主线程中执行
     * @param result
     */
    protected abstract void onProcessSuccess(T result);
    
    /**
     * 表单验证失败的回调函数
     * 在主线程中执行
     * @param msg
     */
    protected abstract void onValidateFailed(String msg);
}
