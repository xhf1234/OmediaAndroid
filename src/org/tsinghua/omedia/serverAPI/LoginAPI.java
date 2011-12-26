package org.tsinghua.omedia.serverAPI;

import org.tsinghua.omedia.activity.OmediaActivityIntf;
import org.tsinghua.omedia.annotation.json.JsonLong;
import org.tsinghua.omedia.consts.ResultCode.Login;
import org.tsinghua.omedia.consts.UrlConst;
import org.tsinghua.omedia.data.Jsonable;
import org.tsinghua.omedia.form.LoginForm;
import org.tsinghua.omedia.serverAPI.LoginAPI.ResultType;

/**
 * 
 * @author xuhongfeng
 *
 */
public abstract class LoginAPI extends
        AbstractServerAPI<LoginForm, ResultType> {
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
                new ResultCodeListener<ResultType>() {
                    @Override
                    protected void exec(ResultType result) {
                        onLoginSuccess(result.accountId, result.token);
                    }
        });
        registerResultCodeListener(Login.FAILED,
                new ResultCodeListener<ResultType>() {
                    @Override
                    protected void exec(ResultType result) {
                        onLoginFailed();
                    }
        });
        registerResultCodeListener(Login.SOFTWARE_NEED_UPDATE,
                new ResultCodeListener<ResultType>() {
                    @Override
                    protected void exec(ResultType result) {
                        onSoftwareNeedUpdate();
                    }
        });
    }

    protected abstract void onLoginSuccess(long accountId, long token);
    protected abstract void onLoginFailed();
    protected abstract void onSoftwareNeedUpdate();
    
    @Override
    protected Class<ResultType> getResultType() {
        return ResultType.class;
    }



    public static class ResultType implements Jsonable {
        @JsonLong(name="accountId")
        private long accountId;
        @JsonLong(name="token")
        private long token;
    }
}
