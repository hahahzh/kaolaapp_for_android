package com.winwinapp.my;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.winwinapp.koala.ActionBarActivity;
import com.winwinapp.koala.R;

public class BindCardPhoneCertActivity extends ActionBarActivity implements
		OnClickListener {

	private static final int MESSAGE_UPDATE_TIMER = 1;
	Button mNextStep;
	String mMobile = "158××××1346";
	TextView mTips;
	TextView mVerCode;
	Button mSend;
	int mSecond = 60;
	updateButtonThrad mUdateThread = new updateButtonThrad();
	
	private Handler mHandler = new Handler(){
		public void handleMessage(Message msg){
			//Intent intent;
			//int arg1
			switch(msg.what){
			case MESSAGE_UPDATE_TIMER:
				//if()
				break;
			}
		}
	};
	
	public class updateButtonThrad extends Thread{
		public void run(){
			if(mSecond >= 0){
				mSecond--;
				mSend.setText("重发("+mSecond+"秒)");
				mHandler.removeCallbacks(mUdateThread);
				mHandler.postDelayed(mUdateThread, 1000);
			}else{
				mHandler.removeCallbacks(mUdateThread);
				mSend.setEnabled(true);
			}
		}
	}
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_bind_card_phone_cert);

		initActionBar();
		
		mNextStep = (Button)findViewById(R.id.my_card_phone_cer_next);
		mNextStep.setOnClickListener(this);
		mSend = (Button)findViewById(R.id.card_phone_send);
		mSend.setOnClickListener(this);
		mSend.setEnabled(false);
		mSecond = 60;
		mHandler.post(mUdateThread);
		mVerCode = (TextView)findViewById(R.id.card_phone_vercode);
		
		mMobile = getIntent().getStringExtra("mobile");
		mTips = (TextView)findViewById(R.id.card_phone_cert_tips);
		String str = mTips.getText().toString();
		String str1 = mMobile.substring(3, 7);
		str = str.replace("158××××1346", mMobile.replace(str1, "××××"));
		mTips.setText(str);
	}
	
	public void initActionBar(){
		ImageView imageView = new ImageView(this);
		imageView.setImageResource(R.drawable.back);
		setLeftView(imageView);
		setTitle("验证手机号");
		this.setOnLeftClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO 自动生成的方法存根
				finish();
			}
			
		});
	}

	@Override
	public void onClick(View arg0) {
		// TODO 自动生成的方法存根
		switch(arg0.getId()){
		case R.id.my_card_phone_cer_next:
			//Intent intent = new Intent(BindCardPhoneCertActivity.this,BindCardTypeActivity.class);
			//startActivity(intent);
			showSelfDefineDialog();
			break;
		case R.id.card_phone_send:
			mSend.setEnabled(false);
			mSecond = 60;
			mHandler.post(mUdateThread);
			break;
		}
	}
	
	@Override
	public void onDestroy(){
		super.onDestroy();
		mHandler.removeCallbacks(mUdateThread);
	}
	
	public void showSelfDefineDialog() {
		AlertDialog.Builder builder = new Builder(this);
		builder.setMessage("绑定银行卡失败,暂时还没有接口"); 
		builder.setTitle("提示");  
		
		builder.setPositiveButton("确认", new DialogInterface.OnClickListener(){
		public void onClick(DialogInterface dialoginterface, int i) {
		  	dialoginterface.dismiss();
				}
			  });
		  builder.create().show();
		}

}
