package org.tsinghua.omedia.serverAPI;

import org.tsinghua.omedia.activity.OmediaActivityIntf;
import org.tsinghua.omedia.annotation.json.JsonArray;
import org.tsinghua.omedia.annotation.json.JsonLong;
import org.tsinghua.omedia.consts.ResultCode;
import org.tsinghua.omedia.consts.UrlConst;
import org.tsinghua.omedia.data.CcnFile;
import org.tsinghua.omedia.data.EmptyInstance.EmptyResultType;
import org.tsinghua.omedia.data.Jsonable;
import org.tsinghua.omedia.form.ShowFriendCcnFileForm;

/**
 * 
 * @author xuhongfeng
 *
 */
public abstract class ShowFriendCcnFilesAPI extends AbstractServerAPI<ShowFriendCcnFileForm> {

    protected ShowFriendCcnFilesAPI(ShowFriendCcnFileForm form,
            OmediaActivityIntf omediaActivity) {
        super(form, omediaActivity);
    }

    @Override
    protected String getUrl() {
        return UrlConst.ShowFriendCcnFilesUrl;
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
        registerResultCodeListener(ResultCode.ShowFriendCcnFile.NOT_YOUR_FRIEND,
                new ResultCodeListener<EmptyResultType>(EmptyResultType.class) {
                    @Override
                    protected void innerRun(EmptyResultType result) {
                        onNotYourFriend();
                    }
                });
    }
    
    protected abstract void onSuccess(long version, CcnFile[] ccnFiles);
    protected abstract void onNotYourFriend();

    public static class ResultType implements Jsonable {
        @JsonLong(name="version")
        private long version;
        @JsonArray(name="ccnFiles", type=CcnFile.class)
        private CcnFile[] ccnFiles;
    }
}
