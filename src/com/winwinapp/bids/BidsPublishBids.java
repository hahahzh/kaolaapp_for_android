package com.winwinapp.bids;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.winwinapp.decorate.PreEvaluateActivity;
import com.winwinapp.koala.ActionBarActivity;
import com.winwinapp.koala.R;
import com.winwinapp.network.HTTPPost;
import com.winwinapp.network.NetworkData;
import com.winwinapp.util.AddSubLL;

public class BidsPublishBids extends ActionBarActivity {

	public static final int REFRESH = 1;
	public static final int REFRESH_EDIT = 2;
	TextView mPosition;
	TextView mbiotope_name;
	TextView mArea;
	TextView mHouseType;
	AddSubLL mHall;
	AddSubLL mRoom;
	AddSubLL mKitchen;
	AddSubLL mToilet;
	AddSubLL mBalcony;
	TextView mDecorateWay;
	TextView mDecorateStyle;
	TextView mBudget;
	EditText mRequirement;
	RadioButton mDesigner;
	RadioButton mLabor;
	RadioButton mSuperior;
	Button mPublish;
	NetworkData.BidPublishData mData = NetworkData.getInstance().getNewBidPublishData();
	NetworkData.CommonBack mBack = NetworkData.getInstance().getCommonBack();
	
	NetworkData.BidDetailData mEditData = NetworkData.getInstance().getNewBidDetailData();
	NetworkData.UserBidEditBack mEditBack = NetworkData.getInstance().getNewUserBidEditBack();
	int mType = 0;
	LinearLayout mLL;
	
	private Handler mHandler = new Handler(){
		public void handleMessage(Message msg){
			//Intent intent;
			switch(msg.what){
			case REFRESH:
				String error = (String)msg.obj;
				if("OK".equals(error)){
					Intent intent = new Intent(BidsPublishBids.this,BidsDetailsActivity.class);
					startActivity(intent);
				}else{
					Toast.makeText(BidsPublishBids.this, "发布招标消息失败："+error, Toast.LENGTH_LONG).show();
				}
				break;
			case REFRESH_EDIT:
				String error1 = (String)msg.obj;
				if("OK".equals(error1)){
					refreshContentOfEdit();
				}else{
					Toast.makeText(BidsPublishBids.this, "获取编辑招标信息失败："+error1, Toast.LENGTH_LONG).show();
				}
				
				break;
			}
		}
	};
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_bids_publish);
		mType = getIntent().getIntExtra("type", 0);
		if(mType == 1){
			mEditData.bid_id = Integer.parseInt(getIntent().getStringExtra("bid_id"));
		}
		
		initActionBar();
		
		initView();
		
		if(mType == 1){
			new Thread(){
				public void run(){
					boolean success = HTTPPost.RequestUserBidEdit(mEditData, mEditBack);
					Message msg = Message.obtain();
					msg.what = REFRESH_EDIT;
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

	public void initView(){
		mLL = (LinearLayout)findViewById(R.id.bid_publish_ll);
		mPosition = (TextView)findViewById(R.id.bid_publish_position);
		mbiotope_name = (TextView)findViewById(R.id.bid_publish_biotope_name);
		mArea = (TextView)findViewById(R.id.bid_publish_area);
		mHouseType = (TextView)findViewById(R.id.bid_publish_house_type);
		mHall = (AddSubLL)findViewById(R.id.bid_publish_hall);	
		mRoom = (AddSubLL)findViewById(R.id.bid_publish_room);
		mKitchen = (AddSubLL)findViewById(R.id.bid_publish_kitchen);
		mToilet = (AddSubLL)findViewById(R.id.bid_publish_toilet);
		mBalcony = (AddSubLL)findViewById(R.id.bid_publish_balcony);
		mDecorateWay = (TextView)findViewById(R.id.bid_publish_decorate_way);
		mDecorateStyle = (TextView)findViewById(R.id.bid_publish_decorate_style);
		mBudget = (TextView)findViewById(R.id.bid_publish_budget);
		mRequirement = (EditText)findViewById(R.id.bid_publish_requirement);
		mDesigner = (RadioButton)findViewById(R.id.bid_publish_designer);
		mLabor = (RadioButton)findViewById(R.id.bid_publish_labor);
		mSuperior = (RadioButton)findViewById(R.id.bid_publish_superior);
		mPublish = (Button)findViewById(R.id.bid_publish_publish);
		
		if(mType == 1){
			mPublish.setText("提交修改");
			mPublish.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View arg0) {
					// TODO 自动生成的方法存根
					postEdit();
				}
				
			});
			mLL.setVisibility(View.GONE);
		}else{
			mPublish.setOnClickListener(new OnClickListener(){
	
				@Override
				public void onClick(View arg0) {
					// TODO 自动生成的方法存根
					publish();
				}
				
			});
		}
	}
	
	public void postEdit(){
		
	}
	
	public void refreshContentOfEdit(){
		mPosition.setText(mEditBack.items.get(0).postion);
		mbiotope_name.setText(mEditBack.items.get(0).biotope_name);
		mArea.setText(mEditBack.items.get(0).area+"㎡");
		mHouseType.setText(mEditBack.constHouseType.get(Integer.parseInt(mEditBack.items.get(0).house_type)));
		mDecorateWay.setText(mEditBack.constDecorateWay.get(Integer.parseInt(mEditBack.items.get(0).decorate_way)));
		mDecorateStyle.setText(mEditBack.constDesignerStyle.get(Integer.parseInt(mEditBack.items.get(0).design_style)));
		mBudget.setText(mEditBack.constBudget.get(Integer.parseInt(mEditBack.items.get(0).budget)));
		mRequirement.setText(mEditBack.items.get(0).requirement);
		mLabor.setChecked("1".equals(mEditBack.items.get(0).need_foreman));
		mSuperior.setChecked("1".equals(mEditBack.items.get(0).need_supervisor));
		mDesigner.setChecked("1".equals(mEditBack.items.get(0).need_designer));
		mHall.setCurrentNumber("1");	
		mRoom.setCurrentNumber("1");
		mKitchen.setCurrentNumber("1");
		mToilet.setCurrentNumber("1");
		mBalcony.setCurrentNumber("1");
		mLL.setVisibility(View.VISIBLE);
	}
	
	public void publish(){
		String area = mArea.getText().toString();
		area = area.replace("㎡", "");
		mData.area = Integer.parseInt(area);
		
		mData.balcony = mBalcony.getCurrentNumber();
		mData.biotope_name = mbiotope_name.getText().toString();
		//mData.budget = Integer.parseInt(mBudget.getText().toString());
		mData.req = mRequirement.getText().toString();
		
		new PublishThread().start();
	}
	
	public class PublishThread extends Thread{
		public void run(){
			boolean success = HTTPPost.PublishBids(mData, mBack);
			Message msg = Message.obtain();
			msg.what = REFRESH;
			if(success){
				msg.obj = "OK";
			}else{
				msg.obj = mBack.error;
			}
			mHandler.sendMessage(msg);
		}
	}
	
	public void initActionBar(){
		ImageView imageView = new ImageView(this);
		imageView.setImageResource(R.drawable.back);
		setLeftView(imageView);
		this.setOnLeftClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO 自动生成的方法存根
				finish();
			}
			
		});
		
		TextView textView = new TextView(this);
		if(mType == 1){
			setTitle("招标编辑");
			textView.setText("完成");
			this.setRightView(textView);
			this.setOnRightClickListener(new OnClickListener(){
	
				@Override
				public void onClick(View arg0) {
					// TODO 自动生成的方法存根
					postEdit();
				}
				
			});
		}else{
			setTitle("发起招标");
			textView.setText("发布");
			this.setRightView(textView);
			this.setOnRightClickListener(new OnClickListener(){
	
				@Override
				public void onClick(View arg0) {
					// TODO 自动生成的方法存根
					publish();
				}
				
			});
		}
		
	}
}
