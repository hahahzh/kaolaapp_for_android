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

public class ResetPWD2Activity extends Activity {

	private Context context = ResetPWD2Activity.this;
	private EditText edit_newpwd;
	private EditText edit_repeat_password;
	private Button btn_submit;
	
	private String newpwd;
	private String repeat_password;
	private LinearLayout layoutProcess;
	private Thread mThread;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_reset_pwd_2);
		edit_newpwd = (EditText)findViewById(R.id.reset2_pwd_txt);
		edit_repeat_password = (EditText)findViewById(R.id.reset2_c_pwd_txt);
		btn_submit = (Button)findViewById(R.id.reset2_submit_btn);
		
		layoutProcess = (LinearLayout)findViewById(R.id.login_status);
		
		btn_submit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {	
				//设置点击背景
//				btn_submit.setBackgroundResource(R.drawable.);
				layoutProcess.setVisibility(View.VISIBLE);
				//获取用户的登录信息，连接服务器，获取登录状态
				newpwd = edit_newpwd.getText().toString().trim();
				repeat_password = edit_repeat_password.getText().toString().trim();
				
				if ("".equals(newpwd) || "".equals(repeat_password)){
					layoutProcess.setVisibility(View.GONE);
//					Toast.makeText(LoginPageActivity.this, context.getString(R.string.login_emptyname_or_emptypwd) , Toast.LENGTH_SHORT).show();
				}else if(!newpwd.equals(repeat_password)){
					layoutProcess.setVisibility(View.GONE);
//					Toast.makeText(LoginPageActivity.this, context.getString(R.string.login_emptyname_or_emptypwd) , Toast.LENGTH_SHORT).show();
				}else {

					
				}
			}
		});
		
		
	}
	
}
