package org.tsinghua.omedia;

import org.tsinghua.omedia.datasource.DataSource;
import org.tsinghua.omedia.service.HttpService;

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
    
    public static OmediaApplication getInstance() {
        return omedia;
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

    public HttpService getHttpService() {
        return HttpService.getInstance();
    }

    public DataSource getDatasource() {
        return DataSource.getInstance();
    }
}
