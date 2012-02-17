package org.tsinghua.omedia.datasource.sdcard;

import java.io.File;
import java.io.IOException;

import org.tsinghua.omedia.service.CcnService;

/**
 * 
 * @author xuhongfeng
 *
 */
public class CcnFileDatasource extends AbstractSdcardDatasource {
    private static CcnFileDatasource me;
    
    private CcnFileDatasource(){}
    
    public static CcnFileDatasource getInstance() {
        if(me != null) return me;
        synchronized (CcnFileDatasource.class) {
            if(me == null) {
                me = new CcnFileDatasource();
            }
        }
        return me;
    }
    
    public File getCcnFile(String ccnFile) throws IOException {
        File file = new File(getAbsolutePath(ccnFile));
        if(!file.exists()) {
            CcnService.getInstance().ccnGetFile(ccnFile);
        }
        return file;
    }
    
    @Override
    protected String getDirName() {
        return "ccn";
    }

}
