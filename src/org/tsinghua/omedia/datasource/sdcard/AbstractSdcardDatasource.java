package org.tsinghua.omedia.datasource.sdcard;

import java.io.File;

public abstract class AbstractSdcardDatasource {
    
    protected File getDirPath() {
        File dir = new File("/sdcard/omedia/"+getDirName());
        dir.mkdirs();
        return dir;
    }
    
    public String getAbsolutePath(String fileName) {
        return new File(getDirPath(), fileName).getAbsolutePath();
    }
    
    protected abstract String getDirName();
}
