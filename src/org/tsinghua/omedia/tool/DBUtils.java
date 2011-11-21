package org.tsinghua.omedia.tool;

import java.lang.reflect.Field;

import org.tsinghua.omedia.annotation.database.Column;
import org.tsinghua.omedia.annotation.database.ColumnType;
import org.tsinghua.omedia.annotation.database.Table;

public class DBUtils {
    private static final Logger logger = Logger.getLogger(DBUtils.class);
    
    public static String getCreateSql(Class<?> tableClazz) {
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE ");
        String tableName;
        if(tableClazz.isAnnotationPresent(Table.class)) {
            Table tableAnno = tableClazz.getAnnotation(Table.class);
            tableName = tableAnno.name();
        } else {
            tableName = tableClazz.getSimpleName();
        }
        sb.append(tableName);
        sb.append("(");
        Field[] fields = tableClazz.getDeclaredFields();
        for(Field f:fields) {
            f.setAccessible(true);
            String columnName;
            if(f.isAnnotationPresent(Column.class)) {
                Column columnAnno = f.getAnnotation(Column.class);
                columnName = columnAnno.name();
            } else {
                columnName = tableClazz.getSimpleName();
            }
            sb.append(columnName+" ");
            if(f.getType() == Boolean.class) {
                sb.append(Column.Type.BOOLEAN.getName() +" ");
            } else if(f.getType() == Long.class) {
                sb.append(Column.Type.LONG.getName() + " ");//TODO
            } else {
                logger.error("unknow field type");
                return null;
            }
        }
        sb.append(")");
        return sb.toString();
    }
}
