package com.winwinapp.calendar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.winwinapp.koala.ActionBarActivity;
import com.winwinapp.koala.R;

public class SetDepositActivity extends ActionBarActivity implements TextWatcher{

	Button mOK;
	TextView mContractTotal;
	TextView mContractFirstPay;
	TextView mSetPrefix;
	EditText mSetPercent;
	TextView mSetSuffix;
	TextView mTips;
	int total = 100000;
	int suffix = 5000;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_set_deposit);

		initActionBar();
		initView();
	}
	
	public void initView(){
		mOK = (Button)findViewById(R.id.set_deposit_OK);
		mContractTotal = (TextView)findViewById(R.id.set_deposit_total);
		mContractFirstPay = (TextView)findViewById(R.id.set_deposit_first_pay);
		mSetPrefix = (TextView)findViewById(R.id.set_deposit_edit_prefix);
		mSetPercent = (EditText)findViewById(R.id.set_deposit_edit);
		mSetSuffix = (TextView)findViewById(R.id.set_deposit_edit_suffix);
		mTips = (TextView)findViewById(R.id.set_deposit_tips);
		
		SpannableStringBuilder style=new SpannableStringBuilder(mTips.getText());
		style.setSpan(new ForegroundColorSpan(Color.RED),6,9,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE); 
		style.setSpan(new ForegroundColorSpan(Color.RED),62,69,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE); 
		mTips.setText(style);
		
		mSetPercent.addTextChangedListener(this);
		
		mOK.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO 自动生成的方法存根
				Intent intent = new Intent(SetDepositActivity.this , SetScore.class);
				startActivity(intent);
			}
			
		});
	}
	
	public void initActionBar(){
		ImageView imageView = new ImageView(this);
		imageView.setImageResource(R.drawable.back);
		setLeftView(imageView);
		setTitle("设置质保金");
		this.setOnLeftClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO 自动生成的方法存根
				finish();
			}
			
		});
	}

	@Override
	public void afterTextChanged(Editable arg0) {
		// TODO 自动生成的方法存根
		String str = mSetPercent.getText().toString();
		if(str != null && !str.isEmpty()){
			int percent = Integer.parseInt(str);
			if(percent > 0 && percent < 50){
				mSetSuffix.setText("% = "+total*percent/100);
			}else{
				percent = 5;
				mSetPercent.setText("5");
				mSetSuffix.setText("% = "+total*percent);
			}
		}
	}

	@Override
	public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
			int arg3) {
		// TODO 自动生成的方法存根
		
	}

	@Override
	public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
		// TODO 自动生成的方法存根
		
	}

}
