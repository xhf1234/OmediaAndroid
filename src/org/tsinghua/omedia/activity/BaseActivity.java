package org.tsinghua.omedia.activity;

import java.io.File;

import org.tsinghua.omedia.OmediaApplication;
import org.tsinghua.omedia.R;
import org.tsinghua.omedia.datasource.DataSource;
import org.tsinghua.omedia.event.Event;
import org.tsinghua.omedia.worker.CheckDataUpdateWorker;
import org.tsinghua.omedia.worker.Worker;
import org.tsinghua.omedia.worker.WorkerManager;

import android.actionbar.compat.ActionBarActivity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

/**
 * 
 * @author xuhongfeng
 *
 */
public class BaseActivity extends ActionBarActivity implements OmediaActivityIntf {
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
            waitingDialog.setIndeterminate(true);
            return waitingDialog;
        default:
            return super.onCreateDialog(id, args);
        }
    }
    
    protected void dismissWaitingDialog() {
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
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case android.R.id.home:
            if(this.getClass()!=LandingActivity.class
                    && this.getClass()!=MainActivity.class
                    && this.getClass()!=RegisterActivity.class) {
                Intent intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
            break;
        }
        return super.onOptionsItemSelected(item);
    }
    
    @Override
    public void toast(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }
    
    @Override
    public void toast(int strId) {
        Toast.makeText(this, strId, Toast.LENGTH_SHORT).show();
    }
    
    protected void runWorker(Worker worker) {
        WorkerManager.getInstance().runWorker(worker);
    }
}
