package org.tsinghua.omedia.serverAPI;

import org.tsinghua.omedia.activity.OmediaActivityIntf;
import org.tsinghua.omedia.annotation.json.JsonLong;
import org.tsinghua.omedia.consts.ResultCode;
import org.tsinghua.omedia.consts.UrlConst;
import org.tsinghua.omedia.data.Jsonable;
import org.tsinghua.omedia.form.CheckDataVersionForm;

/**
 * 
 * @author xuhongfeng
 *
 */
public abstract class CheckDataVersionAPI extends AbstractServerAPI<CheckDataVersionForm> {

    protected CheckDataVersionAPI(CheckDataVersionForm form, OmediaActivityIntf omediaActivity) {
        super(form, omediaActivity);
    }

    @Override
    protected String getUrl() {
        return UrlConst.CheckDataVersionUrl;
    }

    @Override
    protected void initResultCodeListener() {
        registerResultCodeListener(ResultCode.SUCCESS, new ResultCodeListener<ResultType> (ResultType.class) {
            @Override
            protected void innerRun(ResultType result) {
                onSuccess(result.accountVersion, result.friendRequestVersion,
                        result.friendsVersion, result.configVersion, result.ccnFileVersion);
            }
        });
    }
    
    protected abstract void onSuccess(long accountVersion, long friendRequestVersion, long friendsVersion
            , long configVersion, long ccnFileVersion);

    public static class ResultType implements Jsonable {
        @JsonLong(name="account")
        private long accountVersion;
        @JsonLong(name="friendRequest")
        private long friendRequestVersion;
        @JsonLong(name="friends")
        private long friendsVersion;
        @JsonLong(name="config")
        private long configVersion;
        @JsonLong(name="ccnFile")
        private long ccnFileVersion;
    }
}
