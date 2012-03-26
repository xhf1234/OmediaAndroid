package org.tsinghua.omedia.form;

import org.tsinghua.omedia.annotation.form.HttpParam;

/**
 * 
 * @author xuhongfeng
 * 
 */
public class DeleteFriendForm extends BaseForm {
	@HttpParam(name = "friendId")
	private long friendId;

	public long getFriendId() {
		return friendId;
	}

	public void setFriendId(long friendId) {
		this.friendId = friendId;
	}

}
