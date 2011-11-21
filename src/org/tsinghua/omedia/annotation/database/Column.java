package org.tsinghua.omedia.annotation.database;

/**
 * 
 * @author xuhongfeng
 * 
 */
public @interface Column {
    public enum Type {
        BOOLEAN("BOOLEAN"), LONG("BIGINT"), STRING("VARCHAR(32)");

        private String name;

        private Type(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    String name();
}
