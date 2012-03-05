package org.tsinghua.omedia.worker;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author xuhongfeng
 *
 */
public class WorkerManager {
    
    private static WorkerManager me;
    
    private List<Worker> cancelableWorkers = new ArrayList<Worker>();
    
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
    
    public synchronized void startLoopWorkers() {
        if(checkDataUpdateWorker == null) {
            checkDataUpdateWorker = new CheckDataUpdateWorker();
            checkDataUpdateWorker.start();
        }
    }
    
    public synchronized void stopLoopWorkers() {
        checkDataUpdateWorker.stop();
        checkDataUpdateWorker = null;
    }
    
    public synchronized void stopCancelableWorkers() {
        for(Worker w:cancelableWorkers) {
            w.stop();
        }
        cancelableWorkers.clear();
    }

    public synchronized void runWorker(final Worker worker) {
        cancelableWorkers.add(worker);
        worker.setListener(new WorkerListener() {
            @Override
            public void onStop() {
                synchronized (WorkerManager.this) {
                    cancelableWorkers.remove(worker);
                }
            }
        });
        worker.start();
    }
}
