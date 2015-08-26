package com.winwinapp.login;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.winwinapp.about.AboutActivity;
import com.winwinapp.about.FeedbackActivity;
import com.winwinapp.bids.BidsPopupActivity;
import com.winwinapp.bids.BidsPublishBids;
import com.winwinapp.decorate.DecoratePriceActivity;
import com.winwinapp.koala.KoalaApplication;
import com.winwinapp.koala.R;
import com.winwinapp.koala.fragment_homepage;
import com.winwinapp.network.HTTPPost;
import com.winwinapp.network.NetworkData;

public class SettingPageActivity extends Activity implements OnClickListener{

	private EditText edit_nickname;
	private Button btn_submit;
	private String nickname;
	private LinearLayout layoutProcess;
	private View l2;
	LinearLayout mFee = null;
	TextView mExperience;
	ImageView mFeeSep;
	EditText qq,introduce;
	TextView location;
	LinearLayout mLL;
	Button mSave;
	NetworkData.AccountInfoBack mAccountInfo = NetworkData.getInstance().getNewAccountInfoBack();
	NetworkData.CommonBack mBack = NetworkData.getInstance().getCommonBack();
	
	private Handler mHandler = new Handler(){
		public void handleMessage(Message msg){
			//Intent intent;
			String error;
			switch(msg.what){
			case 1:
				error = (String)msg.obj;
				if("OK".equals(error)){
					refresh();
				}else{
					refresh();
					Toast.makeText(SettingPageActivity.this, "获取用户信息失败："+error, Toast.LENGTH_LONG).show();
				}
				break;
			case 2:
				error = (String)msg.obj;
				if("OK".equals(error)){
					//refresh();
					Toast.makeText(SettingPageActivity.this, "更新用户信息成功", Toast.LENGTH_LONG).show();
				}else{
					Toast.makeText(SettingPageActivity.this, "更新用户信息失败："+error, Toast.LENGTH_LONG).show();
				}
				break;
			}
		}
	};
	
	public class UpdateAccountInfoThread extends Thread{
		public void run(){
			if(KoalaApplication.mUserType != fragment_homepage.TYPE_OWER){
				mAccountInfo.city_id = location.getText().toString();
				mAccountInfo.qq = qq.getText().toString();
				mAccountInfo.introduce = introduce.getText().toString();
				mAccountInfo.work_num = mExperience.getText().toString();
			}
			boolean success = HTTPPost.ModifyAccountInfo(mAccountInfo, mBack);
			Message msg = Message.obtain();
			msg.what = 2;
			if(success){
				msg.obj = "OK";
			}else{
				msg.obj = mAccountInfo.error;
			}
			mHandler.sendMessage(msg);
		}
	}
	
	public class GetAccountInfoThread extends Thread{
		public void run(){
			boolean success = HTTPPost.GetAccountInfo(mAccountInfo);
			Message msg = Message.obtain();
			msg.what = 1;
			if(success){
				msg.obj = "OK";
			}else{
				msg.obj = mAccountInfo.error;
			}
			mHandler.sendMessage(msg);
		}
	}
	
	public void refresh(){
		edit_nickname.setText(mAccountInfo.username);
		if(KoalaApplication.mUserType != fragment_homepage.TYPE_OWER){
			qq.setText(mAccountInfo.qq);
			for(NetworkData.RegionsItem item:mAccountInfo.provinces){
				if(item.regions_id.equals(mAccountInfo.city_id)){
					location.setText(item.regions_name);
					break;
				}
			}
			mExperience.setText(mAccountInfo.work_num);
			introduce.setText(mAccountInfo.introduce);
		}
		mLL.setVisibility(View.VISIBLE);
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(KoalaApplication.mUserType == fragment_homepage.TYPE_OWER){
			setContentView(R.layout.layout_setting);
			edit_nickname = (EditText)findViewById(R.id.setting_nickname_txt);
			mLL = (LinearLayout)findViewById(R.id.setting_ll);
			btn_submit = (Button)findViewById(R.id.setting_submit_btn);
		}else{
			setContentView(R.layout.layout_setting_labor);
			edit_nickname = (EditText)findViewById(R.id.setting_label_nickname_txt);
			qq = (EditText)findViewById(R.id.setting_labor_qq);
			location = (TextView)findViewById(R.id.setting_labor_city);
			introduce = (EditText)findViewById(R.id.setting_labor_self_introduce);
			
			mFee = (LinearLayout)findViewById(R.id.setting_designer_fee_ll);
			mFeeSep = (ImageView)findViewById(R.id.setting_designer_fee_sep);
			if(KoalaApplication.mUserType != fragment_homepage.TYPE_DESIGNER){
				mFee.setVisibility(View.GONE);
				mFeeSep.setVisibility(View.GONE);
			}
			mExperience = (TextView)findViewById(R.id.setting_labor_experience);
			mExperience.setOnClickListener(this);
			mLL = (LinearLayout)findViewById(R.id.setting_labor_ll);
			btn_submit = (Button)findViewById(R.id.setting_labor_submit_btn);
		}
		
		mLL.setVisibility(View.GONE);
		new GetAccountInfoThread().start();
		
		
		l2 = (LinearLayout)findViewById(R.id.setting_l2);
		btn_submit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {	
				//设置点击背景
//				btn_submit.setBackgroundResource(R.drawable.);
				//layoutProcess.setVisibility(View.VISIBLE);
				//获取用户的登录信息，连接服务器，获取登录状态
				nickname = edit_nickname.getText().toString().trim();
				new UpdateAccountInfoThread().start();
				
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
