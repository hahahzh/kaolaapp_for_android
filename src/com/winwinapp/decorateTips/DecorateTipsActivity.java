package com.winwinapp.decorateTips;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TextView;

import com.winwinapp.decorateTips.DecorateTipsAdapter.tipsViewHolder;
import com.winwinapp.koala.R;
import com.winwinapp.network.HTTPPost;
import com.winwinapp.network.NetworkData;
import com.winwinapp.util.ActionBarView;
import com.winwinapp.util.RefreshableListView;

@SuppressWarnings("deprecation")
public class DecorateTipsActivity extends Activity  implements OnTabChangeListener{

	private TabHost mTabHost;
	private ActionBarView mActionBar;
	RefreshableListView mRefreshListView;
	private ListView mListView;
	private ArrayList<TipsItems> mArrayList = new ArrayList<TipsItems>();
	private ArrayList<TipsItems> mArrayProject = new ArrayList<TipsItems>();
	private ArrayList<TipsItems> mArraySoft = new ArrayList<TipsItems>();
	private ArrayList<TipsItems> mArrayDesign = new ArrayList<TipsItems>();
	private ArrayList<TipsItems> mArrayMateria = new ArrayList<TipsItems>();
	private ArrayList<TipsItems> mArrayWindWater = new ArrayList<TipsItems>();
	
	NetworkData.DecorateTipsData mData = NetworkData.getInstance().getDecorateTipsData();
	NetworkData.DecorateTipsBack mBack = NetworkData.getInstance().getDecorateTipsBack();
	
	DecorateTipsAdapter mAdapter;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.layout_decorate_tip_main);
		
		mRefreshListView = (RefreshableListView)findViewById(R.id.decorate_tips_refreshable_list_view);
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
		}, 1);
		
		initActionBar();
		
		initList();
		initTabHost();
		
	}

	public void initActionBar(){
		ActionBar actionBar = this.getActionBar();
		if(actionBar != null){
			actionBar.setDisplayUseLogoEnabled(false);
			actionBar.setHomeButtonEnabled(false);
			actionBar.setDisplayShowTitleEnabled(false);
			actionBar.setDisplayShowHomeEnabled(false);
			actionBar.setDisplayShowCustomEnabled(true);
			
			mActionBar = new ActionBarView(this);
			ImageView imageView = new ImageView(this);
			imageView.setImageResource(R.drawable.back);
			mActionBar.setLeftView(imageView);
			
			mActionBar.setOnLeftClickListener(new OnClickListener(){

				@Override
				public void onClick(View arg0) {
					// TODO �Զ����ɵķ������
					finish();
				}
				
			});
			
			mActionBar.setTitle("װ�ޱ���");
			
			
			actionBar.setCustomView(mActionBar,new ActionBar.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
		}
		
	}
	
	public void initTabHost(){
		mTabHost = (TabHost) this.findViewById(R.id.tips_tabhost);
		mTabHost.setup();
		LayoutInflater lf = LayoutInflater.from(this);
		View view = lf.inflate(R.layout.layout_tips_tab, null);
		TextView text = (TextView) view.findViewById(R.id.tips_tab_title);
		text.setText("ȫ��");
		mTabHost.addTab(mTabHost.newTabSpec("ȫ��").setIndicator(view).setContent(R.id.decorate_tips_refreshable_list_view));
		
		view = lf.inflate(R.layout.layout_tips_tab, null);
		text = (TextView) view.findViewById(R.id.tips_tab_title);
		text.setText("ʩ��");
		mTabHost.addTab(mTabHost.newTabSpec("ʩ��").setIndicator(view).setContent(R.id.decorate_tips_refreshable_list_view));
		
		view = lf.inflate(R.layout.layout_tips_tab, null);
		text = (TextView) view.findViewById(R.id.tips_tab_title);
		text.setText("��װ");
		mTabHost.addTab(mTabHost.newTabSpec("��װ").setIndicator(view).setContent(R.id.decorate_tips_refreshable_list_view));
		
		view = lf.inflate(R.layout.layout_tips_tab, null);
		text = (TextView) view.findViewById(R.id.tips_tab_title);
		text.setText("���");
		mTabHost.addTab(mTabHost.newTabSpec("���").setIndicator(view).setContent(R.id.decorate_tips_refreshable_list_view));
		
		view = lf.inflate(R.layout.layout_tips_tab, null);
		text = (TextView) view.findViewById(R.id.tips_tab_title);
		text.setText("����");
		mTabHost.addTab(mTabHost.newTabSpec("����").setIndicator(view).setContent(R.id.decorate_tips_refreshable_list_view));
		
		view = lf.inflate(R.layout.layout_tips_tab, null);
		text = (TextView) view.findViewById(R.id.tips_tab_title);
		text.setText("��ˮ");
		mTabHost.addTab(mTabHost.newTabSpec("��ˮ").setIndicator(view).setContent(R.id.decorate_tips_refreshable_list_view));
		
		refreshTab();
		mTabHost.setOnTabChangedListener(this);
		
		onTabChanged("ȫ��");
	}
	
	public void refreshTab(){
		int current = mTabHost.getCurrentTab();
		for (int i = 0; i < mTabHost.getTabWidget().getChildCount(); i++) {
            TextView tv=(TextView)mTabHost.getTabWidget().getChildAt(i).findViewById(R.id.tips_tab_title);
            ImageView img = (ImageView)mTabHost.getTabWidget().getChildAt(i).findViewById(R.id.tips_tab_img);
            
            if(i == current){
            	tv.setTextColor(getResources().getColor(R.color.green));//�����������ɫ
            	img.setBackgroundColor(getResources().getColor(R.color.green));
            	//mTabHost.getTabWidget().getChildAt(i).setBackgroundColor(getResources().getColor(R.color.yellow));
            }else{
            	tv.setTextColor(Color.GRAY);//�����������ɫ��
            	img.setBackgroundColor(getResources().getColor(R.color.graylight));
            }
                //��ȡtabsͼƬ��
        }
		
		switch(current){
		case 0:
			mAdapter.setArrayList(mArrayList);
			mAdapter.notifyDataSetChanged();
			break;
		case 1:
			mAdapter.setArrayList(mArrayProject);
			mAdapter.notifyDataSetChanged();
			break;
		case 2:
			mAdapter.setArrayList(mArraySoft);
			mAdapter.notifyDataSetChanged();
			break;
		case 3:
			mAdapter.setArrayList(mArrayDesign);
			mAdapter.notifyDataSetChanged();
			break;
		case 4:
			mAdapter.setArrayList(mArrayMateria);
			mAdapter.notifyDataSetChanged();
			break;
		case 5:
			mAdapter.setArrayList(mArrayWindWater);
			mAdapter.notifyDataSetChanged();
			break;
		}
	}
	
	public void onResume(){
		super.onResume();
		mTabHost.setCurrentTab(1);
		mTabHost.setCurrentTab(0);
	}
	
	public void initList(){	
		for(int i=0;i<5;i++){
			TipsItems item = new TipsItems();
			item.content = "װ���Ǽ����鷳���£��ܶ�����װ�޵�ʱ���Ǽ����鷳���£��ܶ�����װ�޵�ʱ���Ǽ����鷳������";
			item.mDate = "2015-03-19";
			item.mViewed = "166";
			item.mTitle = "����װ��װ���б��˿���Ҳ��֪����10����";
			item.type = i+1;
			item.mImage = this.getResources().getDrawable(R.drawable.tips_image_preview);
			mArrayList.add(item);
		}
		
		for(int i=0;i<mArrayList.size();i++){
			TipsItems item = mArrayList.get(i);
			switch(item.type){
			case 1:
				item.mTitle = "��ʩ����װ���б��˿���Ҳ��֪����10����";
				mArrayProject.add(item);
				break;
			case 2:
				item.mTitle = "����װ��װ���б��˿���Ҳ��֪����10����";
				mArraySoft.add(item);
				break;
			case 3:
				item.mTitle = "����ơ�װ���б��˿���Ҳ��֪����10����";
				mArrayDesign.add(item);
				break;
			case 4:
				item.mTitle = "�����ϡ�װ���б��˿���Ҳ��֪����10����";
				mArrayMateria.add(item);
				break;
			case 5:
				item.mTitle = "����ˮ��װ���б��˿���Ҳ��֪����10����";
				mArrayWindWater.add(item);
				break;
			}
		}
		
		mListView = (ListView) this.findViewById(R.id.decorate_tips_list);
		mAdapter = new DecorateTipsAdapter(this,mArrayList);
		mListView.setAdapter(mAdapter);
		mListView.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO �Զ����ɵķ������
				Intent intent = new Intent(DecorateTipsActivity.this,DecorateTipsDetailActivity.class);
				intent.putExtra("type", ((tipsViewHolder)arg1.getTag()).type);
				startActivity(intent);
			}
			
		});
	}

	@Override
	public void onTabChanged(String arg0) {
		// TODO �Զ����ɵķ������
		refreshTab();
		//mListView = (ListView) mTabHost.getCurrentView().findViewById(R.id.decorate_tips_list);
		//mListView.setAdapter(new DecorateTipsAdapter(this,mArrayList));
	}
}
