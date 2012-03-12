
package org.tsinghua.omedia.serverAPI;

import org.tsinghua.omedia.activity.OmediaActivityIntf;
import org.tsinghua.omedia.annotation.json.*;
import org.tsinghua.omedia.consts.ResultCode.Settings;
import org.tsinghua.omedia.consts.UrlConst;
import org.tsinghua.omedia.data.EmptyInstance;
import org.tsinghua.omedia.data.EmptyInstance.EmptyResultType;
import org.tsinghua.omedia.data.Jsonable;
import org.tsinghua.omedia.form.SettingsForm;

/**
 * @author czw
 */
public abstract class SettingsAPI extends AbstractServerAPI<SettingsForm> {

    protected SettingsAPI(SettingsForm form, OmediaActivityIntf omediaActivity) {
        super(form, omediaActivity);
    }

    @Override
    protected String getUrl() {
        return UrlConst.settingsUrl;
    }

    @Override
    protected void initResultCodeListener() {
        registerResultCodeListener(Settings.SUCCESS, new ResultCodeListener<ResultType>(
                ResultType.class) {
            @Override
            protected void innerRun(ResultType result) {
                onSettingsSuccess(result.version);
            }
        });
        registerResultCodeListener(Settings.PSWERROR,
                new ResultCodeListener<EmptyInstance.EmptyResultType>(EmptyResultType.class) {
                    @Override
                    protected void innerRun(EmptyResultType result) {
                        onSettingsPSWWRONG();
                    }
                });
    }

    protected abstract void onSettingsSuccess(long accountVersion);

    protected abstract void onSettingsPSWWRONG();

    public static class ResultType implements Jsonable {
        @JsonLong(name = "version")
        private long version;

    }
}
