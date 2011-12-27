package org.tsinghua.omedia;

import org.tsinghua.omedia.activity.OmediaActivityIntf;
import org.tsinghua.omedia.event.Event;

import android.app.Application;
import android.content.res.Configuration;

/**
 * 
 * 全局的Application对象 单例模式
 * 
 * @author xuhongfeng
 *
 */
public class OmediaApplication extends Application {
    private static OmediaApplication omedia;
    
    private OmediaActivityIntf currentActivity;
    
    public static OmediaApplication getInstance() {
        return omedia;
    }
    
    public OmediaActivityIntf getCurrentActivity() {
        return currentActivity;
    }
    
    public void dispatchEvent(Event event) {
        if(getCurrentActivity() != null) {
            getCurrentActivity().onEventCatch(event);
        }
    }
    
    public void registerCurrentActivity(OmediaActivityIntf activity) {
        currentActivity = activity;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        omedia = this;
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

}
