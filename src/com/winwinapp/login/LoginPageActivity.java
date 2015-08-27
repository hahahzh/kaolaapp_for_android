package com.winwinapp.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.text.TextUtils;
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
import com.winwinapp.koala.KoalaApplication;
import com.winwinapp.koala.R;
import com.winwinapp.network.HTTPPost;
import com.winwinapp.network.NetworkData;

public class LoginPageActivity extends ActionBarActivity {

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
	NetworkData.LoginData mLogData = NetworkData.getInstance().getNewLoginData();
	NetworkData.LoginBack mLogBack = NetworkData.getInstance().getNewLoginBack();
	private static final int MSG_ID_LOGIN_FAIL = 1;
	private Handler mHandler = new Handler(){
		public void handleMessage(Message msg) {  
            switch (msg.what) {      
            case MSG_ID_LOGIN_FAIL:      
                //setTitle("hear me?"); 
            	Toast.makeText(context, "登录失败:"+(TextUtils.isEmpty(mLogBack.error)? "用户名或密码错误":mLogBack.error), Toast.LENGTH_LONG).show();
                break;      
            }      
            super.handleMessage(msg);  
        }  
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_login);
		initActionBar();
		
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
					//layoutProcess.setVisibility(View.GONE);
					Toast.makeText(LoginPageActivity.this, context.getString(R.string.login_emptyname_or_emptypwd) , Toast.LENGTH_SHORT).show();
				}else {
					//启动登陆线程
					mLogData.username = username;
					mLogData.password = password;
					mLogBack.username = username;
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
	
	public void initActionBar(){
		ImageView imageView = new ImageView(this);
		imageView.setImageResource(R.drawable.back);
		setLeftView(imageView);
		setTitle("登录");
		this.setOnLeftClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO 自动生成的方法存根
				//finish();
				Intent intent = new Intent(LoginPageActivity.this,KLHomePageActivity.class);
				intent.putExtra("page", 3);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
			}
			
		});
		
		TextView textview = new TextView(this);
		textview.setText("注册");
		textview.setTextColor(getResources().getColor(R.color.orange));
		setRightView(textview);
		this.setOnRightClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO 自动生成的方法存根
				Bundle bundle = new Bundle();
				Intent intent = new Intent(LoginPageActivity.this, Reg1Activity.class);
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
			boolean success = HTTPPost.sendLoginData(mLogData, mLogBack);
			//Toast.makeText(LoginPageActivity.this, "success="+success+",ses="+mLogBack.sessid+",id="+mLogBack.id, Toast.LENGTH_LONG).show();
			if(mLogBack.code == 0){
				KoalaApplication app = ((KoalaApplication)LoginPageActivity.this.getApplication());
				app.saveSession(mLogBack);
				Intent intent = new Intent(LoginPageActivity.this,KLHomePageActivity.class);
				intent.putExtra("page", 3);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
			}else{
				mHandler.sendEmptyMessage(MSG_ID_LOGIN_FAIL);
			}
		}
	};
	@Override
	public void onBackPressed() {
		// TODO 自动生成的方法存根
		//super.onBackPressed();
		Intent intent = new Intent(LoginPageActivity.this,KLHomePageActivity.class);
		intent.putExtra("page", 0);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
	}

}
