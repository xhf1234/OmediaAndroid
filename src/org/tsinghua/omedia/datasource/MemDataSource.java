package org.tsinghua.omedia.datasource;


/**
 * memory datasource
 * 
 * @author xuhongfeng
 *
 */
public class MemDataSource {
    private Long accountId;
    private long token;
    
    private static MemDataSource me;
    
    //singleton
    private MemDataSource(){}
    
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
}
