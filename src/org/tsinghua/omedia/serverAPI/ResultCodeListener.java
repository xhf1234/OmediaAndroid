package org.tsinghua.omedia.serverAPI;

import org.tsinghua.omedia.data.JsonObject;
import org.tsinghua.omedia.data.Jsonable;
import org.tsinghua.omedia.tool.JsonUtils;

/**
 * 
 * @author xuhongfeng
 *
 */
public abstract class ResultCodeListener<T extends Jsonable> {
    private Class<T> type;
    
    public ResultCodeListener(Class<T> type) {
        this.type = type;
    }
    
    protected void exec(JsonObject result) {
        T t = JsonUtils.parseJsonObject(result, type);
        innerRun(t);
    }
    
    protected abstract void innerRun(T result);
}
