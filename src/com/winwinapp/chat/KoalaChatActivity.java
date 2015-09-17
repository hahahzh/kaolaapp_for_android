package com.winwinapp.chat;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.winwinapp.koala.ActionBarActivity;
import com.winwinapp.koala.KoalaApplication;
import com.winwinapp.koala.MessageItem;
import com.winwinapp.koala.R;
import com.winwinapp.network.HTTPPost;
import com.winwinapp.network.NetworkData;
import com.winwinapp.util.ActionBarView;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class KoalaChatActivity extends ActionBarActivity implements OnClickListener {

	private static final int MESSAGE_REFRESH_LIST = 1;
	private static final int MESSAGE_SEND_PUBLIC_MSG = 2;
	private static final int MESSAGE_SEND_PRIVATE_MSG = 3;
	private Button mBtnSend;// 发送btn
	private EditText mEditTextContent;
	private ListView mListView;
	private ChatMsgViewAdapter mAdapter;// 消息视图的Adapter
	private List<ChatMsgEntity> mDataArrays = new ArrayList<ChatMsgEntity>();// 消息对象数组
	private ActionBarView mActionBar;
	private ArrayList<MessageItem> mMessageItemArray;
	/**
	 * 类型,0:系统消息；1，私人消息
	 */
	int type = 0;
	int msg_id = 0;
	int is_exist = 0;
	int topic_id;
	int rec_id;
	private NetworkData.PublicMessageInfoData mPublicData;
	private NetworkData.PublicMessageInfoBack mPublicBack;
	
	private NetworkData.PrivateMessageInfoData mPrivateData;
	private NetworkData.PrivateMessageInfoBack mPrivateBack;
	
	private ArrayList<Bitmap> mBitmapList = new ArrayList<Bitmap>();
	private NetworkData.SendMessageData mSendMsgData;
	private NetworkData.CommonBack mSendMsgBack;
	Drawable mDefaultAvatar;

	private Handler mHandler = new Handler(){
		public void handleMessage(Message msg){
			switch(msg.what){
			case MESSAGE_REFRESH_LIST:
				if("OK".equals(msg.obj)){
					initData();
					//Toast.makeText(KoalaChatActivity.this, "OK Received", Toast.LENGTH_LONG).show();
				}else{
					Toast.makeText(KoalaChatActivity.this, "获取系统消息出错:"+msg.obj, Toast.LENGTH_LONG).show();
				}
				break;
			case MESSAGE_SEND_PUBLIC_MSG:
				if("OK".equals(msg.obj)){
					
				}else{
					Toast.makeText(KoalaChatActivity.this, "发送系统消息出错:"+msg.obj, Toast.LENGTH_LONG).show();
				}
				break;
			case MESSAGE_SEND_PRIVATE_MSG:
				if("OK".equals(msg.obj)){
					
				}else{
					Toast.makeText(KoalaChatActivity.this, "回复消息出错:"+msg.obj, Toast.LENGTH_LONG).show();
				}
			}
		}
	};
	ImageView mAvatar;
	public static Bitmap mImageAvatar;
	public static String mStrName;
	TextView mName;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_chat_main);
		
		mAvatar = (ImageView)findViewById(R.id.chat_main_avatar);
		mName = (TextView)findViewById(R.id.chat_main_name);
		if(mImageAvatar != null){
			mAvatar.setImageBitmap(mImageAvatar);
		}
		mName.setText(mStrName);

		Intent intent = this.getIntent();
		type = intent.getIntExtra("type", 0);
		if(type == 0){
			try{
				msg_id = Integer.parseInt(intent.getStringExtra("msg_id"));
				is_exist = Integer.parseInt(intent.getStringExtra("is_exist"));
			}catch(Exception e){
				
			}
		}else{
			try{
				msg_id = Integer.parseInt(intent.getStringExtra("msg_id"));
				topic_id = Integer.parseInt(intent.getStringExtra("topic_id"));
				rec_id = Integer.parseInt(intent.getStringExtra("rec_id"));
			}catch(Exception e){
				
			}
		}
		initView();// 初始化view

		initActionBar();
		mDefaultAvatar = getResources().getDrawable(R.drawable.avatar1);
		//initData();// 初始化数据
		//mListView.setSelection(mAdapter.getCount() - 1);
		
		new RquestMsgThread().start();
	}

	public class RquestMsgThread extends Thread{
		public void run(){
			boolean success = false;
			Message msg = Message.obtain();
			msg.what = MESSAGE_REFRESH_LIST;
			if(type == 0){
				mPublicData = NetworkData.getInstance().getNewPublicMessageInfoData();
				mPublicData.is_exist = is_exist;
				mPublicData.msg_id = msg_id;
				mPublicBack = NetworkData.getInstance().getNewPublicMessageInfoBack();
				success = HTTPPost.RequestPublicMsgInfo(mPublicData, mPublicBack);
//				Bitmap bmp = BitmapFactory.decodeStream(new URL(NetworkData.URL_SERVER+mPublicBack.item.a)
//				.openStream());
//				Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp,
//				THUMB_SIZE, THUMB_SIZE, true);
			}else{
				mPrivateData = NetworkData.getInstance().getNewPrivateMessageInfoData();
				mPrivateData.msg_id = msg_id;
				mPrivateData.rec_id = rec_id;
				mPrivateData.topic_id = topic_id;
				mPrivateBack = NetworkData.getInstance().getNewPrivateMessageInfoBack();
				success = HTTPPost.RequestPrivateMsgInfo(mPrivateData, mPrivateBack);
				mBitmapList.clear();
				int size = mPrivateBack.items.size();
				for(int i=0;i<size;i++){
					try {
						Bitmap bmp = BitmapFactory.decodeStream(new URL(NetworkData.URL_SERVER+mPrivateBack.items.get(i).avatar).openStream());
						Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp,mDefaultAvatar.getIntrinsicWidth(), mDefaultAvatar.getIntrinsicHeight(), true);
						mBitmapList.add(thumbBmp);
						
					} catch (MalformedURLException e) {
						// TODO 自动生成的 catch 块
						e.printStackTrace();
						mBitmapList.add(null);
					} catch (IOException e) {
						// TODO 自动生成的 catch 块
						e.printStackTrace();
						mBitmapList.add(null);
					}
				}
			}
			
			if(success){
				msg.obj = (Object)("OK");
			}else{
				if(type == 0)
					msg.obj = mPublicBack.error;
				else
					msg.obj = mPrivateBack.error;
			}
			mHandler.sendMessage(msg);
		}
	}
	
	public class SendPublicMsgThread extends Thread{
		String mContent;
		public SendPublicMsgThread(String content){
			mContent = content;
		}
		
		public void run(){
			boolean success = false;
			Message msg = Message.obtain();
			if(mSendMsgData == null){
				mSendMsgData = NetworkData.getInstance().getNewSendMessageData();
			}
			if(mSendMsgBack == null){
				mSendMsgBack = NetworkData.getInstance().getCommonBack();
			}
			
			if(type == 0){
				msg.what = MESSAGE_SEND_PUBLIC_MSG;
				mSendMsgData.content = mContent;
				mSendMsgData.rec_id = Integer.parseInt(mPublicBack.item.topic_id);
				success = HTTPPost.SendMessage(mSendMsgData, mSendMsgBack,type);
			}else{
				msg.what = MESSAGE_SEND_PRIVATE_MSG;
				mSendMsgData.content = mContent;
				mSendMsgData.rec_id = Integer.parseInt(mPrivateBack.items.get(0).rec_id);
				mSendMsgData.topic_id = Integer.parseInt(mPrivateBack.items.get(0).topic_id);
				success = HTTPPost.SendMessage(mSendMsgData, mSendMsgBack,type);
			}
			
			if(success){
				msg.obj = (Object)("OK");
			}else{
				msg.obj = mSendMsgBack.error;
			}
			mHandler.sendMessage(msg);
		}
	}
	
	public void initActionBar(){
		ImageView imageView = new ImageView(this);
		imageView.setImageResource(R.drawable.back);
		setLeftView(imageView);
		setTitle("消息");
		this.setOnLeftClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO 自动生成的方法存根
				finish();
			}
			
		});
	}
	
	/**
	 * 初始化view
	 */
	public void initView() {
		mListView = (ListView) findViewById(R.id.listview);
		mBtnSend = (Button) findViewById(R.id.btn_send);
		mBtnSend.setOnClickListener(this);
		mEditTextContent = (EditText) findViewById(R.id.et_sendmessage);
	}

	private String[] msgArray = new String[] { "木板什么时候到？","今天木板已经送到，明天工人来安装，请耐心等待。" };

	private String[] dataArray = new String[] { "2015-03-19"};
	private final static int COUNT = 2;// 初始化数组总数

	/**
	 * 模拟加载消息历史，实际开发可以从数据库中读出
	 */
	public void initData() {
		if(type == 0){
			ChatMsgEntity entity = new ChatMsgEntity();
			entity.setDate(mPublicBack.item.send_time);
			entity.setMsgType(false);
			entity.setName("系统消息");
			entity.setMessage(mPublicBack.item.content);
			mDataArrays.add(entity);
		}else{
			for(int i=0;i<mPrivateBack.items.size();i++){
				ChatMsgEntity entity = new ChatMsgEntity();
				entity.setDate(mPrivateBack.items.get(i).send_time);
				if(mPrivateBack.items.get(i).send_name != null && mPrivateBack.items.get(i).send_name.equals(KoalaApplication.loginData.username)){
					entity.setMsgType(true);
					entity.setName("我");
				}else{
					entity.setMsgType(false);
					entity.setName(mPrivateBack.items.get(i).send_name);
				}
				entity.setMessage(mPrivateBack.items.get(i).content);
				entity.setAvatar(mBitmapList.get(i));
				mDataArrays.add(entity);
			}
		}
		//mAdapter.notifyDataSetChanged();
		/*for (int i = 0; i < COUNT; i++) {
			ChatMsgEntity entity = new ChatMsgEntity();
			entity.setDate(dataArray[0]);
			if (i % 2 == 0) {
				entity.setName("我");
				entity.setMsgType(true);// 自己
			} else {
				entity.setName("");
				entity.setMsgType(false);// 发送的消息
			}
			entity.setMessage(msgArray[i]);
			mDataArrays.add(entity);
		}*/
		mAdapter = new ChatMsgViewAdapter(this, mDataArrays);
		mListView.setAdapter(mAdapter);
		mListView.setSelection(mAdapter.getCount() - 1);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_send:// 发送按钮点击事件
			send();
			break;
		}
	}

	/**
	 * 发送消息
	 */
	private void send() {
		String contString = mEditTextContent.getText().toString();
		if (contString.length() > 0) {
			ChatMsgEntity entity = new ChatMsgEntity();
			entity.setName("我");
			entity.setDate(getDate());
			entity.setMessage(contString);
			entity.setMsgType(true);

			mDataArrays.add(entity);
			mAdapter.notifyDataSetChanged();// 通知ListView，数据已发生改变

			mEditTextContent.setText("");// 清空编辑框数据

			mListView.setSelection(mListView.getCount() - 1);// 发送一条消息时，ListView显示选择最后一项
			
			new SendPublicMsgThread(contString).start();
		}
	}

	/**
	 * 发送消息时，获取当前事件
	 * 
	 * @return 当前时间
	 */
	private String getDate() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		return format.format(new Date());
	}

}
