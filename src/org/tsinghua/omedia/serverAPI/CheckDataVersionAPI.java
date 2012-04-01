package org.tsinghua.omedia.serverAPI;

import org.tsinghua.omedia.activity.OmediaActivityIntf;
import org.tsinghua.omedia.annotation.json.JsonInt;
import org.tsinghua.omedia.consts.ResultCode;
import org.tsinghua.omedia.consts.UrlConst;
import org.tsinghua.omedia.data.Jsonable;
import org.tsinghua.omedia.form.CheckDataVersionForm;

/**
 * 
 * @author xuhongfeng
 *
 */
public abstract class CheckDataVersionAPI extends AbstractServerAPI<CheckDataVersionForm> {

    protected CheckDataVersionAPI(CheckDataVersionForm form, OmediaActivityIntf omediaActivity) {
        super(form, omediaActivity);
    }

    @Override
    protected String getUrl() {
        return UrlConst.CheckDataVersionUrl;
    }

    @Override
    protected void initResultCodeListener() {
        registerResultCodeListener(ResultCode.SUCCESS, new ResultCodeListener<ResultType> (ResultType.class) {
            @Override
            protected void innerRun(ResultType result) {
                onSuccess(result.accountFlag, result.friendRequestFlag,
                        result.friendsFlag, result.configFlag, result.ccnFileFlag
                        ,result.groupFlag);
            }
        });
    }
    
    protected abstract void onSuccess(int accountFlag, int friendRequestFlag, int friendsFlag
            , int configFlag, int ccnFileFlag, int groupFlag);

    public static class ResultType implements Jsonable {
        @JsonInt(name="account")
        private int accountFlag;
        @JsonInt(name="friendRequest")
        private int friendRequestFlag;
        @JsonInt(name="friends")
        private int friendsFlag;
        @JsonInt(name="config")
        private int configFlag;
        @JsonInt(name="ccnFile")
        private int ccnFileFlag;
        @JsonInt(name="group")
        private int groupFlag;
    }
}
