package com.winwinapp.login;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.winwinapp.koala.ActionBarActivity;
import com.winwinapp.koala.R;
import com.winwinapp.network.HTTPPost;
import com.winwinapp.network.NetworkData;

public class ResetPWD2Activity extends ActionBarActivity {

	private static final int REQUEST_CODE_BACK = 1;
	private Context context = ResetPWD2Activity.this;
	private EditText edit_newpwd;
	private EditText edit_repeat_password;
	private Button btn_submit;
	
	private String newpwd;
	private String repeat_password;
	//private LinearLayout layoutProcess;
	private Thread mThread;
	private String phone_mail;
	private String auth_code;

	NetworkData.RegisterSendCodeData mData = NetworkData.getInstance().getNewRegisterSendCodeData();
	NetworkData.CommonBack mBack = NetworkData.getInstance().getCommonBack();
	
	private Handler mHandler = new Handler(){
		public void handleMessage(Message msg){
			switch(msg.what){
			case REQUEST_CODE_BACK:
				String error = (String) msg.obj;
				if("OK".equals(error)){
					Toast.makeText(ResetPWD2Activity.this, "重置密码成功："+error, Toast.LENGTH_LONG).show();
					Intent intent = new Intent(context,LoginPageActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(intent);
				}else{
					Toast.makeText(ResetPWD2Activity.this, "重置密码失败："+error, Toast.LENGTH_LONG).show();
					finish();
				}
				
				break;
			}
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_reset_pwd_2);
		initActionBar();
		edit_newpwd = (EditText)findViewById(R.id.reset2_pwd_txt);
		edit_repeat_password = (EditText)findViewById(R.id.reset2_c_pwd_txt);
		btn_submit = (Button)findViewById(R.id.reset2_submit_btn);
		
		phone_mail = getIntent().getStringExtra("phone_mail");
		auth_code = getIntent().getStringExtra("auth_code");
		//layoutProcess = (LinearLayout)findViewById(R.id.login_status);
		
		btn_submit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {	
				//设置点击背景
//				btn_submit.setBackgroundResource(R.drawable.);
				//layoutProcess.setVisibility(View.VISIBLE);
				//获取用户的登录信息，连接服务器，获取登录状态
				newpwd = edit_newpwd.getText().toString().trim();
				repeat_password = edit_repeat_password.getText().toString().trim();
				
				if ("".equals(newpwd) || "".equals(repeat_password)){
					//layoutProcess.setVisibility(View.GONE);
					Toast.makeText(ResetPWD2Activity.this, "密码不能为空" , Toast.LENGTH_SHORT).show();
				}else if(!newpwd.equals(repeat_password)){
					//layoutProcess.setVisibility(View.GONE);
					Toast.makeText(ResetPWD2Activity.this, "两次密码不一致" , Toast.LENGTH_SHORT).show();
				}else {
					new SendVerCodeThread().start();
				}
			}
		});
		
		
	}
	
	public void initActionBar(){
		ImageView imageView = new ImageView(this);
		imageView.setImageResource(R.drawable.back);
		setLeftView(imageView);
		setTitle("新密码");
		this.setOnLeftClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO 自动生成的方法存根
				finish();
			}
			
		});
	}
	
	/**
	 * 获取验证码线程
	 */
	public class SendVerCodeThread extends Thread {
		
		@Override
		public void run() {
			boolean success = false;
			mData.phone_mail = phone_mail;
			mData.auth_code = auth_code;
			success = HTTPPost.ResetPWD(mData, mBack);
			Message msg = Message.obtain();
			msg.what = REQUEST_CODE_BACK;
			if(success){
				msg.obj = "OK";
			}else{
				msg.obj = mBack.error;
			}
			mHandler.sendMessage(msg);
		}
	};
	
}
