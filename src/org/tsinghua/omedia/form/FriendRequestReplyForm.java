package org.tsinghua.omedia.form;

import org.tsinghua.omedia.annotation.form.HttpParam;

/**
 * 
 * @author xuhongfeng
 *
 */
public class FriendRequestReplyForm extends BaseForm {
    public static final int REPLY_ACCEPT = 1;
    public static final int REPLY_REJECT = 0;
    
    @HttpParam(name="friendId")
    private long friendId;
    
    //reply code.   1 for ACCEPT, 0 for REJECT
    @HttpParam(name="reply")
    private int reply;

    public long getFriendId() {
        return friendId;
    }

    public void setFriendId(long friendId) {
        this.friendId = friendId;
    }

    public int getReply() {
        return reply;
    }

    public void setReply(int reply) {
        this.reply = reply;
    }

    
}
