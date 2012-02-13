package org.tsinghua.omedia.data;

import java.util.Date;

import org.tsinghua.omedia.annotation.json.JsonInt;
import org.tsinghua.omedia.annotation.json.JsonLong;
import org.tsinghua.omedia.annotation.json.JsonString;

/**
 * 
 * @author xuhongfeng
 *
 */
public class CcnFile implements Jsonable {
    public static final int TYPE_PRIVATE = 0;
    public static final int TYPE_PUBLIC = TYPE_PRIVATE+1;
    
    @JsonLong(name="accountId")
    private long accountId;
    @JsonLong(name="time")
    private long time;
    @JsonString(name="ccnName")
    private String ccnName;
    @JsonString(name="filePath")
    private String filePath;
    @JsonInt(name="type")
    private int type;
    @JsonLong(name="size")
    private long size;
    
    public CcnFile() {
        
    }
    
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
        this.time = time.getTime();
    }
    public String getCcnname() {
        return ccnName;
    }
    public void setCcnname(String ccnName) {
        this.ccnName = ccnName;
    }
    public String getFilePath() {
        return filePath;
    }
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
    public int getType() {
        return type;
    }
    public void setType(int type) {
        this.type = type;
    }

    public String getCcnName() {
        return ccnName;
    }

    public void setCcnName(String ccnName) {
        this.ccnName = ccnName;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "CcnFile [accountId=" + accountId + ", time=" + time
                + ", ccnName=" + ccnName + ", filePath=" + filePath + ", type="
                + type + ", size=" + size + "]";
    }
    
}
