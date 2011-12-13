package org.tsinghua.omedia.data;

import org.tsinghua.omedia.annotation.db.Column;
import org.tsinghua.omedia.annotation.db.NotNull;
import org.tsinghua.omedia.annotation.db.PrimaryKey;
import org.tsinghua.omedia.annotation.db.Table;
import org.tsinghua.omedia.consts.DatabaseConst.DataType;
import org.tsinghua.omedia.datasource.db.DbEntity;
import org.tsinghua.omedia.datasource.db.DbUtils;

import android.content.ContentValues;


/**
 * 
 * @author xuhongfeng
 *
 */
@Table(name="Account")
public class Account implements DbEntity {
    @PrimaryKey
    @NotNull
    @Column(name="accountId", type=DataType.BIGINT)
    private long accountId;
    @NotNull
    @Column(name="username", type=DataType.VARCHAR32)
    private String username;
    @NotNull
    @Column(name="email", type=DataType.VARCHAR255)
    private String email;
    @Column(name="realName", type=DataType.VARCHAR32)
    private String realName;
    @Column(name="address", type=DataType.VARCHAR255)
    private String address;
    @Column(name="phone", type=DataType.VARCHAR32)
    private String phone;
    
    @Override
    public ContentValues toContentValues() {
        return DbUtils.toContentValues(this);
    }
    public long getAccountId() {
        return accountId;
    }
    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getRealName() {
        return realName;
    }
    public void setRealName(String realName) {
        this.realName = realName;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
}
