package com.winwinapp.decorateTips;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
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

import com.winwinapp.koala.R;
import com.winwinapp.util.ActionBarView;

@SuppressWarnings("deprecation")
public class DecorateTipsActivity extends Activity  implements OnTabChangeListener{

	private TabHost mTabHost;
	private ActionBarView mActionBar;
	private ListView mListView;
	private ArrayList<TipsItems> mArrayList = new ArrayList<TipsItems>();
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.layout_decorate_tip_main);
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
		mTabHost.addTab(mTabHost.newTabSpec("ȫ��").setIndicator("ȫ��").setContent(R.id.decorate_tips_list));
		mTabHost.addTab(mTabHost.newTabSpec("ʩ��").setIndicator("ʩ��").setContent(R.id.decorate_tips_list));
		mTabHost.addTab(mTabHost.newTabSpec("��װ").setIndicator("��װ").setContent(R.id.decorate_tips_list));
		mTabHost.addTab(mTabHost.newTabSpec("���").setIndicator("���").setContent(R.id.decorate_tips_list));
		mTabHost.addTab(mTabHost.newTabSpec("����").setIndicator("����").setContent(R.id.decorate_tips_list));
		mTabHost.addTab(mTabHost.newTabSpec("��ˮ").setIndicator("��ˮ").setContent(R.id.decorate_tips_list));
		mTabHost.setOnTabChangedListener(this);
		
		onTabChanged("ȫ��");
	}
	
	public void onResume(){
		super.onResume();
		mTabHost.setCurrentTab(1);
		mTabHost.setCurrentTab(0);
	}
	
	public void initList(){
		for(int i=0;i<3;i++){
			TipsItems item = new TipsItems();
			item.content = "װ���Ǽ����鷳���£��ܶ�����װ�޵�ʱ���Ǽ����鷳���£��ܶ�����װ�޵�ʱ���Ǽ����鷳������";
			item.mDate = "2015-03-19";
			item.mViewed = "166";
			item.mTitle = "����װ��װ���б��˿���Ҳ��֪����10����";
			item.mImage = this.getResources().getDrawable(R.drawable.tips_image_preview);
			mArrayList.add(item);
		}
		mListView = (ListView) this.findViewById(R.id.decorate_tips_list);
		mListView.setAdapter(new DecorateTipsAdapter(this,mArrayList));
		mListView.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO �Զ����ɵķ������
				Intent intent = new Intent(DecorateTipsActivity.this,DecorateTipsDetailActivity.class);
				startActivity(intent);
			}
			
		});
	}

	@Override
	public void onTabChanged(String arg0) {
		// TODO �Զ����ɵķ������
		mListView = (ListView) mTabHost.getCurrentView().findViewById(R.id.decorate_tips_list);
		mListView.setAdapter(new DecorateTipsAdapter(this,mArrayList));
	}
}
