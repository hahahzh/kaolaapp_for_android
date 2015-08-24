package com.winwinapp.calendar;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Toast;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.winwinapp.koala.ActionBarActivity;
import com.winwinapp.koala.KLHomePageActivity;
import com.winwinapp.koala.KoalaApplication;
import com.winwinapp.koala.R;
import com.winwinapp.koala.fragment_homepage;
import com.winwinapp.network.HTTPPost;
import com.winwinapp.network.NetworkData;

public class SetScore extends ActionBarActivity {

	private static final int USER_EVALUE_BACK = 1;
	NetworkData.UserEvalueData mData = NetworkData.getInstance().getNewUserEvalueData();
	NetworkData.CommonBack mBack = NetworkData.getInstance().getCommonBack();
	SeekBar mSpecialSB;
	SeekBar mAttitudeSB;
	TextView mCurrentSpecial;
	TextView mCurrentAttitude;
	Button mOK;
	EditText mFeedback;
	
	private Handler mHandler = new Handler(){
		public void handleMessage(Message msg){
			//Intent intent;
			switch(msg.what){
			case USER_EVALUE_BACK:
				String error = (String)msg.obj;
				if("OK".equals(error)){
						Intent intent = new Intent(SetScore.this,KLHomePageActivity.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(intent);
				}else{
					Toast.makeText(SetScore.this, "评价失败："+error, Toast.LENGTH_LONG).show();
				}
				break;
			}
		}
	};
	
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
				}else{
					new Thread(){
						public void run(){
							boolean success = false;
							mData.bid = getIntent().getIntExtra("bid", 0);
							try{
								mData.p2 = mData.p3 = mData.p1 = mCurrentSpecial.getText().toString();
								mData.s1 = mData.s2 = mData.s3 = mCurrentAttitude.getText().toString();
								mData.commend1 = mData.commend2 = mData.commend3 = mFeedback.getText().toString();
							}catch(Exception e){
								
							}
							success = HTTPPost.userEvalue(mData, mBack);
							Message msg = Message.obtain();
							msg.what = USER_EVALUE_BACK;
							if(success){
								msg.obj = "OK";
							}else{
								msg.obj = mBack.error;
							}
							mHandler.sendMessage(msg);
						}
					}.start();
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
