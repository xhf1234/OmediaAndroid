package org.tsinghua.omedia.activity;

import org.tsinghua.omedia.R;
import org.tsinghua.omedia.support.TabsAdapter;
import org.tsinghua.omedia.tool.Logger;
import org.tsinghua.omedia.ui.fragment.MyFriendsFragment;
import org.tsinghua.omedia.ui.fragment.SearchFriendsFragment;
import org.tsinghua.omedia.ui.fragment.SocialGraphFragment;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.TabHost;



public class FriendsActivity extends BaseActivity {
    private Logger logger = Logger.getLogger(FriendsActivity.class);
    
    private TabHost mTabHost;
    private ViewPager  mViewPager;
    private TabsAdapter mTabsAdapter;
    
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friends_activity);
        mTabHost = (TabHost)findViewById(android.R.id.tabhost);
        mTabHost.setup();
        mViewPager = (ViewPager)findViewById(R.id.pager);
        mTabsAdapter = new TabsAdapter(this, mTabHost, mViewPager);
        mTabsAdapter.addTab(mTabHost.newTabSpec(getString(R.string.myfriends))
                .setIndicator(getString(R.string.myfriends)),
                MyFriendsFragment.class, null);
        mTabsAdapter.addTab(mTabHost.newTabSpec(getString(R.string.searchfriends))
                .setIndicator(getString(R.string.searchfriends)),
                SearchFriendsFragment.class, null);
        mTabsAdapter.addTab(mTabHost.newTabSpec("SocialGraph")
                .setIndicator("SocialGraph"),
                SocialGraphFragment.class, null);

        if (savedInstanceState != null) {
            mTabHost.setCurrentTabByTag(savedInstanceState.getString("tab"));
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("tab", mTabHost.getCurrentTabTag());
    }
	
}