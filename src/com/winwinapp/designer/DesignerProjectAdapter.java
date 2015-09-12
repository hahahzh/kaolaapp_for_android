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
		return mArrayList==null? 0:mArrayList.size();
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
		DesignerProjectItem item = mArrayList.get(position);
		convertView = mInflater.inflate(R.layout.layout_project_experience_item, null);
		TextView bioName = (TextView) convertView.findViewById(R.id.designer_project_experience_item_area_name);
		TextView area = (TextView) convertView.findViewById(R.id.designer_project_experience_item_area);
		TextView date = (TextView) convertView.findViewById(R.id.designer_project_experience_item_date);
		TextView skill = (TextView) convertView.findViewById(R.id.designer_project_skill);
		TextView service = (TextView) convertView.findViewById(R.id.designer_project_service);
		TextView name = (TextView) convertView.findViewById(R.id.designer_project_comment_name);
		TextView comment = (TextView) convertView.findViewById(R.id.designer_project_comment_content);
		bioName.setText(item.mAreaName);
		area.setText(item.mArea);
		date.setText(item.date);
		skill.setText("专业："+item.mSkills);
		service.setText("服务："+item.mService);
		name.setText(item.mComment.mCommenterName);
		comment.setText(item.mComment.mComments);
		
		return convertView;
	}

}
