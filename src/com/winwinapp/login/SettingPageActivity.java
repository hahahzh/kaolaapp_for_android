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

import com.winwinapp.about.AboutActivity;
import com.winwinapp.about.FeedbackActivity;
import com.winwinapp.koala.R;

public class SettingPageActivity extends Activity {

	private EditText edit_nickname;
	private Button btn_submit;
	private String nickname;
	private LinearLayout layoutProcess;
	private View l2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_setting);
		edit_nickname = (EditText)findViewById(R.id.setting_nickname_txt);
		btn_submit = (Button)findViewById(R.id.setting_submit_btn);
		l2 = (LinearLayout)findViewById(R.id.setting_l2);
		btn_submit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {	
				//设置点击背景
//				btn_submit.setBackgroundResource(R.drawable.);
				layoutProcess.setVisibility(View.VISIBLE);
				//获取用户的登录信息，连接服务器，获取登录状态
				nickname = edit_nickname.getText().toString().trim();
				
				if ("".equals(nickname)){
//					layoutProcess.setVisibility(View.GONE);
//					Toast.makeText(LoginPageActivity.this, context.getString(R.string.login_emptyname_or_emptypwd) , Toast.LENGTH_SHORT).show();
				}else {
				}
			}
		});
		
		l2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Bundle bundle = new Bundle();
				Intent intent = new Intent(SettingPageActivity.this, ResetPWD1Activity.class);
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
	}
	
}
