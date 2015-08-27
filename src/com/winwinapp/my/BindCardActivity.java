package com.winwinapp.my;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.winwinapp.bids.BidsListActivity;
import com.winwinapp.koala.ActionBarActivity;
import com.winwinapp.koala.R;
import com.winwinapp.network.HTTPPost;
import com.winwinapp.network.NetworkData;

public class BindCardActivity extends ActionBarActivity implements OnClickListener{

	Button mNextStep;
	TextView mCardNo;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_bind_card);

		initActionBar();
		
		mCardNo = (TextView) findViewById(R.id.bind_card_no);
		mNextStep = (Button)findViewById(R.id.my_card_next_step);
		mNextStep.setOnClickListener(this);
		
	}
	
	public void initActionBar(){
		ImageView imageView = new ImageView(this);
		imageView.setImageResource(R.drawable.back);
		setLeftView(imageView);
		setTitle("绑定银行卡");
		this.setOnLeftClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO 自动生成的方法存根
				finish();
			}
			
		});
	}

	@Override
	public void onClick(View arg0) {
		// TODO 自动生成的方法存根
		switch(arg0.getId()){
		case R.id.my_card_next_step:
			if(TextUtils.isEmpty(mCardNo.getText())){
				Toast.makeText(this, "卡号不能为空", Toast.LENGTH_LONG).show();
				return;
			}
			Intent intent = new Intent(BindCardActivity.this,BindCardInformationActivity.class);
			intent.putExtra("cardno", mCardNo.getText().toString());
			startActivity(intent);
			break;
		}
	}
}
