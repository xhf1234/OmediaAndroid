package org.tsinghua.omedia.activity;

import org.tsinghua.omedia.consts.ActionConst;
import org.tsinghua.omedia.ui.dialog.AlertDialogFragment;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;

public class OmediaActivityDelegate {
    public static void showAlertDialog(String message, Activity activity) {
        FragmentTransaction ft = activity.getFragmentManager().beginTransaction();
        Fragment prev = activity.getFragmentManager().findFragmentByTag("alertDialog");
        if(prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        DialogFragment dialog = new AlertDialogFragment(message);
        dialog.show(ft, "alertDialog");
    }
    public static void showAlertDialog(int stringId, Activity activity) {
        String message = activity.getResources().getString(stringId);
        showAlertDialog(message, activity);
    }
    
    /**
     * Account Token 错误
     */
    protected static void tokenWrong(Activity activity) {
        Intent intent = new Intent(activity, LandingActivity.class);
        intent.setAction(ActionConst.ACTION_TOKEN_WRONG);
        activity.startActivity(intent);
    }
}
