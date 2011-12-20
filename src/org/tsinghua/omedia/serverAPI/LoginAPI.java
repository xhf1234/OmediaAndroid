package org.tsinghua.omedia.serverAPI;

import org.tsinghua.omedia.activity.OmediaActivityIntf;
import org.tsinghua.omedia.consts.ResultCode.Login;
import org.tsinghua.omedia.consts.UrlConst;
import org.tsinghua.omedia.data.JsonObject;
import org.tsinghua.omedia.form.LoginForm;

/**
 * 
 * @author xuhongfeng
 *
 */
public abstract class LoginAPI extends AbstractServerAPI<LoginForm> {
    protected LoginAPI(LoginForm form, OmediaActivityIntf omediaActivity) {
        super(form, UrlConst.LoginUrl, omediaActivity);
    }
    @Override
    protected void initResultCodeListener() {
        registerResultCodeListener(Login.SUCCESS, new ResultCodeListener() {
            @Override
            protected void exec(JsonObject result) {
                Long accountId = result.getLong("accountId");
                Long token = result.getLong("token");
                onLoginSuccess(accountId, token);
            }
        });
        registerResultCodeListener(Login.FAILED, new ResultCodeListener() {
            @Override
            protected void exec(JsonObject result) {
                onLoginFailed();
            }
        });
        registerResultCodeListener(Login.SOFTWARE_NEED_UPDATE, new ResultCodeListener() {
            @Override
            protected void exec(JsonObject result) {
                onSoftwareNeedUpdate();
            }
        });
    }

    protected abstract void onLoginSuccess(long accountId, long token);
    protected abstract void onLoginFailed();
    protected abstract void onSoftwareNeedUpdate();
}
