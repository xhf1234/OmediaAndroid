package org.tsinghua.omedia.data;

import org.tsinghua.omedia.annotation.db.Column;
import org.tsinghua.omedia.annotation.db.NotNull;
import org.tsinghua.omedia.annotation.db.PrimaryKey;
import org.tsinghua.omedia.annotation.db.Table;
import org.tsinghua.omedia.annotation.json.JsonLong;
import org.tsinghua.omedia.annotation.json.JsonString;
import org.tsinghua.omedia.consts.DatabaseConst.DataType;
import org.tsinghua.omedia.datasource.db.CursorHelper;
import org.tsinghua.omedia.datasource.db.DbEntity;
import org.tsinghua.omedia.datasource.db.DbUtils;

import android.content.ContentValues;
import android.database.Cursor;


/**
 * 
 * @author xuhongfeng
 *
 */
@Table(name="Account")
public class Account implements DbEntity, Jsonable {
    public static String COL_ACCOUNT_ID = "accountId";
    public static String COL_USER_NAME = "username";
    public static String COL_EMAIL = "email";
    public static String COL_REAL_NAME = "realName";
    public static String COL_ADDRESS = "address";
    public static String COL_PHONE = "phone";
    
    @JsonLong(name="accountId")
    @PrimaryKey
    @NotNull
    @Column(name="accountId", type=DataType.BIGINT)
    private long accountId;
    @JsonString(name="username")
    @NotNull
    @Column(name="username", type=DataType.VARCHAR32)
    private String username;
    @JsonString(name="email")
    @NotNull
    @Column(name="email", type=DataType.VARCHAR255)
    private String email;
    @JsonString(name="realName")
    @Column(name="realName", type=DataType.VARCHAR32)
    private String realName;
    @JsonString(name="address")
    @Column(name="address", type=DataType.VARCHAR255)
    private String address;
    @JsonString(name="phone")
    @Column(name="phone", type=DataType.VARCHAR32)
    private String phone;
    
    public static Account fromCursor(Cursor cursor) {
        CursorHelper helper = new CursorHelper(cursor);
        Account account = new Account();
        account.setAccountId(helper.getLong(COL_ACCOUNT_ID));
        account.setAddress(helper.getString(COL_ADDRESS));
        account.setEmail(helper.getString(COL_EMAIL));
        account.setPhone(helper.getString(COL_PHONE));
        account.setRealName(helper.getString(COL_REAL_NAME));
        account.setUsername(helper.getString(COL_USER_NAME));
        return account;
    }
    
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
