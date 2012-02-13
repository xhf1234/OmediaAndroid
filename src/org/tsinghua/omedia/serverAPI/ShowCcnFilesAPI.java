package org.tsinghua.omedia.serverAPI;

import org.tsinghua.omedia.activity.OmediaActivityIntf;
import org.tsinghua.omedia.annotation.json.JsonArray;
import org.tsinghua.omedia.annotation.json.JsonLong;
import org.tsinghua.omedia.consts.ResultCode;
import org.tsinghua.omedia.consts.UrlConst;
import org.tsinghua.omedia.data.CcnFile;
import org.tsinghua.omedia.data.Jsonable;
import org.tsinghua.omedia.form.ShowCcnFilesForm;

/**
 * 
 * @author xuhongfeng
 *
 */
public abstract class ShowCcnFilesAPI extends AbstractServerAPI<ShowCcnFilesForm> {

    protected ShowCcnFilesAPI(ShowCcnFilesForm form,
            OmediaActivityIntf omediaActivity) {
        super(form, omediaActivity);
    }

    @Override
    protected String getUrl() {
        return UrlConst.ShowCcnFilesUrl;
    }

    @Override
    protected void initResultCodeListener() {
        registerResultCodeListener(ResultCode.SUCCESS,
                new ResultCodeListener<ResultType>(ResultType.class) {
            @Override
            protected void innerRun(ResultType result) {
                onSuccess(result.version, result.ccnFiles);
            }
        });
    }
    
    protected abstract void onSuccess(long version, CcnFile[] ccnFiles);

    public static class ResultType implements Jsonable {
        @JsonLong(name="version")
        private long version;
        @JsonArray(name="ccnFiles", type=CcnFile.class)
        private CcnFile[] ccnFiles;
    }
}
