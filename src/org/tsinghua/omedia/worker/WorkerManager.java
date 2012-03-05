package org.tsinghua.omedia.worker;

/**
 * 
 * @author xuhongfeng
 *
 */
public class WorkerManager {
    
    private static WorkerManager me;
    
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
    
    public synchronized void startWorkers() {
        if(checkDataUpdateWorker == null) {
            checkDataUpdateWorker = new CheckDataUpdateWorker();
            checkDataUpdateWorker.start();
        }
    }
    
    public synchronized void stopWorkers() {
        checkDataUpdateWorker.stop();
        checkDataUpdateWorker = null;
    }

}
