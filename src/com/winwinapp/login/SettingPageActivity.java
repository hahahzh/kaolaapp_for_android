package com.winwinapp.login;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.winwinapp.about.AboutActivity;
import com.winwinapp.about.FeedbackActivity;
import com.winwinapp.bids.BidsPopupActivity;
import com.winwinapp.bids.BidsPublishBids;
import com.winwinapp.koala.KoalaApplication;
import com.winwinapp.koala.R;
import com.winwinapp.koala.fragment_homepage;

public class SettingPageActivity extends Activity implements OnClickListener{

	private EditText edit_nickname;
	private Button btn_submit;
	private String nickname;
	private LinearLayout layoutProcess;
	private View l2;
	LinearLayout mFee = null;
	TextView mExperience;
	ImageView mFeeSep;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(KoalaApplication.mUserType == fragment_homepage.TYPE_OWER){
			setContentView(R.layout.layout_setting);
		}else{
			setContentView(R.layout.layout_setting_labor);
			mFee = (LinearLayout)findViewById(R.id.setting_designer_fee_ll);
			mFeeSep = (ImageView)findViewById(R.id.setting_designer_fee_sep);
			if(KoalaApplication.mUserType != fragment_homepage.TYPE_DESIGNER){
				mFee.setVisibility(View.GONE);
				mFeeSep.setVisibility(View.GONE);
			}
			mExperience = (TextView)findViewById(R.id.setting_labor_experience);
			mExperience.setOnClickListener(this);
		}
		edit_nickname = (EditText)findViewById(R.id.setting_nickname_txt);
		btn_submit = (Button)findViewById(R.id.setting_submit_btn);
		l2 = (LinearLayout)findViewById(R.id.setting_l2);
		btn_submit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {	
				//设置点击背景
//				btn_submit.setBackgroundResource(R.drawable.);
				//layoutProcess.setVisibility(View.VISIBLE);
				//获取用户的登录信息，连接服务器，获取登录状态
				nickname = edit_nickname.getText().toString().trim();
				
				if ("".equals(nickname)){
//					layoutProcess.setVisibility(View.GONE);
//					Toast.makeText(LoginPageActivity.this, context.getString(R.string.login_emptyname_or_emptypwd) , Toast.LENGTH_SHORT).show();
				}else {
				}
			}
		});
		
		l2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Bundle bundle = new Bundle();
				Intent intent = new Intent(SettingPageActivity.this, ResetPWD1Activity.class);
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
	}
	@Override
	public void onClick(View arg0) {
		// TODO 自动生成的方法存根
		switch(arg0.getId()){
		case R.id.setting_labor_experience:
			Intent intent = new Intent(this,BidsPopupActivity.class);
			intent.putExtra("type", BidsPublishBids.ACTIVITY_RESQUEST_CODE_EXPERIENCE);
			startActivityForResult(intent,  BidsPublishBids.ACTIVITY_RESQUEST_CODE_EXPERIENCE);
			break;
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO 自动生成的方法存根
		super.onActivityResult(requestCode, resultCode, data);
		switch(resultCode){
		case BidsPublishBids.ACTIVITY_RESQUEST_CODE_EXPERIENCE:
			mExperience.setText(data.getStringExtra("data")+"年");
			break;
		}
	}
	
}
