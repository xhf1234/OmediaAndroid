package org.tsinghua.omedia.serverAPI;

import org.tsinghua.omedia.activity.OmediaActivityIntf;
import org.tsinghua.omedia.annotation.json.JsonLong;
import org.tsinghua.omedia.annotation.json.JsonString;
import org.tsinghua.omedia.consts.ResultCode;
import org.tsinghua.omedia.consts.UrlConst;
import org.tsinghua.omedia.data.Account;
import org.tsinghua.omedia.data.Jsonable;
import org.tsinghua.omedia.form.GetAccountForm;

/**
 * 
 * @author xuhongfeng
 *
 */
public abstract class GetAccountAPI extends AbstractServerAPI<GetAccountForm>{

    private String username;
    
    protected GetAccountAPI(GetAccountForm form, String username, OmediaActivityIntf omediaActivity) {
        super(form, omediaActivity);
        this.username = username;
    }

    @Override
    protected String getUrl() {
        return UrlConst.GetAccountUrl;
    }

    @Override
    protected void initResultCodeListener() {
        registerResultCodeListener(ResultCode.SUCCESS,
                new ResultCodeListener<ResultType>(ResultType.class) {

            @Override
            protected void innerRun(ResultType result) {
                Account account = new Account();
                account.setAccountId(form.getAccountId());
                account.setAddress(result.address);
                account.setEmail(result.email);
                account.setPhone(result.phone);
                account.setRealName(result.realName);
                account.setUsername(username);
                onGetAccountSuccess(account, result.version);
            }
        });
    }
    
    protected abstract void onGetAccountSuccess(Account account, long version);

    public static class ResultType implements Jsonable {
        @JsonString(name="email")
        private String email;
        @JsonString(name="realName")
        private String realName;
        @JsonString(name="address")
        private String address;
        @JsonString(name="phone")
        private String phone;
        @JsonLong(name="version")
        private long version;
    }
}
