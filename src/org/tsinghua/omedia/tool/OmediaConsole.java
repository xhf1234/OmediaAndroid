package org.tsinghua.omedia.tool;

import java.io.File;

import org.tsinghua.omedia.OmediaApplication;
import org.tsinghua.omedia.R;
import org.tsinghua.omedia.activity.OmediaActivityIntf;
import org.tsinghua.omedia.event.Event;

import android.widget.Toast;

/**
 * 
 * @author xuhongfeng
 *
 */
public class OmediaConsole implements OmediaActivityIntf {

    @Override
    public void showAlertDialog(String message) {
        Toast.makeText(OmediaApplication.getInstance()
                .getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showAlertDialog(int stringId) {
        String message = ResourceUtils.getString(stringId);
        showAlertDialog(message);
    }

    @Override
    public void tokenWrong() {
        showAlertDialog(R.string.token_wrong);
    }

    @Override
    public void onEventCatch(Event event) {
        
    }
    
    @Override
    public void openFile(File file) {
        throw new UnsupportedOperationException();
    }

}
