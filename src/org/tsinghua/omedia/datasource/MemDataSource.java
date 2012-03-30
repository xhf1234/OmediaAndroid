package org.tsinghua.omedia.datasource;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.tsinghua.omedia.data.Account;
import org.tsinghua.omedia.data.CcnFile;
import org.tsinghua.omedia.data.Config;
import org.tsinghua.omedia.data.EmptyInstance;
import org.tsinghua.omedia.data.FriendRequest;
import org.tsinghua.omedia.tool.Logger;


/**
 * memory datasource
 * 
 * @author xuhongfeng
 *
 */
public class MemDataSource {
    private Logger logger = Logger.getLogger(MemDataSource.class);
    
    private Long accountId;
    private long token;
    private FriendRequest[] friendRequests = EmptyInstance.EMPTY_FRIEND_REQUESTS;
    private Account[] friends = EmptyInstance.EMPTY_FRIENDS;
    private Config config;
    private CcnFile[] ccnFiles = EmptyInstance.EMPTY_CCN_FILES;
    private FriendCcnFileStore friendCcnFile = new FriendCcnFileStore();
    
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

    public CcnFile[] getCcnFiles() {
        return ccnFiles;
    }

    public void setCcnFiles(CcnFile[] ccnFiles) {
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

    public Config getConfig() {
        if(config == null) {
            synchronized (this) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    logger.error(e);
                }
            }
        }
        return config;
    }

    public synchronized void setConfig(Config config) {
        this.config = config;
        this.notifyAll();
    }
    
    public CcnFile[] getFriendCcnFile(long friendId) {
        return friendCcnFile.getData(friendId);
    }
    
    public void updateFriendCcnFile(long friendId, CcnFile[] ccnFiles) {
        friendCcnFile.updateData(friendId, ccnFiles);
    }
    
    private class FriendCcnFileStore {
        private Map<Long, Set<CcnFile> > datas;
        
        public FriendCcnFileStore() {
            datas = new HashMap<Long, Set<CcnFile> >();
        }
        
        public void updateData(long friendId, CcnFile[] ccnFiles) {
            Set<CcnFile> set = new HashSet<CcnFile>();
            for(CcnFile f:ccnFiles) {
                set.add(f);
            }
            datas.put(friendId, set);
        }
        
        public CcnFile[] getData(long friendId) {
            if(!datas.containsKey(friendId)) {
                return EmptyInstance.EMPTY_CCN_FILES;
            }
            return datas.get(friendId).toArray(new CcnFile[0]);
        }
    }
}
