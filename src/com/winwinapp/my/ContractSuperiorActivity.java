package com.winwinapp.my;

import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.winwinapp.koala.ActionBarActivity;
import com.winwinapp.koala.R;
import com.winwinapp.network.HTTPPost;
import com.winwinapp.network.NetworkData;
import com.winwinapp.selectcity.SelectAddressActivity;

public class ContractSuperiorActivity extends ActionBarActivity implements OnClickListener{

	private static final int REFESH_CONTRACT_UPDATE = 2;
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
	TextView mNormalDay;
	TextView mAddDay;
	TextView mPercent;
	Button mSubmit;
	int mType = 0;//0:new contract 1: view contract
	int mId = 0;//the id of contract
	NetworkData.BidDetailData mData = NetworkData.getInstance().getNewBidDetailData();
	NetworkData.MyContractDetailBack mBack = NetworkData.getInstance().getNewMyContractDetailBack();
	NetworkData.CommonBack mUpdateBack = NetworkData.getInstance().getCommonBack();
	
	private Handler mHandler = new Handler(){
		public void handleMessage(Message msg){
			switch(msg.what){
			case REFESH_CONTRACT:
				String error = (String) msg.obj;
				if("OK".equals(error)){
					refreshContract();
				}else{
					Toast.makeText(ContractSuperiorActivity.this, "获取合同详情失败："+error, Toast.LENGTH_LONG).show();
				}
				break;
			case REFESH_CONTRACT_UPDATE:
				error = (String) msg.obj;
				if("OK".equals(error)){
					if(mType == 0){
						Toast.makeText(ContractSuperiorActivity.this, "发起合同成功", Toast.LENGTH_LONG).show();
					}else{
						Toast.makeText(ContractSuperiorActivity.this, "更新合同成功", Toast.LENGTH_LONG).show();
					}
					ContractSuperiorActivity.this.finish();
				}else{
					if(mType == 0){
						Toast.makeText(ContractSuperiorActivity.this, "发起合同失败："+error, Toast.LENGTH_LONG).show();
					}else{
						Toast.makeText(ContractSuperiorActivity.this, "更新合同失败："+error, Toast.LENGTH_LONG).show();
					}
				}
				break;
			}
		}
	};
	
	public void refreshContract(){
		mName.setText(mBack.real_name);
		mAddress.setText(mBack.province+" "+mBack.city + " "+ mBack.district + " "+ mBack.road + " "+mBack.lane + "号");
		mTotalPay.setText(mBack.total_amount);
		mFirstPay.setText(mBack.first_pay);
		mSecondPay.setText(mBack.second_pay);
		mNormalDay.setText(mBack.normal_day);
		mAddDay.setText(mBack.add_day);
		mPercent.setText(mBack.percent);
		mLL.setVisibility(View.VISIBLE);
	}
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_contract_superior);

		mType = getIntent().getIntExtra("type", 0);
		if(mType == 1){
			mId = getIntent().getIntExtra("id", 0);
		}
		
		initActionBar();
		
		mAddressLL = (LinearLayout)findViewById(R.id.contract_superior_address_ll);
		mAddressLL.setOnClickListener(this);
		mName = (TextView)findViewById(R.id.contract_superior_name);
		mAddress = (TextView)findViewById(R.id.contract_superior_address);

		mTotalPay = (TextView)findViewById(R.id.contract_superior_total);
		mFirstPay = (TextView)findViewById(R.id.contract_superior_first_pay);
		mSecondPay = (TextView)findViewById(R.id.contract_superior_second_pay);
		
		mNormalDay = (TextView)findViewById(R.id.contract_superior_normal_day);
		mAddDay = (TextView)findViewById(R.id.contract_superior_add_day);
		mPercent = (TextView)findViewById(R.id.contract_superior_percent);
		mSubmit = (Button)findViewById(R.id.contract_superior_submit);
		mSubmit.setOnClickListener(this);
		
		mLL = (LinearLayout)findViewById(R.id.contract_superior_ll);
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
		case R.id.contract_superior_address_ll:
			Intent intent = new Intent(ContractSuperiorActivity.this,SelectAddressActivity.class);
			this.startActivityForResult(intent, 0);
			break;
		case R.id.contract_labor_submit:
			mBack.real_name = mName.getText().toString();
			mBack.total_amount = mTotalPay.getText().toString();
			mBack.first_pay = mFirstPay.getText().toString();
			mBack.second_pay = mSecondPay.getText().toString();
			mBack.normal_day = Integer.parseInt(mNormalDay.getText().toString());
			mBack.add_day = Integer.parseInt(mAddDay.getText().toString());
			mBack.percent = Integer.parseInt(mPercent.getText().toString());
			
			new Thread(){
				public void run(){
					boolean success = false;
					mData.bid_id = mId;
					success = HTTPPost.UpdateDesignerContract(mBack, mUpdateBack);
					Message msg = Message.obtain();
					msg.what = REFESH_CONTRACT_UPDATE;
					if(success){
						msg.obj = "OK";
					}else{
						msg.obj = mBack.error;
					}
					mHandler.sendMessage(msg);
				}
			}.start();
			break;
		}
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data){
		super.onActivityResult(requestCode, resultCode, data);
		switch(requestCode){
		case 0:
			if(data != null){
				mBack.province = data.getStringExtra("province");
				mBack.city = data.getStringExtra("city");
				mBack.district = data.getStringExtra("country");
				mBack.road = data.getStringExtra("road");
				mBack.lane = data.getStringExtra("street");
				mBack.number = data.getStringExtra("number");
				mBack.floor = data.getStringExtra("build");
				mBack.room = data.getStringExtra("room");
				mAddress.setText(mBack.province+mBack.city+mBack.district+mBack.road+mBack.lane+"弄"+mBack.number+"号"+mBack.floor+"层"+mBack.room+"室");
			}
			break;
		}
	}
}
