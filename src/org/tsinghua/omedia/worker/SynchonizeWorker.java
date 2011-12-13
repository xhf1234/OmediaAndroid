package org.tsinghua.omedia.worker;

import android.os.AsyncTask;

/**
 * 
 * @author xuhongfeng
 *
 */
public class SynchonizeWorker extends AsyncTask<Void,Void,Void> {
    private volatile boolean running = false;

    @Override
    protected Void doInBackground(Void... params) {
        try {
            while(running) {
                
            }
        } finally {
            running = false;
        }
        return null;
    }
    
    public void start() {
        synchronized (this) {
            if(running) return;
            running = true;
            execute();
        }
    }
    public void stop() {
        synchronized (this) {
            running = false;
        }
    }
    
    public boolean isRunning() {
        return running;
    }
}
