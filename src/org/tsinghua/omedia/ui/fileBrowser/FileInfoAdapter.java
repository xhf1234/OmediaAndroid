package org.tsinghua.omedia.ui.fileBrowser;

import java.util.List;

import org.tsinghua.omedia.R;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class FileInfoAdapter extends BaseAdapter {
	private LayoutInflater layoutInflater; // 用于从xml文件加载listview的layout

	private Context ctx; // 容器／activity

	public List<FileInfo> fileInfoList; // 所有item

	public FileInfoAdapter(Context ctx, List<FileInfo> list) {

		this.ctx = ctx;

		fileInfoList = list;

		layoutInflater = LayoutInflater.from(ctx);

	}

	@Override
	public int getCount() {

		return fileInfoList.size();

	}

	@Override
	public Object getItem(int position) {

		return fileInfoList.get(position);

	}

	@Override
	public long getItemId(int position) {

		return position;

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder = null; // 清空临时变量

		if (convertView == null) { // 若行未初始化

			convertView = layoutInflater.inflate(

			R.layout.file_info, null); // 通过flater初始化行视图

			holder = new ViewHolder(); // 并将行视图的3个子视图引用放到tag中

			holder.itemIcon = (ImageView) convertView
					.findViewById(R.id.fileinfo_icon);

			holder.itemText = (TextView) convertView
					.findViewById(R.id.fileinfo_filename);

			convertView.setTag(holder);

		} else {

			holder = (ViewHolder) convertView.getTag();

		}

		FileInfo info = fileInfoList.get(position);

		if (info != null) {

			holder.itemText.setText(info.fileName);

			Drawable draw = this.ctx.getResources().getDrawable(

			info.imageId);

			holder.itemIcon.setImageDrawable(draw);

		}

		return convertView;

	}

	static class ViewHolder {

		TextView itemText;

		ImageView itemIcon;

	}
}
