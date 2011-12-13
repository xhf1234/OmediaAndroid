package org.tsinghua.omedia.tool;

import java.io.IOException;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;

/**
 * Json 工具类
 * @author xuhongfeng
 *
 */
public class JsonUtils {
    private static final ObjectMapper ob = new ObjectMapper();
    
    @SuppressWarnings("unchecked")
    public static Map<String, Object> read(String json) throws IOException {
        return ob.readValue(json, Map.class);
    }
    
}
