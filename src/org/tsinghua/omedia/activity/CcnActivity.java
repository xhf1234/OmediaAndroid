package org.tsinghua.omedia.activity;

import java.io.File;

import org.tsinghua.omedia.R;
import org.tsinghua.omedia.event.CcnFilesUpdateEvent;
import org.tsinghua.omedia.event.Event;
import org.tsinghua.omedia.form.DeleteCcnFileForm;
import org.tsinghua.omedia.serverAPI.DeleteCcnFileAPI;
import org.tsinghua.omedia.tool.Logger;
import org.tsinghua.omedia.worker.CcnDownloadWorker;
import org.tsinghua.omedia.worker.MultipartWorker;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;

/**
 * 
 * @author xuhongfeng
 * 
 */
public class CcnActivity extends BaseActivity {
	private static final Logger logger = Logger.getLogger(CcnActivity.class);

	private ListView listView;
	private CcnListAdapter listAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ccn_activity);
		listView = (ListView) findViewById(R.id.listview);
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.ccn_activity_context_menu, menu);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item
				.getMenuInfo();
		switch (item.getItemId()) {
		case R.id.delete_file:
			String ccnName = listAdapter.getFile(info.position);
			doDeleteFile(ccnName);
			return true;
		default:
			return super.onContextItemSelected(item);
		}
	}

	private void doDeleteFile(final String ccnName) {
		long accountId = dataSource.getAccountId();
		long token = dataSource.getToken();
		DeleteCcnFileForm form = new DeleteCcnFileForm();
		form.setAccountId(accountId);
		form.setToken(token);
		form.setCcnName(ccnName);
		showWaitingDialog();
		new DeleteCcnFileAPI(form, this) {
			@Override
			protected void onSuccess() {
				dataSource.deleteCcnFile(ccnName);
				checkDataUpdate();
			}

			@Override
			protected void onStop() {
				super.onStop();
				dismissWaitingDialog();
			}

		}.call();
	}

	private void initListener() {
		registerForContextMenu(listView);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long id) {
				String ccnFile = listAdapter.getFile(position);
				CcnDownloadWorker worker = new CcnDownloadWorker(ccnFile) {
					@Override
					protected void onSuccess(File file) {
						dismissWaitingDialog();
						openFile(file);
					}

					@Override
					protected void onFailed(Exception e) {
						dismissWaitingDialog();
						logger.error(e);
					}
				};
				showWaitingDialog();
				runWorker(worker);
			}
		});
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
		for (int i = 0; i < files.length; i++) {
			files[i] = dataSource.getCcnFiles()[i].getCcnname();
		}
		listAdapter = new CcnListAdapter(this, files);
		listView.setAdapter(listAdapter);
		initListener();
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
		if (event instanceof CcnFilesUpdateEvent) {
			updateUI();
			dismissWaitingDialog();
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
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.ccn_list_view_item,
						parent, false);
			}
			TextView textView = (TextView) convertView
					.findViewById(R.id.ccn_file_name);
			textView.setText(files[position]);
			return convertView;
		}

		public String getFile(int position) {
			return files[position];
		}
	}

	private static final int REQUEST_SELECT_FILE = 1;

	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {
		if (requestCode == REQUEST_SELECT_FILE && resultCode == RESULT_OK) {
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
				dismissWaitingDialog();
				checkDataUpdate();
			}

			@Override
			protected void onFailed(Exception e) {
				dismissWaitingDialog();
				logger.error(e);
			}
		};
		showWaitingDialog();
		runWorker(worker);
	}

}
