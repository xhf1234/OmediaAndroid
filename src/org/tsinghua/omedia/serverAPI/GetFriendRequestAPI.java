package org.tsinghua.omedia.serverAPI;

import org.tsinghua.omedia.activity.OmediaActivityIntf;
import org.tsinghua.omedia.annotation.json.JsonArray;
import org.tsinghua.omedia.annotation.json.JsonLong;
import org.tsinghua.omedia.consts.ResultCode;
import org.tsinghua.omedia.consts.UrlConst;
import org.tsinghua.omedia.data.FriendRequest;
import org.tsinghua.omedia.data.Jsonable;
import org.tsinghua.omedia.form.GetFriendRequestForm;

/**
 * 
 * @author xuhongfeng
 *
 */
public abstract class GetFriendRequestAPI extends
        AbstractServerAPI<GetFriendRequestForm>{

    protected GetFriendRequestAPI(GetFriendRequestForm form, OmediaActivityIntf omediaActivity) {
        super(form, omediaActivity);
    }

    @Override
    protected String getUrl() {
        return UrlConst.GetFriendRequestUrl;
    }

    @Override
    protected void initResultCodeListener() {
        registerResultCodeListener(ResultCode.SUCCESS,
                new ResultCodeListener<GetFriendRequestAPI.ResultType>(ResultType.class) {
            
            @Override
            protected void innerRun(ResultType result) {
                onSuccess(result.version, result.requests);
            }
        });
    }

    protected abstract void onSuccess(long version, FriendRequest[] requests);
    
    public static class ResultType implements Jsonable {
        @JsonLong(name="version")
        private long version;
        @JsonArray(name="requests", type=FriendRequest.class)
        private FriendRequest[] requests;
    }
}
