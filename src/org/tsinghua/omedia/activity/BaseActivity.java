package org.tsinghua.omedia.activity;

import org.tsinghua.omedia.OmediaApplication;
import org.tsinghua.omedia.datasource.DataSource;

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
    
}
