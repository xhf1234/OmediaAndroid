package org.tsinghua.omedia.activity;

import org.tsinghua.omedia.R;
import org.tsinghua.omedia.event.Event;
import org.tsinghua.omedia.event.FriendRequestUpdateEvent;
import org.tsinghua.omedia.worker.WorkerManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * 
 * @author xuhongfeng
 *
 */
public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        initListener();
    }
    
    @Override
    public void onResume() {
        updateUI();
        WorkerManager.getInstance().startWorkers();
        super.onResume();
    }

    private void updateUI() {
        int count = dataSource.getFriendRequests().length;
        String text = getResources().getString(R.string.btn_notice)
                .replace("${count}", count+"");
        Button noticeBtn = (Button) findViewById(R.id.btn_notice);
        noticeBtn.setText(text);
    }

    private void initListener() {
        findViewById(R.id.btn_ccn).setOnClickListener(new OnClickListener() {
            
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CcnActivity.class);
                startActivity(intent);
            }
        });
        
        findViewById(R.id.btn_friends).setOnClickListener(new OnClickListener() {
            
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FriendsActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onEventCatch(Event event) {
        if(event instanceof FriendRequestUpdateEvent) {
            updateUI();
        }
        super.onEventCatch(event);
    }
}
