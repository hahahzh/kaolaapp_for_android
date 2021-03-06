package com.winwinapp.designer;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.winwinapp.bids.BidsListActivity;
import com.winwinapp.chat.KoalaChatActivity;
import com.winwinapp.koala.ActionBarActivity;
import com.winwinapp.koala.R;
import com.winwinapp.network.HTTPPost;
import com.winwinapp.network.NetworkData;

public class DesignerActivity extends ActionBarActivity {

	TextView mTextView;
	ImageView mLove;
	boolean bLove = false;
	String mId;
	
	TextView mProjectNum;
	ListView mProjectList;
	ImageView avatar;
	TextView mLocation;
	TextView mNameType;
	TextView mSkill;
	TextView mAttu;
	TextView mExperience;
	TextView mPrice;
	TextView mIDAuth;
	TextView mCertAuth;
	TextView mIntroduce;
	Bitmap mAvartarImg;
	NetworkData.MemberDetailData mData = NetworkData.getInstance().getNewMemberDetailData();
	NetworkData.MemberDetailBack mBack = NetworkData.getInstance().getNewMemberDetailBack();
	Drawable mDefaultAvatar = null;
	LinearLayout mLL;
	
	private Handler mHandler = new Handler(){
		public void handleMessage(Message msg){
			//Intent intent;
			String error;
			switch(msg.what){
			case 1:
				error = (String)msg.obj;
				if("OK".equals(error)){
					refresh();
				}else{
					Toast.makeText(DesignerActivity.this, "获取招会员信息失败："+error, Toast.LENGTH_LONG).show();
				}
				break;
			case 2://add
				error = (String)msg.obj;
				if("OK".equals(error)){
					Toast.makeText(DesignerActivity.this, "添加收藏成功", Toast.LENGTH_LONG).show();
				}else{
					Toast.makeText(DesignerActivity.this, "添加收藏失败："+error, Toast.LENGTH_LONG).show();
				}
				break;
			case 3://del
				error = (String)msg.obj;
				if("OK".equals(error)){
					Toast.makeText(DesignerActivity.this, "删除收藏成功", Toast.LENGTH_LONG).show();
				}else{
					Toast.makeText(DesignerActivity.this, "删除收藏失败："+error, Toast.LENGTH_LONG).show();
				}
				break;
			}
		}
	};
	
	NetworkData.AddDelMyCollectData mDelData = NetworkData.getInstance().getNewAddDelMyCollectData();
	NetworkData.CommonBack mCommBack = NetworkData.getInstance().getCommonBack();
	public class DelCollectThread extends Thread{
		int uid =0;
		boolean mAdd;
		public DelCollectThread(String id,boolean add){
			try{
			uid = Integer.parseInt(id);
			}catch(Exception e){
				uid = 0;
			}
			mAdd = add;
		}
		public void run(){
			boolean success = false;
			mDelData.uid = uid;
			Message msg = Message.obtain();
			if(mAdd){
				msg.what = 2;
				success = HTTPPost.AddCollectList(mDelData, mCommBack);
			}else{
				msg.what = 3;
				success = HTTPPost.DelCollectList(mDelData, mCommBack);
			}
			if(success){
				msg.obj = "OK";
			}else{
				msg.obj = mCommBack.error;
			}
			mHandler.sendMessage(msg);
		}
	}
	
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
		mNameType.setText(mBack.username+"  设计师");
		mSkill.setText(mBack.rate_avg);
		mAttu.setText(mBack.attud_avg);
		if("1".equals(mBack.name_auth)){
			
		}else{
			mIDAuth.setText("身份未认证");
			mIDAuth.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.id_no), null, null, null);
		}
		mIntroduce.setText(mBack.introduce);
		mProjectNum.setText("项目经验("+mBack.exps.size()+")");
		if(mBack.exps.size() <= 0){
			mTextView.setEnabled(false);
			mTextView.setVisibility(View.GONE);
		}else{
			ArrayList<DesignerProjectItem> list = new ArrayList<DesignerProjectItem>();
			int cnt = mBack.exps.size() >= 2? 2:mBack.exps.size();
			for(int i=0;i<cnt;i++){
				NetworkData.ProjectExperienceItem im = mBack.exps.get(i);
				DesignerProjectItem item = new DesignerProjectItem();
				item.mArea = im.area + "㎡";
				item.mAreaName = im.biotope_name;
				item.date = im.datetime;
				item.mSkills = im.rate;
				item.mService = im.atud;
				item.mComment.mCommenterName = im.name;
				item.mComment.mComments = im.cmt;
				list.add(item);
			}
			mProjectList.setAdapter(new DesignerProjectAdapter(this,list));
		}
		mLL.setVisibility(View.VISIBLE);
//		if("1".equals(mBack.certauth)){
//			
//		}else{
//			mIDAuth.setText("身份未认证");
//			mIDAuth.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.id_no), null, null, null);
//		}
	}
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_designer);

		mLL = (LinearLayout)findViewById(R.id.designer_page_ll);
		mLL.setVisibility(View.GONE);
		avatar = (ImageView)findViewById(R.id.designer_avatar);
		mLocation = (TextView)findViewById(R.id.designer_location);
		mNameType = (TextView)findViewById(R.id.designer_name_type);
		mSkill = (TextView)findViewById(R.id.designer_skill);
		mAttu = (TextView)findViewById(R.id.designer_atitude);
		mExperience = (TextView)findViewById(R.id.designer_experience);
		mPrice = (TextView)findViewById(R.id.designer_price);
		mIDAuth = (TextView)findViewById(R.id.designer_id_auth);
		mCertAuth = (TextView)findViewById(R.id.designer_certi_auth);
		mIntroduce = (TextView)findViewById(R.id.designer_introduce);
		initActionBar();
		mId = getIntent().getStringExtra("id");
		mDefaultAvatar = getResources().getDrawable(R.drawable.avatar1);
		
		new getMemberDetailThread().start();
		mProjectNum = (TextView)findViewById(R.id.designer_project_num);
		mProjectList = (ListView)findViewById(R.id.designer_project_experience_list);
		
		mTextView = (TextView)findViewById(R.id.designer_project_more);
		mTextView.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO 自动生成的方法存根
				Intent intent = new Intent(DesignerActivity.this,DesignerProjectActivity.class);
				DesignerProjectActivity.mArrayList.clear();
				int cnt = mBack.exps.size();
				for(int i=0;i<cnt;i++){
					NetworkData.ProjectExperienceItem im = mBack.exps.get(i);
					DesignerProjectItem item = new DesignerProjectItem();
					item.mArea = im.area + "㎡";
					item.mAreaName = im.biotope_name;
					item.date = im.datetime;
					item.mSkills = im.rate;
					item.mService = im.atud;
					item.mComment.mCommenterName = im.name;
					item.mComment.mComments = im.cmt;
					DesignerProjectActivity.mArrayList.add(item);
				}
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
				new DelCollectThread(mId,bLove).start();
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
