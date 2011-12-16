package org.tsinghua.omedia.activity;

import org.tsinghua.omedia.R;
import org.tsinghua.omedia.datasource.DataSource;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CcnActivity extends ListActivity implements OmediaActivityIntf {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setListAdapter(new CcnListAdapter(this));
    }

    private class CcnListAdapter extends ArrayAdapter<String> {
        private LayoutInflater inflater;

        public CcnListAdapter(Activity context) {
            super(context, R.layout.ccn_list_view_item, DataSource.getInstance().getCcnFiles());
            inflater = context.getLayoutInflater();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView == null) {
                convertView = inflater.inflate(R.layout.ccn_list_view_item, parent, false);
            }
            TextView textView = (TextView) convertView.findViewById(R.id.ccn_file_name);
            textView.setText(DataSource.getInstance().getCcnFiles()[position]);
            return convertView;
        }

    }

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
