package com.winwinapp.my;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.winwinapp.koala.R;

public class MyActivity extends Fragment {

	Activity mActivity;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		View view = inflater.inflate(R.layout.layout_my, null);
		
		mActivity = this.getActivity();
		return view;
	}
	
}
