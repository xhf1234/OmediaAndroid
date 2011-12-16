package org.tsinghua.omedia.activity;

import org.tsinghua.omedia.R;
import org.tsinghua.omedia.worker.WorkerManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        initListener();
    }
    
    @Override
    protected void onResume() {
        WorkerManager.getInstance().startWorkers();
        super.onResume();
    }



    private void initListener() {
        findViewById(R.id.btn_ccn).setOnClickListener(new OnClickListener() {
            
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CcnActivity.class);
                startActivity(intent);
            }
        });
    }
}
