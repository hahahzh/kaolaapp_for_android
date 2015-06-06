package com.winwinapp.my;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

import com.winwinapp.koala.ActionBarActivity;
import com.winwinapp.koala.R;

public class BindCardInformationActivity extends ActionBarActivity implements OnClickListener{

	Button mNextStep;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_bind_card_information);

		initActionBar();
		
		mNextStep = (Button)findViewById(R.id.my_card_information_next_step);
		mNextStep.setOnClickListener(this);
		
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
		switch(arg0.getId()){
		case R.id.my_card_information_next_step:
			Intent intent = new Intent(BindCardInformationActivity.this,BindCardPhoneCertActivity.class);
			startActivity(intent);
			break;
		}
	}
}
