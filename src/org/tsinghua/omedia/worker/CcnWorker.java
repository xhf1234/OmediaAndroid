package org.tsinghua.omedia.worker;

import org.tsinghua.omedia.datasource.DataSource;
import org.tsinghua.omedia.service.CcnService;
import org.tsinghua.omedia.service.CcnService.LsrepoTask;

/**
 * 
 * @author xuhongfeng
 *
 */
public class CcnWorker extends SyncWorker {
    private LsrepoTask task;

    public CcnWorker() {
        super();
    }

    public CcnWorker(long interval) {
        super(interval);
    }

    @Override
    protected void singleRun() {
        if(task == null) {
            task = CcnService.getInstance().syncCcnData();
        }
        while(task.getCcnFiles() == null) {
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
            }
        }
        DataSource.getInstance().setCcnFiles(task.getCcnFiles());
    }

    @Override
    protected void onStop() {
        if(task != null) {
            task.stop();
        }
        super.onStop();
    }

}
