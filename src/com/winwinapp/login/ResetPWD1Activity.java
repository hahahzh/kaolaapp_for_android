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
import com.winwinapp.koala.KLHomePageActivity;
import com.winwinapp.koala.R;
import com.winwinapp.network.HTTPPost;
import com.winwinapp.network.NetworkData;

public class ResetPWD1Activity extends ActionBarActivity {

	private static final int REQUEST_CODE_BACK = 1;
	private Context context = ResetPWD1Activity.this;
	private EditText edit_mailandphone;
	private EditText edit_v_code;
	private Button btn_next;
	private Button btn_get_vcode;
	private String mailandphone;
	private String v_code;
	private LinearLayout layoutProcess;
	private Thread mThread;

	NetworkData.RegisterSendCodeData mData = NetworkData.getInstance().getNewRegisterSendCodeData();
	NetworkData.CommonBack mBack = NetworkData.getInstance().getCommonBack();
	
	private Handler mHandler = new Handler(){
		public void handleMessage(Message msg){
			switch(msg.what){
			case REQUEST_CODE_BACK:
				String error = (String) msg.obj;
				if("OK".equals(error)){
					Toast.makeText(ResetPWD1Activity.this, "发送验证码成功："+error, Toast.LENGTH_LONG).show();
				}else{
					Toast.makeText(ResetPWD1Activity.this, "发送验证码失败："+error, Toast.LENGTH_LONG).show();
				}
				break;
			}
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_reset_pwd_1);
		edit_mailandphone = (EditText)findViewById(R.id.reset_mailorphone_txt);
		edit_v_code = (EditText)findViewById(R.id.reset_v_code_txt);
		btn_next = (Button)findViewById(R.id.reset_submit_btn);
		btn_get_vcode = (Button)findViewById(R.id.reset_vcode_btn);
		
		initActionBar();
		//layoutProcess = (LinearLayout)findViewById(R.id.login_status);
		
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
					Toast.makeText(ResetPWD1Activity.this, "邮箱/手机和验证码不能为空" , Toast.LENGTH_SHORT).show();
				}else {
					Intent intent = new Intent(ResetPWD1Activity.this, ResetPWD2Activity.class);
					intent.putExtra("phone_mail", mailandphone);
					intent.putExtra("auth_code", v_code);
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
				//layoutProcess.setVisibility(View.VISIBLE);
				//获取用户的登录信息，连接服务器，获取登录状态
				mailandphone = edit_mailandphone.getText().toString().trim();
				v_code = edit_v_code.getText().toString().trim();
				
				if ("".equals(mailandphone)){
					//layoutProcess.setVisibility(View.GONE);
					Toast.makeText(ResetPWD1Activity.this, "邮箱/手机不能为空" , Toast.LENGTH_SHORT).show();
//					Toast.makeText(LoginPageActivity.this, context.getString(R.string.login_emptyname_or_emptypwd) , Toast.LENGTH_SHORT).show();
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
		setTitle("忘记密码");
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
			mData.phone_mail = mailandphone;
			success = HTTPPost.RegisterSendCode(mData, mBack);
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
	
	/**
	 * 下一步
	 */
	Runnable nextRunable = new Runnable() {
		
		@Override
		public void run() {
			Bundle bundle = new Bundle();
			Intent intent = new Intent(ResetPWD1Activity.this, ResetPWD2Activity.class);
			intent.putExtra("phone_mail", mailandphone);
			intent.putExtra("auth_code", v_code);
			intent.putExtras(bundle);
			
			startActivity(intent);
		}
	};
	
}
