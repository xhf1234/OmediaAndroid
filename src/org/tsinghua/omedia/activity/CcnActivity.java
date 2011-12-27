package org.tsinghua.omedia.activity;

import java.io.File;
import java.io.IOException;

import org.tsinghua.omedia.R;
import org.tsinghua.omedia.datasource.DataSource;
import org.tsinghua.omedia.datasource.sdcard.CcnFileDatasource;
import org.tsinghua.omedia.tool.Logger;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class CcnActivity extends BaseListActivity {
    
    private static Logger logger = Logger.getLogger(CcnActivity.class);
    
    private String[] ccnFiles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        ccnFiles = DataSource.getInstance().getCcnFiles();
        setListAdapter(new CcnListAdapter(this));
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        String ccnFile = ccnFiles[position];
        try {
            File file = CcnFileDatasource.getInstance().getCcnFile(ccnFile);
            openFile(file);
        } catch (IOException e) {
            logger.error(e);
        }
        super.onListItemClick(l, v, position, id);
    }

    private class CcnListAdapter extends ArrayAdapter<String> {
        private LayoutInflater inflater;

        public CcnListAdapter(Activity context) {
            super(context, R.layout.ccn_list_view_item, ccnFiles);
            inflater = context.getLayoutInflater();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView == null) {
                convertView = inflater.inflate(R.layout.ccn_list_view_item, parent, false);
            }
            TextView textView = (TextView) convertView.findViewById(R.id.ccn_file_name);
            textView.setText(ccnFiles[position]);
            return convertView;
        }

    }
}
