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
import android.widget.Toast;

import com.winwinapp.koala.ActionBarActivity;
import com.winwinapp.koala.KLHomePageActivity;
import com.winwinapp.koala.KoalaApplication;
import com.winwinapp.koala.R;
import com.winwinapp.login.LoginPageActivity;
import com.winwinapp.network.HTTPPost;
import com.winwinapp.network.NetworkData;

public class BindCardPhoneCertActivity extends ActionBarActivity implements
		OnClickListener {

	private static final int MESSAGE_UPDATE_TIMER = 1;
	Button mNextStep;
	String mMobile = "158��������1346";
	String mBankName = "";
	String mBankNo;
	TextView mTips;
	TextView mVerCode;
	Button mSend;
	int mSecond = 60;
	updateButtonThrad mUdateThread = new updateButtonThrad();
	NetworkData.CommonBack mBack = NetworkData.getInstance().getCommonBack();
	NetworkData.DoBindBankData mData = NetworkData.getInstance().getNewDoBindBankData();
	
	private Handler mHandler = new Handler(){
		public void handleMessage(Message msg){
			//Intent intent;
			//int arg1
			String error;
			switch(msg.what){
			case MESSAGE_UPDATE_TIMER:
				//if()
				break;
			case 2:
				error = (String)msg.obj;
				if("OK".equals(error)){
					Toast.makeText(BindCardPhoneCertActivity.this, "������֤�뷢�ͳɹ�,��������֤��", Toast.LENGTH_LONG).show();
				}else{
					Toast.makeText(BindCardPhoneCertActivity.this, "������֤�뷢��ʧ�ܣ�"+error, Toast.LENGTH_LONG).show();
				}
				break;
			case 3:
				error = (String)msg.obj;
				if("OK".equals(error)){
					Toast.makeText(BindCardPhoneCertActivity.this, "�����п��ɹ�", Toast.LENGTH_LONG).show();
					Intent intent = new Intent(BindCardPhoneCertActivity.this,KLHomePageActivity.class);
					intent.putExtra("page", 3);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(intent);
				}else{
					Toast.makeText(BindCardPhoneCertActivity.this, "�����п�ʧ�ܣ�"+error, Toast.LENGTH_LONG).show();
				}
				break;
			}
		}
	};
	
	public class SendShortMsgThread extends Thread{
		public void run(){
			boolean success = false;
			success = HTTPPost.SendShortMessage(mMobile, mBack);
			Message msg = Message.obtain();
			msg.what = 2;
			if(success){
				msg.obj = "OK";
			}else{
				msg.obj = mBack.error;
			}
			mHandler.sendMessage(msg);
		}
	}
	
	public class BindBankFinishThread extends Thread{
		public void run(){
			boolean success = false;
			mData.auth_code = mVerCode.getText().toString();
			mData.bank_name = mBankName;
			mData.bank_no = mBankNo;
			success = HTTPPost.BindBankFinish(mData, mBack);
			Message msg = Message.obtain();
			msg.what = 3;
			mBack.error = "";
			if(success){
				msg.obj = "OK";
			}else{
				msg.obj = mBack.error;
			}
			mHandler.sendMessage(msg);
		}
	}
	
	public class updateButtonThrad extends Thread{
		public void run(){
			if(mSecond > 0){
				mSecond--;
				mSend.setText("�ط�("+mSecond+"��)");
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
		mBankName = getIntent().getStringExtra("bank_name");
		mBankNo = getIntent().getStringExtra("bank_no");
		mTips = (TextView)findViewById(R.id.card_phone_cert_tips);
		String str = mTips.getText().toString();
		String str1 = mMobile.substring(3, 7);
		str = str.replace("158��������1346", mMobile.replace(str1, "��������"));
		mTips.setText(str);
		
		new SendShortMsgThread().start();
	}
	
	public void initActionBar(){
		ImageView imageView = new ImageView(this);
		imageView.setImageResource(R.drawable.back);
		setLeftView(imageView);
		setTitle("��֤�ֻ���");
		this.setOnLeftClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO �Զ����ɵķ������
				finish();
			}
			
		});
	}

	@Override
	public void onClick(View arg0) {
		// TODO �Զ����ɵķ������
		switch(arg0.getId()){
		case R.id.my_card_phone_cer_next:
			//Intent intent = new Intent(BindCardPhoneCertActivity.this,BindCardTypeActivity.class);
			//startActivity(intent);
			//showSelfDefineDialog();
			new BindBankFinishThread().start();
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
		builder.setMessage("�����п�ʧ��,��ʱ��û�нӿ�"); 
		builder.setTitle("��ʾ");  
		
		builder.setPositiveButton("ȷ��", new DialogInterface.OnClickListener(){
		public void onClick(DialogInterface dialoginterface, int i) {
		  	dialoginterface.dismiss();
				}
			  });
		  builder.create().show();
		}

}
