package org.tsinghua.omedia.data;

import java.util.Map;

/**
 * Json对象
 * @author xuhongfeng
 *
 */
public class JsonObject {
    private Map<String, Object> values;
    
    public JsonObject(Map<String, Object> values) {
        super();
        this.values = values;
    }

    public long getLong(String key) {
        // values.get(key)有可能是Long也有可能是Integer
        // 所以先转为String
        return Long.parseLong(values.get(key).toString());
    }
    
    public int getInt(String key) {
        // values.get(key)有可能是Long也有可能是Integer
        // 所以先转为String
        return Integer.parseInt(values.get(key).toString());
    }
    
    public String getString(String key) {
        return (String) values.get(key);
    }
}
