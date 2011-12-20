package org.tsinghua.omedia.serverAPI;

import org.tsinghua.omedia.data.JsonObject;

/**
 * 
 * @author xuhongfeng
 *
 */
public abstract class ResultCodeListener {
    protected abstract void exec(JsonObject result);
}
