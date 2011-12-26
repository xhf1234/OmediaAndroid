package org.tsinghua.omedia.serverAPI;

import org.tsinghua.omedia.data.Jsonable;

/**
 * 
 * @author xuhongfeng
 *
 */
public abstract class ResultCodeListener<T extends Jsonable> {
    protected abstract void exec(T result);
}
