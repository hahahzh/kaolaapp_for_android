package com.winwinapp.calendar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.winwinapp.koala.ActionBarActivity;
import com.winwinapp.koala.KLHomePageActivity;
import com.winwinapp.koala.KoalaApplication;
import com.winwinapp.koala.R;
import com.winwinapp.koala.fragment_homepage;
import com.winwinapp.login.LoginPageActivity;
import com.winwinapp.network.HTTPPost;
import com.winwinapp.network.NetworkData;
import com.winwinapp.network.NetworkData.SetDepositData;

public class SetDepositActivity extends ActionBarActivity implements TextWatcher{

	private static final int SET_DEPOSIT_BACK = 0;
	NetworkData.SetDepositData mData = NetworkData.getInstance().getNewSetDepositData();
	NetworkData.CommonBack mBack = NetworkData.getInstance().getCommonBack();
	Button mOK;
	TextView mContractTotal;
	TextView mContractFirstPay;
	TextView mSetPrefix;
	EditText mSetPercent;
	TextView mSetSuffix;
	TextView mTips;
	int total = 100000;
	int suffix = 5000;
	int bid = 0;
	
	private Handler mHandler = new Handler(){
		public void handleMessage(Message msg){
			//Intent intent;
			switch(msg.what){
			case SET_DEPOSIT_BACK:
				String error = (String)msg.obj;
				if("OK".equals(error)){
					if(KoalaApplication.mUserType == fragment_homepage.TYPE_OWER){
						Intent intent = new Intent(SetDepositActivity.this , SetScore.class);
						intent.putExtra("bid", bid);
						startActivity(intent);
					}else{
						Intent intent = new Intent(SetDepositActivity.this,KLHomePageActivity.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(intent);
					}	
				}else{
					Toast.makeText(SetDepositActivity.this, "设置保证金失败："+error, Toast.LENGTH_LONG).show();
				}
				break;
			}
		}
	};
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_set_deposit);

		String bidId = getIntent().getStringExtra("bid");
		try{
			bid = Integer.parseInt(bidId);
		}catch(Exception e){
			bid = 0;
		}
		initActionBar();
		initView();
	}
	
	public void initView(){
		mOK = (Button)findViewById(R.id.set_deposit_OK);
		mContractTotal = (TextView)findViewById(R.id.set_deposit_total);
		mContractFirstPay = (TextView)findViewById(R.id.set_deposit_first_pay);
		mSetPrefix = (TextView)findViewById(R.id.set_deposit_edit_prefix);
		mSetPercent = (EditText)findViewById(R.id.set_deposit_edit);
		mSetSuffix = (TextView)findViewById(R.id.set_deposit_edit_suffix);
		mTips = (TextView)findViewById(R.id.set_deposit_tips);
		
		SpannableStringBuilder style=new SpannableStringBuilder(mTips.getText());
		style.setSpan(new ForegroundColorSpan(Color.RED),6,9,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE); 
		style.setSpan(new ForegroundColorSpan(Color.RED),62,69,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE); 
		mTips.setText(style);
		
		mSetPercent.addTextChangedListener(this);
		
		mOK.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO 自动生成的方法存根
				
				new Thread(){
					public void run(){
						boolean success = false;
						mData.bid = bid;
						try{
							mData.grate = Integer.parseInt(mSetPercent.getText().toString());
						}catch(Exception e){
							mData.grate = 0;
						}
						success = HTTPPost.setDeposit(mData, mBack);
						Message msg = Message.obtain();
						msg.what = SET_DEPOSIT_BACK;
						if(success){
							msg.obj = "OK";
						}else{
							msg.obj = mBack.error;
						}
						mHandler.sendMessage(msg);
					}
				}.start();
				
			}
			
		});
	}
	
	public void initActionBar(){
		ImageView imageView = new ImageView(this);
		imageView.setImageResource(R.drawable.back);
		setLeftView(imageView);
		setTitle("设置质保金");
		this.setOnLeftClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO 自动生成的方法存根
				finish();
			}
			
		});
	}

	@Override
	public void afterTextChanged(Editable arg0) {
		// TODO 自动生成的方法存根
		String str = mSetPercent.getText().toString();
		if(str != null && !str.isEmpty()){
			int percent = Integer.parseInt(str);
			if(percent > 0 && percent < 50){
				mSetSuffix.setText("% = "+total*percent/100);
			}else{
				percent = 5;
				mSetPercent.setText("5");
				mSetSuffix.setText("% = "+total*percent);
			}
		}
	}

	@Override
	public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
			int arg3) {
		// TODO 自动生成的方法存根
		
	}

	@Override
	public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
		// TODO 自动生成的方法存根
		
	}

}
