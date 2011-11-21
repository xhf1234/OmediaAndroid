package org.tsinghua.omedia.datasource;

import org.tsinghua.omedia.data.Account;

/**
 * memory datasource
 * 
 * @author xuhongfeng
 *
 */
public class MemDataSource {
    private Account loginAccount;
    
    private static MemDataSource me;
    
    //singleton
    private MemDataSource(){}
    
    public static MemDataSource getInstance() {
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
    
    /**
     * 
     * @return get current login account
     */
    public Account getLoginAccount() {
        return loginAccount;
    }
    
    /**
     * 
     * @param account
     */
    public void setLoginAccount(Account account) {
        loginAccount = account;
    }
}
