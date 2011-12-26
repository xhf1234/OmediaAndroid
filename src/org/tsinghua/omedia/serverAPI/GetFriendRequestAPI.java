package org.tsinghua.omedia.serverAPI;

import org.tsinghua.omedia.activity.OmediaActivityIntf;
import org.tsinghua.omedia.consts.ResultCode;
import org.tsinghua.omedia.consts.UrlConst;
import org.tsinghua.omedia.data.Jsonable;
import org.tsinghua.omedia.form.GetFriendRequestForm;
import org.tsinghua.omedia.serverAPI.GetFriendRequestAPI.ResultType;

/**
 * 
 * @author xuhongfeng
 *
 */
public class GetFriendRequestAPI extends
        AbstractServerAPI<GetFriendRequestForm, ResultType>{

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
                new ResultCodeListener<ResultType>() {
                    @Override
                    protected void exec(ResultType result) {
                        //TODO
                    }
        });
    }

    @Override
    protected Class<ResultType> getResultType() {
        return ResultType.class;
    }



    public static class ResultType implements Jsonable {
        //TODO
    }
}
