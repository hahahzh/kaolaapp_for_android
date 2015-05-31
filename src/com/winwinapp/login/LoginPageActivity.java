package com.winwinapp.login;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.winwinapp.koala.R;

public class LoginPageActivity extends Activity {

	private Context context = LoginPageActivity.this;
	private EditText edit_username;
	private EditText edit_password;
	private Button btn_submit;
	private String username;
	private String password;
	private LinearLayout layoutProcess;
	private Thread mThread;
	private TextView reg;
	private TextView resetpwd;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_login);
		edit_username = (EditText)findViewById(R.id.login_username_txt);
		edit_password = (EditText)findViewById(R.id.login_pwd_txt);
		btn_submit = (Button)findViewById(R.id.login_submit_btn);
		layoutProcess = (LinearLayout)findViewById(R.id.login_status);
		reg = (TextView)findViewById(R.id.login_reg_tv);
		resetpwd = (TextView)findViewById(R.id.login_forgetpwg_tv);
		/*防止UI冲突*/
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
			.detectDiskReads()
			.detectDiskWrites()
			.detectNetwork()   // or .detectAll() for all detectable problems
			.penaltyLog()
			.build());
		StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
			.detectLeakedSqlLiteObjects()
			.penaltyLog()
			.penaltyDeath()
			.build());
		
		btn_submit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {	
				//设置点击背景
//				btn_submit.setBackgroundResource(R.drawable.);
				layoutProcess.setVisibility(View.VISIBLE);
				//获取用户的登录信息，连接服务器，获取登录状态
				username = edit_username.getText().toString().trim();
				password = edit_password.getText().toString().trim();
				
				if ("".equals(username) || "".equals(password)){
					layoutProcess.setVisibility(View.GONE);
//					Toast.makeText(LoginPageActivity.this, context.getString(R.string.login_emptyname_or_emptypwd) , Toast.LENGTH_SHORT).show();
				}else {
					//启动登陆线程
					mThread = new Thread(loginRunable);
					mThread.start();
					
				}
			}
		});
		
		reg.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {	
//				btnRegNewUser.setBackgroundResource(R.drawable.btn_click);
				Bundle bundle = new Bundle();
				Intent intent = new Intent(LoginPageActivity.this, Reg1Activity.class);
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
		
		resetpwd.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {	
//				btnRegNewUser.setBackgroundResource(R.drawable.btn_click);
				Bundle bundle = new Bundle();
				Intent intent = new Intent(LoginPageActivity.this, ResetPWD1Activity.class);
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
	}
	
	/**
	 * 登陆线程
	 */
	Runnable loginRunable = new Runnable() {
		
		@Override
		public void run() {
		}
	};

}
