package org.tsinghua.omedia.worker;

/**
 * 
 * @author xuhongfeng
 *
 */
public class WorkerManager {
    
    private static WorkerManager me;
    
    private CcnWorker ccnWorker = new CcnWorker(1000L);
    
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
        ccnWorker.start();
    }

    public CcnWorker getCcnWorker() {
        return ccnWorker;
    }
}
