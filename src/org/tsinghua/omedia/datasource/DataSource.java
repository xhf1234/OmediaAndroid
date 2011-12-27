package org.tsinghua.omedia.datasource;

import org.tsinghua.omedia.OmediaApplication;
import org.tsinghua.omedia.data.Account;
import org.tsinghua.omedia.data.FriendRequest;
import org.tsinghua.omedia.datasource.db.DataBase;
import org.tsinghua.omedia.event.FriendRequestUpdateEvent;
import org.tsinghua.omedia.worker.WorkerManager;

/**
 * 数据源 封装一些存取数据的对象和方法

/**
 * 数据源的对象包括 数据库Database, 内存中的MemDataSource等
 * @author xuhongfeng
 *
 */
public class DataSource {
    private static DataSource me;
    //singleton
    private DataSource(){}

    public static DataSource getInstance() {
        if(me != null) return me;
        synchronized (DataSource.class) {
            if(me == null) {
                me = new DataSource();
            }
        }
        return me;
    }
    
    /**
     * 当前登陆用户的ID
     * @return
     */
    public long getAccountId() {
        return getMemDataSource().getAccountId();
    }
    
    
    /**
     * 当前登陆用户的token
     * @return
     */
    public long getToken() {
        return getMemDataSource().getToken();
    }
    
    /**
     * 保存用户accountId
     * @param accountId
     */
    public void saveAccountId(long accountId) {
        getMemDataSource().setAccountId(accountId);
    }
    
    /**
     * 保存账户token
     * @param token
     */
    public void saveToken(long token) {
        getMemDataSource().setToken(token);
    }
    
    /**
     * 保存账户信息的版本号。 当客户端与服务端的版本号不同时，将会从服务端更新账户
     * 信息到客户端
     * @param version
     */
    public void saveAccountVersion(long version) {
        getPreference().setAccountVersion(version);
    }
    /**
     * 保存账号信息
     * @param account
     */
    public void saveAccount(Account account) {
        getDataBase().saveOrUpdateAccount(account);
    }
    
    public Account getAccount(long accountId) {
        return getDataBase().getAccount(accountId);
    }
    
    public long getAccountVersion() {
        return getPreference().getAccountVersion();
    }
    
    public void setAccountVersion(long accountVersion) {
        getPreference().setAccountVersion(accountVersion);
    }

    public long getFriendRequestVersion() {
        return getPreference().getFriendRequestVersion();
    }
    
    public void setFriendRequestVersion(long friendRequestVersion) {
        getPreference().setFriendRequestVersion(friendRequestVersion);
    }
    public long getFriendsVersion() {
        return getPreference().getFriendsVersion();
    }
    
    public void setFriendsVersion(long friendsVersion) {
        getPreference().setFriendsVersion(friendsVersion);
    }
    
    public String[] getCcnFiles() {
        WorkerManager.getInstance().getCcnWorker().waitingForData();
        return getMemDataSource().getCcnFiles();
    }
    
    public void setCcnFiles(String[] ccnFiles) {
        getMemDataSource().setCcnFiles(ccnFiles);
    }
    
    public String getCcnHost() {
        return "166.111.137.72";
    }
    
    public String getCcnUri() {
        return "ccnx:/node1";
    }
    
    public FriendRequest[] getFriendRequests() {
        return getMemDataSource().getFriendRequests();
    }
    
    public void saveFriendRequests(FriendRequest[] friendRequests) {
        getMemDataSource().setFriendRequests(friendRequests);
        OmediaApplication.getInstance().dispatchEvent(
                new FriendRequestUpdateEvent());
    }
    
    public Account[] getFriends() {
        return getMemDataSource().getFriends();
    }
    
    public void saveFriends(Account[] friends) {
        getMemDataSource().setFriends(friends);
    }
    
    public OmediaPreference getPreference() {
        return OmediaPreference.getInstance();
    }
    
    public MemDataSource getMemDataSource() {
        return MemDataSource.getInstance();
    }
    
    public DataBase getDataBase() {
        return DataBase.getInstance();
    }
}
