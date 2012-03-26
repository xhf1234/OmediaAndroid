package org.tsinghua.omedia.serverAPI;

import org.tsinghua.omedia.activity.OmediaActivityIntf;
import org.tsinghua.omedia.consts.ResultCode;
import org.tsinghua.omedia.consts.UrlConst;
import org.tsinghua.omedia.data.EmptyInstance.EmptyResultType;
import org.tsinghua.omedia.form.FriendRequestReplyForm;

/**
 * 
 * @author xuhongfeng
 *
 */
public abstract class FriendRequestReplyAPI extends AbstractServerAPI<FriendRequestReplyForm>{

    protected FriendRequestReplyAPI(FriendRequestReplyForm form,
            OmediaActivityIntf omediaActivity) {
        super(form, omediaActivity);
    }

    @Override
    protected String getUrl() {
        return UrlConst.friendRequestReplyUrl;
    }

    @Override
    protected void initResultCodeListener() {
        registerResultCodeListener(ResultCode.FriendRequestReply.ACCEPT_SUCCESS, new ResultCodeListener<EmptyResultType>(EmptyResultType.class) {
            @Override
            protected void innerRun(EmptyResultType result) {
                onAcceptSuccess();
            }
        });
        registerResultCodeListener(ResultCode.FriendRequestReply.REJECT_SUCCESS, new ResultCodeListener<EmptyResultType>(EmptyResultType.class) {
            @Override
            protected void innerRun(EmptyResultType result) {
                onRejectSuccess();
            }
        });
        registerResultCodeListener(ResultCode.FriendRequestReply.FAILED, new ResultCodeListener<EmptyResultType>(EmptyResultType.class) {
            @Override
            protected void innerRun(EmptyResultType result) {
                onFailed();
            }
        });
    }

    protected abstract void onAcceptSuccess();
    protected abstract void onRejectSuccess();
    protected abstract void onFailed();
}
