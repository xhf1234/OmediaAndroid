package org.tsinghua.omedia.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.ccnx.android.ccnlib.CCNxConfiguration;
import org.ccnx.android.ccnlib.CCNxServiceControl;
import org.ccnx.ccn.CCNHandle;
import org.ccnx.ccn.impl.CCNNetworkManager.NetworkProtocol;
import org.ccnx.ccn.io.CCNInputStream;
import org.ccnx.ccn.profiles.ccnd.FaceManager;
import org.ccnx.ccn.profiles.ccnd.PrefixRegistrationManager;
import org.ccnx.ccn.profiles.nameenum.BasicNameEnumeratorListener;
import org.ccnx.ccn.profiles.nameenum.CCNNameEnumerator;
import org.ccnx.ccn.protocol.ContentName;
import org.tsinghua.omedia.OmediaApplication;
import org.tsinghua.omedia.datasource.DataSource;
import org.tsinghua.omedia.datasource.sdcard.CcnFileDatasource;
import org.tsinghua.omedia.tool.Logger;

import android.os.AsyncTask;

/**
 * 
 * @author xuhongfeng
 *
 */
public class CcnService {
    private static final Logger logger = Logger.getLogger(CcnService.class);
    
    private static CcnService me;
    
    private CcnService(){}
    
    public static CcnService getInstance() {
        if(me != null) return me;
        synchronized (CcnService.class) {
            if(me == null) {
                me = new CcnService();
            }
        }
        return me;
    }
    
    private CCNxServiceControl ccnd;
    
    /**
     * 
     * @return
     */
    public LsrepoTask syncCcnData() {
        String url = DataSource.getInstance().getCcnUrl();
        LsrepoTask task = new LsrepoTask();
        task.execute(url);
        return task;
    }
    
    public void ccnGetFile(final String ccnFile) throws IOException {
        AsyncTask<Void, Void, Throwable> task = new AsyncTask<Void, Void, Throwable>() {

            @Override
            protected Throwable doInBackground(Void... params) {
                try {
                    String uri = DataSource.getInstance().getCcnUrl();
                    ContentName name = ContentName.fromURI(uri+"/"+ccnFile);
                    CCNHandle handle = CCNHandle.open();
                    String filePath = CcnFileDatasource.getInstance()
                            .getAbsolutePath(ccnFile);
                    File file = new File(filePath);
                    FileOutputStream fos = null;
                    CCNInputStream input = null;
                    try {
                        fos = new FileOutputStream(file);
                        input = new CCNInputStream(name, handle);
                        byte [] buffer = new byte[1024];
                        int readed;
                        while((readed=input.read(buffer)) != -1) {
                            fos.write(buffer, 0, readed);
                            fos.flush();
                        }
                    } finally {
                        try {
                            if(input != null) input.close();
                        } finally {
                            if(fos != null) fos.close();
                        }
                    }
                } catch (Throwable e) {
                    return e;
                }
                return null;
            }
            
        };
        task.execute();
        Throwable e;
        try {
            e = task.get();
        } catch (Throwable e1) {
            e = e1;
        }
        if(e != null) {
            throw new IOException(e);
        }
    }
    
    private boolean isCcnRunning() {
        boolean running = false;
        try {
            running = getCcnd().isCcndRunning();
        } catch (Throwable e) {
            logger.error(e);
            ccnd = null;
        }
        return running;
    }
    
    private boolean ccndc(String uri, String host) {
        try {
            CCNHandle ccnHandle = CCNHandle.open();
            FaceManager fHandle = new FaceManager(ccnHandle);
            int faceID = fHandle.createFace(NetworkProtocol.UDP, host, 9695);
            PrefixRegistrationManager pre = new PrefixRegistrationManager(ccnHandle);
            pre.registerPrefix(uri, faceID, null);
            faceID = fHandle.createFace(NetworkProtocol.TCP, host, 9695);
            pre.registerPrefix(uri, faceID, null);
            ccnHandle.close();
            return true;
        } catch (Throwable e) {
            logger.error(e);
            ccnd = null;
            return false;
        }
    }
    
    private CCNxServiceControl getCcnd() throws IOException {
        if(ccnd != null) return ccnd;
        synchronized (this) {
            if(ccnd == null) {
                String host = DataSource.getInstance().getCcnHost();
                CCNxConfiguration.config(OmediaApplication.getInstance().getApplicationContext());
                ccnd = new CCNxServiceControl(OmediaApplication.getInstance().getApplicationContext());
                ccnd.startCcnd();
                ccndc("ccnx:/", host);
            }
        }
        if(ccnd == null) throw new IOException("start ccnd failed");
        return ccnd;
    }
    
    public class LsrepoTask extends AsyncTask<String, String, Void >
            implements BasicNameEnumeratorListener {
        
        private String[] ccnFiles;

        @Override
        protected synchronized Void doInBackground(String... params) {
            CCNHandle handle = null;
            try {
                if(!isCcnRunning()) {
                    throw new Exception("ccnd is not running");
                }
                ContentName name = ContentName.fromURI(params[0]);
                handle = CCNHandle.open();
                CCNNameEnumerator ccnNE = new CCNNameEnumerator(handle,this);
                ccnNE.registerPrefix(name);
                this.wait();
                ccnNE.cancelPrefix(name);
            } catch (Throwable e) {
                logger.error(e);
                ccnd = null;
            } finally {
                if(handle != null) {
                    handle.close();
                }
            }
            return null;
        }
        
        public String[] getCcnFiles() {
            return ccnFiles;
        }
        
        public synchronized void stop() {
            this.notifyAll();
        }

        @Override
        public synchronized int handleNameEnumerator(ContentName prefix,
                ArrayList<ContentName> names) {
            String[] ccnFiles = new String[names.size()];
            for(int i=0; i<ccnFiles.length; i++) {
                ccnFiles[i] = names.get(i).toString();
            }
            this.ccnFiles = ccnFiles;
            return names.size();
        }
    }
}
