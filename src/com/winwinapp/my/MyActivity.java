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
import com.winwinapp.koala.MessageListActivity;
import com.winwinapp.koala.R;
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
		case R.id.my_about_txt:
			intent = new Intent(mActivity,AboutActivity.class);
			startActivity(intent);
			break;
		case R.id.my_logout_btn:
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
			intent = new Intent(mActivity,BidsListActivity.class);
			startActivity(intent);
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
		}
	}
	
}
