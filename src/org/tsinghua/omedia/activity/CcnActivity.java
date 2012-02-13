package org.tsinghua.omedia.activity;

import org.tsinghua.omedia.R;
import org.tsinghua.omedia.event.CcnFilesUpdateEvent;
import org.tsinghua.omedia.event.Event;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * 
 * @author xuhongfeng
 *
 */
public class CcnActivity extends BaseActivity {
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ccn_activity);
        listView = (ListView) findViewById(R.id.filelist);
        initListener();
    }

    private void initListener() {
        findViewById(R.id.selectfile_button).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CcnActivity.this, FileBrowerAcitvity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    private void updateUI() {
        //TODO for FuYe
        String[] files = new String[dataSource.getCcnFiles().length];
        for(int i=0; i<files.length; i++) {
            files[i] = dataSource.getCcnFiles()[i].getCcnname();
        }
        listView.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, files));
    }

    @Override
    public void onEventCatch(Event event) {
        if(event instanceof CcnFilesUpdateEvent) {
            updateUI();
        }
        super.onEventCatch(event);
    }

    
}
