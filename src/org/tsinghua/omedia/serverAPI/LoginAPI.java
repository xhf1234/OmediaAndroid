package org.tsinghua.omedia.serverAPI;

import org.tsinghua.omedia.activity.OmediaActivityIntf;
import org.tsinghua.omedia.annotation.json.JsonLong;
import org.tsinghua.omedia.consts.ResultCode.Login;
import org.tsinghua.omedia.consts.UrlConst;
import org.tsinghua.omedia.data.EmptyInstance;
import org.tsinghua.omedia.data.EmptyInstance.EmptyResultType;
import org.tsinghua.omedia.data.Jsonable;
import org.tsinghua.omedia.form.LoginForm;

/**
 * 
 * @author xuhongfeng
 *
 */
public abstract class LoginAPI extends AbstractServerAPI<LoginForm> {
    
    protected LoginAPI(LoginForm form, OmediaActivityIntf omediaActivity) {
        super(form, omediaActivity);
    }
    
    @Override
    protected String getUrl() {
        return UrlConst.LoginUrl;
    }

    @Override
    protected void initResultCodeListener() {
        registerResultCodeListener(Login.SUCCESS,
                new ResultCodeListener<ResultType>(ResultType.class) {
                    @Override
                    protected void innerRun(ResultType result) {
                        onLoginSuccess(result.accountId, result.token);
                    }
        });
        registerResultCodeListener(Login.FAILED,
                new ResultCodeListener<EmptyInstance.EmptyResultType>(EmptyResultType.class) {
                    @Override
                    protected void innerRun(EmptyResultType result) {
                        onLoginFailed();
                    }
        });
        registerResultCodeListener(Login.SOFTWARE_NEED_UPDATE,
                new ResultCodeListener<EmptyInstance.EmptyResultType>(EmptyResultType.class) {

                    @Override
                    protected void innerRun(EmptyResultType result) {
                        onSoftwareNeedUpdate();
                    }
        });
    }

    protected abstract void onLoginSuccess(long accountId, long token);
    protected abstract void onLoginFailed();
    protected abstract void onSoftwareNeedUpdate();

    private static class ResultType implements Jsonable {
        @JsonLong(name="accountId")
        private long accountId;
        @JsonLong(name="token")
        private long token;
    }
}
