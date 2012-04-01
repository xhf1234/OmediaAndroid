package org.tsinghua.omedia.data;

import org.tsinghua.omedia.annotation.json.JsonInt;
import org.tsinghua.omedia.annotation.json.JsonLong;
import org.tsinghua.omedia.annotation.json.JsonString;

/**
 * 
 * @author xuhongfeng
 *
 */
public class Group implements Jsonable {
    @JsonLong(name="groupId")
    private long groupId;
    @JsonLong(name="creatorId")
    private long creatorId;
    @JsonString(name="name")
    private String name;
    @JsonLong(name="createTime")
    private long createTime;
    @JsonInt(name="count")
    private int count;
    @JsonString(name="creatorName")
    private String creatorName;
    public long getGroupId() {
        return groupId;
    }
    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }
    public long getCreatorId() {
        return creatorId;
    }
    public void setCreatorId(long creatorId) {
        this.creatorId = creatorId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public long getCreateTime() {
        return createTime;
    }
    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }
    public int getCount() {
        return count;
    }
    public void setCount(int count) {
        this.count = count;
    }
    public String getCreatorName() {
        return creatorName;
    }
    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }
    
    

}
