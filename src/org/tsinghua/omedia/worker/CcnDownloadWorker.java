package org.tsinghua.omedia.worker;

import java.io.File;
import java.io.IOException;

/**
 * 
 * @author xuhongfeng
 *
 */
public abstract class CcnDownloadWorker extends Worker{
    private String ccnFile;

    public CcnDownloadWorker(String ccnFile) {
        super();
        this.ccnFile = ccnFile;
    }

    @Override
    protected void innerRun() {
        try {
            File file = dataSource.getCcnFile(ccnFile);
            onSuccess(file);
        } catch (IOException e) {
            onFailed(e);
        }
    }
    
    protected abstract void onSuccess(File file);
    protected abstract void  onFailed(Exception e);
}
