package org.tsinghua.omedia.activity;

import java.io.File;

import org.tsinghua.omedia.R;
import org.tsinghua.omedia.event.CcnFilesUpdateEvent;
import org.tsinghua.omedia.event.Event;
import org.tsinghua.omedia.tool.Logger;
import org.tsinghua.omedia.worker.CcnDownloadWorker;
import org.tsinghua.omedia.worker.MultipartWorker;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 
 * @author xuhongfeng
 *
 */
public class CcnActivity extends BaseActivity {
    private static final Logger logger = Logger.getLogger(BaseActivity.class);
    
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ccn_activity);
        listView = (ListView) findViewById(R.id.listview);
        initListener();
    }

    private void initListener() {
    }
    
    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.ccn_activity_menu, menu);
        return super.onCreateOptionsMenu(menu);

    }

    private void updateUI() {
        String[] files = new String[dataSource.getCcnFiles().length];
        for(int i=0; i<files.length; i++) {
            files[i] = dataSource.getCcnFiles()[i].getCcnname();
        }
        listView.setAdapter(new CcnListAdapter(this, files));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.select_file) {
            Intent intent = new Intent(CcnActivity.this,
                    FileBrowerAcitvity.class);
            startActivityForResult(intent, REQUEST_SELECT_FILE);
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onEventCatch(Event event) {
        if(event instanceof CcnFilesUpdateEvent) {
            updateUI();
            dissmissWaitingDialog();
        }
        super.onEventCatch(event);
    }


    private class CcnListAdapter extends ArrayAdapter<String> {
        private LayoutInflater inflater;
        private String[] files;

        public CcnListAdapter(Activity context, String[] files) {
            super(context, R.layout.ccn_list_view_item, files);
            this.files = files;
            inflater = context.getLayoutInflater();
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if(convertView == null) {
                convertView = inflater.inflate(R.layout.ccn_list_view_item, parent, false);
            }
            TextView textView = (TextView) convertView.findViewById(R.id.ccn_file_name);
            textView.setText(files[position]);
            convertView.setOnClickListener(new OnClickListener() {
                
                @Override
                public void onClick(View v) {
                    String ccnFile = files[position];
                    CcnDownloadWorker worker = new CcnDownloadWorker(ccnFile) {
                        @Override
                        protected void onSuccess(File file) {
                            dissmissWaitingDialog();
                            openFile(file);
                        }
                        
                        @Override
                        protected void onFailed(Exception e) {
                            dissmissWaitingDialog();
                            logger.error(e);
                        }
                    };
                    showWaitingDialog();
                    runWorker(worker);
                }
            });
            return convertView;
        }

    }


    private static final int REQUEST_SELECT_FILE = 1;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if(requestCode == REQUEST_SELECT_FILE && resultCode==RESULT_OK) {
            Uri uri = intent.getData();
            ccnPutFile(uri);
        } else {
            super.onActivityResult(requestCode, resultCode, intent);
        }
    }
    
    private void ccnPutFile(Uri fileUri) {
        File file = new File(fileUri.getPath());
        MultipartWorker worker = new MultipartWorker(file, file.getName()) {

            @Override
            protected void onSuccess() {
                dissmissWaitingDialog();
                checkDataUpdate();
            }

            @Override
            protected void onFailed(Exception e) {
                dissmissWaitingDialog();
                logger.error(e);
            }
        };
        showWaitingDialog();
        runWorker(worker);
    }
    
}
