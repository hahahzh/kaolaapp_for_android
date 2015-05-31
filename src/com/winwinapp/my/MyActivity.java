package com.winwinapp.my;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.winwinapp.koala.R;

public class MyActivity extends Fragment implements OnClickListener{

	Activity mActivity;
	TextView mMyProjectTxt;
	ImageView mMyProjectImg;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		View view = inflater.inflate(R.layout.layout_my, null);
		
		mActivity = this.getActivity();
		mMyProjectTxt = (TextView)view.findViewById(R.id.my_project_txt);
		mMyProjectImg = (ImageView)view.findViewById(R.id.my_project_img);
		mMyProjectTxt.setOnClickListener(this);
		mMyProjectImg.setOnClickListener(this);
		return view;
	}

	@Override
	public void onClick(View view) {
		// TODO 自动生成的方法存根
		Intent intent;
		switch(view.getId()){
		case R.id.my_project_txt:
		case R.id.my_project_img:
			intent = new Intent(mActivity,MyProjectActivity.class);
			startActivity(intent);
			break;
		}
	}
	
}
