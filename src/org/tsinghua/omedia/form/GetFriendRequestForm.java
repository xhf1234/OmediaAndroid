package org.tsinghua.omedia.form;

import org.tsinghua.omedia.annotation.form.HttpParam;

public class GetFriendRequestForm extends AbstractForm {
    @HttpParam(name="accountId")
    private long accountId;
    @HttpParam(name="token")
    private long token;
    
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
}
