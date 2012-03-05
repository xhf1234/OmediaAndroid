package org.tsinghua.omedia.worker;

import org.tsinghua.omedia.service.CcnService;

/**
 * 
 * @author xuhongfeng
 *
 */
public class InitCcnWorker extends Worker {

    @Override
    protected void innerRun() {
        CcnService.getInstance().init();
    }

}
