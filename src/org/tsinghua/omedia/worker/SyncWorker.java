package org.tsinghua.omedia.worker;

import org.tsinghua.omedia.tool.Logger;

/**
 * 
 * @author xuhongfeng
 *
 */
public abstract class SyncWorker extends Worker {
    private static final Logger logger = Logger.getLogger(SyncWorker.class);
    
    private boolean gotData =false;
    
    private long interval;
    
    private static final long DEFAULT_INTERVAL = 20*1000L;
    
    protected abstract void singleRun();
    
    public SyncWorker() {
        this(DEFAULT_INTERVAL);
    }

    public SyncWorker(long interval) {
        super();
        this.interval = interval;
    }

    @Override
    protected void innerRun() {
        while(running) {
            try {
                singleRun();
                gotData = true;
                Thread.sleep(interval);
            } catch (Throwable e) {
                logger.error(e);
            }
        }
    }

    /**
     * blocked if gotData==false
     */
    public void waitingForData() {
        while(gotData == false) {
            try {
                Thread.sleep(100L);
            } catch (InterruptedException e) {
                logger.error(e);
            }
        }
    }
}
