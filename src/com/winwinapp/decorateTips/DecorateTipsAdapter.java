package com.winwinapp.decorateTips;

import java.util.ArrayList;

import com.winwinapp.koala.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class DecorateTipsAdapter extends BaseAdapter {

	ArrayList<TipsItems> mArrayList;
	LayoutInflater mInflater;
	Context mContext;
	public DecorateTipsAdapter(Context context,ArrayList<TipsItems> list){
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
		tipsViewHolder mHolder;
		if(convertView == null){
			convertView = mInflater.inflate(R.layout.layout_tips_items, null);
			mHolder = new tipsViewHolder();
			mHolder.mtitleText = (TextView)convertView.findViewById(R.id.tips_item_title);
			mHolder.mContentText = (TextView)convertView.findViewById(R.id.tips_item_content);
			mHolder.mDateText = (TextView)convertView.findViewById(R.id.tips_item_date);
			mHolder.mPreviewImage = (ImageView)convertView.findViewById(R.id.tips_item_preview);
			mHolder.mViewedText = (TextView)convertView.findViewById(R.id.tips_item_viewed);
			convertView.setTag(mHolder);
		}
		
		mHolder = (tipsViewHolder) convertView.getTag();
		
		mHolder.mtitleText.setText(mArrayList.get(position).mTitle);
		mHolder.mContentText.setText(mArrayList.get(position).content);
		mHolder.mDateText.setText(mArrayList.get(position).mDate);
		mHolder.mViewedText.setText(mArrayList.get(position).mViewed);
		mHolder.mPreviewImage.setImageDrawable(mArrayList.get(position).mImage);
		
		return convertView;
	}
	
	public class tipsViewHolder{
		TextView mtitleText;
		TextView mDateText;
		TextView mViewedText;
		ImageView mPreviewImage;
		TextView mContentText;
	}

}
