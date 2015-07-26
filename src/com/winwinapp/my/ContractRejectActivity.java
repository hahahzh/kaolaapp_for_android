package com.winwinapp.my;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.winwinapp.koala.ActionBarActivity;
import com.winwinapp.koala.R;
import com.winwinapp.network.HTTPPost;
import com.winwinapp.network.NetworkData;

public class ContractRejectActivity extends ActionBarActivity {

	private final static int MESSAGE_REJECT_BACK = 1;
	Button mSubmit;
	int mCid;
	EditText mReason;
	NetworkData.ContractAgreeRejectData mData = NetworkData.getInstance().getNewContractAgreeRejectData();
	NetworkData.CommonBack mBack = NetworkData.getInstance().getCommonBack();
	
	private Handler mHandler = new Handler(){
		public void handleMessage(Message msg){
			switch(msg.what){
			case MESSAGE_REJECT_BACK:
				String error = (String) msg.obj;
				if("OK".equals(error)){
					ContractRejectActivity.this.finish();
				}else{
					Toast.makeText(ContractRejectActivity.this, "发送驳回消息失败："+error, Toast.LENGTH_LONG).show();
				}
				break;
			}
		}
	};
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_reject);
		
		mCid = getIntent().getIntExtra("id", 0);
		mReason = (EditText)findViewById(R.id.reject_reason_txt);
		mSubmit = (Button)findViewById(R.id.reject_submit);
		mSubmit.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				// TODO 自动生成的方法存根
				new SendRejectThread().start();
			}
		});
		
	}
	
	public class SendRejectThread extends Thread{
		public void run(){
			mData.c_id = mCid;
			mData.reason = mReason.getText().toString();
			boolean success = HTTPPost.SendMyContractReject(mData, mBack);
			Message msg = Message.obtain();
			msg.what = MESSAGE_REJECT_BACK;
			if(success){
				msg.obj = "OK";
			}else{
				msg.obj = mBack.error;
			}
			mHandler.sendMessage(msg);
		}
	}
}
