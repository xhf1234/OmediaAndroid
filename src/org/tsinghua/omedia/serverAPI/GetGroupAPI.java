package org.tsinghua.omedia.serverAPI;

import org.tsinghua.omedia.activity.OmediaActivityIntf;
import org.tsinghua.omedia.annotation.json.JsonArray;
import org.tsinghua.omedia.annotation.json.JsonLong;
import org.tsinghua.omedia.consts.ResultCode;
import org.tsinghua.omedia.consts.UrlConst;
import org.tsinghua.omedia.data.Group;
import org.tsinghua.omedia.data.Jsonable;
import org.tsinghua.omedia.form.GetGroupForm;

/**
 * 
 * @author xuhongfeng
 *
 */
public abstract class GetGroupAPI extends AbstractServerAPI<GetGroupForm> {

    protected GetGroupAPI(GetGroupForm form, OmediaActivityIntf omediaActivity) {
        super(form, omediaActivity);
    }

    @Override
    protected String getUrl() {
        return UrlConst.getGroupUrl;
    }

    @Override
    protected void initResultCodeListener() {
        registerResultCodeListener(ResultCode.SUCCESS, new ResultCodeListener<ResultType>(ResultType.class) {
            @Override
            protected void innerRun(ResultType result) {
                onSuccess(result.groups, result.version);
            }
        });
    }
    
    protected abstract void onSuccess(Group[] groups, long groupVersion);

    public static class ResultType implements Jsonable {
        @JsonLong(name="version")
        private long version;
        @JsonArray(name="groups", type=Group.class)
        private Group[] groups;
    }
}
