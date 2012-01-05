package org.tsinghua.omedia.serverAPI;

import org.tsinghua.omedia.activity.OmediaActivityIntf;
import org.tsinghua.omedia.annotation.json.JsonLong;
import org.tsinghua.omedia.annotation.json.JsonObj;
import org.tsinghua.omedia.consts.ResultCode;
import org.tsinghua.omedia.consts.UrlConst;
import org.tsinghua.omedia.data.Config;
import org.tsinghua.omedia.data.Jsonable;
import org.tsinghua.omedia.form.GetConfigForm;

/**
 * 
 * @author xuhongfeng
 *
 */
public abstract class GetConfigAPI extends AbstractServerAPI<GetConfigForm> {

    protected GetConfigAPI(GetConfigForm form, OmediaActivityIntf omediaActivity) {
        super(form, omediaActivity);
    }

    @Override
    protected String getUrl() {
        return UrlConst.GetConfigUrl;
    }

    @Override
    protected void initResultCodeListener() {
        registerResultCodeListener(ResultCode.SUCCESS,
                new ResultCodeListener<ResultType>(ResultType.class) {
                    @Override
                    protected void innerRun(ResultType result) {
                        onSuccess(result.version, result.config);
                    }
        });
    }
    
    protected abstract void onSuccess(long version, Config config);
    
    public static class ResultType implements Jsonable {
        @JsonObj(name="config", type=Config.class)
        private Config config;
        @JsonLong(name="version")
        private long version;
    }
}
