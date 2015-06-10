package com.winwinapp.my;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

import com.winwinapp.koala.ActionBarActivity;
import com.winwinapp.koala.R;

public class BindCardActivity extends ActionBarActivity implements OnClickListener{

	Button mNextStep;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_bind_card);

		initActionBar();
		
		mNextStep = (Button)findViewById(R.id.my_card_next_step);
		mNextStep.setOnClickListener(this);
		
	}
	
	public void initActionBar(){
		ImageView imageView = new ImageView(this);
		imageView.setImageResource(R.drawable.back);
		setLeftView(imageView);
		setTitle("�����п�");
		this.setOnLeftClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO �Զ����ɵķ������
				finish();
			}
			
		});
	}

	@Override
	public void onClick(View arg0) {
		// TODO �Զ����ɵķ������
		switch(arg0.getId()){
		case R.id.my_card_next_step:
			Intent intent = new Intent(BindCardActivity.this,BindCardInformationActivity.class);
			startActivity(intent);
			break;
		}
	}
}