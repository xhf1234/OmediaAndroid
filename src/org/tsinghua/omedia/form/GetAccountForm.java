package org.tsinghua.omedia.form;


/**
 * 表单，获得账户信息
 * @author xuhongfeng
 *
 */
public class GetAccountForm extends BaseForm {
    
    public GetAccountForm(long accountId, long token) {
        this.accountId = accountId;
        this.token = token;
    }
}
