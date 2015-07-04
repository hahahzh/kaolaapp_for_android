package com.winwinapp.my;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.winwinapp.koala.ActionBarActivity;
import com.winwinapp.koala.R;
import com.winwinapp.network.HTTPPost;
import com.winwinapp.network.NetworkData;
import com.winwinapp.selectcity.SelectAddressActivity;

public class ContractLaborActivity extends ActionBarActivity implements OnClickListener{

	private static final int REFESH_CONTRACT = 1;
	LinearLayout mAddressLL;
	LinearLayout mLL;
	TextView mName;
	TextView mAddress;
	TextView mDecoreateWay;
	TextView mStartDate;
	TextView mEndDate;
	TextView mTotalPay;
	TextView mFirstPay;
	TextView mSecondPay;
	TextView mThirdPay;
	TextView mFourthPay;
	
	int mType = 0;//0:new contract 1: view contract
	int mId = 0;//the id of contract
	NetworkData.BidDetailData mData = NetworkData.getInstance().getNewBidDetailData();
	NetworkData.MyContractDetailBack mBack = NetworkData.getInstance().getNewMyContractDetailBack();
	
	private Handler mHandler = new Handler(){
		public void handleMessage(Message msg){
			switch(msg.what){
			case REFESH_CONTRACT:
				String error = (String) msg.obj;
				if("OK".equals(error)){
					refreshContract();
				}else{
					Toast.makeText(ContractLaborActivity.this, "获取合同详情失败："+error, Toast.LENGTH_LONG).show();
				}
				break;
			}
		}
	};
	
	public void refreshContract(){
		mName.setText(mBack.real_name);
		mAddress.setText(mBack.province+" "+mBack.city + " "+ mBack.district + " "+ mBack.road + " "+mBack.lane + "号");
		mDecoreateWay.setText(mBack.decorate_way);
		mStartDate.setText(mBack.syear+"年"+mBack.smonth+"月"+mBack.sday+"日");
		mEndDate.setText(mBack.eyear+"年"+mBack.emonth+"月"+mBack.eday+"日");
		mTotalPay.setText(mBack.total_amount);
		mFirstPay.setText(mBack.first_pay);
		mSecondPay.setText(mBack.second_pay);
		mThirdPay.setText(mBack.third_pay);
		mFourthPay.setText(mBack.fourth_pay);
		mLL.setVisibility(View.VISIBLE);
	}
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_contract_labor);

		mType = getIntent().getIntExtra("type", 0);
		if(mType == 1){
			mId = getIntent().getIntExtra("id", 0);
		}
		
		initActionBar();
		
		mAddressLL = (LinearLayout)findViewById(R.id.contract_address_ll);
		mAddressLL.setOnClickListener(this);
		mName = (TextView)findViewById(R.id.my_contract_name);
		mAddress = (TextView)findViewById(R.id.my_contract_address);
		mDecoreateWay = (TextView)findViewById(R.id.my_contract_decoreate_way);
		mStartDate = (TextView)findViewById(R.id.my_contract_start_date);
		mEndDate = (TextView)findViewById(R.id.my_contract_end_date);
		mTotalPay = (TextView)findViewById(R.id.my_contract_total_pay);
		mFirstPay = (TextView)findViewById(R.id.my_contract_first_pay);
		mSecondPay = (TextView)findViewById(R.id.my_contract_second_pay);
		mThirdPay = (TextView)findViewById(R.id.my_contract_third_pay);
		mFourthPay = (TextView)findViewById(R.id.my_contract_fourth_pay);
		
		mLL = (LinearLayout)findViewById(R.id.my_contract_ll);
		if(mType == 1){
			mLL.setVisibility(View.GONE);
			new Thread(){
				public void run(){
					boolean success = false;
					mData.bid_id = mId;
					success = HTTPPost.RequestMyContractDetail(mData, mBack);
					Message msg = Message.obtain();
					msg.what = REFESH_CONTRACT;
					if(success){
						msg.obj = "OK";
					}else{
						msg.obj = mBack.error;
					}
					mHandler.sendMessage(msg);
				}
			}.start();
		}
	}
	
	public void initActionBar(){
		ImageView imageView = new ImageView(this);
		imageView.setImageResource(R.drawable.back);
		setLeftView(imageView);
		setTitle("合同");
		this.setOnLeftClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO 自动生成的方法存根
				finish();
			}
			
		});
		
		 imageView = new ImageView(this);
		imageView.setImageResource(R.drawable.eye_green);
		setRightView(imageView);
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
		case R.id.contract_address_ll:
			Intent intent = new Intent(ContractLaborActivity.this,SelectAddressActivity.class);
			startActivity(intent);
			break;
		}
	}
}
