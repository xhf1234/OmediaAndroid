package org.tsinghua.omedia.tool;

import android.util.Log;

/**
 * 
 * @author xuhongfeng
 *
 */
public class Logger {
    private String tag;
    
    private Logger(){}
    
    public static Logger getLogger(Class<?> clazz) {
        Logger logger = new Logger();
        logger.tag = clazz.getName();
        return logger;
    }
    
    public void info(String msg) {
        Log.i(msg, msg);
    }
    
    public void warn(String msg) {
        Log.w(tag, msg);
    }
    
    public void error(String msg) {
        Log.e(tag, msg);
    }
    
    public void error(String msg, Throwable e) {
        Log.e(tag, msg, e);
    }
    
    public void debug(String msg) {
        Log.d(tag, msg);
    }
}
