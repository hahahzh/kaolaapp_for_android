package com.winwinapp.my;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.winwinapp.koala.ActionBarActivity;
import com.winwinapp.koala.R;

public class ContractLaborActivity extends ActionBarActivity {

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_contract_labor);

		initActionBar();
		
	}
	
	public void initActionBar(){
		ImageView imageView = new ImageView(this);
		imageView.setImageResource(R.drawable.back);
		setLeftView(imageView);
		setTitle("合同");
		this.setOnLeftClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO 自动生成的方法存根
				finish();
			}
			
		});
		
		 imageView = new ImageView(this);
		imageView.setImageResource(R.drawable.eye_green);
		setRightView(imageView);
		this.setOnRightClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO 自动生成的方法存根
				//finish();
			}
			
		});
	}
}
