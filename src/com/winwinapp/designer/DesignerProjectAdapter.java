package com.winwinapp.designer;

import java.util.ArrayList;

import com.winwinapp.decorateTips.TipsItems;
import com.winwinapp.koala.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class DesignerProjectAdapter extends BaseAdapter {

	
	ArrayList<DesignerProjectItem> mArrayList;
	LayoutInflater mInflater;
	Context mContext;
	public DesignerProjectAdapter(Context context,ArrayList<DesignerProjectItem> list){
		mContext = context;
		mArrayList = list;
		mInflater = LayoutInflater.from(mContext);
	}
	
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
	public View getView(int position, View convertView, ViewGroup arg2) {
		// TODO 自动生成的方法存根
		convertView = mInflater.inflate(R.layout.layout_project_experience_item, null);
		
		return convertView;
	}

}
