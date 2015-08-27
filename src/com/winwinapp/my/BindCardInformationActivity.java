package com.winwinapp.my;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.winwinapp.koala.ActionBarActivity;
import com.winwinapp.koala.KoalaApplication;
import com.winwinapp.koala.R;
import com.winwinapp.koala.WebActivity;
import com.winwinapp.network.HTTPPost;
import com.winwinapp.network.NetworkData;
import com.winwinapp.util.Utils;

public class BindCardInformationActivity extends ActionBarActivity implements OnClickListener{

	Button mNextStep;
	TextView mCardType;
	TextView mName;
	TextView mID;
	TextView mMobile;
	CheckBox mAgree;
	TextView mProtocol;
	
	String mCardNo;
	
	NetworkData.BindBankData mData = NetworkData.getInstance().getNewBindBankData();
	NetworkData.BindBankBack mBack = NetworkData.getInstance().getNewBindBankBack();
	
	private Handler mHandler = new Handler(){
		public void handleMessage(Message msg){
			//Intent intent;
			switch(msg.what){
			case 1:
				String error = (String)msg.obj;
				if("OK".equals(error)){
					Toast.makeText(BindCardInformationActivity.this, "绑定银行卡成功", Toast.LENGTH_LONG).show();
					Intent intent = new Intent(BindCardInformationActivity.this,BindCardPhoneCertActivity.class);
					intent.putExtra("mobile", mMobile.getText().toString());
					intent.putExtra("bank_no", mCardNo);
					intent.putExtra("bank_name", mBank);
					startActivity(intent);
				}else{
					Toast.makeText(BindCardInformationActivity.this, "绑定银行卡失败："+error, Toast.LENGTH_LONG).show();
				}
				break;
			}
		}
	};
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_bind_card_information);

		initActionBar();
		mCardNo = getIntent().getStringExtra("cardno");
		
		mNextStep = (Button)findViewById(R.id.my_card_information_next_step);
		mNextStep.setOnClickListener(this);
		mNextStep.setEnabled(false);
		
		mCardType = (TextView)findViewById(R.id.card_info_type);
		mCardType.setOnClickListener(this);
		mName = (TextView)findViewById(R.id.card_info_name);
		mID = (TextView)findViewById(R.id.card_info_id);
		mMobile = (TextView)findViewById(R.id.card_info_mobile);
		
		mAgree = (CheckBox)findViewById(R.id.card_info_agree);
		mAgree.setOnCheckedChangeListener(new OnCheckedChangeListener(){

			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				// TODO 自动生成的方法存根
				mNextStep.setEnabled(arg1);
			}
			
		});
		mProtocol = (TextView)findViewById(R.id.card_info_protocol);
		mProtocol.setOnClickListener(this);
	}
	
	public void initActionBar(){
		ImageView imageView = new ImageView(this);
		imageView.setImageResource(R.drawable.back);
		setLeftView(imageView);
		setTitle("银行卡信息");
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
		Intent intent;
		switch(arg0.getId()){
		case R.id.my_card_information_next_step:
			if(TextUtils.isEmpty(mName.getText()) || TextUtils.isEmpty(mID.getText()) || TextUtils.isEmpty(mMobile.getText())){
				Toast.makeText(this, "请填写完整信息", Toast.LENGTH_LONG).show();
			}else if( !Utils.isMobilePhone(mMobile.getText().toString())){
				Toast.makeText(this, "请填写合法的手机号", Toast.LENGTH_LONG).show();
			}else if(18 != mID.getText().toString().length()){
				Toast.makeText(this, "请填写合法的身份证号", Toast.LENGTH_LONG).show();
			}else{
				new Thread(){
					public void run(){
						boolean success = false;
						mData.bank_add = "";
						mData.bank_name = mBank;
						mData.bank_no = mCardNo;
						mData.id = 0;
						mData.uid = KoalaApplication.mUserType;
						mData.is_on = 0;
						mData.mobile = mMobile.getText().toString();
						mData.real_name = mName.getText().toString();
						mData.idcard = mID.getText().toString();
						success = HTTPPost.BindBank(mData, mBack);
						Message msg = Message.obtain();
						msg.what = 1;
						if(success){
							msg.obj = "OK";
						}else{
							msg.obj = mBack.error;
						}
						mHandler.sendMessage(msg);
					}
				}.start();
				
			}
			break;
		case R.id.card_info_type:
			intent = new Intent(BindCardInformationActivity.this,BindCardTypeActivity.class);
			this.startActivityForResult(intent, 0);
			break;
		case R.id.card_info_protocol:
			intent = new Intent(BindCardInformationActivity.this,WebActivity.class);
			startActivity(intent);
			break;
		}
	}
	
	String mType = "储蓄卡";
	String mBank = "工商银行";
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data){
		super.onActivityResult(requestCode, resultCode, data);
		switch(requestCode){
		case 0:
			mType = data.getStringExtra("type");
			mBank = data.getStringExtra("bank");
			mCardType.setText(mBank + " " + mType);
			break;
		}
	}
}
