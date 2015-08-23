package com.winwinapp.decorateTips;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.winwinapp.koala.ActionBarActivity;
import com.winwinapp.koala.R;
import com.winwinapp.network.HTTPPost;
import com.winwinapp.network.NetworkData;


public class DecorateTipsDetailActivity extends ActionBarActivity {

	public static final int URL_INVALIDATE = 1;
	LayoutInflater mInflater;
	NetworkData.DecorateTipDetailData mData = NetworkData.getInstance().getDecorateTipDetailData();
	NetworkData.DecorateTipDetailBack mBack = NetworkData.getInstance().getDecorateTipDetailBack();
	
	TextView mtitleText;
	TextView mDateText;
	TextView mViewedText;
	ImageView mPreviewImage;
	TextView mContentText;
	int type;
	class MyThread extends Thread {  
		public void run(){
			boolean success = false;
			success = HTTPPost.RequestDecorateTipDetail(mData, mBack);
			Message msg = Message.obtain();
			msg.what = URL_INVALIDATE;
			if(success){
				msg.obj = "OK";
			}else{
				msg.obj = mBack.error;
			}
			mHandler.sendMessage(msg);
		}
	}
	
	private Handler mHandler = new Handler(){
		public void handleMessage(Message msg){
			//Intent intent;ta
			switch(msg.what){
			case URL_INVALIDATE:
				String error = (String)msg.obj;
				if("OK".equals(error)){
					mtitleText.setText(mBack.title);
					mContentText.setText(mBack.content);
					mDateText.setText(mBack.add_time);
					mViewedText.setText(mBack.scan_num);

				}else{
					Toast.makeText(DecorateTipsDetailActivity.this, "获取装修宝典详情失败："+error, Toast.LENGTH_LONG).show();
					mtitleText.setText("");
					mContentText.setText("");
					mDateText.setText("");
					mViewedText.setText("");
				}
				break;
			}
		}
	};
	
	int mType = 1;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_decorate_tip_detail);

		mtitleText = (TextView)findViewById(R.id.tips_detail_title);
		mDateText = (TextView)findViewById(R.id.tips_item_date);
		mViewedText = (TextView)findViewById(R.id.tips_item_viewed);
		mContentText = (TextView)findViewById(R.id.tips_content);
		
		mType = getIntent().getIntExtra("type", 1);
		initActionBar();
		mInflater = LayoutInflater.from(this);
		new MyThread().start();
		
	}

	public void initActionBar(){
		ImageView imageView = new ImageView(this);
		imageView.setImageResource(R.drawable.back);
		setLeftView(imageView);
		setTitle("软装");
		switch(mType){
		case 1:
			setTitle("施工");
			break;
		case 2:
			setTitle("软装");
			break;
		case 3:
			setTitle("设计");
			break;
		case 4:
			setTitle("材料");
			break;
		case 5:
			setTitle("风水");
			break;
		}
		this.setOnLeftClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO 自动生成的方法存根
				finish();
			}
			
		});
		
		imageView = new ImageView(this);
		imageView.setImageResource(R.drawable.share);
		setRightView(imageView);
		this.setOnRightClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO 自动生成的方法存根
				startActivity(new Intent(DecorateTipsDetailActivity.this,Decorate_tips_share_window.class));  
			}
			
		});
		
	}
	
}
