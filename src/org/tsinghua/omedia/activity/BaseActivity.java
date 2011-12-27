package org.tsinghua.omedia.activity;

import java.io.File;

import org.tsinghua.omedia.OmediaApplication;
import org.tsinghua.omedia.datasource.DataSource;
import org.tsinghua.omedia.event.Event;

import android.app.Activity;

/**
 * 
 * @author xuhongfeng
 *
 */
public class BaseActivity extends Activity implements OmediaActivityIntf {
    protected OmediaApplication omedia = OmediaApplication.getInstance();
    protected DataSource dataSource = DataSource.getInstance();
    
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
}
