package org.tsinghua.omedia.datasource.db;

import android.content.ContentValues;

/**
 * 定义数据库对象的接口
 * @author xuhongfeng
 *
 */
public interface DbEntity {
    
    /**
     * 
     * @return
     */
    public ContentValues toContentValues();
}
