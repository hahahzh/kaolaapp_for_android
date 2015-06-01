package com.winwinapp.about;


import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.winwinapp.koala.ActionBarActivity;
import com.winwinapp.koala.R;

public class FuncDescActivity extends ActionBarActivity {

	private Context context = FuncDescActivity.this;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_about_func);
		
		initActionBar();
	}
	
	public void initActionBar(){
		ImageView imageView = new ImageView(this);
		imageView.setImageResource(R.drawable.back);
		setLeftView(imageView);
		setTitle("功能介绍");
		this.setOnLeftClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO 自动生成的方法存根
				finish();
			}
			
		});
	}
}
