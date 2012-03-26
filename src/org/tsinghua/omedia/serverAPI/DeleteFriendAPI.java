package org.tsinghua.omedia.serverAPI;

import org.tsinghua.omedia.activity.OmediaActivityIntf;
import org.tsinghua.omedia.consts.ResultCode;
import org.tsinghua.omedia.consts.UrlConst;
import org.tsinghua.omedia.data.EmptyInstance.EmptyResultType;
import org.tsinghua.omedia.form.DeleteFriendForm;

/**
 * 
 * @author xuhongfeng
 *
 */
public abstract class DeleteFriendAPI extends AbstractServerAPI<DeleteFriendForm>{

    protected DeleteFriendAPI(DeleteFriendForm form,
            OmediaActivityIntf omediaActivity) {
        super(form, omediaActivity);
    }

    @Override
    protected String getUrl() {
        return UrlConst.deleteFriendUrl;
    }

    @Override
    protected void initResultCodeListener() {
        registerResultCodeListener(ResultCode.SUCCESS, new ResultCodeListener<EmptyResultType>(EmptyResultType.class) {

            @Override
            protected void innerRun(EmptyResultType result) {
                onSuccess();
            }
        });
    }

    protected abstract void onSuccess();
}
