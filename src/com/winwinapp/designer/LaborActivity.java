package com.winwinapp.designer;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.winwinapp.chat.KoalaChatActivity;
import com.winwinapp.designer.DesignerActivity.getMemberDetailThread;
import com.winwinapp.koala.ActionBarActivity;
import com.winwinapp.koala.R;
import com.winwinapp.network.HTTPPost;
import com.winwinapp.network.NetworkData;

public class LaborActivity extends ActionBarActivity {

	TextView mTextView;
	ImageView mLove;
	boolean bLove = false;
	
	String mId;
	ImageView avatar;
	TextView mLocation;
	TextView mNameType;
	TextView mSkill;
	TextView mAttu;
	TextView mExperience;
	TextView mIDAuth;
	TextView mCertAuth;
	TextView mIntroduce;
	Bitmap mAvartarImg;
	NetworkData.MemberDetailData mData = NetworkData.getInstance().getNewMemberDetailData();
	NetworkData.MemberDetailBack mBack = NetworkData.getInstance().getNewMemberDetailBack();
	Drawable mDefaultAvatar = null;
	
	private Handler mHandler = new Handler(){
		public void handleMessage(Message msg){
			//Intent intent;
			switch(msg.what){
			case 1:
				String error = (String)msg.obj;
				if("OK".equals(error)){
					refresh();
				}else{
					Toast.makeText(LaborActivity.this, "获取招会员信息失败："+error, Toast.LENGTH_LONG).show();
				}
				break;
			}
		}
	};
	
	public class getMemberDetailThread extends Thread{
		public void run(){
			if(TextUtils.isEmpty(mId)){
				mData.uid = 0;
			}else{
				mData.uid = Integer.parseInt(mId);
			}
			boolean success = HTTPPost.FindMemberDetail(mData, mBack);
			Message msg = Message.obtain();
			msg.what = 1;
			if(success){
				msg.obj = "OK";
				Bitmap bmp;
				try {
					bmp = BitmapFactory.decodeStream(new URL(NetworkData.URL_SERVER+mBack.avatar).openStream());
					Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp,mDefaultAvatar.getIntrinsicWidth(), mDefaultAvatar.getIntrinsicHeight(), true);
					mAvartarImg = thumbBmp;
				} catch (MalformedURLException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				} catch (IOException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
			}else{
				msg.obj = mBack.error;
			}
			mHandler.sendMessage(msg);
		}
	}
	public void refresh(){
		if(mAvartarImg != null){
			avatar.setImageBitmap(mAvartarImg);
		}
		mNameType.setText(mBack.username+"  工长");
		mSkill.setText(mBack.rate_avg);
		mAttu.setText(mBack.attud_avg);
		if("1".equals(mBack.name_auth)){
			
		}else{
			mIDAuth.setText("身份未认证");
			mIDAuth.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.id_no), null, null, null);
		}
		mIntroduce.setText(mBack.introduce);
//		if("1".equals(mBack.certauth)){
//			
//		}else{
//			mIDAuth.setText("身份未认证");
//			mIDAuth.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.id_no), null, null, null);
//		}
	}
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_labor);

		initActionBar();
		
		mTextView = (TextView)findViewById(R.id.designer_project_more);
		mTextView.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO 自动生成的方法存根
				Intent intent = new Intent(LaborActivity.this,DesignerProjectActivity.class);
				startActivity(intent);
			}
			
		});
		
		mLove = (ImageView)findViewById(R.id.labor_love);
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
		setTitle("工长");
		
		avatar = (ImageView)findViewById(R.id.labor_avatar);
		mLocation = (TextView)findViewById(R.id.labor_location);
		mNameType = (TextView)findViewById(R.id.labor_name_type);
		mSkill = (TextView)findViewById(R.id.labor_skill);
		mAttu = (TextView)findViewById(R.id.labor_atitude);
		mExperience = (TextView)findViewById(R.id.labor_experience);
		mIDAuth = (TextView)findViewById(R.id.labor_id_auth);
		mCertAuth = (TextView)findViewById(R.id.labor_certi_auth);
		mIntroduce = (TextView)findViewById(R.id.labor_introduce);
		initActionBar();
		mId = getIntent().getStringExtra("id");
		mDefaultAvatar = getResources().getDrawable(R.drawable.avatar1);
		
		new getMemberDetailThread().start();
		
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
				Intent intent = new Intent(LaborActivity.this,KoalaChatActivity.class);
				intent.putExtra("type", 1);
				intent.putExtra("msg_id", "54");
				intent.putExtra("topic_id", "21");
				intent.putExtra("rec_id", "5");
				startActivity(intent);
			}
			
		});
	}
}
