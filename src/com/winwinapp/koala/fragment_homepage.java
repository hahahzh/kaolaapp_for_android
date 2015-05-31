package com.winwinapp.koala;

import java.util.ArrayList;
import java.util.HashMap;

import com.winwinapp.bids.BidsDistinctActivity;
import com.winwinapp.decorate.DecoratePriceActivity;
import com.winwinapp.decorate.PreEvaluateActivity;
import com.winwinapp.decorateTips.DecorateTipsActivity;
import com.winwinapp.designer.ContactDesignerActivity;
import com.winwinapp.selectcity.SelectCityActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;

public class fragment_homepage extends Fragment implements OnItemClickListener{

	public static final int TYPE_DESIGNER = 1;
	public static final int TYPE_LABOR = 2;
	public static final int TYPE_SUPRIOR = 3;
	
	private final String TAG = fragment_homepage.class.getName();
	ViewPager mViewPager;
    ArrayList<View> mViewContainer = new ArrayList<View>();
    myPageAdapter mPageAdapter;
    private int mOffset = 0;
    private int mPageWidth = 0;
    private PageIndicator mPageIndicator;
    private GridView mGridView;
    private Activity mActivity;
    
    private int mImageResource[] = {R.drawable.image_preview,R.drawable.image_preview,R.drawable.image_preview,R.drawable.image_preview};
    private String mGridViewTitle[] = {"装修预算","我要竞标","装修宝典","联系设计师","寻找好工长","监理来帮忙"};
    private int mGridImageResourceId[] = {R.drawable.calculator,R.drawable.edit,R.drawable.literature,R.drawable.design,R.drawable.roller,R.drawable.supervisor};
    
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		View view = inflater.inflate(R.layout.layout_mainpage, null);
		
		initView(inflater,view);
		mActivity = this.getActivity();
		return view;
	}
    
	private void initView(LayoutInflater inflater,View view){
		mViewPager = (ViewPager)view.findViewById(R.id.viewpager);
        mViewPager.setBackgroundColor(0xFF0000);
        
        int reqWidth = getResources().getDisplayMetrics().widthPixels;
        int reqHeight = (int) getResources().getDimension(R.dimen.hot_pic_height);
        Log.i(TAG,"reW="+reqWidth + ",reH = "+reqHeight);
        
        mViewContainer.clear();
        for(int i=0;i<mImageResource.length;i++){
        	View view_item = inflater.inflate(R.layout.layout_page_item, null);
            ImageView iv = (ImageView)view_item.findViewById(R.id.imageView1);
            //iv.setImageBitmap(Utils.decodeSampleBitmapFromResource(getResources(),mImageResource[i] , reqWidth));
            iv.setImageResource(mImageResource[i]);
            mViewContainer.add(view_item);
        }
        
        mPageAdapter = new myPageAdapter();
        mViewPager.setAdapter(mPageAdapter);
        
        mViewPager.setCurrentItem(0);
        mViewPager.setOnPageChangeListener(new myOnPageChangeListner());
        
        DisplayMetrics dm = new DisplayMetrics();
        mOffset = 0;
        mPageWidth = dm.widthPixels/3;
        
        mPageIndicator = (PageIndicator)view.findViewById(R.id.page_indicator);
        mPageIndicator.setPages(mViewContainer.size());
        mPageIndicator.setCurrentPage(0);
        
        ArrayList<HashMap<String,Object>> listImageItem = new ArrayList<HashMap<String, Object>>();
        mGridView = (GridView)view.findViewById(R.id.grid_view);
        for(int i=0;i<6;i++){
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("ItemImage", mGridImageResourceId[i]);
            map.put("ItemText", mGridViewTitle[i]);
            listImageItem.add(map);
        }
        SimpleAdapter saImageItems = new SimpleAdapter(getActivity(),listImageItem,R.layout.layout_grid_item,
                    new String[]{"ItemImage","ItemText"}, new int[]{R.id.item_image,R.id.item_text});
        mGridView.setAdapter(saImageItems);
        mGridView.setOnItemClickListener(this);
	}
    /*protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        
        //setContentView(R.layout.layout_viewpager_test);
        
        //Bitmap b1 = BitmapFactory.decodeResource(getResources(), R.drawable.pic1);
        //Bitmap b2 = BitmapFactory.decodeResource(getResources(), R.drawable.pic2);
        //Bitmap b3 = BitmapFactory.decodeResource(getResources(), R.drawable.pic3)
        
    }*/
    
    public class myPageAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            //Log.i("ViewPager","size="+mViewContainer.size());
            return mViewContainer.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            // TODO Auto-generated method stub
            return arg0 == arg1;
        }
        
        public void destroyItem(View arg0, int arg1, Object arg2){
            ((ViewPager) arg0).removeView(mViewContainer.get(arg1));
        }
        
        public void finishUpdate(View arg0){
            
        }
        
        public Object instantiateItem(View arg0, int arg1){
            ((ViewPager)arg0).addView(mViewContainer.get(arg1),0);
            return mViewContainer.get(arg1);
        }
        
        public void restoreState(Parcelable arg0, ClassLoader arg1){
            
        }
        
        public Parcelable saveState(){
            return null;
        }
        
        public void startUpdate(View arg0){
            
        }
        
    }
    
    public class myOnPageChangeListner implements OnPageChangeListener{

        int one = mOffset*2 + mPageWidth;
        int two = one*2;
        @Override
        public void onPageScrollStateChanged(int arg0) {
            // TODO Auto-generated method stub
            
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
            // TODO Auto-generated method stub
            
        }

        @Override
        public void onPageSelected(int arg0) {
            // TODO Auto-generated method stub
            mPageIndicator.setCurrentPage(arg0);
        }
        
    }

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO 自动生成的方法存根
		Intent intent;
		switch(arg2){
		case 0:
			intent = new Intent(mActivity,PreEvaluateActivity.class);
			startActivity(intent);
			break;
		case 1:
			intent = new Intent(mActivity,BidsDistinctActivity.class);
			startActivity(intent);
			break;
		case 2:
			intent = new Intent(mActivity,DecorateTipsActivity.class);
			startActivity(intent);
			break;
		case 3:
			intent = new Intent(mActivity,ContactDesignerActivity.class);
			intent.putExtra("type", TYPE_DESIGNER);
			startActivity(intent);
			break;
		case 4:
			intent = new Intent(mActivity,ContactDesignerActivity.class);
			intent.putExtra("type", TYPE_LABOR);
			startActivity(intent);
			break;
		case 5:
			intent = new Intent(mActivity,ContactDesignerActivity.class);
			intent.putExtra("type", TYPE_SUPRIOR);
			startActivity(intent);
			break;
		}
	}
}
