package org.tsinghua.omedia.datasource.db;

import static org.tsinghua.omedia.consts.DatabaseConst.DB_NAME;
import static org.tsinghua.omedia.consts.DatabaseConst.DB_VERSION;

import org.tsinghua.omedia.OmediaApplication;
import org.tsinghua.omedia.data.Account;
import org.tsinghua.omedia.tool.Logger;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 封装数据库操作
 * @author xuhongfeng
 *
 */
public class DataBase extends SQLiteOpenHelper {
    private static final Logger logger = Logger.getLogger(DataBase.class);
    private static DataBase me;
    
    private SQLiteDatabase db;

    private DataBase() {
        super(OmediaApplication.getInstance().getApplicationContext(), DB_NAME, null, DB_VERSION);
        db = super.getWritableDatabase();
    }
    
    public static DataBase getInstance() {
        if(me != null) {
            return me;
        }
        synchronized (DataBase.class) {
            if(me == null) {
                me = new DataBase();
            }
        }
        return me;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;
        db.execSQL(DbUtils.getCreateSql(Account.class));
        logger.debug("create table:" + DbUtils.getCreateSql(Account.class));
    }
    
    public void saveOrUpdateAccount(Account account) {
        db.replaceOrThrow(DbUtils.getTableName(Account.class), null,
                account.toContentValues());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
        this.db = db;
    }

}
