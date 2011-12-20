package org.tsinghua.omedia.serverAPI;

import org.tsinghua.omedia.activity.OmediaActivityIntf;
import org.tsinghua.omedia.consts.ResultCode;
import org.tsinghua.omedia.consts.UrlConst;
import org.tsinghua.omedia.data.JsonObject;
import org.tsinghua.omedia.form.CheckDataVersionForm;

/**
 * 
 * @author xuhongfeng
 *
 */
public abstract class CheckDataVersionAPI extends AbstractServerAPI<CheckDataVersionForm> {

    protected CheckDataVersionAPI(CheckDataVersionForm form, OmediaActivityIntf omediaActivity) {
        super(form, UrlConst.CheckDataVersionUrl, omediaActivity);
    }

    @Override
    protected void initResultCodeListener() {
        registerResultCodeListener(ResultCode.SUCCESS, new ResultCodeListener() {
            @Override
            protected void exec(JsonObject result) {
                long accountVersion = result.getLong("account");
                long friendRequestVersion = result.getLong("friendRequest");
                long friendsVersion = result.getLong("friendsVersion");
                onSuccess(accountVersion, friendRequestVersion, friendsVersion);
            }
        });
    }
    
    protected abstract void onSuccess(long accountVersion, long friendRequestVersion, long friendsVersion);

}
