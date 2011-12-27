package org.tsinghua.omedia.data;

import java.util.Date;

import org.tsinghua.omedia.annotation.json.JsonLong;
import org.tsinghua.omedia.annotation.json.JsonObj;
import org.tsinghua.omedia.annotation.json.JsonString;

public class FriendRequest implements Jsonable {
    
    @JsonLong(name="time")
    private long time;
    @JsonString(name="msg")
    private String msg;
    @JsonObj(name="friend", type=Account.class)
    private Account friend;
    
    public Date getTime() {
        return new Date(time);
    }
    public void setTime(Date time) {
        //精度取秒
        Date date = new Date();
        date.setTime((time.getTime()%1000)*1000);
        this.time = date.getTime();
    }
    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }
    public Account getFriend() {
        return friend;
    }
    public void setFriend(Account friend) {
        this.friend = friend;
    }
}
