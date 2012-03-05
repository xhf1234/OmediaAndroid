package org.tsinghua.omedia.worker;

import org.tsinghua.omedia.datasource.DataSource;
import org.tsinghua.omedia.tool.OmediaConsole;

import android.os.AsyncTask;

/**
 * 
 * @author xuhongfeng
 *
 */
public abstract class Worker extends AsyncTask<Void, Void, Void> {
    protected OmediaConsole omediaConsole = new OmediaConsole();
    protected DataSource dataSource = DataSource.getInstance();
    
    private WorkerListener listener;
    protected volatile boolean running = false;
    
    /**
     * start the worker if it is not running
     */
    public void start() {
        if(!running) {
            running = true;
            execute();
        }
    }
    
    /**
     * stop the worker
     */
    public void stop() {
        running = false;
    }
    
    protected abstract void innerRun();
    
    protected void onStop() {
        if(listener != null) {
            listener.onStop();
        }
    }

    @Override
    protected Void doInBackground(Void... params) {
        innerRun();
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
        onStop();
    }
    
    public void setListener(WorkerListener listener) {
        this.listener = listener;
    }
}
