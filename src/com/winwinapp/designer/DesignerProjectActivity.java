package com.winwinapp.designer;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;

import com.winwinapp.koala.ActionBarActivity;
import com.winwinapp.koala.R;

public class DesignerProjectActivity extends ActionBarActivity {

	ArrayList<DesignerProjectItem> mArrayList = new ArrayList<DesignerProjectItem>();
	ListView mListView;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_designer_project_detail);

		initActionBar();
		initListView();
	}
	
	public void initListView(){
		mListView = (ListView)this.findViewById(R.id.designer_project_detail_list);
		DesignerProjectItem item = new DesignerProjectItem();
		mArrayList.add(item);
		item = new DesignerProjectItem();
		mArrayList.add(item);
		mListView.setAdapter(new DesignerProjectAdapter(this,mArrayList));
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
	}
}
