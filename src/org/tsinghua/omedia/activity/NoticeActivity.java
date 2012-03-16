package org.tsinghua.omedia.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.tsinghua.omedia.R;
import org.tsinghua.omedia.data.Account;
import org.tsinghua.omedia.data.FriendRequest;
import org.tsinghua.omedia.datasource.DataSource;
import org.tsinghua.omedia.form.AddFriendForm;
import org.tsinghua.omedia.form.GetFriendRequestForm;
import org.tsinghua.omedia.serverAPI.AddFriendAPI;
import org.tsinghua.omedia.serverAPI.GetFriendRequestAPI;
import org.tsinghua.omedia.tool.Logger;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.webkit.DateSorter;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;
/*
 *
 *	@author liangyong
 *
 */
public class NoticeActivity extends BaseActivity {
	private Logger logger = Logger.getLogger(NoticeActivity.class);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.notice_activity);

		GetFriendRequestForm form = new GetFriendRequestForm();
		form.setAccountId(dataSource.getAccountId());
		form.setToken(dataSource.getToken());
		doGetFriendRequest(form);

	}

	private void doGetFriendRequest(GetFriendRequestForm form) {
		new GetFriendRequestAPI(form, this) {
			@Override
			protected void onSuccess(long version, FriendRequest[] requests) {
				getFriendRequestSuccess(requests);
			}
		}.call();
	}

	private void getFriendRequestSuccess(final FriendRequest[] requests) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sender", getString(R.string.notice_sender));
		map.put("notice", getString(R.string.notice_content));
		map.put("time", getString(R.string.notice_time));
		list.add(map);
		for (int i = 0; i < requests.length; i++) {
			map = new HashMap<String, Object>();
			map.put("sender", requests[i].getFriend().getUsername());
			map.put("notice", requests[i].getMsg());
			map.put("time", requests[i].getTime());
			list.add(map);
		}
		SimpleAdapter listAdapter = new SimpleAdapter(this, list,
				R.layout.notice_list,
				new String[] { "sender", "notice", "time" }, new int[] {
						R.id.sender, R.id.notice, R.id.time });
		ListView listview = (ListView) findViewById(R.id.listView1);
		listview.setAdapter(listAdapter);

		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (position > 0) {
					showAccessRequest(requests[position - 1].getFriend());
				}

			};
		});
	}

	private void showAccessRequest(final Account account) {
		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		alert.setTitle(getString(R.string.alertdialog_title_access))
				.setPositiveButton(getString(R.string.btn_yes),
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								AddFriendForm form = new AddFriendForm();
								form.setAccountId(dataSource
										.getAccountId());
								form.setToken(dataSource
										.getToken());
								form.setFriendId(account.getAccountId());
								form.setMsg("");
								doAccessRequest(form);
							}
						}).setNegativeButton(getString(R.string.btn_cancel),
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.cancel();
							}
						}).show();
	}

	private void doAccessRequest(AddFriendForm form) {
		new AddFriendAPI(form, this) {
			@Override
			protected void onSuccess() {
				// TODO:
			};
		}.call();

	}
}
