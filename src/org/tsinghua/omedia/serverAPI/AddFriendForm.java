package org.tsinghua.omedia.serverAPI;

import org.tsinghua.omedia.annotation.form.HttpParam;
import org.tsinghua.omedia.form.BaseForm;

public class AddFriendForm extends BaseForm {
    @HttpParam(name="friendId")
    private long friendId;
    @HttpParam(name="msg")
    private String msg;
    
    public long getFriendId() {
        return friendId;
    }
    public void setFriendId(long friendId) {
        this.friendId = friendId;
    }
    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }
    
    
}
