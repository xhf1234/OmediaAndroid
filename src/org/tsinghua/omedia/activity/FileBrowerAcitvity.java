package org.tsinghua.omedia.activity;

import java.io.File;
import java.util.ArrayList;

import org.tsinghua.omedia.R;
import org.tsinghua.omedia.tool.FileUtils;
import org.tsinghua.omedia.ui.fileBrowser.FileInfoDataSet;
import org.tsinghua.omedia.ui.fileBrowser.FileInfoAdapter;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

/**
 * 
 * @author hanfuye
 *
 */
public class FileBrowerAcitvity extends BaseActivity {

	private ArrayList<FileInfoDataSet> listItem;

	private GridView gView;
	private TextView tv;

	private File nowFile;
	private File[] root = new File[10];
	private int i = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.filebrower_directory_list);
		root[i] = new File("/");
		if (root[i].exists()) {
			nowFile = root[i];
		}

		tv = (TextView) findViewById(R.id.filebrower_path);

		inflateListView(nowFile);

		initListener();
	}

	private void initListener() {
		gridview();
		back_button();
		close_button();
	}

	private void close_button() {
		Button backbutton = (Button) findViewById(R.id.filebrower_close);
		backbutton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
			    onBackPressed();
			}
		});
	}

	private void back_button() {
		Button backbutton = (Button) findViewById(R.id.filebrower_back);
		backbutton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(!doBack()) {
					FileBrowerAcitvity.this.onBackPressed();
				}
			}
		});
	}
	
	private boolean doBack() {
		if (i != 0) {
			i = i - 1;
			inflateListView(root[i]);
			return true;
		} else {
			return false;
		}
	}
	
	

    @Override
	public void onBackPressed() {
    	if(!doBack()) {
    		super.onBackPressed();
    	}
	}

	private void gridview() {
        gView = (GridView) findViewById(R.id.lvFileList);
        gView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                    long arg3) {
                FileInfoDataSet info = listItem.get(arg2);
                if (info.getIsDirectory()) {
                    if (info.getFile() == null) {
                        Log.e("hanfuye", "tmp==null");
                    } else {
                        i = i + 1;
                        nowFile = info.getFile();
                        root[i] = nowFile;
                        inflateListView(nowFile);
                    }
                } else {
                    Intent intent = new Intent();
                    File file = info.getFile();
                    Uri uri = Uri.fromFile(file);
                    intent.setData(uri);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
    }

	private void inflateListView(File file) {

		File[] currentFiles = file.listFiles();

		ArrayList<FileInfoDataSet> array = new ArrayList<FileInfoDataSet>();

		tv.setText(file.getAbsolutePath());

		FileInfoDataSet info;

		for (File f : currentFiles) {
			if (!f.canRead())
				continue;
			if (f.isFile()) {
				info = new FileInfoDataSet(f, FileUtils.getImageIdByType(FileUtils.getMIMEType(f.getName())));
				array.add(info);
			} else {
				info = new FileInfoDataSet(f, R.drawable.filebrower_folder);
				array.add(info);
			}
		}

		gView = (GridView) findViewById(R.id.lvFileList);

		listItem = array;

		FileInfoAdapter adapter = new FileInfoAdapter(this, listItem);

		gView.setAdapter(adapter);
	}

}
