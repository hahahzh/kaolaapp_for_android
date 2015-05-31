package com.winwinapp.bids;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.winwinapp.koala.ActionBarActivity;
import com.winwinapp.koala.R;

public class BidsListActivity extends ActionBarActivity {

	ListView mListView;
	ArrayList<String> mArrayList = new ArrayList<String>();
	LayoutInflater mInflater;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_list);

		initActionBar();
		
		mInflater = LayoutInflater.from(this);
		mListView = (ListView)findViewById(R.id.list_common);
		String str = new String();
		mArrayList.add(str);
		str = new String();
		mArrayList.add(str);
		str = new String();
		mArrayList.add(str);
		mListView.setAdapter(new MyAdapter());
		
	}
	
	public void initActionBar(){
		ImageView imageView = new ImageView(this);
		imageView.setImageResource(R.drawable.back);
		setLeftView(imageView);
		setTitle("我要竞标");
		this.setOnLeftClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO 自动生成的方法存根
				finish();
			}
			
		});
	}
	
	public class MyAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO 自动生成的方法存根
			return mArrayList.size();
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
			arg1 = mInflater.inflate(R.layout.layout_bids_item, null);
			return arg1;
		}
		
	}

}
