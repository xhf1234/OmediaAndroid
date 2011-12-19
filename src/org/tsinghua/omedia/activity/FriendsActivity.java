package org.tsinghua.omedia.activity;

import org.tsinghua.omedia.R;
import android.os.Bundle;
import android.widget.TabHost;
import android.view.LayoutInflater;



public class FriendsActivity extends BaseTabActivity {
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    
        TabHost tabHost = getTabHost();

        LayoutInflater.from(this).inflate(R.layout.friends_activity , tabHost.getTabContentView(), true);
        tabHost.addTab(tabHost.newTabSpec("Friends").setIndicator(getString(R.string.myfriends)).setContent(R.id.friendsTabView));
        tabHost.addTab(tabHost.newTabSpec("Search").setIndicator(getString(R.string.searchfriends)).setContent(R.id.searchTabView));
        tabHost.addTab(tabHost.newTabSpec("SocialGraph").setIndicator(getString(R.string.socialgraph)).setContent(R.id.socialgraphTabView));
    	
	
	}
}