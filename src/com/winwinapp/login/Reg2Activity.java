package com.winwinapp.login;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import com.winwinapp.koala.R;

public class Reg2Activity extends Activity {

	private Context context = Reg2Activity.this;
	private RadioButton btn_customer;
	private RadioButton btn_designer;
	private RadioButton btn_labor;
	private RadioButton btn_suprior;
	private EditText edit_qq;
	private EditText edit_city;
	private Button btn_submit;

	private int status;
	private String qq;
	private String city;

	private LinearLayout layoutProcess;
	private Thread mThread;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_reg_2);
//		btn_customer = (RadioButton)findViewById(R.id.reg2_c_cb);
//		btn_designer = (RadioButton)findViewById(R.id.reg2_d_cb);
//		btn_labor = (RadioButton)findViewById(R.id.reg2_m_cb);
//		btn_suprior = (RadioButton)findViewById(R.id.reg2_v_cb);
		
		edit_qq = (EditText)findViewById(R.id.reg2_qq_txt);
		edit_city = (EditText)findViewById(R.id.reg2_city_txt);
		
		btn_submit = (Button)findViewById(R.id.reg2_submit_btn);
		
		layoutProcess = (LinearLayout)findViewById(R.id.login_status);
		
		btn_submit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {	
				//设置点击背景
//				btn_submit.setBackgroundResource(R.drawable.);
				layoutProcess.setVisibility(View.VISIBLE);
				//获取用户的登录信息，连接服务器，获取登录状态
				qq = edit_qq.getText().toString().trim();
				city = edit_city.getText().toString().trim();

				if ("".equals(qq) || "".equals(city)){
//					layoutProcess.setVisibility(View.GONE);
//					Toast.makeText(LoginPageActivity.this, context.getString(R.string.login_emptyname_or_emptypwd) , Toast.LENGTH_SHORT).show();
				}else {
//					mThread = new Thread(nextRunable);
//					mThread.start();
					
				}
			}
		});
		
	}

}
