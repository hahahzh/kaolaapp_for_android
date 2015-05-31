package com.winwinapp.util;

import com.winwinapp.koala.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ActionBarView extends RelativeLayout {

	Context mContext;
	LinearLayout mLeftLayout;
	LinearLayout mRightLayout;
	TextView mTitle;
	
	public ActionBarView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO 自动生成的构造函数存根
		initView();
	}

	public ActionBarView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO 自动生成的构造函数存根
		initView();
	}
	
	public ActionBarView(Context context) {
		super(context);
		// TODO 自动生成的构造函数存根
		mContext = context;
		initView();
	}
	
	
	
	private void initView(){
		LayoutInflater.from(getContext()).inflate(R.layout.layout_action_bar, this);
		mLeftLayout = (LinearLayout)findViewById(R.id.actionview_left_layout);
		mTitle = (TextView)findViewById(R.id.actionview_title);
		mRightLayout = (LinearLayout)findViewById(R.id.actionview_right_layout);
		this.setBackgroundColor(0xFFFFFF);
	}
	
	public void setLeftView(View view){
		if(mLeftLayout != null){
			if(view != null){
				mLeftLayout.addView(view,new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
				mLeftLayout.setVisibility(View.VISIBLE);
			}else{
				mLeftLayout.removeAllViews();
			}
			
		}
	}
	
	public void setOnLeftClickListener(OnClickListener l){
		if(mLeftLayout != null)
			mLeftLayout.setOnClickListener(l);
	}
	
	public void setRightView(View view){
		if(mRightLayout != null){
			if(view != null){
				mRightLayout.addView(view,new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
				mRightLayout.setVisibility(View.VISIBLE);
			}
			else{
				mRightLayout.removeAllViews();
			}
		}
	}
	
	public void setOnRightClickListener(OnClickListener l){
		if(mRightLayout != null)
			mRightLayout.setOnClickListener(l);
	}
	
	public void setTitle(String title){
		if(mTitle != null)
		{
			mTitle.setText(title);
			mTitle.setVisibility(View.VISIBLE);
		}
	}

}
