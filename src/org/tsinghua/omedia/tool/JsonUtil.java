package org.tsinghua.omedia.tool;

import java.io.IOException;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;

public class JsonUtil {
    private static final ObjectMapper ob = new ObjectMapper();
    
    @SuppressWarnings("unchecked")
    public static Map<String, Object> read(String json) throws IOException {
        return ob.readValue(json, Map.class);
    }
    
}
