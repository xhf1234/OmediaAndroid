package org.tsinghua.omedia.form;

import org.tsinghua.omedia.annotation.form.HttpParam;

/**
 * 
 * @author xuhongfeng
 *
 */
public class CheckDataVersionForm extends BaseForm {
    @HttpParam(name="accountVersion")
    private long accountVersion;
    @HttpParam(name="friendRequestVersion")
    private long friendRequestVersion;
    @HttpParam(name="friendsVersion")
    private long friendsVersion;
    @HttpParam(name="ccnFileVersion")
    private long ccnFileVersion;
    @HttpParam(name="configVersion")
    private long configVersion;
    @HttpParam(name="groupVersion")
    private long groupVersion;
    
    public long getGroupVersion() {
        return groupVersion;
    }
    public void setGroupVersion(long groupVersion) {
        this.groupVersion = groupVersion;
    }
    public long getAccountVersion() {
        return accountVersion;
    }
    public void setAccountVersion(long accountVersion) {
        this.accountVersion = accountVersion;
    }
    public long getFriendRequestVersion() {
        return friendRequestVersion;
    }
    public void setFriendRequestVersion(long friendRequestVersion) {
        this.friendRequestVersion = friendRequestVersion;
    }
    public long getFriendsVersion() {
        return friendsVersion;
    }
    public void setFriendsVersion(long friendsVersion) {
        this.friendsVersion = friendsVersion;
    }
    public long getCcnFileVersion() {
        return ccnFileVersion;
    }
    public void setCcnFileVersion(long ccnFileVersion) {
        this.ccnFileVersion = ccnFileVersion;
    }
    public long getConfigVersion() {
        return configVersion;
    }
    public void setConfigVersion(long configVersion) {
        this.configVersion = configVersion;
    }
    
}
