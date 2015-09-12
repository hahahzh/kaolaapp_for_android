package com.winwinapp.designer;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;

import com.winwinapp.koala.ActionBarActivity;
import com.winwinapp.koala.R;
import com.winwinapp.util.RefreshableListView;

public class DesignerProjectActivity extends ActionBarActivity {

	public static ArrayList<DesignerProjectItem> mArrayList = new ArrayList<DesignerProjectItem>();
	
	ListView mListView;
	RefreshableListView mRefreshListView;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_list_refresh);

		mRefreshListView = (RefreshableListView)findViewById(R.id.refreshable_list_view);
		mRefreshListView.setOnRefreshListener(new com.winwinapp.util.RefreshableListView.PullToRefreshListener() {
			@Override
			public void onRefresh() {
				try {
					Thread.sleep(3000);//refresh item
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				mRefreshListView.finishRefreshing();
			}
		}, 0);
		initActionBar();
		initListView();
	}
	
	public void initListView(){
		mListView = (ListView)this.findViewById(R.id.refresh_list_view);
//		DesignerProjectItem item = new DesignerProjectItem();
//		mArrayList.add(item);
//		item = new DesignerProjectItem();
//		mArrayList.add(item);
		mListView.setAdapter(new DesignerProjectAdapter(this,mArrayList));
	}
	
	public void initActionBar(){
		ImageView imageView = new ImageView(this);
		imageView.setImageResource(R.drawable.back);
		setLeftView(imageView);
		setTitle("项目经验");
		this.setOnLeftClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO 自动生成的方法存根
				finish();
			}
			
		});
	}
}
