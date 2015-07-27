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
			mWork = "���ʦ";
		}else if(mType == 1){
			mWork = "����";
		}else if(mType == 2){
			mWork = "����";
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
		setTitle(mWork+"��ͬ");
		this.setOnLeftClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO �Զ����ɵķ������
				finish();
			}
			
		});
		
		TextView txtView = new TextView(this);
		txtView.setTextColor(0xFF000000);
		txtView.setText("��"+mCurrentPage+"ҳ");
		setRightView(txtView);
		
	}
}
