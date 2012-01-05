package org.tsinghua.omedia.datasource;

import org.tsinghua.omedia.OmediaApplication;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 
 * @author xuhongfeng
 *
 */
public class OmediaPreference {
    private static final String PREFERENCE_FILE_NAME = "omedia.preference";
    
    private static final String KEY_USERNAME = "omedia_username";
    private static final String KEY_PASSWORD = "omedia_password";
    private static final String KEY_REMEMBER_PASSWORD = "omedia_remember_password";
    private static final String KEY_FRIENDS_REQUEST_VERSION = "omedia_friends_request_version";
    private static final String KEY_FRIENDS_VERSION = "omedia_friends_version";
    private static final String KEY_ACCOUNT_VERSION = "omedia_account_version";
    private static final String KEY_CONFIG_VERSION = "omedia_config_version";
    private static final String KEY_CCN_FILE_VERSION = "omedia_ccnFile_version";

    private static OmediaPreference me;
    
    //singleton
    private OmediaPreference(){}
    
    static OmediaPreference getInstance() {
        if(me != null) return me;
        synchronized (OmediaPreference.class) {
            if(me == null) {
                me = new OmediaPreference();
            }
        }
        return me;
    }
    
    public String getUsername() {
        return preference().getString(KEY_USERNAME, "");
    }
    
    public void setUsername(String username) {
        preference().edit().putString(KEY_USERNAME, username).commit();
    }
    
    public String getPassword() {
        return preference().getString(KEY_PASSWORD, "");
    }
    
    public void setPassword(String password) {
        preference().edit().putString(KEY_PASSWORD, password).commit();
    }
    
    public boolean isRememberPassword() {
        return preference().getBoolean(KEY_REMEMBER_PASSWORD, false);
    }
    
    public void setRememberPassword(boolean b) {
        preference().edit().putBoolean(KEY_REMEMBER_PASSWORD, b).commit();
    }
    
    public long getFriendRequestVersion() {
        return preference().getLong(genKey(KEY_FRIENDS_REQUEST_VERSION), -1L);
    }
    
    public void setFriendRequestVersion(long version) {
        preference().edit().putLong(genKey(KEY_FRIENDS_REQUEST_VERSION), version);
    }
    
    public long getFriendsVersion() {
        return preference().getLong(genKey(KEY_FRIENDS_VERSION), -1L);
    }
    
    public void setFriendsVersion(long version) {
        preference().edit().putLong(genKey(KEY_FRIENDS_VERSION), version);
    }
    
    public long getAccountVersion() {
        return preference().getLong(genKey(KEY_ACCOUNT_VERSION), -1L);
    }
    
    public void setAccountVersion(long version) {
        preference().edit().putLong(genKey(KEY_ACCOUNT_VERSION), version);
    }
    
    public long getConfigVersion() {
        return preference().getLong(genKey(KEY_CONFIG_VERSION), -1L);
    }
    
    public void setConfigVersion(long version) {
        preference().edit().putLong(genKey(KEY_CONFIG_VERSION), version);
    }
    
    public long getCcnFileVersion() {
        return preference().getLong(genKey(KEY_CCN_FILE_VERSION), -1L);
    }
    
    public void setCcnFileVersion(long version) {
        preference().edit().putLong(genKey(KEY_CCN_FILE_VERSION), version);
    }
    
    private String genKey(String key) {
        long accountId = DataSource.getInstance().getAccountId();
        return key + accountId;
    }
    
    private SharedPreferences preference() {
        return OmediaApplication.getInstance().getSharedPreferences(PREFERENCE_FILE_NAME,
                Context.MODE_PRIVATE);
    }
}
