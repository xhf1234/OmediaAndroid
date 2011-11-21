package org.tsinghua.omedia.activity;


import java.io.IOException;
import java.util.Map;

import org.tsinghua.omedia.OmediaApplication;
import org.tsinghua.omedia.R;
import org.tsinghua.omedia.datasource.OmediaPreference;
import org.tsinghua.omedia.form.FormProcessor;
import org.tsinghua.omedia.form.LoginForm;
import org.tsinghua.omedia.tool.JsonUtil;
import org.tsinghua.omedia.tool.ResourceUtil;

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
    private static final int LOGIN_AUTH_SUCCESS = 1;
    private static final int LOGIN_AUTH_FAILED = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.landing_layout);
        initViewsFromPreferences();
        initListener();
    }
    

    private void initViewsFromPreferences() {
        OmediaPreference preferences = OmediaApplication.getInstance()
                .getDatasource().getPreference();
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
        new FormProcessor<LoginForm,String>(form) {

            @Override
            protected String onProcessForm(LoginForm form) throws Exception {
                return OmediaApplication.getInstance().getHttpService().login(form);
            }

            @Override
            protected void onProcessSuccess(String result) {
                try {
                    Map<String, Object> map = JsonUtil.read(result);
                    Integer r = (Integer) map.get("result");
                    switch(r) {
                    case LOGIN_AUTH_SUCCESS:
                        loginSuccess();
                        break;
                    case LOGIN_AUTH_FAILED:
                        showAlertDialog(ResourceUtil.getString(R.string.login_auth_failed));
                        break;
                    default: throw new IOException("unknow result code");
                    }
                } catch (IOException e) {
                    showAlertDialog(e.getMessage());
                }
                
            }

            @Override
            protected void onValidateFailed(String msg) {
                showAlertDialog(msg);
            }
        }.exec();
    }

    private void loginSuccess() {
        //update preferences
        OmediaPreference preferences = dataSource.getPreference();
        CheckBox cb = (CheckBox)findViewById(R.id.rememberPassword_login);
        preferences.setRememberPassword(cb.isChecked());
        EditText username = (EditText)findViewById(R.id.username_login);
        preferences.setUsername(username.getText().toString());
        if(preferences.isRememberPassword()) {
            EditText password = (EditText)findViewById(R.id.password_login);
            preferences.setPassword(password.getText().toString());
        }
        //TODO
    }
}
