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

import com.winwinapp.bids.BidsPopupActivity;
import com.winwinapp.bids.BidsPublishBids;
import com.winwinapp.koala.ActionBarActivity;
import com.winwinapp.koala.R;
import com.winwinapp.network.HTTPPost;
import com.winwinapp.network.NetworkData;
import com.winwinapp.selectcity.SelectAddressActivity;

public class ContractLaborActivity extends ActionBarActivity implements OnClickListener{

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
	TextView mThirdPay;
	TextView mFourthPay;
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
					Toast.makeText(ContractLaborActivity.this, "获取合同详情失败："+error, Toast.LENGTH_LONG).show();
				}
				break;
			case REFESH_CONTRACT_UPDATE:
				error = (String) msg.obj;
				if("OK".equals(error)){
					if(mType == 0){
						Toast.makeText(ContractLaborActivity.this, "发起合同成功", Toast.LENGTH_LONG).show();
					}else{
						Toast.makeText(ContractLaborActivity.this, "更新合同成功", Toast.LENGTH_LONG).show();
					}
					ContractLaborActivity.this.finish();
				}else{
					if(mType == 0){
						Toast.makeText(ContractLaborActivity.this, "发起合同失败："+error, Toast.LENGTH_LONG).show();
					}else{
						Toast.makeText(ContractLaborActivity.this, "更新合同失败："+error, Toast.LENGTH_LONG).show();
					}
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
		mDecoreateWay.setOnClickListener(this);
		mStartDate = (TextView)findViewById(R.id.my_contract_start_date);
		mStartDate.setOnClickListener(this);
		mEndDate = (TextView)findViewById(R.id.my_contract_end_date);
		mEndDate.setOnClickListener(this);
		mTotalPay = (TextView)findViewById(R.id.my_contract_total_pay);
		mFirstPay = (TextView)findViewById(R.id.my_contract_first_pay);
		mSecondPay = (TextView)findViewById(R.id.my_contract_second_pay);
		mThirdPay = (TextView)findViewById(R.id.my_contract_third_pay);
		mFourthPay = (TextView)findViewById(R.id.my_contract_fourth_pay);
		mSubmit = (Button)findViewById(R.id.contract_labor_submit);
		mSubmit.setOnClickListener(this);
		
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
		Calendar calendar;
		Intent intent;
		Dialog dialog;
		switch(arg0.getId()){
		case R.id.my_contract_decoreate_way:
			intent = new Intent(this,BidsPopupActivity.class);
			intent.putExtra("type", BidsPublishBids.ACTIVITY_RESQUEST_CODE_HOUSE_DECORATE_WAY);
			startActivityForResult(intent,BidsPublishBids.ACTIVITY_RESQUEST_CODE_HOUSE_DECORATE_WAY);
			break;
		case R.id.contract_address_ll:
			intent = new Intent(ContractLaborActivity.this,SelectAddressActivity.class);
			//startActivity(intent);
			this.startActivityForResult(intent, 1);
			break;
		case R.id.my_contract_start_date:
			calendar = Calendar.getInstance();
	        dialog = new DatePickerDialog(this,
	                         new DatePickerDialog.OnDateSetListener() {
	 
	                             @Override
	                             public void onDateSet(DatePicker view, int year,
	                                     int monthOfYear, int dayOfMonth) {
	                            	 mBack.syear = year+"";
	                            	 mBack.smonth = monthOfYear+"";
	                            	 mBack.sday = dayOfMonth+"";
	                            	 mStartDate.setText(year + "年" + monthOfYear + "月" + dayOfMonth + "日");
	             }
	         }, calendar.get(Calendar.YEAR), calendar
	                 .get(Calendar.MONTH), calendar
	                 .get(Calendar.DAY_OF_MONTH));
			dialog.show();
			break;
		case R.id.my_contract_end_date:
			calendar = Calendar.getInstance();
	        dialog = new DatePickerDialog(this,
	                         new DatePickerDialog.OnDateSetListener() {
	 
	                             @Override
	                             public void onDateSet(DatePicker view, int year,
	                                     int monthOfYear, int dayOfMonth) {
	                            	 mBack.eyear = year+"";
	                            	 mBack.emonth = (monthOfYear+1)+"";
	                            	 mBack.eday = dayOfMonth+"";
	                            	 mEndDate.setText(year + "年" + (monthOfYear+1) + "月" + dayOfMonth + "日");
	             }
	         }, calendar.get(Calendar.YEAR), calendar
	                 .get(Calendar.MONTH), calendar
	                 .get(Calendar.DAY_OF_MONTH));
			dialog.show();
			break;
		case R.id.contract_labor_submit:
			mBack.real_name = mName.getText().toString();
			mBack.total_amount = mTotalPay.getText().toString();
			mBack.first_pay = mFirstPay.getText().toString();
			mBack.second_pay = mSecondPay.getText().toString();
			mBack.third_pay = mThirdPay.getText().toString();
			mBack.fourth_pay = mFourthPay.getText().toString();
			mBack.decorate_way = mDecoreateWay.getText().toString();
			
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
		case 1:
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
		case BidsPublishBids.ACTIVITY_RESQUEST_CODE_HOUSE_DECORATE_WAY:
			mDecoreateWay.setText(data.getStringExtra("data"));
			break;
		}
	}
}
