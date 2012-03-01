package org.tsinghua.omedia.activity;

import java.io.File;

import org.tsinghua.omedia.consts.ActionConst;
import org.tsinghua.omedia.tool.FileUtils;
import org.tsinghua.omedia.ui.dialog.AlertDialogFragment;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

/**
 * 
 * @author xuhongfeng
 *
 */
public class OmediaActivityDelegate {
	
    public static void showAlertDialog(String message, BaseActivity activity) {
        FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
        Fragment prev = activity.getSupportFragmentManager().findFragmentByTag("alertDialog");
        if(prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        DialogFragment dialog = new AlertDialogFragment(message);
        dialog.show(ft, "alertDialog");
    }
    public static void showAlertDialog(int stringId, BaseActivity activity) {
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

    /**
     * 打开文件
     * @param file
     */
    public static void openFile(File file, Activity activity){
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //设置intent的Action属性
        intent.setAction(Intent.ACTION_VIEW);
        //获取文件file的MIME类型
        String type = FileUtils.getMIMEType(file);
        //设置intent的data和Type属性。
        intent.setDataAndType(/*uri*/Uri.fromFile(file), type);
        //跳转
        activity.startActivity(intent);    
    }
}
