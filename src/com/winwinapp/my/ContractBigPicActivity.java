package com.winwinapp.my;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.winwinapp.koala.ActionBarActivity;
import com.winwinapp.koala.R;

public class ContractBigPicActivity extends ActionBarActivity {

	ImageView mImageView;
	int mCurrentPage = 5;
	int mType = 0;
	String mWork;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_contract_big_picture);

		mCurrentPage = getIntent().getIntExtra("page", 5);
		mType = getIntent().getIntExtra("type", 0);
		if(mType == 0){
			mWork = "设计师";
		}else if(mType == 1){
			mWork = "工长";
		}else if(mType == 2){
			mWork = "监理";
		}else{
			mWork = "";
		}
		initActionBar();
		mImageView = (ImageView)findViewById(R.id.contract_big_pic_img);
		mImageView.setImageResource(R.drawable.contract_preview);
	}
	
	public void initActionBar(){
		ImageView imageView = new ImageView(this);
		imageView.setImageResource(R.drawable.cancel);
		setLeftView(imageView);
		setTitle(mWork+"合同");
		this.setOnLeftClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO 自动生成的方法存根
				finish();
			}
			
		});
		
		TextView txtView = new TextView(this);
		txtView.setTextColor(0xFF000000);
		txtView.setText("第"+mCurrentPage+"页");
		setRightView(txtView);
		
	}
}
