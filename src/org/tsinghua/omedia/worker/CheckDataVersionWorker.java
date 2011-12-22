package org.tsinghua.omedia.worker;

import org.tsinghua.omedia.data.Account;
import org.tsinghua.omedia.datasource.DataSource;
import org.tsinghua.omedia.form.CheckDataVersionForm;
import org.tsinghua.omedia.form.GetAccountForm;
import org.tsinghua.omedia.serverAPI.CheckDataVersionAPI;
import org.tsinghua.omedia.serverAPI.GetAccountAPI;

/**
 * 调用服务端接口获得各种数据的版本号如accountVersion, friendsVersion
 * 将服务端版本号与本地版本号对比，若不通则意味着数据需要更新，则调用相应
 * 服务端接口更新数据
 * @author xuhongfeng
 *
 */
public class CheckDataVersionWorker extends SyncWorker {
    
    @Override
    protected void singleRun() {
        DataSource dataSource = DataSource.getInstance();
        long accountId = dataSource.getAccountId();
        long token = dataSource.getToken();
        long accountVersion = dataSource.getAccountVersion();
        long friendRequestVersion = dataSource.getFriendRequestVersion();
        long friendsVersion = dataSource.getFriendsVersion();
        CheckDataVersionForm form = new CheckDataVersionForm();
        form.setAccountId(accountId);
        form.setToken(token);
        form.setAccountVersion(accountVersion);
        form.setFriendRequestVersion(friendRequestVersion);
        form.setFriendsVersion(friendsVersion);
        new CheckDataVersionAPI(form, omediaConsole) {
            
            @Override
            protected void onSuccess(long accountVersion, long friendRequestVersion,
                    long friendsVersion) {
                DataSource dataSource = DataSource.getInstance();
                long oldAccountVersion = dataSource.getAccountVersion();
                long oldFriendRequestVersion = dataSource.getFriendRequestVersion();
                long oldFriendsVersion = dataSource.getFriendsVersion();
                
                //若客户端版本号与服务端版本号不同， 更新数据
                if(oldAccountVersion != accountVersion) {
                    long accountId = dataSource.getAccountId();
                    long token = dataSource.getToken();
                    Account account  = dataSource.getAccount(accountId);
                    String username = account.getUsername();
                    GetAccountForm form = new GetAccountForm(accountId, token);
                    new GetAccountAPI(form, username, omediaConsole) {
                        @Override
                        protected void onGetAccountSuccess(Account account, long version) {
                            DataSource dataSource = DataSource.getInstance();
                            dataSource.saveAccount(account);
                            dataSource.setAccountVersion(version);
                        }
                    };
                }
                //TODO
                if(oldFriendRequestVersion != friendRequestVersion) {
                    
                }
                if(oldFriendsVersion != friendsVersion) {
                }
            }
        };
    }

}
