package org.tsinghua.omedia.activity;

import java.io.File;

import org.tsinghua.omedia.OmediaApplication;
import org.tsinghua.omedia.R;
import org.tsinghua.omedia.datasource.DataSource;
import org.tsinghua.omedia.event.Event;
import org.tsinghua.omedia.worker.CheckDataUpdateWorker;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

/**
 * 
 * @author xuhongfeng
 *
 */
public class BaseActivity extends FragmentActivity implements OmediaActivityIntf {
    protected OmediaApplication omedia = OmediaApplication.getInstance();
    protected DataSource dataSource = DataSource.getInstance();
    
    private static final int DIALOG_WAITING = 1;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    public void showAlertDialog(String message) {
        OmediaActivityDelegate.showAlertDialog(message, this);
    }
    public void showAlertDialog(int stringId) {
        OmediaActivityDelegate.showAlertDialog(stringId, this);
    }
    
    /**
     * Account Token 错误
     */
    public void tokenWrong() {
        OmediaActivityDelegate.tokenWrong(this);
    }

    private ProgressDialog waitingDialog;

    @Override
    protected Dialog onCreateDialog(int id, Bundle args) {
        switch (id) {
        case DIALOG_WAITING:
            waitingDialog = new ProgressDialog(this);
            waitingDialog.setTitle(R.string.waiting);
            return waitingDialog;
        default:
            return super.onCreateDialog(id, args);
        }
    }
    
    protected void dissmissWaitingDialog() {
        if(waitingDialog != null && waitingDialog.isShowing()) {
            waitingDialog.dismiss();
        }
    }
    
    protected void showWaitingDialog() {
        showDialog(DIALOG_WAITING);
    }
    
    /**
     * 打开文件
     * @param file
     */
    public void openFile(File file){
        OmediaActivityDelegate.openFile(file, this);
    }
    
    public void onResume() {
        omedia.registerCurrentActivity(this);
        super.onResume();
    }
    
    @Override
    public void onEventCatch(Event event) {
        
    }
    
    protected void checkDataUpdate() {
    	new CheckDataUpdateWorker(1).start();
    }
}
