package org.tsinghua.omedia.serverAPI;

import org.tsinghua.omedia.R;
import org.tsinghua.omedia.activity.OmediaActivityIntf;
import org.tsinghua.omedia.consts.ResultCode;
import org.tsinghua.omedia.consts.UrlConst;
import org.tsinghua.omedia.data.EmptyInstance;
import org.tsinghua.omedia.data.EmptyInstance.EmptyResultType;

public abstract class AddFriendAPI extends AbstractServerAPI<AddFriendForm, EmptyResultType> {

    protected AddFriendAPI(AddFriendForm form, OmediaActivityIntf omediaActivity) {
        super(form, omediaActivity);
    }
    
    @Override
    protected String getUrl() {
        return UrlConst.AddFriendUrl;
    }

    @Override
    protected Class<EmptyResultType> getResultType() {
        return EmptyResultType.class;
    }



    @Override
    protected void initResultCodeListener() {
        registerResultCodeListener(ResultCode.SUCCESS, new ResultCodeListener<EmptyInstance.EmptyResultType>() {
            
            @Override
            protected void exec(EmptyResultType result) {
                onSuccess();
            }
        });
        registerResultCodeListener(ResultCode.AddFriend.ADD_YOUR_SELF, new ResultCodeListener<EmptyInstance.EmptyResultType>() {
            
            @Override
            protected void exec(EmptyResultType result) {
                omediaActivity.showAlertDialog(R.string.add_yourself);
            }
        });
        registerResultCodeListener(ResultCode.AddFriend.ALREADY_FRIEND, new ResultCodeListener<EmptyInstance.EmptyResultType>() {
            
            @Override
            protected void exec(EmptyResultType result) {
                omediaActivity.showAlertDialog(R.string.already_your_friend);
            }
        });
    }

    protected abstract void onSuccess();
}
