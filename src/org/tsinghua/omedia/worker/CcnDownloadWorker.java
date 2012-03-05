package org.tsinghua.omedia.worker;

import java.io.File;
import java.io.IOException;

import org.tsinghua.omedia.datasource.sdcard.CcnFileDatasource;

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
            File file = CcnFileDatasource.getInstance().getCcnFile(ccnFile);
            onSuccess(file);
        } catch (IOException e) {
            onFailed(e);
        }
    }
    
    protected abstract void onSuccess(File file);
    protected abstract void  onFailed(Exception e);
}
