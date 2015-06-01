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

public class ResetPWD1Activity extends Activity {

	private Context context = ResetPWD1Activity.this;
	private EditText edit_mailandphone;
	private EditText edit_v_code;
	private Button btn_next;
	private Button btn_get_vcode;
	private String mailandphone;
	private String v_code;
	private LinearLayout layoutProcess;
	private Thread mThread;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_reset_pwd_1);
		edit_mailandphone = (EditText)findViewById(R.id.reset_mailorphone_txt);
		edit_v_code = (EditText)findViewById(R.id.reset_v_code_txt);
		btn_next = (Button)findViewById(R.id.reset_submit_btn);
		btn_get_vcode = (Button)findViewById(R.id.reset_vcode_btn);
		
		layoutProcess = (LinearLayout)findViewById(R.id.login_status);
		
		btn_next.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {	
				//设置点击背景
//				btn_submit.setBackgroundResource(R.drawable.);
//				layoutProcess.setVisibility(View.VISIBLE);
				//获取用户的登录信息，连接服务器，获取登录状态
				mailandphone = edit_mailandphone.getText().toString().trim();
				v_code = edit_v_code.getText().toString().trim();
				
				if ("".equals(mailandphone) || "".equals(v_code)){
//					layoutProcess.setVisibility(View.GONE);
//					Toast.makeText(LoginPageActivity.this, context.getString(R.string.login_emptyname_or_emptypwd) , Toast.LENGTH_SHORT).show();
				}else {
					Bundle bundle = new Bundle();
					Intent intent = new Intent(ResetPWD1Activity.this, ResetPWD2Activity.class);
					intent.putExtras(bundle);
					startActivity(intent);
//					mThread = new Thread(nextRunable);
//					mThread.start();
					
				}
			}
		});
		
		btn_get_vcode.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {	
				//设置点击背景
//				btn_submit.setBackgroundResource(R.drawable.);
				layoutProcess.setVisibility(View.VISIBLE);
				//获取用户的登录信息，连接服务器，获取登录状态
				mailandphone = edit_mailandphone.getText().toString().trim();
				v_code = edit_v_code.getText().toString().trim();
				
				if ("".equals(mailandphone)){
					layoutProcess.setVisibility(View.GONE);
//					Toast.makeText(LoginPageActivity.this, context.getString(R.string.login_emptyname_or_emptypwd) , Toast.LENGTH_SHORT).show();
				}else {


					
				}
			}
		});
	}
	
	/**
	 * 下一步
	 */
	Runnable nextRunable = new Runnable() {
		
		@Override
		public void run() {
			Bundle bundle = new Bundle();
			Intent intent = new Intent(ResetPWD1Activity.this, ResetPWD2Activity.class);
			intent.putExtras(bundle);
			
			startActivity(intent);
		}
	};
	
}
