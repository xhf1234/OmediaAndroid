package org.tsinghua.omedia.activity;

import org.tsinghua.omedia.R;
import org.tsinghua.omedia.event.Event;
import org.tsinghua.omedia.event.FriendRequestUpdateEvent;
import org.tsinghua.omedia.worker.WorkerManager;

import android.content.Intent;
import android.content.res.Resources;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListAdapter;

/**
 * 
 * @author xuhongfeng
 *
 */
public class MainActivity extends BaseActivity {
	private GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        gridView = (GridView) findViewById(R.id.gridview);
        initListener();
    }
    
    @Override
    public void onResume() {
        updateUI();
        WorkerManager.getInstance().startWorkers();
        super.onResume();
    }

    private void updateUI() {
    	GridViewAdapter adapter = new GridViewAdapter();
        int count = dataSource.getFriendRequests().length;
        adapter.setNoticeCount(count);
    	gridView.setAdapter(adapter);
    }

    private void initListener() {
    }

    @Override
    public void onBackPressed() {
        WorkerManager.getInstance().stopWorkers();
        super.onBackPressed();
    }

    @Override
    public void onEventCatch(Event event) {
        if(event instanceof FriendRequestUpdateEvent) {
            updateUI();
        }
        super.onEventCatch(event);
    }
    
    private class GridViewAdapter implements ListAdapter {
    	private final String[] labels;
    	
    	private final static int ID_FRIENDS = 0;
    	private final static int ID_NOTICE = 1;
    	private final static int ID_CCN = 2;
    	private final static int ID_SETTINGS = 3;
    	
    	private LayoutInflater inflater;
    	
    	public void setNoticeCount(int count) {
    		labels[ID_NOTICE] = labels[ID_NOTICE].replace("${count}", count+"");
    	}
    	
		public GridViewAdapter() {
			super();
			inflater = MainActivity.this.getLayoutInflater();
			Resources r = MainActivity.this.getResources();
			labels = new String[4];
			labels[ID_FRIENDS] = r.getString(R.string.btn_friends);
			labels[ID_NOTICE] = r.getString(R.string.btn_notice);
			labels[ID_CCN] = "CCN";
			labels[ID_SETTINGS] = r.getString(R.string.btn_settings);
		}

		@Override
		public void registerDataSetObserver(DataSetObserver observer) {
			
		}

		@Override
		public void unregisterDataSetObserver(DataSetObserver observer) {
			
		}

		@Override
		public int getCount() {
			return labels.length;
		}

		@Override
		public Object getItem(int position) {
			return labels[position];
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public boolean hasStableIds() {
			return true;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			Button button = (Button) convertView;
			if(button == null) {
				button = (Button) inflater.inflate(R.layout.main_activity_button, null);
			}
			button.setText(labels[position]);
			button.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					switch(position) {
					case ID_FRIENDS:
		                Intent intent = new Intent(MainActivity.this, FriendsActivity.class);
		                startActivity(intent);
						break;
					case ID_NOTICE:
						break;
					case ID_CCN:
		                intent = new Intent(MainActivity.this, CcnActivity.class);
		                startActivity(intent);
						break;
					case ID_SETTINGS:
						break;
					default:
						break;
					}
				}
			});
			return button;
		}

		@Override
		public int getItemViewType(int position) {
			return 0;
		}

		@Override
		public int getViewTypeCount() {
			return 1;
		}

		@Override
		public boolean isEmpty() {
			return false;
		}

		@Override
		public boolean areAllItemsEnabled() {
			return true;
		}

		@Override
		public boolean isEnabled(int position) {
			return true;
		}
    	
    }
}
