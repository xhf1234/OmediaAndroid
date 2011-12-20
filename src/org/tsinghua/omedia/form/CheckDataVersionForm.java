package org.tsinghua.omedia.form;

import org.tsinghua.omedia.annotation.form.HttpParam;

public class CheckDataVersionForm extends AbstractForm {
    @HttpParam(name="accountId")
    private long accountId;
    @HttpParam(name="token")
    private long token;
    @HttpParam(name="accountVersion")
    private long accountVersion;
    @HttpParam(name="friendRequestVersion")
    private long friendRequestVersion;
    @HttpParam(name="friendsVersion")
    private long friendsVersion;
    
    public long getAccountId() {
        return accountId;
    }
    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }
    public long getToken() {
        return token;
    }
    public void setToken(long token) {
        this.token = token;
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
    
}
