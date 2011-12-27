package org.tsinghua.omedia.datasource;

import org.tsinghua.omedia.data.Account;
import org.tsinghua.omedia.data.EmptyInstance;
import org.tsinghua.omedia.data.FriendRequest;


/**
 * memory datasource
 * 
 * @author xuhongfeng
 *
 */
public class MemDataSource {
    private Long accountId;
    private long token;
    private String[] ccnFiles = EmptyInstance.EMPTY_STRINGS;
    private FriendRequest[] friendRequests = EmptyInstance.EMPTY_FRIEND_REQUESTS;
    private Account[] friends = EmptyInstance.EMPTY_FRIENDS; 
    
    private static MemDataSource me;
    
    //singleton
    private MemDataSource(){}
    
    public static void clearData() {
        synchronized (MemDataSource.class) {
            me = null;
        }
    }
    
    static MemDataSource getInstance() {
        if(me != null) {
            return me;
        }
        synchronized (MemDataSource.class) {
            if(me == null) {
                me = new MemDataSource();
            }
        }
        return me;
    }
    
    public long getAccountId() {
        return accountId;
    }
    
    public long getToken() {
        return token;
    }
    
    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public void setToken(long token) {
        this.token = token;
    }

    public boolean isLogin() {
        return accountId != null;
    }
    
    public void logout() {
        accountId = null;
        token = -1L;
    }

    public String[] getCcnFiles() {
        return ccnFiles;
    }

    public void setCcnFiles(String[] ccnFiles) {
        this.ccnFiles = ccnFiles;
    }

    public FriendRequest[] getFriendRequests() {
        return friendRequests;
    }

    public void setFriendRequests(FriendRequest[] friendRequests) {
        this.friendRequests = friendRequests;
    }

    public Account[] getFriends() {
        return friends;
    }

    public void setFriends(Account[] friends) {
        this.friends = friends;
    }
}
