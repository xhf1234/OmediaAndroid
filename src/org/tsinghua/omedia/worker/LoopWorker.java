package org.tsinghua.omedia.worker;

import org.tsinghua.omedia.tool.Logger;

/**
 * 
 * @author xuhongfeng
 *
 */
public abstract class LoopWorker extends Worker {
    private static final Logger logger = Logger.getLogger(LoopWorker.class);
    
    private static final long DEFAULT_INTERVAL = 20*1000L;
    
    private boolean gotData =false;
    
    private long interval;
    
    private static final int LOOP_INFINITE = -99999;
    private int loopTime = LOOP_INFINITE;
    
    public LoopWorker() {
        this(LOOP_INFINITE, DEFAULT_INTERVAL);
    }

    public LoopWorker(long interval) {
    	this(LOOP_INFINITE, interval);
    }
    
    public LoopWorker(int loopTime) {
    	this(loopTime, DEFAULT_INTERVAL);
    }
    
    public LoopWorker(int loopTime, long interval) {
        super();
        this.interval = interval;
    	this.loopTime = loopTime;
    }

    @Override
    protected void innerRun() {
        while(running) {
        	if(loopTime != LOOP_INFINITE) {
        		if(loopTime <= 0) {
        			break;
        		}
        		loopTime --;
        	}
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
    
    protected abstract void singleRun();
}
