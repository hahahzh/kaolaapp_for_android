package com.winwinapp.designer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.winwinapp.chat.KoalaChatActivity;
import com.winwinapp.koala.ActionBarActivity;
import com.winwinapp.koala.R;

public class DesignerActivity extends ActionBarActivity {

	TextView mTextView;
	ImageView mLove;
	boolean bLove = false;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_designer);

		initActionBar();
		
		mTextView = (TextView)findViewById(R.id.designer_project_more);
		mTextView.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO 自动生成的方法存根
				Intent intent = new Intent(DesignerActivity.this,DesignerProjectActivity.class);
				startActivity(intent);
			}
			
		});
		
		mLove = (ImageView)findViewById(R.id.designer_love);
		mLove.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO 自动生成的方法存根
				bLove = !bLove;
				mLove.setImageResource(bLove?R.drawable.heart_yes:R.drawable.heart_no);
			}
			
		});
	}
	
	public void initActionBar(){
		ImageView imageView = new ImageView(this);
		imageView.setImageResource(R.drawable.back);
		setLeftView(imageView);
		setTitle("设计师");
		this.setOnLeftClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO 自动生成的方法存根
				finish();
			}
			
		});
		imageView = new ImageView(this);
		imageView.setImageResource(R.drawable.message_1);
		setRightView(imageView);
		this.setOnRightClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO 自动生成的方法存根
				//finish();
				Intent intent = new Intent(DesignerActivity.this,KoalaChatActivity.class);
				intent.putExtra("type", 1);
				intent.putExtra("msg_id", "54");
				intent.putExtra("topic_id", "21");
				intent.putExtra("rec_id", "5");
				startActivity(intent);
			}
			
		});
	}
}
