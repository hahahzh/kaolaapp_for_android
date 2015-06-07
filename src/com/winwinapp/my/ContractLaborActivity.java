package com.winwinapp.my;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.winwinapp.koala.ActionBarActivity;
import com.winwinapp.koala.R;
import com.winwinapp.selectcity.SelectAddressActivity;

public class ContractLaborActivity extends ActionBarActivity implements OnClickListener{

	LinearLayout mAddressLL;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_contract_labor);

		initActionBar();
		
		mAddressLL = (LinearLayout)findViewById(R.id.contract_address_ll);
		mAddressLL.setOnClickListener(this);
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

	@Override
	public void onClick(View arg0) {
		// TODO 自动生成的方法存根
		switch(arg0.getId()){
		case R.id.contract_address_ll:
			Intent intent = new Intent(ContractLaborActivity.this,SelectAddressActivity.class);
			startActivity(intent);
			break;
		}
	}
}
