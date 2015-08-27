package com.winwinapp.my;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.winwinapp.about.AboutActivity;
import com.winwinapp.bids.BidsListActivity;
import com.winwinapp.koala.KoalaApplication;
import com.winwinapp.koala.MessageListActivity;
import com.winwinapp.koala.R;
import com.winwinapp.koala.fragment_homepage;
import com.winwinapp.login.LoginPageActivity;

public class MyActivity extends Fragment implements OnClickListener{

	Activity mActivity;
	TextView mMyProjectTxt;
	ImageView mMyProjectImg;
	
	TextView mMyLoveText;
	ImageView mMyLoveImg;
	TextView mMyBidTxt;
	ImageView mMyBidImg;
	TextView mMyAboutTxt;
	Button mMyLogoutBtn;
	LinearLayout mMyIDAuthenLL;
	LinearLayout mMyContractLL;
	LinearLayout mMyMessageLL;
	LinearLayout mMyCardLL;
	LinearLayout mMyAccountLL;
	LinearLayout mMyPayLL;
	LinearLayout mMyAboutLL;
	TextView mMyName;
	ImageView mMyAvatar;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		View view = inflater.inflate(R.layout.layout_my, null);
		
		mActivity = this.getActivity();
		mMyProjectTxt = (TextView)view.findViewById(R.id.my_project_txt);
		mMyProjectImg = (ImageView)view.findViewById(R.id.my_project_img);
		mMyAboutTxt = (TextView)view.findViewById(R.id.my_about_txt);
		mMyLogoutBtn = (Button)view.findViewById(R.id.my_logout_btn);
		mMyLoveText = (TextView)view.findViewById(R.id.my_love_text);
		mMyLoveImg = (ImageView)view.findViewById(R.id.my_love_img);
		mMyBidTxt = (TextView)view.findViewById(R.id.my_bid_txt);
		mMyBidImg = (ImageView)view.findViewById(R.id.my_bid_img);
		mMyIDAuthenLL = (LinearLayout)view.findViewById(R.id.my_id_cert_layout);
		mMyContractLL = (LinearLayout)view.findViewById(R.id.my_contract_ll);
		mMyMessageLL = (LinearLayout)view.findViewById(R.id.my_message_ll);
		mMyCardLL = (LinearLayout)view.findViewById(R.id.my_card_ll);
		mMyAccountLL = (LinearLayout)view.findViewById(R.id.my_account_ll);
		mMyPayLL = (LinearLayout)view.findViewById(R.id.my_pay_ll);
		mMyAboutLL = (LinearLayout)view.findViewById(R.id.my_about_ll);
		
		mMyProjectTxt.setOnClickListener(this);
		mMyProjectImg.setOnClickListener(this);
		mMyAboutTxt.setOnClickListener(this);
		mMyLogoutBtn.setOnClickListener(this);
		mMyLoveText.setOnClickListener(this);
		mMyLoveImg.setOnClickListener(this);
		mMyBidTxt.setOnClickListener(this);
		mMyBidImg.setOnClickListener(this);
		mMyIDAuthenLL.setOnClickListener(this);
		mMyContractLL.setOnClickListener(this);
		mMyMessageLL.setOnClickListener(this);
		mMyCardLL.setOnClickListener(this);
		mMyAccountLL.setOnClickListener(this);
		mMyPayLL.setOnClickListener(this);
		mMyAboutLL.setOnClickListener(this);
		
		mMyName = (TextView)view.findViewById(R.id.my_name);
		mMyAvatar = (ImageView)view.findViewById(R.id.my_avatar);
		
		
		
		if(KoalaApplication.mUserType == fragment_homepage.TYPE_OWER){
			mMyBidTxt.setText("我的招标");
			//mMyCardLL.setVisibility(View.GONE);
			mMyAccountLL.setVisibility(View.GONE);
		}else{
			mMyBidTxt.setText("我的竞标");
			mMyPayLL.setVisibility(View.GONE);
		}
		
		
		return view;
	}

	@Override
	public void onClick(View view) {
		// TODO 自动生成的方法存根
		Intent intent;
		switch(view.getId()){
		case R.id.my_project_txt:
		case R.id.my_project_img:
			intent = new Intent(mActivity,MyProjectActivity.class);
			startActivity(intent);
			break;
		case R.id.my_about_ll:
		case R.id.my_about_txt:
			intent = new Intent(mActivity,AboutActivity.class);
			startActivity(intent);
			break;
		case R.id.my_logout_btn:
			KoalaApplication app = ((KoalaApplication)mActivity.getApplication());
			app.clearLoginInformation();
			intent = new Intent(mActivity,LoginPageActivity.class);
			startActivity(intent);
			break;
		case R.id.my_love_img:
		case R.id.my_love_text:
			intent = new Intent(mActivity,MyLoveActivity.class);
			startActivity(intent);
			break;
		case R.id.my_bid_img:
		case R.id.my_bid_txt:
			if(KoalaApplication.mUserType == fragment_homepage.TYPE_OWER){
				intent = new Intent(mActivity,BidsListActivity.class);//我的招标
				intent.putExtra("type", 1);//My bid list
				startActivity(intent);
			}else{
				intent = new Intent(mActivity,BidsListActivity.class);//我的招标
				intent.putExtra("type", 0);//My bid list
				startActivity(intent);
			}
			break;
		case R.id.my_id_cert_layout:
			intent = new Intent(mActivity,MyIDAuthenActivity.class);
			startActivity(intent);
			break;
		case R.id.my_contract_ll:
			intent = new Intent(mActivity,MyContractActivity.class);
			startActivity(intent);
			break;
		case R.id.my_message_ll:
			intent = new Intent(mActivity,MessageListActivity.class);
			intent.putExtra("type", 0);
			startActivity(intent);
			break;
		case R.id.my_account_ll:
			intent = new Intent(mActivity,MyAccountActivity.class);
			startActivity(intent);
			break;
		case R.id.my_card_ll:
			intent = new Intent(mActivity,BindCardActivity.class);
			startActivity(intent);
			break;
		case R.id.my_pay_ll:
			intent = new Intent(mActivity,MyPayActivity.class);
			startActivity(intent);
			break;
		}
	}

	@Override
	public void onResume() {
		// TODO 自动生成的方法存根
		super.onResume();
		mMyName.setText(KoalaApplication.loginData.username);
	}
	
}
