package org.tsinghua.omedia.datasource;

/**
 * 数据源 封装一下存取数据的对象和方法
 * @author xuhongfeng
 *
 */
public class DataSource {
    private static DataSource me;
    //singleton
    private DataSource(){}

    public static DataSource getInstance() {
        if(me != null) return me;
        synchronized (DataSource.class) {
            if(me == null) {
                me = new DataSource();
            }
        }
        return me;
    }
    
    public OmediaPreference getPreference() {
        return OmediaPreference.getInstance();
    }
}
