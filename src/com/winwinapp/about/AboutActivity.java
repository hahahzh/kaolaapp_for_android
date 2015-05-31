package com.winwinapp.about;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

import com.winwinapp.koala.R;
import com.winwinapp.login.LoginPageActivity;
import com.winwinapp.login.ResetPWD1Activity;

public class AboutActivity extends Activity {

	private Context context = AboutActivity.this;
	private View l1;
	private View l2;
	private View l3;
	
	private LinearLayout layoutProcess;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_about);
		l1 = (View)findViewById(R.id.about_l1);
		l2 = (LinearLayout)findViewById(R.id.about_l2);
		l3 = (LinearLayout)findViewById(R.id.about_l3);
		
		layoutProcess = (LinearLayout)findViewById(R.id.login_status);
		l1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Bundle bundle = new Bundle();
				Intent intent = new Intent(AboutActivity.this, FuncDescActivity.class);
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
		
		l2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		
		l3.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Bundle bundle = new Bundle();
				Intent intent = new Intent(AboutActivity.this, FeedbackActivity.class);
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
		
	}
}
