package org.tsinghua.omedia.serverAPI;

import org.tsinghua.omedia.activity.OmediaActivityIntf;
import org.tsinghua.omedia.consts.ResultCode;
import org.tsinghua.omedia.consts.UrlConst;
import org.tsinghua.omedia.data.Account;
import org.tsinghua.omedia.data.JsonObject;
import org.tsinghua.omedia.form.GetAccountForm;

/**
 * 
 * @author xuhongfeng
 *
 */
public abstract class GetAccountAPI extends AbstractServerAPI<GetAccountForm>{

    private String username;
    
    protected GetAccountAPI(GetAccountForm form, String username, OmediaActivityIntf omediaActivity) {
        super(form, UrlConst.GetAccountUrl, omediaActivity);
        this.username = username;
    }

    @Override
    protected void initResultCodeListener() {
        registerResultCodeListener(ResultCode.SUCCESS, new ResultCodeListener() {
            @Override
            protected void exec(JsonObject result) {
                String email = result.getString("email");
                String realName = result.getString("realName");
                String address = result.getString("address");
                String phone = result.getString("phone");
                long version = result.getLong("version");
                Account account = new Account();
                account.setAccountId(form.getAccountId());
                account.setAddress(address);
                account.setEmail(email);
                account.setPhone(phone);
                account.setRealName(realName);
                account.setUsername(username);
                onGetAccountSuccess(account, version);
            }
        });
    }
    
    protected abstract void onGetAccountSuccess(Account account, long version);

}
