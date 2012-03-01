package org.tsinghua.omedia.ui.fragment;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.tsinghua.omedia.R;
import org.tsinghua.omedia.consts.UrlConst;
import org.tsinghua.omedia.data.Account;
import org.tsinghua.omedia.tool.JsonUtils;
import org.tsinghua.omedia.tool.Logger;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * 
 * @author xuhongfeng
 *
 */
public class SocialGraphFragment extends BaseFragment {
    private Logger logger = Logger.getLogger(SocialGraphFragment.class);
    
    
    private static Pattern ADD_FRIEND_PATTERN =
            Pattern.compile("^"+UrlConst.BaseUrl+"addfriend=(\\{[^\\}]*\\})$");
    private static Pattern SHOW_FRIEND_PATTERN =
            Pattern.compile("^"+UrlConst.BaseUrl+"friend=(\\{[^\\}]*\\})$");
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_social_graph, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        WebView webView = (WebView) getActivity().findViewById(R.id.webView1);
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


}
