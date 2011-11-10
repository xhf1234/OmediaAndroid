package org.tsinghua.omedia.form;


import android.os.AsyncTask;
import android.util.Log;

/**
 * 对表单进行处理
 * 异步执行
 * @author xuhongfeng
 *
 */
public abstract class FormProcessor<F extends AbstractForm> {
    protected F form;
    
    public FormProcessor(F form) {
        this.form = form;
    }

    public void exec() {
        new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... arg0) {
                try {
                    //前端验证
                    String validateMsg = form.validate();
                    if(validateMsg != null) {
                        //前端验证不通过
                        onValidateFailed(validateMsg);
                        return null;
                    }
                    //前端验证通过
                    onValidateSuccess();
                } catch (Exception e) {
                    //处理异常
                    onExceptionCatched(e);
                }
                return null;
            }
        }.execute();
    }
    
    /**
     * 处理异常
     * 默认行为是打log,子类可重写该方法
     * @param e
     */
    protected void onExceptionCatched(Exception e) {
        Log.e(FormProcessor.class.getSimpleName(), "Form Processor Exception", e);
    }
    
    /**
     * 表单验证通过的回调函数
     */
    protected abstract void onValidateSuccess();
    
    /**
     * 表单验证失败的回调函数
     * @param msg
     */
    protected abstract void onValidateFailed(String msg);
}
