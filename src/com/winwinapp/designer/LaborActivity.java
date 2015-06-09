package com.winwinapp.designer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.winwinapp.koala.ActionBarActivity;
import com.winwinapp.koala.R;

public class LaborActivity extends ActionBarActivity {

	TextView mTextView;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_labor);

		initActionBar();
		
		mTextView = (TextView)findViewById(R.id.designer_project_more);
		mTextView.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO �Զ����ɵķ������
				Intent intent = new Intent(LaborActivity.this,DesignerProjectActivity.class);
				startActivity(intent);
			}
			
		});
	}
	
	public void initActionBar(){
		ImageView imageView = new ImageView(this);
		imageView.setImageResource(R.drawable.back);
		setLeftView(imageView);
		setTitle("����");
		this.setOnLeftClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO �Զ����ɵķ������
				finish();
			}
			
		});
		imageView = new ImageView(this);
		imageView.setImageResource(R.drawable.message_1);
		setRightView(imageView);
	}
}
