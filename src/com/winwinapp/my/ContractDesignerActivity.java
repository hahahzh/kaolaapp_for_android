package com.winwinapp.my;

import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
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

public class ContractDesignerActivity extends ActionBarActivity implements OnClickListener {

	private static final int REFESH_CONTRACT_UPDATE = 2;
	private static final int REFESH_CONTRACT = 1;
	LinearLayout mAddressLL;
	LinearLayout mLL;
	LinearLayout mMeasureTimeLL;
	boolean bDateSet = false;
	int mYear;
	int mMonthOfYear;
	int mDayOfMonth;
	TextView mName;
	TextView mAddress;
	TextView mMeasureDate;
	TextView mFirstDraftDays;
	TextView mModifyTimes;
	TextView mFinalDraftDays;
	TextView mFieldGuideTimes;
	TextView mGraphicSwitch;
	TextView mGraphicStairs;
	
	TextView mTotalPay;
	TextView mFirstPay;
	TextView mSecondPay;
	TextView mExtraPay;
	
	TextView mLatePay;
	TextView mLatePayTenDaysPercent;
	TextView mLateDraftPercent;
	TextView mLateDraftTenDayPercent;
	TextView mDraftNotSatisfyPercent;
	Button mSubmit;
	
	int mType = 0;//0:new contract 1: view contract
	int mId = 0;//the id of contract
	NetworkData.BidDetailData mData = NetworkData.getInstance().getNewBidDetailData();
	NetworkData.MyContractDetailBack mBack = NetworkData.getInstance().getNewMyContractDetailBack();
	NetworkData.CommonBack mUpdateBack = NetworkData.getInstance().getCommonBack();
	
	private Handler mHandler = new Handler(){
		public void handleMessage(Message msg){
			String error;
			switch(msg.what){
			case REFESH_CONTRACT:
				error = (String) msg.obj;
				if("OK".equals(error)){
					refreshContract();
				}else{
					Toast.makeText(ContractDesignerActivity.this, "获取合同详情失败："+error, Toast.LENGTH_LONG).show();
				}
				break;
			case REFESH_CONTRACT_UPDATE:
				error = (String) msg.obj;
				if("OK".equals(error)){
					if(mType == 0){
						Toast.makeText(ContractDesignerActivity.this, "发起合同成功", Toast.LENGTH_LONG).show();
					}else{
						Toast.makeText(ContractDesignerActivity.this, "更新合同成功", Toast.LENGTH_LONG).show();
					}
					ContractDesignerActivity.this.finish();
				}else{
					if(mType == 0){
						Toast.makeText(ContractDesignerActivity.this, "发起合同失败："+error, Toast.LENGTH_LONG).show();
					}else{
						Toast.makeText(ContractDesignerActivity.this, "更新合同失败："+error, Toast.LENGTH_LONG).show();
					}
				}
				break;
			}
		}
	};
	
	public String getValidNumber(String str){
		if(TextUtils.isEmpty(str)){
			return "0";
		}
		int number = 0;
		try{
			number = Integer.parseInt(str);
		}catch(Exception e){
			
		}
		return number+"";
	}
	
	public void refreshContract(){
		mName.setText(mBack.real_name);
		mAddress.setText(mBack.province+" "+mBack.city + " "+ mBack.district + " "+ mBack.road + " "+mBack.lane + "号");

		mTotalPay.setText(getValidNumber(mBack.total_amount));
		mFirstPay.setText(getValidNumber(mBack.first_pay));
		mSecondPay.setText(getValidNumber(mBack.second_pay));
		mExtraPay.setText(getValidNumber(mBack.other_pay));
		mMeasureDate.setText(mBack.start_date.year+"年"+mBack.start_date.month+"月"+mBack.start_date.day+"日");
		
		mFirstDraftDays.setText(getValidNumber(mBack.draft_day));
		mModifyTimes.setText(getValidNumber(mBack.free_num));
		mFinalDraftDays.setText(getValidNumber(mBack.final_day));
		mFieldGuideTimes.setText(getValidNumber(mBack.guide_num));
		
		mLatePay.setText(getValidNumber(mBack.liability.unpaid_percent));
		mLatePayTenDaysPercent.setText(getValidNumber(mBack.liability.first_percent));
		mLateDraftPercent.setText(getValidNumber(mBack.liability.second_percent));
		mLateDraftTenDayPercent.setText(getValidNumber(mBack.liability.third_percent));
		mDraftNotSatisfyPercent.setText(getValidNumber(mBack.liability.fourth_percent));
		
		mLL.setVisibility(View.VISIBLE);
	}
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_contract_designer);

		mType = getIntent().getIntExtra("type", 0);
		if(mType == 1){
			mId = getIntent().getIntExtra("id", 0);
		}
		
		initActionBar();
		
		mLL = (LinearLayout)findViewById(R.id.contract_designer_ll);
		mAddressLL = (LinearLayout)findViewById(R.id.contract_designer_address_ll);
		mAddressLL.setOnClickListener(this);
		mMeasureTimeLL = (LinearLayout)findViewById(R.id.contract_designer_measure_time_ll);
		mMeasureTimeLL.setOnClickListener(this);
		mName = (TextView)findViewById(R.id.contract_designer_name);
		mAddress = (TextView)findViewById(R.id.contract_designer_address);
		mMeasureDate = (TextView)findViewById(R.id.contract_designer_measure_time);
		
		mFirstDraftDays = (TextView)findViewById(R.id.contract_designer_first_draft);
		mModifyTimes = (TextView)findViewById(R.id.contract_designer_modify_times);
		mFinalDraftDays = (TextView)findViewById(R.id.contract_designer_final_draft);
		mFieldGuideTimes = (TextView)findViewById(R.id.contract_designer_field_guide);
		mGraphicSwitch = (TextView)findViewById(R.id.contract_designer_graphic_switch);
		mGraphicStairs = (TextView)findViewById(R.id.contract_designer_graphic_stairs);
		
		mTotalPay = (TextView)findViewById(R.id.contract_designer_total_pay);
		mFirstPay = (TextView)findViewById(R.id.contract_designer_first_pay);
		mSecondPay = (TextView)findViewById(R.id.contract_designer_second_pay);
		mExtraPay = (TextView)findViewById(R.id.contract_designer_extra_pay);
		
		mLatePay = (TextView)findViewById(R.id.contract_designer_x_late_pay);
		mLatePayTenDaysPercent = (TextView)findViewById(R.id.contract_designer_x__late_pay_percent);
		mLateDraftPercent = (TextView)findViewById(R.id.contract_designer_late_draft_percent);
		mLateDraftTenDayPercent = (TextView)findViewById(R.id.contract_designer_late_draft_tenday_percent);
		mDraftNotSatisfyPercent = (TextView)findViewById(R.id.contract_designer_draft_not_satisfy_percent);
		
		mSubmit = (Button)findViewById(R.id.contract_designer_submit);
		mSubmit.setOnClickListener(this);
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
		case R.id.contract_designer_address_ll:
			Intent intent = new Intent(ContractDesignerActivity.this,SelectAddressActivity.class);
			this.startActivityForResult(intent, 0);
			break;
		case R.id.contract_designer_measure_time_ll:
			Calendar calendar = Calendar.getInstance();
	        Dialog dialog = new DatePickerDialog(this,
	                         new DatePickerDialog.OnDateSetListener() {
	 
	                             @Override
	                             public void onDateSet(DatePicker view, int year,
	                                     int monthOfYear, int dayOfMonth) {
	                            	 bDateSet = true;
	                            	 mYear = year;
	                            	 mMonthOfYear = monthOfYear+1;
	                            	 mDayOfMonth = dayOfMonth;
	                            	 mMeasureDate.setText(year + "年" + mMonthOfYear + "月" + dayOfMonth + "日");
	             }
	         }, calendar.get(Calendar.YEAR), calendar
	                 .get(Calendar.MONTH), calendar
	                 .get(Calendar.DAY_OF_MONTH));
			dialog.show();
			break;
		case R.id.contract_designer_submit:
			mBack.total_amount = mTotalPay.getText().toString();
			mBack.first_pay = mFirstPay.getText().toString();
			mBack.second_pay = mSecondPay.getText().toString();
			if(bDateSet){
				mBack.year = mYear;
				mBack.month = mMonthOfYear;
				mBack.day = mDayOfMonth;
			}
			mBack.guide_num = mFieldGuideTimes.getText().toString();
			mBack.plan = "";
			mBack.free_num = mModifyTimes.getText().toString();
			mBack.draft_day = mFirstDraftDays.getText().toString();
			mBack.final_day = mFinalDraftDays.getText().toString();
			
			try{
				mBack.unpaid_percent = Integer.parseInt(mLatePay.getText().toString());
			}catch(Exception e){
				mBack.unpaid_percent = 0;
			}
			try{
				mBack.first_percent = Integer.parseInt(mLatePayTenDaysPercent.getText().toString());
			}catch(Exception e){
				mBack.first_percent = 0;
			}
			try{
				mBack.second_percent = Integer.parseInt(mLateDraftPercent.getText().toString());
			}catch(Exception e){
				mBack.second_percent = 0;
			}
			try{
				mBack.third_percent = Integer.parseInt(mLateDraftTenDayPercent.getText().toString());
			}catch(Exception e){
				mBack.third_percent = 0;
			}
			try{
				mBack.fourth_percent = Integer.parseInt(mDraftNotSatisfyPercent.getText().toString());
			}catch(Exception e){
				mBack.fourth_percent = 0;
			}
			
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
