package org.tsinghua.omedia.activity;

import org.tsinghua.omedia.R;
import org.tsinghua.omedia.datasource.DataSource;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class CcnActivity extends BaseActivity {

	private String[] ccnFiles ;
	ListView filelist;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ccn_activity);
		filelist = (ListView) findViewById(R.id.filelist);
		initdata();
		initListenner();
	}

	private void initListenner() {
		Button selectfile = (Button) findViewById(R.id.selectfile_button);
		selectfile.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent();
				i.setClass(CcnActivity.this, FileBrowerAcitvity.class);
				startActivity(i);
			}
		});
	}

	private void initdata() {
		ccnFiles = DataSource.getInstance().getCcnFiles();

		if (ccnFiles == null) {
			return;
		}

		filelist.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, ccnFiles));
	}

	private void show_waiting() {
		// TODO
	}
}
