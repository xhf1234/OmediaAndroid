
package org.tsinghua.omedia.activity;

import org.tsinghua.omedia.R;
import org.tsinghua.omedia.form.RegisterForm;
import org.tsinghua.omedia.serverAPI.RegisterAPI;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * @author czw
 */
public class RegisterActivity extends BaseActivity {

    private Button resetButton;

    private Button okButton;

    private EditText userNameText;

    private EditText pswText;

    private EditText confirmPswText;

    private EditText nameText;

    private EditText emailText;

    private EditText addressText;

    private EditText phoneNumberText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_view);

        resetButton = (Button) findViewById(R.id.register_reset_id);
        okButton = (Button) findViewById(R.id.register_ok);
        userNameText = (EditText) findViewById(R.id.register_username_id);
        pswText = (EditText) findViewById(R.id.register_psw);
        confirmPswText = (EditText) findViewById(R.id.register_check_psw);
        nameText = (EditText) findViewById(R.id.register_real_name);
        emailText = (EditText) findViewById(R.id.register_email_id);
        addressText = (EditText) findViewById(R.id.register_address_id);
        phoneNumberText = (EditText) findViewById(R.id.register_phone_id);

        initListener();

    }

    private void initListener() {
        okButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                RegisterForm form = new RegisterForm();
                form.setUsername(userNameText.getText().toString());
                form.setPassword(pswText.getText().toString());
                form.setConfirmPassword(confirmPswText.getText().toString());
                form.setEmail(emailText.getText().toString());
                doRegisterInfo(form);
            }
        });

        resetButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                userNameText.setText("");
                pswText.setText("");
                confirmPswText.setText("");
                nameText.setText("");
                emailText.setText("");
                addressText.setText("");
                phoneNumberText.setText("");
            }
        });

    }

    private void doRegisterInfo(RegisterForm form) {
        new RegisterAPI(form, this) {

            @Override
            protected void onSuccess() {
                Toast.makeText(RegisterActivity.this, R.string.register_success, 0);
                // TODO landing?
                RegisterActivity.this.finish();
            }

        }.call();
    }
}
