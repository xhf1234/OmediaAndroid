package org.tsinghua.omedia.serverAPI;

import org.tsinghua.omedia.activity.OmediaActivityIntf;
import org.tsinghua.omedia.consts.ResultCode;
import org.tsinghua.omedia.consts.UrlConst;
import org.tsinghua.omedia.data.JsonObject;
import org.tsinghua.omedia.form.GetFriendRequestForm;

/**
 * 
 * @author xuhongfeng
 *
 */
public class GetFriendRequestAPI extends AbstractServerAPI<GetFriendRequestForm>{

    protected GetFriendRequestAPI(GetFriendRequestForm form, OmediaActivityIntf omediaActivity) {
        super(form, UrlConst.GetFriendRequestUrl, omediaActivity);
    }

    @Override
    protected void initResultCodeListener() {
        registerResultCodeListener(ResultCode.SUCCESS, new ResultCodeListener() {
            @Override
            protected void exec(JsonObject result) {
                long version = result.getLong("version");
                //TDODO
            }
        });
    }

}
