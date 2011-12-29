package org.tsinghua.omedia.activity;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.tsinghua.omedia.R;
import org.tsinghua.omedia.consts.UrlConst;
import org.tsinghua.omedia.data.Account;
import org.tsinghua.omedia.datasource.DataSource;
import org.tsinghua.omedia.form.SearchFriendsForm;
import org.tsinghua.omedia.serverAPI.SearchFriendsAPI;
import org.tsinghua.omedia.tool.JsonUtils;
import org.tsinghua.omedia.tool.Logger;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TabHost;



public class FriendsActivity extends BaseTabActivity {
    private Logger logger = Logger.getLogger(FriendsActivity.class);
    
    private static Pattern ADD_FRIEND_PATTERN =
            Pattern.compile("^"+UrlConst.BaseUrl+"addfriend=(\\{[^\\}]*\\})$");
    private static Pattern SHOW_FRIEND_PATTERN =
            Pattern.compile("^"+UrlConst.BaseUrl+"friend=(\\{[^\\}]*\\})$");
    
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    
        TabHost tabHost = getTabHost();

        LayoutInflater.from(this).inflate(R.layout.friends_activity , tabHost.getTabContentView(), true);
        tabHost.addTab(tabHost.newTabSpec("Friends").setIndicator(getString(R.string.myfriends)).setContent(R.id.friendsTabView));
        tabHost.addTab(tabHost.newTabSpec("Search").setIndicator(getString(R.string.searchfriends)).setContent(R.id.searchTabView));
        tabHost.addTab(tabHost.newTabSpec("SocialGraph").setIndicator(getString(R.string.socialgraph)).setContent(R.id.socialgraphTabView));
    	
        initListener();
        
        WebView webView = (WebView) findViewById(R.id.webView1);
        long accountId = dataSource.getAccountId();
        long token = dataSource.getToken();
        String url = UrlConst.SocialGraphUrl+"?accountId="+accountId
                +"&token="+token;
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(url);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                url = URLDecoder.decode(url);
                Matcher m = ADD_FRIEND_PATTERN.matcher(url);
                if(m.find()) {
                    String jsonString = m.group(1);
                    try {
                        Account account = JsonUtils.parseJsonObject(jsonString, Account.class);
                        showAddFriend(account);
                    } catch (IOException e) {
                        logger.error("parse json failed! json=" + jsonString, e);
                    }
                    
                    return true;
                } else {
                    m = SHOW_FRIEND_PATTERN.matcher(url);
                    if(m.find()) {
                        String jsonString = m.group(1);
                        try {
                            Account account = JsonUtils.parseJsonObject(jsonString, Account.class);
                            showFriendInfo(account);
                        } catch (IOException e) {
                            logger.error("parse json failed! json=" + jsonString, e);
                        }
                        
                        return true;
                    }
                }
                return false;
            }
        });
    }

    private void showAddFriend(Account friend) {
        //TODO
    }
    
    private void showFriendInfo(Account friend) {
        //TODO
    }

    private void initListener() {
        // searchFriendsTab
        findViewById(R.id.searchButton).setOnClickListener(
                new OnClickListener() {
                    public void onClick(View v) {
                        EditText searchText = (EditText) findViewById(R.id.searchText);
                        SearchFriendsForm form = new SearchFriendsForm();
                        form.setAccountId(DataSource.getInstance()
                                .getAccountId());
                        form.setToken(DataSource.getInstance().getToken());
                        form.setKeyword(searchText.getText().toString());
                        // showAlertDialog(Long.toString(form.getAccountId())+" "+Long.toString(form.getToken()));
                        doSearchFriends(form);
                    }
                });
    }

    private void doSearchFriends(SearchFriendsForm form) {
        new SearchFriendsAPI(form, this) {
            @Override
            protected void onSuccess(Account[] accounts) {
                // showAlertDialog(accounts.toString());
                searchFriendsSuccess(accounts);
            }
            /*
             * @Override protected void onLoginFailed() {
             * showAlertDialog(R.string.login_auth_failed); }
             * 
             * @Override protected void onSoftwareNeedUpdate() {
             * showAlertDialog(R.string.software_need_update); }
             */
        }.call();

    }

    private void searchFriendsSuccess(Account[] accounts) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("friends_name", getString(R.string.friends_name));
        map.put("friends_realname", getString(R.string.friends_realname));
        map.put("friends_address", getString(R.string.friends_address));
        map.put("friends_phone", getString(R.string.friends_phone));
        map.put("friends_email", getString(R.string.friends_email));
        list.add(map);
        for (int i = 0; i < accounts.length; i++) {
            map = new HashMap<String, Object>();
            map.put("friends_name", accounts[i].getUsername());
            map.put("friends_realname", accounts[i].getRealName());
            map.put("friends_address", accounts[i].getAddress());
            map.put("friends_phone", accounts[i].getPhone());
            map.put("friends_email", accounts[i].getEmail());
            list.add(map);
        }
        SimpleAdapter listAdapter = new SimpleAdapter(this, list,
                R.layout.search_friends_list, new String[] { "friends_name",
                        "friends_realname", "friends_address", "friends_phone",
                        "friends_email" }, new int[] { R.id.friends_name,
                        R.id.friends_realname, R.id.friends_address,
                        R.id.friends_phone, R.id.friends_email });
        ListView listview = (ListView) findViewById(R.id.searchResult);
        listview.setAdapter(listAdapter);

    }

}