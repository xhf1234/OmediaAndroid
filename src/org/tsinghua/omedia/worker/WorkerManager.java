package org.tsinghua.omedia.worker;

/**
 * 
 * @author xuhongfeng
 *
 */
public class WorkerManager {
    
    private static WorkerManager me;
    
    private CcnWorker ccnWorker;
    
    private CheckDataUpdateWorker checkDataUpdateWorker;
    
    private WorkerManager(){}
    
    public static WorkerManager getInstance() {
        if(me != null) return me;
        synchronized (WorkerManager.class) {
            if(me == null) {
                me = new WorkerManager();
            }
        }
        return me;
    }
    
    public void startWorkers() {
        if(ccnWorker == null) {
            ccnWorker = new CcnWorker(1000L);
            ccnWorker.start();
        }
        if(checkDataUpdateWorker == null) {
            checkDataUpdateWorker = new CheckDataUpdateWorker();
            checkDataUpdateWorker.start();
        }
    }
    
    public void stopWorkers() {
        ccnWorker.stop();
        ccnWorker = null;
        checkDataUpdateWorker.stop();
        checkDataUpdateWorker = null;
    }

    public CcnWorker getCcnWorker() {
        return ccnWorker;
    }
}
