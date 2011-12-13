package org.tsinghua.omedia.consts;

/**
 * 数据库相关的常量
 * @author xuhongfeng
 *
 */
public class DatabaseConst {
    /**
     * 数据库名
     */
    public static final String DB_NAME = "Omedia_DB";
    /**
     * 当前数据库的版本
     */
    public static final int DB_VERSION = 1;
    
    /**
     * 数据类型
     * @author xuhongfeng
     *
     */
    public static enum DataType {
        BIGINT("BIGINT"),VARCHAR32("VARCHAR(32)")
            ,VARCHAR255("VARCHAR(255)");
        
        private DataType(String typeName) {
            this.typeName = typeName;
        }
        
        private String typeName;

        @Override
        public String toString() {
            return typeName;
        }
        
        
    }
}
