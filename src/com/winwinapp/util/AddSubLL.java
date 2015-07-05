package com.winwinapp.util;

import com.winwinapp.koala.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AddSubLL extends LinearLayout {

	Context mContext;
	ImageView mAddView;
	ImageView mSubView;
	TextView mNumberView;
	int mCurrentNumber= 0;
	public AddSubLL(Context context) {
		super(context);
		mContext = context;
		initView();
		// TODO �Զ����ɵĹ��캯�����
	}

	public AddSubLL(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		initView();
		// TODO �Զ����ɵĹ��캯�����
	}

	public AddSubLL(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO �Զ����ɵĹ��캯�����
		mContext = context;
		initView();
	}
	
	public void initView(){
		this.setOrientation(LinearLayout.HORIZONTAL);
		mAddView = new ImageView(mContext);
		LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
		params.rightMargin = 5;
		mAddView.setLayoutParams(params);
		mAddView.setBackgroundResource(R.drawable.ic_add);
		
		mSubView = new ImageView(mContext);
		mSubView.setLayoutParams(params);
		mSubView.setBackgroundResource(R.drawable.ic_sub);
		
		mNumberView = new TextView(mContext);
		mNumberView.setGravity(Gravity.CENTER);
		mNumberView.setText(""+mCurrentNumber);
		mNumberView.setLayoutParams(params);
		mNumberView.setBackgroundResource(R.drawable.ic_circle);
		mNumberView.setTextSize(18);
		mNumberView.getPaint().setFakeBoldText(true);
		
		mSubView.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO �Զ����ɵķ������
				if(mCurrentNumber > 0){
					mCurrentNumber--;
					mNumberView.setText(""+mCurrentNumber);
					mNumberView.invalidate();
				}
			}
			
		});
		
		mAddView.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO �Զ����ɵķ������
				mCurrentNumber++;
				mNumberView.setText(""+mCurrentNumber);
				mNumberView.invalidate();
			}
			
		});
		
		addView(mSubView);
		addView(mNumberView);
		addView(mAddView);
	}

	public int getCurrentNumber(){
		return mCurrentNumber;
	}
	
	public void setCurrentNumber(String str){
		mCurrentNumber = Integer.parseInt(str);
		if(mCurrentNumber < 0){
			mCurrentNumber = 0;
		}
		mNumberView.setText(""+mCurrentNumber);
	}
}
