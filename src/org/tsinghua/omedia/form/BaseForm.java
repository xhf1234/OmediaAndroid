package org.tsinghua.omedia.form;

import org.tsinghua.omedia.annotation.form.HttpParam;

/**
 * 
 * @author xuhongfeng
 *
 */
public class BaseForm extends AbstractForm {
    @HttpParam(name="accountId")
    protected long accountId;
    @HttpParam(name="token")
    protected long token;
    
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
