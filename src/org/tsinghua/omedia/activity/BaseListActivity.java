package org.tsinghua.omedia.activity;

import java.io.File;

import android.app.ListActivity;

public class BaseListActivity extends ListActivity implements OmediaActivityIntf {

    public void showAlertDialog(String message) {
        OmediaActivityDelegate.showAlertDialog(message, this);
    }
    public void showAlertDialog(int stringId) {
        OmediaActivityDelegate.showAlertDialog(stringId, this);
    }
    
    public void tokenWrong() {
        OmediaActivityDelegate.tokenWrong(this);
    }

    @Override
    public void openFile(File file) {
        OmediaActivityDelegate.openFile(file, this);
    }
}
