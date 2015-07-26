package com.winwinapp.bids;

import java.util.ArrayList;

import com.winwinapp.koala.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;

public class BidsPopupActivity extends Activity implements OnClickListener{

	BidsSlideTextView mSlideView;
	ImageView mCheck;
	ImageView mUncheck;
	ArrayList<String> mRawData = new ArrayList<String>();
	String[] HouseTypeArr = {"别墅","普通住宅","商铺"};
	String[] mDecorateWay = {"半包","全包","清包"};
	String[] mDecorateStyle = {"欧美","古典","现代"};
	String[] mDecorateBudget = {"2万以下","2-4万","4-6万","6-8万","8-10万","10-15万","15-20万","20-50万","50万以上"};
    
	int mType = 0;
	private Handler mHandler = new Handler();
	
	 @Override  
	 protected void onCreate(Bundle savedInstanceState) {  
	        super.onCreate(savedInstanceState);  
	        setContentView(R.layout.layout_popup_bids_select);
	        
	        mSlideView = (BidsSlideTextView)findViewById(R.id.pop_ll);
	        mCheck = (ImageView)findViewById(R.id.pop_check);
	        mUncheck = (ImageView)findViewById(R.id.pop_uncheck);
	        mCheck.setOnClickListener(this);
	        mUncheck.setOnClickListener(this);
	        mType = getIntent().getIntExtra("type", 0);
	        if(mType == BidsPublishBids.ACTIVITY_RESQUEST_CODE_HOUSE_AREA){
	        	String str;
	        	for(int i=20;i<200;i++){
	        		str = i+"";
	        		mRawData.add(str);
	        	}
	        }else if(mType == BidsPublishBids.ACTIVITY_RESQUEST_CODE_EXPERIENCE){
	        	String str;
	        	for(int i=0;i<40;i++){
	        		str = i+"";
	        		mRawData.add(str);
	        	}
	        }else{
	        	String[] tmp = null;
	        	switch(mType){
	        	case BidsPublishBids.ACTIVITY_RESQUEST_CODE_HOUSE_TYPE:
	        		tmp = HouseTypeArr;
	        		break;
	        	case BidsPublishBids.ACTIVITY_RESQUEST_CODE_HOUSE_DECORATE_WAY:
	        		tmp = mDecorateWay;
	        		break;
	        	case BidsPublishBids.ACTIVITY_RESQUEST_CODE_HOUSE_DECORATE_TYPE:
	        		tmp = mDecorateStyle;
	        		break;
	        	case BidsPublishBids.ACTIVITY_RESQUEST_CODE_HOUSE_BUDGET:
	        		tmp = mDecorateBudget;
	        		break;
	        	}
	        	if(tmp != null && tmp.length>0){
	        		int len = tmp.length;
	        		for(int i=0;i<len;i++)
	        			mRawData.add(tmp[i]);
	        	}
	        }
	        mSlideView.setRawData(mRawData);
	 }
	 
	 public void onStart(){
		super.onStart();
		
	 }

	

	@Override
	public void onBackPressed() {
		// TODO 自动生成的方法存根
		super.onBackPressed();
	}

	@Override
	public void onClick(View view) {
		// TODO 自动生成的方法存根
		switch(view.getId()){
		case R.id.pop_check:
			//Toast.makeText(this, mSlideView.getCurrentSelect(), Toast.LENGTH_LONG).show();
			Intent data = new Intent();
			data.putExtra("data",mSlideView.getCurrentSelect());
			this.setResult(mType, data);
			finish();
			break;
		case R.id.pop_uncheck:
			finish();
			break;
		}
	}

	
}
