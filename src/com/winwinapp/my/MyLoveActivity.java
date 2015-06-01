package com.winwinapp.my;

import java.util.ArrayList;

import android.content.Context;
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

import com.winwinapp.koala.ActionBarActivity;
import com.winwinapp.koala.R;

public class MyLoveActivity extends ActionBarActivity {

	private static final int INDEX_BUTTON_MESSAGE = 1;
	private static final int INDEX_BUTTON_DELETE = 2;
	private static final int INDEX_BUTTON_OTHER = 3;
	ArrayList<MyLoveItem> mArrayList = new ArrayList<MyLoveItem>();
	ListView mListView;
	
	private Handler mHandler = new Handler(){
		public void handleMessage(Message msg){
			switch(msg.what){
			case INDEX_BUTTON_MESSAGE:
				//int position = msg.arg1;
				//Intent intent = new Intent(MyProjectActivity.this,MyProjectCalendarActivity.class);
				//startActivity(intent);
				break;
			case INDEX_BUTTON_DELETE:
				break;
			case INDEX_BUTTON_OTHER:
				break;
			}
		}
	};
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_list);

		initActionBar();
		initListView();
	}
	
	public void initListView(){
		mListView = (ListView)this.findViewById(R.id.list_common);
		MyLoveItem item = new MyLoveItem();
		item.mIndex = 1;
		item.mSpecial = "9�꾭��";
		mArrayList.add(item);
		item = new MyLoveItem();
		item.mIndex = 2;
		item.mSpecial = "��100/m2";
		mArrayList.add(item);
		
		item = new MyLoveItem();
		item.mIndex = 2;
		item.mSpecial = "9�꾭��";
		mArrayList.add(item);
		mListView.setAdapter(new MyLoveAdapter(this));
	}
	
	public void initActionBar(){
		ImageView imageView = new ImageView(this);
		imageView.setImageResource(R.drawable.back);
		setLeftView(imageView);
		setTitle("�ҵ��ղ�");
		this.setOnLeftClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO �Զ����ɵķ������
				finish();
			}
			
		});
	}
	
	public class MyLoveItem{
		int mIndex;
		String mSpecial;
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
			// TODO �Զ����ɵķ������
			return mArrayList.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO �Զ����ɵķ������
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			// TODO �Զ����ɵķ������
			return 0;
		}

		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2) {
			// TODO �Զ����ɵķ������
			ImageView message;
			ImageView delete;
			TextView mSpecial;
			arg1 = mInflater.inflate(R.layout.layout_my_love_item, null);
			message = (ImageView)arg1.findViewById(R.id.my_love_item_message);
			message.setOnClickListener(new OnItemChildClickListener(INDEX_BUTTON_MESSAGE,arg0));
			
			delete = (ImageView)arg1.findViewById(R.id.my_love_item_delete);
			delete.setOnClickListener(new OnItemChildClickListener(INDEX_BUTTON_DELETE,arg0));
			
			mSpecial = (TextView)arg1.findViewById(R.id.my_love_item_special);
			mSpecial.setText(mArrayList.get(arg0).mSpecial);
			
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
			// TODO �Զ����ɵķ������
			Message msg = Message.obtain();
			msg.what = mClickIndex;
			msg.arg1 = mPosition;
			mHandler.sendMessage(msg);
		}
		
	}
}
