package com.winwinapp.about;


import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.winwinapp.koala.ActionBarActivity;
import com.winwinapp.koala.R;

public class FeedbackActivity extends ActionBarActivity {

	private Context context = FeedbackActivity.this;
	private EditText edit_content;
	private Button btn_submit;
	
	private String content;
	private LinearLayout layoutProcess;
	private Thread mThread;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_about_feedback);
		edit_content = (EditText)findViewById(R.id.about_feedback_txt);
		btn_submit = (Button)findViewById(R.id.about_feedback_btn);
		
		layoutProcess = (LinearLayout)findViewById(R.id.login_status);
		
		btn_submit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {	
				//设置点击背景
//				btn_submit.setBackgroundResource(R.drawable.);
				//layoutProcess.setVisibility(View.VISIBLE);
				//获取用户的登录信息，连接服务器，获取登录状态
				//content = edit_content.getText().toString().trim();
				
				
				if ("".equals(content)){
					//layoutProcess.setVisibility(View.GONE);
					Toast.makeText(FeedbackActivity.this, "反馈失败：建议不能为空" , Toast.LENGTH_SHORT).show();
				}else {
					Toast.makeText(FeedbackActivity.this,"反馈失败：服务端无返回",Toast.LENGTH_LONG).show();
				}
			}
		});
	}
	
	public void initActionBar(){
		ImageView imageView = new ImageView(this);
		imageView.setImageResource(R.drawable.back);
		setLeftView(imageView);
		setTitle("反馈");
		this.setOnLeftClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO 自动生成的方法存根
				finish();
			}
			
		});
	}
}
