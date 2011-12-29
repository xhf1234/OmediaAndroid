package org.tsinghua.omedia.serverAPI;

import org.tsinghua.omedia.activity.OmediaActivityIntf;
import org.tsinghua.omedia.annotation.json.JsonArray;
import org.tsinghua.omedia.consts.ResultCode;
import org.tsinghua.omedia.consts.UrlConst;
import org.tsinghua.omedia.data.Account;
import org.tsinghua.omedia.data.Jsonable;
import org.tsinghua.omedia.form.SearchFriendsForm;

/**
 * 
 * @author xuhongfeng
 *
 */
public abstract class SearchFriendsAPI extends
        AbstractServerAPI<SearchFriendsForm> {

    protected SearchFriendsAPI(SearchFriendsForm form, OmediaActivityIntf omediaActivity) {
        super(form, omediaActivity);
    }

    @Override
    protected String getUrl() {
        return UrlConst.SearchFriendsUrl;
    }

    @Override
    protected void initResultCodeListener() {
        registerResultCodeListener(ResultCode.SUCCESS,
                new ResultCodeListener<ResultType>(ResultType.class) {
                    @Override
                    protected void innerRun(ResultType result) {
                        onSuccess(result.accounts);
                    }
        });
    }

    protected abstract void onSuccess(Account[] accounts);

    public static class ResultType implements Jsonable {
        @JsonArray(name="friends", type=Account.class)
        private Account[] accounts;
    }
}
