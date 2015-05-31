package com.winwinapp.bids;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

import com.winwinapp.decorate.PreEvaluateActivity;
import com.winwinapp.koala.ActionBarActivity;
import com.winwinapp.koala.R;

public class BidsDistinctActivity extends ActionBarActivity {

	Button mInviteBids;
	Button mBids;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_distinct_select);

		initActionBar();
		mInviteBids = (Button)findViewById(R.id.distict_invite_bids);
		mInviteBids.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO 自动生成的方法存根
				Intent intent = new Intent(BidsDistinctActivity.this,BidsPublishBids.class);
				startActivity(intent);
			}
			
		});
		
		mBids = (Button)findViewById(R.id.distict_bids);
		mBids.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO 自动生成的方法存根
				Intent intent = new Intent(BidsDistinctActivity.this,BidsListActivity.class);
				startActivity(intent);
			}
			
		});
	}

	public void initActionBar(){
		ImageView imageView = new ImageView(this);
		imageView.setImageResource(R.drawable.back);
		setLeftView(imageView);
		setTitle("地区");
		this.setOnLeftClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO 自动生成的方法存根
				finish();
			}
			
		});
		
	}
}
