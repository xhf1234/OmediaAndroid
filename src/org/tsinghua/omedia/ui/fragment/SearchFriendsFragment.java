package org.tsinghua.omedia.ui.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.tsinghua.omedia.R;
import org.tsinghua.omedia.data.Account;
import org.tsinghua.omedia.datasource.DataSource;
import org.tsinghua.omedia.form.SearchFriendsForm;
import org.tsinghua.omedia.serverAPI.SearchFriendsAPI;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

/**
 * 
 * @author xuhongfeng
 *
 */
public class SearchFriendsFragment extends BaseFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search_friends, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        findViewById(R.id.searchButton).setOnClickListener(
                new OnClickListener() {
                    public void onClick(View v) {
                        EditText searchText = (EditText) getActivity().findViewById(R.id.searchText);
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
        new SearchFriendsAPI(form, getBaseActivity()) {
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
        SimpleAdapter listAdapter = new SimpleAdapter(getBaseActivity(), list,
                R.layout.search_friends_list, new String[] { "friends_name",
                        "friends_realname", "friends_address", "friends_phone",
                        "friends_email" }, new int[] { R.id.friends_name,
                        R.id.friends_realname, R.id.friends_address,
                        R.id.friends_phone, R.id.friends_email });
        ListView listview = (ListView) findViewById(R.id.searchResult);
        listview.setAdapter(listAdapter);

    }
}
