package org.tsinghua.omedia.form;

import org.tsinghua.omedia.annotation.form.HttpParam;

public class CheckDataVersionForm extends BaseForm {
    @HttpParam(name="accountVersion")
    private long accountVersion;
    @HttpParam(name="friendRequestVersion")
    private long friendRequestVersion;
    @HttpParam(name="friendsVersion")
    private long friendsVersion;
    
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
    
}
