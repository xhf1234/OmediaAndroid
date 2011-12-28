package org.tsinghua.omedia.serverAPI;

import org.tsinghua.omedia.activity.OmediaActivityIntf;
import org.tsinghua.omedia.annotation.json.JsonArray;
import org.tsinghua.omedia.annotation.json.JsonLong;
import org.tsinghua.omedia.consts.ResultCode;
import org.tsinghua.omedia.consts.UrlConst;
import org.tsinghua.omedia.data.Account;
import org.tsinghua.omedia.data.Jsonable;
import org.tsinghua.omedia.form.GetFriendsForm;

/**
 * 
 * @author xuhongfeng
 *
 */
public abstract class GetFriendsAPI extends AbstractServerAPI<GetFriendsForm> {

    protected GetFriendsAPI(GetFriendsForm form, OmediaActivityIntf omediaActivity) {
        super(form, omediaActivity);
    }

    @Override
    protected String getUrl() {
        return UrlConst.GetFriendsUrl;
    }

    @Override
    protected void initResultCodeListener() {
        registerResultCodeListener(ResultCode.SUCCESS,
                new ResultCodeListener<GetFriendsAPI.ResultType>(ResultType.class) {
            
            @Override
            protected void innerRun(ResultType result) {
                onSuccess(result.friends, result.version);
            }
        });
    }

    protected abstract void onSuccess(Account[] friends, long version);

    private static class ResultType implements Jsonable {
        @JsonLong(name="version")
        private long version;
        @JsonArray(name="friends", type=Account.class)
        private Account friends[];
    }
}
