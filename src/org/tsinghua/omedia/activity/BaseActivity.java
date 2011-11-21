package org.tsinghua.omedia.activity;

import org.tsinghua.omedia.OmediaApplication;
import org.tsinghua.omedia.datasource.DataSource;
import org.tsinghua.omedia.ui.dialog.AlertDialogFragment;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;

/**
 * 
 * @author xuhongfeng
 *
 */
public class BaseActivity extends Activity {
    protected OmediaApplication omedia = OmediaApplication.getInstance();
    protected DataSource dataSource = omedia.getDatasource();
    
    protected void showAlertDialog(String message) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag("alertDialog");
        if(prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        DialogFragment dialog = new AlertDialogFragment(message);
        dialog.show(ft, "alertDialog");
    }
}
