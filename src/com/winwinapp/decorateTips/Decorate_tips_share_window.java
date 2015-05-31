package com.winwinapp.decorateTips;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.winwinapp.koala.R;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.SimpleAdapter;

public class Decorate_tips_share_window extends Activity {

	GridView mGridView;
	String mTitleList[] = new String[]{"Œ¢–≈","≈Û”—»¶","Œ¢≤©"};
	int mImageId[] = new int[]{R.drawable.share_wechat,R.drawable.share_friends,R.drawable.share_weibo};
	ArrayList<HashMap<String,Object>> imagelist = new ArrayList<HashMap<String,Object>>();
	
	 @Override  
	    protected void onCreate(Bundle savedInstanceState) {  
	        super.onCreate(savedInstanceState);  
	        setContentView(R.layout.layout_popup_share_window);
	        
	        mGridView = (GridView) this.findViewById(R.id.tips_detail_share_grid);
	        
	        HashMap<String,Object> map1 = new HashMap<String,Object>();
	        map1.put("image", R.drawable.share_wechat);
	        map1.put("title", mTitleList[0]);
	        imagelist.add(map1);
	        HashMap<String,Object> map2 = new HashMap<String,Object>();
	        map2.put("image", R.drawable.share_friends);
	        map2.put("title", mTitleList[1]);
	        imagelist.add(map2);
	        HashMap<String,Object> map3 = new HashMap<String,Object>();
	        map3.put("image", R.drawable.share_weibo);
	        map3.put("title", mTitleList[2]);
	        imagelist.add(map3);
	        SimpleAdapter simpleAdapter = new SimpleAdapter(this, imagelist, 
	        		R.layout.layout_decorate_tip_share_item, 
	        		new String[] {"image","title"}, new int[]{R.id.tips_share_item_image,R.id.tips_shared_item_title});
	        mGridView.setAdapter(simpleAdapter);
	 }
	
}
