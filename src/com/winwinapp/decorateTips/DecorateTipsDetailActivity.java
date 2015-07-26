package com.winwinapp.decorateTips;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.winwinapp.koala.ActionBarActivity;
import com.winwinapp.koala.R;

public class DecorateTipsDetailActivity extends ActionBarActivity {

	int mType = 1;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_decorate_tip_detail);

		mType = getIntent().getIntExtra("type", 1);
		initActionBar();
		
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
