package org.tsinghua.omedia.datasource.db;

import java.lang.reflect.Field;

import org.tsinghua.omedia.annotation.db.Column;
import org.tsinghua.omedia.annotation.db.NotNull;
import org.tsinghua.omedia.annotation.db.PrimaryKey;
import org.tsinghua.omedia.annotation.db.Table;
import org.tsinghua.omedia.consts.DatabaseConst.DataType;
import org.tsinghua.omedia.tool.Logger;

import android.content.ContentValues;

/**
 * 数据库工具类
 * @author xuhongfeng
 *
 */
public class DbUtils {
    private static final Logger logger = Logger.getLogger(DbUtils.class);
    
    public static String getTableName(Class<? extends DbEntity> entityClass) {
        Table tableAnno = entityClass.getAnnotation(Table.class);
        String tableName = tableAnno.name();
        return tableName;
    }
    
    public static String getCreateSql(Class<? extends DbEntity> entityClass) {
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE ");
        Table tableAnno = entityClass.getAnnotation(Table.class);
        String tableName = tableAnno.name();
        sb.append(tableName);
        sb.append("(");
        Field[] fields = entityClass.getDeclaredFields();
        boolean isFirstField = true;
        for(Field field:fields) {
            field.setAccessible(true);
            if(field.isAnnotationPresent(Column.class)) {
                if(!isFirstField) {
                    sb.append(", ");
                }
                isFirstField = false;
                Column columnAnno = field.getAnnotation(Column.class);
                String columnName = columnAnno.name();
                DataType columnType = columnAnno.type();
                sb.append(columnName+" ");
                sb.append(columnType+" ");
                if(field.isAnnotationPresent(PrimaryKey.class)) {
                    sb.append("PRIMARY KEY ");
                }
                if(field.isAnnotationPresent(NotNull.class)) {
                    sb.append("NOT NULL ");
                }
            }
        }
        sb.append(")");
        return sb.toString();
    }
    
    public static ContentValues toContentValues(DbEntity entity) {
        ContentValues cv = new ContentValues();
        Class<? extends DbEntity> entityClass = entity.getClass();
        Field[] fields = entityClass.getDeclaredFields();
        for(Field field:fields) {
            field.setAccessible(true);
            if(field.isAnnotationPresent(Column.class)) {
                Column columnAnno = field.getAnnotation(Column.class);
                String columnName = columnAnno.name();
                DataType columnType = columnAnno.type();
                try {
                    switch(columnType) {
                    case BIGINT :
                        Long longValue = field.getLong(entity);
                        cv.put(columnName,longValue);
                        break;
                    case VARCHAR32:
                    case VARCHAR255:
                        String stringValue = (String) field.get(entity);
                        cv.put(columnName,stringValue);
                        break;
                    default :
                        logger.error("unknow type");
                    }
                } catch (IllegalArgumentException e) {
                    logger.error(e);
                } catch (IllegalAccessException e) {
                    logger.error(e);
                }
            }
        }
        return cv;
    }
}
