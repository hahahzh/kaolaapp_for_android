package com.winwinapp.calendar;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.winwinapp.koala.ActionBarActivity;
import com.winwinapp.koala.R;

public class SetScore extends ActionBarActivity {

	SeekBar mSpecialSB;
	SeekBar mAttitudeSB;
	TextView mCurrentSpecial;
	TextView mCurrentAttitude;
	Button mOK;
	EditText mFeedback;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_score);

		initActionBar();
		initView();
	}
	
	public void initView(){
		mCurrentSpecial = (TextView)findViewById(R.id.score_special_current_score);
		mCurrentAttitude = (TextView)findViewById(R.id.score_attitude_current_score);
		
		mFeedback = (EditText)findViewById(R.id.score_feedback);
		mSpecialSB = (SeekBar)findViewById(R.id.score_seekbar_special);
		mSpecialSB.setMax(100);
		mSpecialSB.setOnSeekBarChangeListener(new OnSeekBarChangeListener(){

			@Override
			public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
				// TODO 自动生成的方法存根
				float progress = arg1;
				mCurrentSpecial.setText(""+progress/10);
			}

			@Override
			public void onStartTrackingTouch(SeekBar arg0) {
				// TODO 自动生成的方法存根
				
			}

			@Override
			public void onStopTrackingTouch(SeekBar arg0) {
				// TODO 自动生成的方法存根
				
			}
			
		});
		
		mAttitudeSB = (SeekBar)findViewById(R.id.score_seekbar_attitude);
		mAttitudeSB.setMax(100);
		mAttitudeSB.setOnSeekBarChangeListener(new OnSeekBarChangeListener(){

			@Override
			public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
				// TODO 自动生成的方法存根
				float progress = arg1;
				mCurrentAttitude.setText(""+progress/10);
			}

			@Override
			public void onStartTrackingTouch(SeekBar arg0) {
				// TODO 自动生成的方法存根
				
			}

			@Override
			public void onStopTrackingTouch(SeekBar arg0) {
				// TODO 自动生成的方法存根
				
			}
			
		});
	
		mOK = (Button)findViewById(R.id.score_ok);
		mOK.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO 自动生成的方法存根
				if(mFeedback.getText().toString()==null || (mFeedback.getText().toString().isEmpty())){
					showDialog();
				}
			}
			
		});
	}
	
	protected void showDialog() {
		  AlertDialog.Builder builder = new Builder(this);
		  builder.setMessage("评价不能为空"); 
		  builder.setTitle("提示");  
		  builder.setNegativeButton("关闭", new DialogInterface.OnClickListener(){
          public void onClick(DialogInterface dialoginterface, int i) {
        	  dialoginterface.dismiss();
          }
			  });
		  builder.create().show();
	}
	
	public void initActionBar(){
		ImageView imageView = new ImageView(this);
		imageView.setImageResource(R.drawable.back);
		setLeftView(imageView);
		setTitle("评分");
		this.setOnLeftClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO 自动生成的方法存根
				finish();
			}
			
		});
	}
}
