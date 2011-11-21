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
    
    private static OmediaPreference me;
    
    //singleton
    private OmediaPreference(){}
    
    public static OmediaPreference getInstance() {
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
    
    private SharedPreferences preference() {
        return OmediaApplication.getInstance().getSharedPreferences(PREFERENCE_FILE_NAME,
                Context.MODE_PRIVATE);
    }
}
