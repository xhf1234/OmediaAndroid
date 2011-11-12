package org.tsinghua.omedia.ui.dialog;

import org.tsinghua.omedia.OmediaApplication;
import org.tsinghua.omedia.R;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;

/**
 * 
 * @author xuhongfeng
 *
 */
public class AlertDialogFragment extends DialogFragment {
    private static String alert_dialog_ok = OmediaApplication.getInstance()
            .getString(R.string.alert_dialog_ok);
    
    private ClickCallback clickCallback;
    
    public interface ClickCallback {
        public void exec();
    }

    public AlertDialogFragment(String message) {
        this(message,alert_dialog_ok);
    }
    
    public AlertDialogFragment(String message, String buttonLabel) {
        Bundle args = new Bundle();
        args.putString("message", message);
        args.putString("buttonLabel", buttonLabel);
        setArguments(args);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String message = getArguments().getString("message");
        String buttonLabel = getArguments().getString("buttonLabel");
        return new AlertDialog.Builder(getActivity())
            .setMessage(message)
            .setPositiveButton(buttonLabel, new OnClickListener(){
                public void onClick(DialogInterface dialog, int which) {
                    if(clickCallback != null) {
                        clickCallback.exec();
                    }
                    AlertDialogFragment.this.dismiss();
                }
                
            }).create();
    }

    public AlertDialogFragment setClickCallback(ClickCallback clickCallback) {
        this.clickCallback = clickCallback;
        return this;
    }
}
