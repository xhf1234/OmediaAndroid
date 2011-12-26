package org.tsinghua.omedia.serverAPI;

import org.tsinghua.omedia.activity.OmediaActivityIntf;
import org.tsinghua.omedia.annotation.json.JsonLong;
import org.tsinghua.omedia.consts.ResultCode;
import org.tsinghua.omedia.consts.UrlConst;
import org.tsinghua.omedia.data.Jsonable;
import org.tsinghua.omedia.form.CheckDataVersionForm;
import org.tsinghua.omedia.serverAPI.CheckDataVersionAPI.ResultType;

/**
 * 
 * @author xuhongfeng
 *
 */
public abstract class CheckDataVersionAPI extends AbstractServerAPI<CheckDataVersionForm, ResultType> {

    protected CheckDataVersionAPI(CheckDataVersionForm form, OmediaActivityIntf omediaActivity) {
        super(form, omediaActivity);
    }

    @Override
    protected String getUrl() {
        return UrlConst.CheckDataVersionUrl;
    }



    @Override
    protected void initResultCodeListener() {
        registerResultCodeListener(ResultCode.SUCCESS, new ResultCodeListener<ResultType> () {

            @Override
            protected void exec(ResultType result) {
                onSuccess(result.accountVersion, result.friendRequestVersion,
                        result.friendsVersion);
            }
        });
    }
    
    protected abstract void onSuccess(long accountVersion, long friendRequestVersion, long friendsVersion);

    @Override
    protected Class<ResultType> getResultType() {
        return ResultType.class;
    }



    public static class ResultType implements Jsonable {
        @JsonLong(name="account")
        private long accountVersion;
        @JsonLong(name="friendRequest")
        private long friendRequestVersion;
        @JsonLong(name="friendsVersion")
        private long friendsVersion;
    }
}
