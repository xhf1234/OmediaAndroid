package org.tsinghua.omedia.data;

import java.util.List;
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
    
    @SuppressWarnings("rawtypes")
    public JsonObject[] getArray(String key) {
        List list = (List) values.get(key);
        JsonObject[] array = null;
        if(list==null || list.size()==0) {
            array = new JsonObject[0];
        } else {
            array = new JsonObject[list.size()];
            for(int i=0; i<list.size(); i++) {
                @SuppressWarnings("unchecked")
                Map<String, Object> map = (Map<String, Object>) list.get(i);
                JsonObject jsonObject = new JsonObject(map);
                array[i] = jsonObject;
            }
        }
        return array;
    }
    
    public JsonObject getObject(String key) {
        @SuppressWarnings("unchecked")
        Map<String, Object> map = (Map<String, Object>) values.get(key);
        return new JsonObject(map);
    }
    
    public void putObject(String key, Object object) {
        values.put(key, object);
    }
}
