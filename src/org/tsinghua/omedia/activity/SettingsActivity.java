
package org.tsinghua.omedia.activity;

import org.tsinghua.omedia.R;
import org.tsinghua.omedia.data.Account;
import org.tsinghua.omedia.form.SettingsForm;
import org.tsinghua.omedia.serverAPI.SettingsAPI;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

/**
 * @author czw
 */
public class SettingsActivity extends BaseActivity {

    private long accountId;

    private long token;

    private Button cancelButton;

    private Button okButton;

    private EditText oldpswText;

    private EditText newpswText;

    private EditText confirmPswText;

    private EditText nameText;

    private EditText emailText;

    private EditText addressText;

    private EditText phoneNumberText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_view);

        cancelButton = (Button) findViewById(R.id.setting_cancel);
        okButton = (Button) findViewById(R.id.setting_ok);
        oldpswText = (EditText) findViewById(R.id.setting_oldpsw);
        newpswText = (EditText) findViewById(R.id.setting_newpsw);
        confirmPswText = (EditText) findViewById(R.id.setting_check_psw);
        nameText = (EditText) findViewById(R.id.setting_name);
        emailText = (EditText) findViewById(R.id.setting_email);
        addressText = (EditText) findViewById(R.id.setting_address);
        phoneNumberText = (EditText) findViewById(R.id.setting_phone);

        initData();
        initListener();

    }

    private void initData() {
        accountId = dataSource.getAccountId();
        token = dataSource.getToken();
        Account account = dataSource.getAccount(accountId);
        addressText.setText(account.getAddress());
        emailText.setText(account.getEmail());
        phoneNumberText.setText(account.getPhone());
        nameText.setText(account.getRealName());

    }

    private void initListener() {
        okButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                SettingsForm form = new SettingsForm();
                form.setAccountId(accountId);
                form.setToken(token);
                form.setOldPsw(oldpswText.getText().toString());
                form.setNewPsw(newpswText.getText().toString());
                form.setName(nameText.getText().toString());
                form.setEmail(emailText.getText().toString());
                form.setAddress(addressText.getText().toString());
                form.setPhone(phoneNumberText.getText().toString());
                form.setConfirmPassword(confirmPswText.getText().toString());
                doModifySettings(form);
            }
        });

        cancelButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                SettingsActivity.this.finish();
            }
        });

    }

    private void doModifySettings(SettingsForm form) {
        new SettingsAPI(form, this) {
            @Override
            protected void onSettingsSuccess(long accountVersion) {
                showAlertDialog(R.string.settings_success);
                dataSource.setAccountVersion(accountVersion);
                SettingsActivity.this.finish();
            }

            @Override
            protected void onSettingsPSWWRONG() {
                showAlertDialog(R.string.settings_failed);

            }

        }.call();
    }
}
