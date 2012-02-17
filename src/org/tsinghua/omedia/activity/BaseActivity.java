package org.tsinghua.omedia.activity;

import java.io.File;

import org.tsinghua.omedia.OmediaApplication;
import org.tsinghua.omedia.R;
import org.tsinghua.omedia.datasource.DataSource;
import org.tsinghua.omedia.event.Event;
import org.tsinghua.omedia.worker.CheckDataUpdateWorker;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;

/**
 * 
 * @author xuhongfeng
 *
 */
public class BaseActivity extends Activity implements OmediaActivityIntf {
    protected OmediaApplication omedia = OmediaApplication.getInstance();
    protected DataSource dataSource = DataSource.getInstance();
    
    protected static final int DIALOG_WAITING = 1;
    
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

	@Override
	protected Dialog onCreateDialog(int id, Bundle args) {
		switch(id) {
		case DIALOG_WAITING:
			ProgressDialog dialog = new ProgressDialog(this);
			dialog.setTitle(R.string.waiting);
			return dialog;
		default:
			return super.onCreateDialog(id, args);
		}
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
    
    protected interface RequestCode {
        public static int CCN_SELECT_FILE = 1;
    }
}
