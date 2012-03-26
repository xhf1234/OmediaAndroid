package org.tsinghua.omedia.activity;

import org.tsinghua.omedia.R;
import org.tsinghua.omedia.consts.ActionConst;
import org.tsinghua.omedia.consts.UrlConst;
import org.tsinghua.omedia.data.Account;
import org.tsinghua.omedia.datasource.DataSource;
import org.tsinghua.omedia.datasource.MemDataSource;
import org.tsinghua.omedia.datasource.OmediaPreference;
import org.tsinghua.omedia.form.GetAccountForm;
import org.tsinghua.omedia.form.LoginForm;
import org.tsinghua.omedia.serverAPI.GetAccountAPI;
import org.tsinghua.omedia.serverAPI.LoginAPI;
import org.tsinghua.omedia.tool.Logger;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.EditText;

/**
 * 登陆和注册页
 * 
 * @author xuhongfeng
 * 
 */
public class LandingActivity extends BaseActivity {
    private static final int DIALOG_NEED_UPDATE = 100;

    private static final Logger logger = Logger
            .getLogger(LandingActivity.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.landing_activity);
        String action = getIntent().getAction();
        if (action != null && action.equals(ActionConst.ACTION_TOKEN_WRONG)) {
            showAlertDialog(R.string.token_wrong);
        }
        initViewsFromPreferences();
        initListener();
    }

    private void initViewsFromPreferences() {
        OmediaPreference preferences = DataSource.getInstance().getPreference();
        CheckBox cb = (CheckBox) findViewById(R.id.rememberPassword_login);
        cb.setChecked(preferences.isRememberPassword());
        EditText username = (EditText) findViewById(R.id.username_login);
        username.setText(preferences.getUsername());
        if (preferences.isRememberPassword()) {
            EditText password = (EditText) findViewById(R.id.password_login);
            password.setText(preferences.getPassword());
        }
    }

    private void initListener() {
        // 登录按钮
        findViewById(R.id.login_button).setOnClickListener(
                new OnClickListener() {
                    public void onClick(View v) {
                        EditText usernameView = (EditText) findViewById(R.id.username_login);
                        EditText passwordView = (EditText) findViewById(R.id.password_login);
                        CheckBox checkBox = (CheckBox) findViewById(R.id.rememberPassword_login);
                        LoginForm form = new LoginForm();
                        form.setUsername(usernameView.getText().toString());
                        form.setPassword(passwordView.getText().toString());
                        form.setRememberPassword(checkBox.isChecked());
                        doLogin(form);
                    }
                });
        // 注册按钮
        findViewById(R.id.registerButton).setOnClickListener(
                new OnClickListener() {
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setClass(LandingActivity.this,
                                RegisterActivity.class);
                        startActivity(intent);
                    }
                });
    }

    private void doLogin(LoginForm form) {
        showWaitingDialog();
        new LoginAPI(form, this) {
            @Override
            protected void onLoginSuccess(long accountId, long token) {
                loginSuccess(accountId, token);
            }

            @Override
            protected void onLoginFailed() {
                showAlertDialog(R.string.login_auth_failed);
            }

            @Override
            protected void onSoftwareNeedUpdate() {
                showDialog(DIALOG_NEED_UPDATE);
            }

            @Override
            protected void onStop() {
                super.onStop();
                dismissWaitingDialog();
            }
            
        }.call();
    }

    private void loginSuccess(final long accountId, final long token) {
        // update preferences
        OmediaPreference preferences = dataSource.getPreference();
        CheckBox cb = (CheckBox) findViewById(R.id.rememberPassword_login);
        preferences.setRememberPassword(cb.isChecked());
        EditText usernameView = (EditText) findViewById(R.id.username_login);
        final String username = usernameView.getText().toString();
        preferences.setUsername(username);
        if (preferences.isRememberPassword()) {
            EditText password = (EditText) findViewById(R.id.password_login);
            preferences.setPassword(password.getText().toString());
        }
        // save accountId and token in MemDatasource
        MemDataSource.clearData();
        dataSource.saveAccountId(accountId);
        dataSource.saveToken(token);
        // 从服务端更新账户信息
        GetAccountForm form = new GetAccountForm(accountId, token);
        new GetAccountAPI(form, username, this) {

            @Override
            protected void onGetAccountSuccess(Account account, long version) {
                dataSource.saveAccount(account);
                dataSource.saveAccountVersion(version);
                logger.debug("intent to MainActivity");
                Intent intent = new Intent(LandingActivity.this,
                        MainActivity.class);
                startActivity(intent);
            }
        }.call();
    }

    @Override
    protected Dialog onCreateDialog(int id, Bundle args) {
        if(id == DIALOG_NEED_UPDATE) {
            return new AlertDialog.Builder(this).setMessage(R.string.software_need_update)
                .setPositiveButton(R.string.download_now, new AlertDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(UrlConst.DownloadApkUrl));
                        startActivity(intent);
                    }
                }).setNegativeButton(R.string.download_later, new AlertDialog.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dismissDialog(DIALOG_NEED_UPDATE);
                    }
                    
                }).create();
        } else {
            return super.onCreateDialog(id, args);
        }
    }

    
}
