package org.tsinghua.omedia.activity;


import java.io.IOException;

import static org.tsinghua.omedia.consts.ResultCode.*;

import org.tsinghua.omedia.R;
import org.tsinghua.omedia.consts.ActionConst;
import org.tsinghua.omedia.consts.ResultCode;
import org.tsinghua.omedia.data.Account;
import org.tsinghua.omedia.data.JsonObject;
import org.tsinghua.omedia.datasource.DataSource;
import org.tsinghua.omedia.datasource.OmediaPreference;
import org.tsinghua.omedia.form.FormProcessor;
import org.tsinghua.omedia.form.GetAccountForm;
import org.tsinghua.omedia.form.LoginForm;
import org.tsinghua.omedia.service.HttpService;
import org.tsinghua.omedia.tool.Logger;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.EditText;

/**
 * 登陆和注册页
 * @author xuhongfeng
 *
 */
public class LandingActivity extends BaseActivity {
    
    private static final Logger logger = Logger.getLogger(LandingActivity.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.landing_activity);
        String action = getIntent().getAction();
        if(action!=null && action.equals(ActionConst.ACTION_TOKEN_WRONG)) {
            showAlertDialog(R.string.token_wrong);
        }
        initViewsFromPreferences();
        initListener();
    }
    

    private void initViewsFromPreferences() {
        OmediaPreference preferences = DataSource.getInstance().getPreference();
        CheckBox cb = (CheckBox)findViewById(R.id.rememberPassword_login);
        cb.setChecked(preferences.isRememberPassword());
        EditText username = (EditText)findViewById(R.id.username_login);
        username.setText(preferences.getUsername());
        if(preferences.isRememberPassword()) {
            EditText password = (EditText)findViewById(R.id.password_login);
            password.setText(preferences.getPassword());
        }
    }
    
    private void initListener() {
        findViewById(R.id.login_button).setOnClickListener(new OnClickListener() {
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
    }
    
    private void doLogin(LoginForm form) {
        new FormProcessor<LoginForm>(this, form) {

            @Override
            protected String onProcessForm(LoginForm form) throws Exception {
                return HttpService.getInstance().login(form);
            }

            @Override
            protected void onProcessSuccess(JsonObject result, int resultCode) {
                try {
                    switch(resultCode) {
                    case Login.SUCCESS:
                        Long accountId = result.getLong("accountId");
                        Long token = result.getLong("token");
                        loginSuccess(accountId, token);
                        break;
                    case Login.FAILED:
                        showAlertDialog(R.string.login_auth_failed);
                        break;
                    default: throw new IOException("unknow result code");
                    }
                } catch (IOException e) {
                    showAlertDialog(e.getMessage());
                }
                
            }
        }.exec();
    }
    
    private void loginSuccess(final long accountId, final long token) {
        //update preferences
        OmediaPreference preferences = dataSource.getPreference();
        CheckBox cb = (CheckBox)findViewById(R.id.rememberPassword_login);
        preferences.setRememberPassword(cb.isChecked());
        EditText usernameView = (EditText)findViewById(R.id.username_login);
        final String username = usernameView.getText().toString();
        preferences.setUsername(username);
        if(preferences.isRememberPassword()) {
            EditText password = (EditText)findViewById(R.id.password_login);
            preferences.setPassword(password.getText().toString());
        }
        //从服务端更新账户信息
        GetAccountForm form = new GetAccountForm(accountId, token);
        new FormProcessor<GetAccountForm>(this, form) {
            
            @Override
            protected String onProcessForm(GetAccountForm form)
                    throws Exception {
                return HttpService.getInstance().getAccount(form);
            }
            
            @Override
            protected void onProcessSuccess(JsonObject result, int resultCode) {
                try {
                    switch(resultCode) {
                        case ResultCode.SUCCESS:
                            String email = result.getString("email");
                            String realName = result.getString("realName");
                            String address = result.getString("address");
                            String phone = result.getString("phone");
                            long version = result.getLong("version");
                            Account account = new Account();
                            account.setAccountId(accountId);
                            account.setAddress(address);
                            account.setEmail(email);
                            account.setPhone(phone);
                            account.setRealName(realName);
                            account.setUsername(username);
                            dataSource.saveAccount(account);
                            dataSource.saveAccountId(accountId);
                            dataSource.saveToken(token);
                            dataSource.saveAccountVersion(version);
                            logger.debug("intent to MainActivity");
                            Intent intent = new Intent(LandingActivity.this, MainActivity.class);
                            startActivity(intent);
                            break;
                    case ResultCode.TOKEN_WRONG:
                        tokenWrong();
                        break;
                    default: throw new IOException("unknow result code");
                    }
                } catch(IOException e) {
                    showAlertDialog(e.getMessage());
                }
            }
        }.exec();
    }
}
