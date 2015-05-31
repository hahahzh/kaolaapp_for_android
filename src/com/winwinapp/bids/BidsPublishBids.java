package com.winwinapp.bids;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.winwinapp.decorate.PreEvaluateActivity;
import com.winwinapp.koala.ActionBarActivity;
import com.winwinapp.koala.R;

public class BidsPublishBids extends ActionBarActivity {

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_bids_publish);

		initActionBar();
	}

	public void initActionBar(){
		ImageView imageView = new ImageView(this);
		imageView.setImageResource(R.drawable.back);
		setLeftView(imageView);
		setTitle("发起招标");
		this.setOnLeftClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO 自动生成的方法存根
				finish();
			}
			
		});
		
		TextView textView = new TextView(this);
		textView.setText("发布");
		this.setRightView(textView);
		this.setOnRightClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO 自动生成的方法存根
				Intent intent = new Intent(BidsPublishBids.this,BidsDetailsActivity.class);
				startActivity(intent);
			}
			
		});
		
	}
}
