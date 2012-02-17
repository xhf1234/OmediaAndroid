package org.tsinghua.omedia.worker;

import java.io.File;

import org.tsinghua.omedia.consts.UrlConst;
import org.tsinghua.omedia.datasource.DataSource;
import org.tsinghua.omedia.tool.HttpExecutor;
import org.tsinghua.omedia.worker.Worker;

/**
 * 
 * @author xuhongfeng
 *
 */
public abstract class MultipartWorker extends Worker {
    private File file;
    private String fileName;

    public MultipartWorker(File file, String fileName) {
        super();
        this.file = file;
        this.fileName = fileName;
    }

    @Override
    protected void innerRun() {
        try {
            HttpExecutor.postMultipart(UrlConst.ccnPutFileUrl).addFilePart(fileName, file)
                .addParam("accountId", DataSource.getInstance().getAccountId())
                .addParam("token", DataSource.getInstance().getToken())
                .exec();
            onSuccess();
        } catch (Exception e) {
            onFailed(e);
        }
    }

    protected abstract void onFailed(Exception e);
    protected abstract void onSuccess();
}
