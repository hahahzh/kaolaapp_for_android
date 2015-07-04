package com.winwinapp.my;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.winwinapp.koala.ActionBarActivity;
import com.winwinapp.koala.R;

public class MyIDAuthenActivity extends ActionBarActivity implements OnClickListener{

	ImageView mIDFront;
	ImageView mIDBack;
	ImageView mCert;
	LinearLayout mIDLL;
	LinearLayout mCertLL;
	TextView mIDText;
	TextView mCertText;
	ImageView mIDIndicator;
	ImageView mCertIndicator;
	TextView mName;
	TextView mIDNumber;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_id_authen);

		initActionBar();
		
		mIDFront = (ImageView)findViewById(R.id.id_authen_id_front);
		mIDBack = (ImageView)findViewById(R.id.id_authen_id_back);
		mCert = (ImageView)findViewById(R.id.id_authen_add_cert);
		mIDFront.setOnClickListener(this);
		mIDBack.setOnClickListener(this);
		mCert.setOnClickListener(this);
		
		mIDText = (TextView)findViewById(R.id.id_auth_id_txt);
		mCertText = (TextView)findViewById(R.id.id_auth_cert_txt);
		mIDText.setOnClickListener(this);
		mCertText.setOnClickListener(this);
		mIDLL = (LinearLayout)findViewById(R.id.id_authen_id_ll);
		mCertLL = (LinearLayout)findViewById(R.id.id_authen_cert_ll);
		
		mIDIndicator = (ImageView)findViewById(R.id.id_auth_id_indicator);
		mCertIndicator = (ImageView)findViewById(R.id.id_auth_cert_indicator);
		mName = (TextView)findViewById(R.id.id_auth_name);
		mIDNumber = (TextView)findViewById(R.id.id_auth_id_number);
		
		mIDText.setTextColor(0xFFFF6600);
		mCertText.setTextColor(0xFF000000);
		mCertLL.setVisibility(View.GONE);
		mCertIndicator.setVisibility(View.INVISIBLE);
	}
	
	public void initActionBar(){
		ImageView imageView = new ImageView(this);
		imageView.setImageResource(R.drawable.back);
		setLeftView(imageView);
		setTitle("身份认证");
		this.setOnLeftClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO 自动生成的方法存根
				finish();
			}
			
		});
		
		TextView txtView = new TextView(this);
		txtView.setTextColor(0xFF00FF00);
		txtView.setText("提交");
		setRightView(txtView);
		this.setOnRightClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO 自动生成的方法存根
				//finish();
			}
			
		});
	}

	@Override
	public void onClick(View arg0) {
		// TODO 自动生成的方法存根
		switch(arg0.getId()){
		case R.id.id_authen_add_cert:
		case R.id.id_authen_id_back:
		case R.id.id_authen_id_front:
			Intent intent = new Intent(MyIDAuthenActivity.this,MySelectPicActivity.class);
			startActivity(intent);
			break;
		case R.id.id_auth_cert_txt:
			mIDLL.setVisibility(View.GONE);
			mIDIndicator.setVisibility(View.INVISIBLE);
			mIDText.setTextColor(0xFF000000);
			
			mCertLL.setVisibility(View.VISIBLE);
			mCertIndicator.setVisibility(View.VISIBLE);
			mCertText.setTextColor(0xFFFF6600);
			break;
		case R.id.id_auth_id_txt:
			mCertLL.setVisibility(View.GONE);
			mCertIndicator.setVisibility(View.INVISIBLE);
			mCertText.setTextColor(0xFF000000);
			
			mIDLL.setVisibility(View.VISIBLE);
			mIDIndicator.setVisibility(View.VISIBLE);
			mIDText.setTextColor(0xFFFF6600);
			break;
		}
	}
}
