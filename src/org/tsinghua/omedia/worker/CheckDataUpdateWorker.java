package org.tsinghua.omedia.worker;

import org.tsinghua.omedia.data.Account;
import org.tsinghua.omedia.data.CcnFile;
import org.tsinghua.omedia.data.Config;
import org.tsinghua.omedia.data.FriendRequest;
import org.tsinghua.omedia.form.CheckDataVersionForm;
import org.tsinghua.omedia.form.GetAccountForm;
import org.tsinghua.omedia.form.GetConfigForm;
import org.tsinghua.omedia.form.GetFriendRequestForm;
import org.tsinghua.omedia.form.GetFriendsForm;
import org.tsinghua.omedia.form.ShowCcnFilesForm;
import org.tsinghua.omedia.serverAPI.CheckDataVersionAPI;
import org.tsinghua.omedia.serverAPI.GetAccountAPI;
import org.tsinghua.omedia.serverAPI.GetConfigAPI;
import org.tsinghua.omedia.serverAPI.GetFriendRequestAPI;
import org.tsinghua.omedia.serverAPI.GetFriendsAPI;
import org.tsinghua.omedia.serverAPI.ShowCcnFilesAPI;

/**
 * 调用服务端接口获得各种数据的版本号如accountVersion, friendsVersion
 * 将服务端版本号与本地版本号对比，若不通则意味着数据需要更新，则调用相应
 * 服务端接口更新数据
 * @author xuhongfeng
 *
 */
public class CheckDataUpdateWorker extends SyncWorker {
    
    @Override
    protected void singleRun() {
        long accountId = dataSource.getAccountId();
        long token = dataSource.getToken();
        long accountVersion = dataSource.getAccountVersion();
        long friendRequestVersion = dataSource.getFriendRequestVersion();
        long friendsVersion = dataSource.getFriendsVersion();
        long configVersion = dataSource.getConfigVersion();
        long ccnFileVersion = dataSource.getCcnFileVersion();
        CheckDataVersionForm form = new CheckDataVersionForm();
        form.setAccountId(accountId);
        form.setToken(token);
        form.setAccountVersion(accountVersion);
        form.setFriendRequestVersion(friendRequestVersion);
        form.setFriendsVersion(friendsVersion);
        form.setCcnFileVersion(ccnFileVersion);
        form.setConfigVersion(configVersion);
        new CheckDataVersionAPI(form, omediaConsole) {
            
            @Override
            protected void onSuccess(long accountVersion, long friendRequestVersion,
                    long friendsVersion, long configVersion, long ccnFileVersion) {
                long oldAccountVersion = dataSource.getAccountVersion();
                long oldFriendRequestVersion = dataSource.getFriendRequestVersion();
                long oldFriendsVersion = dataSource.getFriendsVersion();
                long oldConfigVersion = dataSource.getConfigVersion();
                long oldCcnFileVersion = dataSource.getCcnFileVersion();
                //若客户端版本号与服务端版本号不同， 更新数据
                if(oldAccountVersion != accountVersion) {
                    updateAccount();
                }
                if(oldFriendRequestVersion != friendRequestVersion) {
                    updateFriendRequest();
                }
                if(oldFriendsVersion != friendsVersion) {
                    updateFriends();
                }
                if(oldConfigVersion != configVersion) {
                    updateConfig();
                }
                if(oldCcnFileVersion != ccnFileVersion) {
                    updateCcnFiles();
                }
            }
        }.call();
    }
    
    private void updateAccount() {
        long accountId = dataSource.getAccountId();
        long token = dataSource.getToken();
        Account account  = dataSource.getAccount(accountId);
        String username = account.getUsername();
        GetAccountForm getAccountForm = new GetAccountForm(accountId, token);
        new GetAccountAPI(getAccountForm, username, omediaConsole) {
            
            @Override
            protected void onGetAccountSuccess(Account account, long version) {
                dataSource.saveAccount(account);
                dataSource.setAccountVersion(version);
            }
        }.call();
    }

    private void updateFriendRequest() {
        long accountId = dataSource.getAccountId();
        long token = dataSource.getToken();
        GetFriendRequestForm getRequestForm = new GetFriendRequestForm();
        getRequestForm.setAccountId(accountId);
        getRequestForm.setToken(token);
        new GetFriendRequestAPI(getRequestForm, omediaConsole) {
            
            @Override
            protected void onSuccess(long version, FriendRequest[] requests) {
                dataSource.saveFriendRequests(requests);
                dataSource.setFriendRequestVersion(version);
            }
        }.call();
    }
    
    private void updateFriends() {
        long accountId = dataSource.getAccountId();
        long token = dataSource.getToken();
        GetFriendsForm getFriendsForm = new GetFriendsForm();
        getFriendsForm.setAccountId(accountId);
        getFriendsForm.setToken(token);
        new GetFriendsAPI(getFriendsForm, omediaConsole) {
            
            @Override
            protected void onSuccess(Account[] friends, long version) {
                dataSource.saveFriends(friends);
                dataSource.setFriendsVersion(version);
            }
        }.call();
    }
    
    private void updateConfig() {
        long accountId = dataSource.getAccountId();
        long token = dataSource.getToken();
        GetConfigForm form = new GetConfigForm();
        form.setAccountId(accountId);
        form.setToken(token);
        new GetConfigAPI(form, omediaConsole) {
            @Override
            protected void onSuccess(long version, Config config) {
                dataSource.setConfigVersion(version);
                dataSource.saveConfig(config);
            }
            
        }.call();
    }
    
    private void updateCcnFiles() {
        long accountId = dataSource.getAccountId();
        long token = dataSource.getToken();
        ShowCcnFilesForm form = new ShowCcnFilesForm();
        form.setAccountId(accountId);
        form.setToken(token);
        new ShowCcnFilesAPI(form, omediaConsole) {
            @Override
            protected void onSuccess(long version, CcnFile[] ccnFiles) {
                dataSource.saveCcnFiles(ccnFiles);
                dataSource.setCcnFileVersion(version);
            }
        }.call();
    }
}
