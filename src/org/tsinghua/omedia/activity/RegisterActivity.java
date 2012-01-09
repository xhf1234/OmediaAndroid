package org.tsinghua.omedia.activity;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.tsinghua.omedia.R;
import org.tsinghua.omedia.R.id;
import org.tsinghua.omedia.form.RegisterForm;
import org.tsinghua.omedia.serverAPI.RegisterAPI;
import org.tsinghua.omedia.tool.Logger;
import org.tsinghua.omedia.ui.camera.CameraPreview;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

/**
 * 
 * @author hanfuye
 * 
 */
public class RegisterActivity extends BaseActivity {

	private static final Logger logger = Logger
			.getLogger(LandingActivity.class);
	
	private static Camera mCamera;
	private CameraPreview mPreview;
	private Calendar cal;

	private String userSex;
	private String userImage;

	private RegisterForm registerForm;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register_activity);

		initListener();
	}

	private void initListener() {
		// 照片
		register_camera_init();

		// 出生日期
		register_date_init();
		register_date_check();

		// 性别
		RadioGroup sexgroup = (RadioGroup) findViewById(R.id.register_sexGroup);
		register_sex_init(sexgroup);
		register_sex_check(sexgroup);

		// 注册
		Button registerButton = (Button) findViewById(R.id.register_button);
		registerButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (checkRegister()) {
					doRegister();
				} else {
					logger.error( "checkRegister() return false");
				}
			}

		});
	}

	private void register_camera_init() {

		mCamera = getCameraInstance();
		mPreview = new CameraPreview(this, mCamera);
		FrameLayout preview = (FrameLayout) findViewById(id.camera_view);
		preview.addView(mPreview);

	}

	public static Camera getCameraInstance() {
		Camera cam = null;
		try {
			cam = Camera.open(1);
		} catch (Exception e) {
			logger.error( "getCameraInstance()" + e.toString());
		}
		return cam;

	}

	private void register_sex_init(RadioGroup sexgroup) {
		int radioButtonId = sexgroup.getCheckedRadioButtonId();
		RadioButton rb = (RadioButton) RegisterActivity.this
				.findViewById(radioButtonId);
		userSex = rb.getTag() + "";
	}

	private void register_sex_check(RadioGroup sexgroup) {
		sexgroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup arg0, int arg1) {
				// TODO Auto-generated method stub

				int radioButtonId = arg0.getCheckedRadioButtonId();

				RadioButton rb = (RadioButton) RegisterActivity.this
						.findViewById(radioButtonId);
				userSex = rb.getTag() + "";
			}

		});
	}

	private void register_date_init() {
		cal = Calendar.getInstance();
	}

	private void register_date_check() {
		Button selectDatebutton = (Button) findViewById(R.id.register_selectdate);
		selectDatebutton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				new DatePickerDialog(RegisterActivity.this,
						new OnDateSetListener() {
							@Override
							public void onDateSet(DatePicker arg0, int arg1,
									int arg2, int arg3) {
								cal.set(Calendar.YEAR, arg1);
								cal.set(Calendar.MONTH, arg2);
								cal.set(Calendar.DAY_OF_MONTH, arg3);
								updateDate();
							}
						}, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal
								.get(Calendar.DAY_OF_MONTH)).show();
			}
		});
	}

	private void updateDate() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日");
		EditText et = (EditText) findViewById(R.id.register_dateview);
		String temp = df.format(cal.getTime());
		et.setText(temp);
	}

	private boolean checkRegister() {
		// TODO Auto-generated method stub
		if (userImage != null) {
			alert(1, "好像还没有图像呢");
			return false;
		}
		return true;
	}

	private void doRegister() {


		EditText name = (EditText) findViewById(R.id.username_register);
		EditText email = (EditText) findViewById(R.id.email_register);
		EditText passwd = (EditText) findViewById(R.id.password_register);
		EditText Rpasswd = (EditText) findViewById(R.id.password_register_check);

		registerForm = new RegisterForm();
		registerForm.setUsername(name.getText().toString());
		registerForm.setPassword(passwd.getText().toString());
		registerForm.setEmail(email.getText().toString());
		registerForm.setConfirmPassword(Rpasswd.getText().toString());

		new RegisterAPI(registerForm, RegisterActivity.this) {

			@Override
			protected void onSuccess() {
				alert(2, "注册成功");
			}
		}.call();

	}

	private void alert(int kind, String string) {
		// TODO Auto-generated method stub
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		switch (kind) {
		case 1:
			builder.setMessage(string);
			builder.setCancelable(false);
			builder.setPositiveButton("Yes",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {

						}
					});
			break;
		case 2:
			builder.setMessage(string);
			builder.setCancelable(false);
			builder.setPositiveButton("Yes",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							Intent intent = new Intent();
							intent.setClass(RegisterActivity.this,
									LandingActivity.class);
							startActivity(intent);
						}
					});
			break;
		}
		AlertDialog ad = builder.create();
		ad.show();
	}
}
