package org.tsinghua.omedia.annotation.db;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.tsinghua.omedia.consts.DatabaseConst;

/**
 * 数据表的一列
 * @author xuhongfeng
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Column {
    //列名
    public String name();
    //类型
    public DatabaseConst.DataType type();
}
