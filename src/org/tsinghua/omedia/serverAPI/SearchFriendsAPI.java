package org.tsinghua.omedia.serverAPI;

import org.tsinghua.omedia.activity.OmediaActivityIntf;
import org.tsinghua.omedia.consts.ResultCode;
import org.tsinghua.omedia.consts.UrlConst;
import org.tsinghua.omedia.data.Account;
import org.tsinghua.omedia.data.JsonObject;
import org.tsinghua.omedia.form.SearchFriendsForm;

public abstract class SearchFriendsAPI extends AbstractServerAPI<SearchFriendsForm> {

    protected SearchFriendsAPI(SearchFriendsForm form, OmediaActivityIntf omediaActivity) {
        super(form, UrlConst.SearchFriendsUrl, omediaActivity);
    }

    @Override
    protected void initResultCodeListener() {
        registerResultCodeListener(ResultCode.SUCCESS, new ResultCodeListener() {
            @Override
            protected void exec(JsonObject result) {
                JsonObject[] obs = result.getArray("friends");
                Account[] accounts = new Account[obs.length];
                for(int i=0; i<obs.length; i++) {
                    accounts[i] = new Account();
                    accounts[i].setAccountId(obs[i].getLong("accountId"));
                    accounts[i].setUsername(obs[i].getString("username"));
                    accounts[i].setEmail(obs[i].getString("email"));
                    accounts[i].setRealName(obs[i].getString("realName"));
                    accounts[i].setPhone(obs[i].getString("phone"));
                    accounts[i].setAddress(obs[i].getString("address"));
                }
                onSuccess(accounts);
            }
        });
    }

    protected abstract void onSuccess(Account[] accounts);
}
