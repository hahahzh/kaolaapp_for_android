package com.winwinapp.my;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.winwinapp.koala.ActionBarActivity;
import com.winwinapp.koala.R;

public class MyIDAuthenActivity extends ActionBarActivity implements OnClickListener{

	ImageView mIDFront;
	ImageView mIDBack;
	ImageView mCert;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_id_authen);

		initActionBar();
		
		mIDFront = (ImageView)findViewById(R.id.id_authen_id_front);
		mIDBack = (ImageView)findViewById(R.id.id_authen_id_back);
//		mCert = (ImageView)findViewById(R.id.id_authen_cert);
		mIDFront.setOnClickListener(this);
		mIDBack.setOnClickListener(this);
//		mCert.setOnClickListener(this);
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
//		case R.id.id_authen_cert:
		case R.id.id_authen_id_back:
		case R.id.id_authen_id_front:
			Intent intent = new Intent(MyIDAuthenActivity.this,MySelectPicActivity.class);
			startActivity(intent);
			break;
		}
	}
}
