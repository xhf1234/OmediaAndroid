package org.tsinghua.omedia.form;

/**
 * 表单，获得账户信息
 * @author xuhongfeng
 *
 */
public class GetAccountForm extends AbstractForm {
    private long accountId;
    private long token;
    
    public GetAccountForm(long accountId, long token) {
        this.accountId = accountId;
        this.token = token;
    }
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
