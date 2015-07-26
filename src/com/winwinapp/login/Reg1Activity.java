package com.winwinapp.login;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.winwinapp.about.AboutActivity;
import com.winwinapp.about.FuncDescActivity;
import com.winwinapp.koala.ActionBarActivity;
import com.winwinapp.koala.R;
import com.winwinapp.my.MyContractActivity;
import com.winwinapp.network.HTTPPost;
import com.winwinapp.network.NetworkData;
import com.winwinapp.util.Utils;

public class Reg1Activity extends ActionBarActivity {

	private static final int REQUEST_CODE_BACK = 1;
	private Context context = Reg1Activity.this;
	private EditText edit_mailandphone;
	private EditText edit_username;
	private EditText edit_password;
	private EditText edit_repeat_password;
	private EditText edit_v_code;
	private EditText edit_policy;
	private Button btn_next;
	private Button btn_vcode;
	private String mailandphone;
	private String username;
	private String password;
	private String repeat_password;
	private String v_code;
	private LinearLayout layoutProcess;
	private Thread mThread;
	NetworkData.RegisterSendCodeData mData = NetworkData.getInstance().getNewRegisterSendCodeData();
	NetworkData.CommonBack mBack = NetworkData.getInstance().getCommonBack();
	
	private Handler mHandler = new Handler(){
		public void handleMessage(Message msg){
			switch(msg.what){
			case REQUEST_CODE_BACK:
				String error = (String) msg.obj;
				if("OK".equals(error)){
					Toast.makeText(Reg1Activity.this, "������֤��ɹ���"+error, Toast.LENGTH_LONG).show();
				}else{
					Toast.makeText(Reg1Activity.this, "������֤��ʧ�ܣ�"+error, Toast.LENGTH_LONG).show();
				}
				break;
			}
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_reg_1);
		initActionBar();
		edit_mailandphone = (EditText)findViewById(R.id.reg_mailorphone_txt);
		edit_username = (EditText)findViewById(R.id.reg_username_txt);
		edit_password = (EditText)findViewById(R.id.reg_pwd_txt);
		edit_repeat_password = (EditText)findViewById(R.id.reg_c_pwd_txt);
		edit_v_code = (EditText)findViewById(R.id.reg_v_code_txt);
		btn_next = (Button)findViewById(R.id.reg_submit_btn);
		btn_vcode = (Button)findViewById(R.id.reg_vcode_btn);
		
//		edit_policy =  (EditText)findViewById(R.id.reg_policy_tv);
		
		layoutProcess = (LinearLayout)findViewById(R.id.login_status);
	
		btn_next.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {	
				//���õ������
//				btn_submit.setBackgroundResource(R.drawable.);
//				layoutProcess.setVisibility(View.VISIBLE);
				//��ȡ�û��ĵ�¼��Ϣ�����ӷ���������ȡ��¼״̬
				mailandphone = edit_mailandphone.getText().toString().trim();
				username = edit_username.getText().toString().trim();
				password = edit_password.getText().toString().trim();
				repeat_password = edit_repeat_password.getText().toString().trim();
				v_code = edit_v_code.getText().toString().trim();
				if ("".equals(mailandphone) || "".equals(username) || "".equals(password) || "".equals(repeat_password) || "".equals(v_code)){
//					layoutProcess.setVisibility(View.GONE);
					Toast.makeText(context, context.getString(R.string.login_emptyname_or_emptypwd) , Toast.LENGTH_SHORT).show();
				}else if(!Utils.isEmail(mailandphone) && !Utils.isMobilePhone(mailandphone)){
					Toast.makeText(context, "��������ֻ��ŷǷ�" , Toast.LENGTH_SHORT).show();
				}else if(!password.equals(repeat_password)){
					Toast.makeText(context, "�������벻һ��" , Toast.LENGTH_SHORT).show();
				}else{
					Bundle bundle = new Bundle();
					Intent intent = new Intent(Reg1Activity.this, Reg2Activity.class);
					if(Utils.isEmail(mailandphone)){
						bundle.putString("mail", mailandphone);
						bundle.putString("mobile", "");
					}else{
						bundle.putString("mobile", mailandphone);
						bundle.putString("mail", "");
					}
					bundle.putString("password", password);
					bundle.putString("username", username);
					intent.putExtras(bundle);
					startActivity(intent);
				}
			}
		});
		
		btn_vcode.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {	
				
//					mThread = new Thread(getVCodeRunable);
//					mThread.start();
				if(edit_mailandphone.getText().toString().isEmpty()){
					Toast.makeText(context, "������ֻ�����Ϊ��", Toast.LENGTH_LONG).show();
				}else{
					mData.phone_mail = edit_mailandphone.getText().toString();
					mailandphone = edit_mailandphone.getText().toString().trim();
					if(Utils.isEmail(mailandphone)){
						mData.is_email = 1;
					}else if(Utils.isMobilePhone(mailandphone)){
						mData.is_email = 0;
					}else{
						Toast.makeText(context, "��������ֻ��ŷǷ�" , Toast.LENGTH_SHORT).show();
						return;
					}
					mData.auth_code = "ABCD";
					new SendVerCodeThread().start();
				}
					
			}
		});
		
	}
	

	public void initActionBar(){
		ImageView imageView = new ImageView(this);
		imageView.setImageResource(R.drawable.back);
		setLeftView(imageView);
		setTitle("ע��");
		this.setOnLeftClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO �Զ����ɵķ������
				finish();
			}
			
		});
	}
	/**
	 * ��ȡ��֤���߳�
	 */
	public class SendVerCodeThread extends Thread {
		
		@Override
		public void run() {
			boolean success = false;
			success = HTTPPost.RegisterSendCode(mData, mBack);
			Message msg = Message.obtain();
			msg.what = REQUEST_CODE_BACK;
			if(success){
				msg.obj = "OK";
			}else{
				msg.obj = mBack.error;
			}
			mHandler.sendMessage(msg);
		}
	};

}
