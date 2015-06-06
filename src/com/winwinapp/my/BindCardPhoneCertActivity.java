package com.winwinapp.my;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

import com.winwinapp.koala.ActionBarActivity;
import com.winwinapp.koala.R;

public class BindCardPhoneCertActivity extends ActionBarActivity implements
		OnClickListener {

	Button mNextStep;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_bind_card_phone_cert);

		initActionBar();
		
		mNextStep = (Button)findViewById(R.id.my_card_phone_cer_next);
		mNextStep.setOnClickListener(this);
		
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
			Intent intent = new Intent(BindCardPhoneCertActivity.this,BindCardTypeActivity.class);
			startActivity(intent);
			break;
		}
	}

}
