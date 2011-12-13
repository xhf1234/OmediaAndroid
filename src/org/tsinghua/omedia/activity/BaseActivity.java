package org.tsinghua.omedia.activity;

import org.tsinghua.omedia.OmediaApplication;
import org.tsinghua.omedia.consts.ActionConst;
import org.tsinghua.omedia.datasource.DataSource;
import org.tsinghua.omedia.ui.dialog.AlertDialogFragment;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;

/**
 * 
 * @author xuhongfeng
 *
 */
public class BaseActivity extends Activity {
    protected OmediaApplication omedia = OmediaApplication.getInstance();
    protected DataSource dataSource = DataSource.getInstance();
    
    public void showAlertDialog(String message) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag("alertDialog");
        if(prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        DialogFragment dialog = new AlertDialogFragment(message);
        dialog.show(ft, "alertDialog");
    }
    public void showAlertDialog(int stringId) {
        String message = getResources().getString(stringId);
        showAlertDialog(message);
    }
    
    /**
     * Account Token 错误
     */
    protected void tokenWrong() {
        Intent intent = new Intent(this, LandingActivity.class);
        intent.setAction(ActionConst.ACTION_TOKEN_WRONG);
        startActivity(intent);
    }
}
