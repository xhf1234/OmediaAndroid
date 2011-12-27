package org.tsinghua.omedia.tool;

import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.tsinghua.omedia.annotation.json.JsonArray;
import org.tsinghua.omedia.annotation.json.JsonInt;
import org.tsinghua.omedia.annotation.json.JsonLong;
import org.tsinghua.omedia.annotation.json.JsonObj;
import org.tsinghua.omedia.annotation.json.JsonString;
import org.tsinghua.omedia.data.JsonObject;
import org.tsinghua.omedia.data.Jsonable;

/**
 * Json 工具类
 * @author xuhongfeng
 *
 */
public class JsonUtils {
    private static final Logger logger = Logger.getLogger(JsonUtils.class);
    private static final ObjectMapper ob = new ObjectMapper();
    
    @SuppressWarnings("unchecked")
    public static Map<String, Object> read(String json) throws IOException {
        return ob.readValue(json, Map.class);
    }
    
    /**
     * 解析一个json对象
     * 
     * @param json
     * @param clazz
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T extends Jsonable> T parseJsonObject(JsonObject json,
            Class<T> clazz) {
        T object = null;
        try {
            object = clazz.newInstance();
        } catch (Exception e) {
            logger.error(e);
            return null;
        }
        while(true) {
            Field[] fields = clazz.getDeclaredFields();
            for(Field f:fields) {
                f.setAccessible(true);
                if(f.isAnnotationPresent(JsonLong.class)) {
                    JsonLong anno = f.getAnnotation(JsonLong.class);
                    String name = anno.name();
                    Long value = json.getLong(name);
                    try {
                        f.setLong(object, value);
                    } catch (Exception e) {
                        logger.error(e);
                        return null;
                    }
                } else if(f.isAnnotationPresent(JsonInt.class)) {
                    JsonInt anno = f.getAnnotation(JsonInt.class);
                    String name = anno.name();
                    Integer value = json.getInt(name);
                    try {
                        f.setInt(object, value);
                    } catch (Exception e) {
                        logger.error(e);
                        return null;
                    }
                } else if(f.isAnnotationPresent(JsonString.class)) {
                    JsonString anno = f.getAnnotation(JsonString.class);
                    String name = anno.name();
                    String value = json.getString(name);
                    try {
                        f.set(object, value);
                    } catch (Exception e) {
                        logger.error(e);
                        return null;
                    }
                } else if(f.isAnnotationPresent(JsonArray.class)) {
                    JsonArray anno = f.getAnnotation(JsonArray.class);
                    String name = anno.name();
                    Class<? extends Jsonable> type = anno.type();
                    JsonObject[] jsonObjects = json.getArray(name);
                    Jsonable[] objectsArray = 
                            (Jsonable[]) Array.newInstance(type, jsonObjects.length);
                    for(int i=0; i < objectsArray.length; i++) {
                        objectsArray[i] = parseJsonObject(jsonObjects[i], type);
                    }
                    try {
                        f.set(object, objectsArray);
                    } catch (Exception e) {
                        logger.error(e);
                        return null;
                    }
                } else if(f.isAnnotationPresent(JsonObj.class)) {
                    JsonObj anno = f.getAnnotation(JsonObj.class);
                    String name = anno.name();
                    Class<? extends Jsonable> type = anno.type();
                    JsonObject jsonObject = json.getObject(name);
                    Jsonable jsonable = parseJsonObject(jsonObject, type);try {
                        f.set(object, jsonable);
                    } catch (Exception e) {
                        logger.error(e);
                        return null;
                    }
                }
            }
            if(!Jsonable.class.isAssignableFrom(clazz.getSuperclass()))
                break;
            clazz = (Class<T>) clazz.getSuperclass();
        }
        return object;
    }
}
