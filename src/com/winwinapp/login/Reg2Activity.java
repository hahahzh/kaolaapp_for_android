package com.winwinapp.login;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.winwinapp.koala.KLHomePageActivity;
import com.winwinapp.koala.KoalaApplication;
import com.winwinapp.koala.MessageListActivity;
import com.winwinapp.koala.R;
import com.winwinapp.network.HTTPPost;
import com.winwinapp.network.NetworkData;

public class Reg2Activity extends Activity implements OnClickListener{

	private Context context = Reg2Activity.this;
	private static final int MESSAGE_REG = 1;
	private TextView btn_ower;
	private TextView btn_designer;
	private TextView btn_labor;
	private TextView btn_suprior;
	private EditText edit_qq;
	private EditText edit_city;
	private Button btn_submit;

	private int status;
	private String qq;
	private String city;

	private LinearLayout layoutProcess;
	private ProgressBar mProgressBar;
	private Thread mThread;
	private int mUserType = -1;
	Drawable mCheckLeft = null;
	Drawable mUncheckLeft = null;
	
	String mobile;
	String mail;
	String password;
	String username;
	
	
	NetworkData.RegisterData mRegData = NetworkData.getInstance().getNewRegisterData();
	NetworkData.RegisterBack mRegBack = NetworkData.getInstance().getNewRegisterBack();
	
	private Handler mHandler = new Handler(){
		public void handleMessage(Message msg){
			Intent intent;
			String error;
			switch(msg.what){
			case MESSAGE_REG:
				error = (String)msg.obj;
				if("OK".equals(error)){
					KoalaApplication app = ((KoalaApplication)Reg2Activity.this.getApplication());
					NetworkData.LoginBack mLogBack = NetworkData.getInstance().getNewLoginBack();
					mLogBack.id = mRegBack.id;
					mLogBack.sessid = mRegBack.sessid;
					mLogBack.username = mRegData.username;
					app.saveSession(mLogBack);
					intent = new Intent(context,KLHomePageActivity.class);
					intent.putExtra("page", 3);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(intent);
					
				}
				else{
					Toast.makeText(context, "注册用户失败："+error, Toast.LENGTH_LONG).show();
				}
				break;
			}
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_reg_2);
		btn_ower = (TextView)findViewById(R.id.reg2_ower);
		btn_designer = (TextView)findViewById(R.id.reg2_designer);
		btn_labor = (TextView)findViewById(R.id.reg2_labor);
		btn_suprior = (TextView)findViewById(R.id.reg2_superior);
		btn_ower.setOnClickListener(this);
		btn_designer.setOnClickListener(this);
		btn_labor.setOnClickListener(this);
		btn_suprior.setOnClickListener(this);
		
		mCheckLeft = getResources().getDrawable(R.drawable.reg2_check_c);
		mUncheckLeft = getResources().getDrawable(R.drawable.reg2_check);
	
		edit_qq = (EditText)findViewById(R.id.reg2_qq_txt);
		edit_city = (EditText)findViewById(R.id.reg2_city_txt);
		
		btn_submit = (Button)findViewById(R.id.reg2_submit_btn);
		
		//layoutProcess = (LinearLayout)findViewById(R.id.login_status);
		mProgressBar = (ProgressBar)findViewById(R.id.reg2_progressBar);
		
		Bundle bundle = getIntent().getExtras();
		username = bundle.getString("username");
		mobile = bundle.getString("mobile");
		mail = bundle.getString("mail");
		password = bundle.getString("password");
		
		btn_submit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {	
				//设置点击背景
//				btn_submit.setBackgroundResource(R.drawable.);
				//layoutProcess.setVisibility(View.VISIBLE);
				//获取用户的登录信息，连接服务器，获取登录状态
				qq = edit_qq.getText().toString().trim();
				city = edit_city.getText().toString().trim();
				
				if(mUserType == -1){
					Toast.makeText(context, "请选择您注册的角色" , Toast.LENGTH_SHORT).show();
				}else if ("".equals(qq) || "".equals(city)){
//					layoutProcess.setVisibility(View.GONE);
					Toast.makeText(context, "QQ和城市不能为空" , Toast.LENGTH_SHORT).show();
				}else {
					mProgressBar.setVisibility(View.VISIBLE);
					btn_submit.setEnabled(false);
					btn_ower.setEnabled(false);
					btn_designer.setEnabled(false);
					btn_labor.setEnabled(false);
					btn_suprior.setEnabled(false);
					edit_qq.setEnabled(false);
					edit_city.setEnabled(false);
					mRegData.email = mail;
					mRegData.mobile = mobile;
					mRegData.password = password;
					mRegData.type = mUserType;
					mRegData.username = username;
					
					new Thread(){
						public void run(){
							boolean success = false;
							Message msg = Message.obtain();
							msg.what = MESSAGE_REG;
							success = HTTPPost.RegisterUser(mRegData, mRegBack);
							if(success){
								msg.obj = (Object)("OK");
							}else{
								msg.obj = mRegBack.error;
							}
							mHandler.sendMessage(msg);
						}
					}.start();
					
					
				}
			}
		});
		
	}
	@Override
	public void onClick(View arg0) {
		// TODO 自动生成的方法存根
		switch(arg0.getId()){
		case R.id.reg2_ower:
			btn_ower.setCompoundDrawablesWithIntrinsicBounds(mCheckLeft, null, null, null);
			btn_ower.setTextColor(0xFF00FF00);
			
			btn_designer.setCompoundDrawablesWithIntrinsicBounds(mUncheckLeft, null, null, null);
			btn_designer.setTextColor(0xFF000000);
			btn_labor.setCompoundDrawablesWithIntrinsicBounds(mUncheckLeft, null, null, null);
			btn_labor.setTextColor(0xFF000000);
			btn_suprior.setCompoundDrawablesWithIntrinsicBounds(mUncheckLeft, null, null, null);
			btn_suprior.setTextColor(0xFF000000);
			mUserType = 1;
			break;
		case R.id.reg2_designer:
			btn_designer.setCompoundDrawablesWithIntrinsicBounds(mCheckLeft, null, null, null);
			btn_designer.setTextColor(0xFF00FF00);
			
			btn_ower.setCompoundDrawablesWithIntrinsicBounds(mUncheckLeft, null, null, null);
			btn_ower.setTextColor(0xFF000000);
			btn_labor.setCompoundDrawablesWithIntrinsicBounds(mUncheckLeft, null, null, null);
			btn_labor.setTextColor(0xFF000000);
			btn_suprior.setCompoundDrawablesWithIntrinsicBounds(mUncheckLeft, null, null, null);
			btn_suprior.setTextColor(0xFF000000);
			mUserType = 2;
			break;
		case R.id.reg2_labor:
			btn_labor.setCompoundDrawablesWithIntrinsicBounds(mCheckLeft, null, null, null);
			btn_labor.setTextColor(0xFF00FF00);
			
			btn_ower.setCompoundDrawablesWithIntrinsicBounds(mUncheckLeft, null, null, null);
			btn_ower.setTextColor(0xFF000000);
			btn_designer.setCompoundDrawablesWithIntrinsicBounds(mUncheckLeft, null, null, null);
			btn_designer.setTextColor(0xFF000000);
			btn_suprior.setCompoundDrawablesWithIntrinsicBounds(mUncheckLeft, null, null, null);
			btn_suprior.setTextColor(0xFF000000);
			mUserType = 4;
			break;
		case R.id.reg2_superior:
			btn_suprior.setCompoundDrawablesWithIntrinsicBounds(mCheckLeft, null, null, null);
			btn_suprior.setTextColor(0xFF00FF00);
			
			btn_ower.setCompoundDrawablesWithIntrinsicBounds(mUncheckLeft, null, null, null);
			btn_ower.setTextColor(0xFF000000);
			btn_labor.setCompoundDrawablesWithIntrinsicBounds(mUncheckLeft, null, null, null);
			btn_labor.setTextColor(0xFF000000);
			btn_designer.setCompoundDrawablesWithIntrinsicBounds(mUncheckLeft, null, null, null);
			btn_designer.setTextColor(0xFF000000);
			mUserType = 3;
			break;
		}
	}

}
