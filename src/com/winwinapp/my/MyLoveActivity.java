package com.winwinapp.my;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.winwinapp.bids.BidsDetailsActivity;
import com.winwinapp.chat.KoalaChatActivity;
import com.winwinapp.designer.ContactDesignerActivity;
import com.winwinapp.koala.ActionBarActivity;
import com.winwinapp.koala.R;
import com.winwinapp.network.HTTPPost;
import com.winwinapp.network.NetworkData;

public class MyLoveActivity extends ActionBarActivity {

	private static final int INDEX_BUTTON_MESSAGE = 1;
	private static final int INDEX_BUTTON_DELETE = 2;
	private static final int INDEX_BUTTON_OTHER = 3;
	private static final int MESSAGE_LIST_BACK = 4;
	private static final int MESSAGE_LIST_DELETE = 5;
//	ArrayList<MyLoveItem> mArrayList = new ArrayList<MyLoveItem>();
	ListView mListView;
	MyLoveAdapter mAdapter;
	NetworkData.AddDelMyCollectData mDelData = NetworkData.getInstance().getNewAddDelMyCollectData();
	NetworkData.CommonBack mCommBack = NetworkData.getInstance().getCommonBack();
	NetworkData.MyCollectBack mBack = NetworkData.getInstance().getNewMyCollectBack();
	NetworkData.MyCollectData mData = NetworkData.getInstance().getNewMyCollectData();
	Drawable mDefaultAvatar;
	
	private Handler mHandler = new Handler(){
		public void handleMessage(Message msg){
			int position;
			String error;
			switch(msg.what){
			case INDEX_BUTTON_MESSAGE:
				Intent intent = new Intent(MyLoveActivity.this,KoalaChatActivity.class);
				intent.putExtra("type", 1);
				intent.putExtra("msg_id", "54");
				intent.putExtra("topic_id", "21");
				intent.putExtra("rec_id", "5");
				startActivity(intent);
				break;
			case INDEX_BUTTON_DELETE:
				position = msg.arg1;
				if(position >= 0 && position <= mBack.memberInfo.size()-1){
					showSelfDefineDialog(position);
				}
				break;
			case INDEX_BUTTON_OTHER:
				break;
			case MESSAGE_LIST_BACK:
				error = (String)msg.obj;
				if("OK".equals(error)){
						mAdapter.notifyDataSetChanged();
						if(mBack.memberInfo.size() <= 0){
							Toast.makeText(MyLoveActivity.this, "获取收藏列表成功，列表为空。", Toast.LENGTH_LONG).show();
						}
				}else{
					Toast.makeText(MyLoveActivity.this, "获取收藏列表失败："+error, Toast.LENGTH_LONG).show();
				}
				break;
			case MESSAGE_LIST_DELETE:
				error = (String)msg.obj;
				if("OK".equals(error)){
					Toast.makeText(MyLoveActivity.this, "删除收藏成功。", Toast.LENGTH_LONG).show();
				}else{
					Toast.makeText(MyLoveActivity.this, "删除收藏失败："+error, Toast.LENGTH_LONG).show();
				}
				break;
			}
		}
	};
	
	public void showSelfDefineDialog(final int position) {
		  AlertDialog.Builder builder = new Builder(this);
		  builder.setMessage("确定删除收藏？"); 
		  builder.setTitle("提示");  
		  builder.setNegativeButton("取消", new DialogInterface.OnClickListener(){
          public void onClick(DialogInterface dialoginterface, int i) {
        	  dialoginterface.dismiss();
          }
			  });
		  builder.setPositiveButton("确认", new DialogInterface.OnClickListener(){
          public void onClick(DialogInterface dialoginterface, int i) {
        	  	new DelCollectThread(mBack.memberInfo.get(position).id).start();
        	  	mBack.memberInfo.remove(position);
				mAdapter.notifyDataSetChanged();
        	  	dialoginterface.dismiss();
          		}
			  });
		  builder.create().show();
	}
	
	public class DelCollectThread extends Thread{
		int uid =0;
		public DelCollectThread(String id){
			try{
			uid = Integer.parseInt(id);
			}catch(Exception e){
				uid = 0;
			}
		}
		public void run(){
			boolean success = false;
			mDelData.uid = uid;
			success = HTTPPost.DelCollectList(mDelData, mCommBack);
			Message msg = Message.obtain();
			msg.what = MESSAGE_LIST_DELETE;
			if(success){
				msg.obj = "OK";
			}else{
				msg.obj = mCommBack.error;
			}
			mHandler.sendMessage(msg);
		}
	}
	
	public class GetCollectListThread extends Thread{
		public void run(){
			boolean success = false;
			mData.typeid = 0;
			mData.page = 0;
			mData.limit = 5;
			success = HTTPPost.getMyCollectList(mData, mBack);
			Message msg = Message.obtain();
			msg.what = MESSAGE_LIST_BACK;
			if(success){
				msg.obj = "OK";
				if(mBack.total > 0){
					for(int i=0;i<mBack.total;i++){
						NetworkData.FindMemberItem item = mBack.memberInfo.get(i);
						Bitmap bmp;
						try {
							bmp = BitmapFactory.decodeStream(new URL(NetworkData.URL_SERVER+item.avatar).openStream());
							Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp,mDefaultAvatar.getIntrinsicWidth(), mDefaultAvatar.getIntrinsicHeight(), true);
							item.imgAvatar = thumbBmp;
						} catch (MalformedURLException e) {
							// TODO 自动生成的 catch 块
							e.printStackTrace();
						} catch (IOException e) {
							// TODO 自动生成的 catch 块
							e.printStackTrace();
						}
					}
				}
			}else{
				msg.obj = mBack.error;
			}
			mHandler.sendMessage(msg);
		}
	}
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_list);

		initActionBar();
		initListView();
		mDefaultAvatar = getResources().getDrawable(R.drawable.avatar1);
		new GetCollectListThread().start();
	}
	
	public void initListView(){
		mListView = (ListView)this.findViewById(R.id.list_common);
//		MyLoveItem item = new MyLoveItem();
//		item.mIndex = 1;
//		item.mSpecial = "9年经验";
//		item.name = "钟晓晓";
//		item.attitude_score = "9.0";
//		item.profess_score = "9.0";
//		item.cases = "8";
//		item.job = "监理";
//		mArrayList.add(item);
//		item = new MyLoveItem();
//		item.mIndex = 2;
//		item.mSpecial = "￥100/m2";
//		item.name = "钟晓晓";
//		item.attitude_score = "9.0";
//		item.profess_score = "9.0";
//		item.cases = "8";
//		item.job = "设计师";
//		mArrayList.add(item);
//		
//		item = new MyLoveItem();
//		item.mIndex = 2;
//		item.mSpecial = "9年经验";
//		item.name = "钟晓晓";
//		item.attitude_score = "9.0";
//		item.profess_score = "9.0";
//		item.cases = "8";
//		item.job = "工长";
//		mArrayList.add(item);
		mAdapter = new MyLoveAdapter(this);
		mListView.setAdapter(mAdapter);
	}
	
	public void initActionBar(){
		ImageView imageView = new ImageView(this);
		imageView.setImageResource(R.drawable.back);
		setLeftView(imageView);
		setTitle("我的收藏");
		this.setOnLeftClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO 自动生成的方法存根
				finish();
			}
			
		});
	}
	
	public class MyLoveItem{
		int mIndex;
		String mSpecial;
		String job;
		String name;
		String profess_score;
		String attitude_score;
		String cases;
	}
	
	public class MyLoveAdapter extends BaseAdapter{
		Context mContext;
		LayoutInflater mInflater;
		public MyLoveAdapter(Context context){
			mContext = context;
			mInflater = LayoutInflater.from(mContext);
		}
		@Override
		public int getCount() {
			// TODO 自动生成的方法存根
			return mBack.memberInfo.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO 自动生成的方法存根
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			// TODO 自动生成的方法存根
			return 0;
		}

		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2) {
			// TODO 自动生成的方法存根
			ImageView message;
			ImageView delete;
			TextView mSpecial,name,job,cases,score;
			NetworkData.FindMemberItem item = mBack.memberInfo.get(arg0);
			arg1 = mInflater.inflate(R.layout.layout_my_love_item, null);
			message = (ImageView)arg1.findViewById(R.id.my_love_item_message);
			message.setOnClickListener(new OnItemChildClickListener(INDEX_BUTTON_MESSAGE,arg0));
			
			delete = (ImageView)arg1.findViewById(R.id.my_love_item_delete);
			delete.setOnClickListener(new OnItemChildClickListener(INDEX_BUTTON_DELETE,arg0));
			
			mSpecial = (TextView)arg1.findViewById(R.id.my_love_item_special);
			//mSpecial.setText(item.);
			
			name = (TextView)arg1.findViewById(R.id.my_love_item_name);
			name.setText(item.username);
			job = (TextView)arg1.findViewById(R.id.my_love_item_job);
			//job.setText(item.job);
			cases = (TextView)arg1.findViewById(R.id.my_love_item_cases);
			cases.setText("案例数："+item.case_num);
			score = (TextView)arg1.findViewById(R.id.my_love_item_score);
			score.setText("专业："+item.rate_avg+"     服务："+item.attud_avg);
			
			ImageView avatar = (ImageView)arg1.findViewById(R.id.my_love_item_avatar);
			if(item.imgAvatar != null){
				avatar.setImageBitmap(item.imgAvatar);
			}
			
			
			return arg1;
		}
		
	}
	
	public class OnItemChildClickListener implements View.OnClickListener{
		private int mClickIndex;
		private int mPosition;
		
		public OnItemChildClickListener(int clickIndex, int position){
			mClickIndex = clickIndex;
			mPosition = position;
		}
		
		@Override
		public void onClick(View arg0) {
			// TODO 自动生成的方法存根
			Message msg = Message.obtain();
			msg.what = mClickIndex;
			msg.arg1 = mPosition;
			mHandler.sendMessage(msg);
		}
		
	}
}
