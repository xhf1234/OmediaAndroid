package org.tsinghua.omedia.activity;


import org.tsinghua.omedia.R;
import org.tsinghua.omedia.form.FormProcessor;
import org.tsinghua.omedia.form.LoginForm;

import android.os.Bundle;

public class LandingActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.landing_layout);
    }
    
    private void doLogin(LoginForm form) {
        new FormProcessor<LoginForm>(form) {
            protected void onValidateSuccess() {
                //TODO
            }
            protected void onValidateFailed(String msg) {
                //TODO
            }
        }.exec();
    }
}
