package org.tsinghua.omedia.data;

import java.util.Date;

import org.tsinghua.omedia.annotation.json.JsonInt;
import org.tsinghua.omedia.annotation.json.JsonLong;
import org.tsinghua.omedia.annotation.json.JsonString;

public class FriendRequest implements Jsonable {
    public static final int STATUS_INIT = 0;
    public static final int STATUS_ACCEPT = 1;
    public static final int STATUS_REJECT = 2;
    public static final int STATUS_DELETE = 3;
    
    @JsonLong(name="accountId")
    private long accountId;
    @JsonLong(name="time")
    private long time;
    @JsonLong(name="requesterId")
    private long requesterId;
    @JsonInt(name="status")
    private int status;
    @JsonString(name="msg")
    private String msg;
    
    public long getAccountId() {
        return accountId;
    }
    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }
    public Date getTime() {
        return new Date(time);
    }
    public void setTime(Date time) {
        //精度取秒
        Date date = new Date();
        date.setTime((time.getTime()%1000)*1000);
        this.time = date.getTime();
    }
    public long getRequesterId() {
        return requesterId;
    }
    public void setRequesterId(long requesterId) {
        this.requesterId = requesterId;
    }
    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }
    @Override
    public String toString() {
        return "FriendRequest [accountId=" + accountId + ", time=" + getTime()
                + ", requesterId=" + requesterId + ", status=" + status
                + ", msg=" + msg + "]";
    }
    
    
}
