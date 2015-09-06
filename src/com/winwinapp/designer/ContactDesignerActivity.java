package com.winwinapp.designer;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;
import com.winwinapp.koala.ActionBarActivity;
import com.winwinapp.koala.R;
import com.winwinapp.koala.fragment_homepage;
import com.winwinapp.network.HTTPPost;
import com.winwinapp.network.NetworkData;

public class ContactDesignerActivity extends ActionBarActivity implements TabHost.OnTabChangeListener{

	TabHost mTabHost;
	ListView mListView;
	ArrayList<DesignerListItem> mArrayList = new ArrayList<DesignerListItem>();
	LayoutInflater mInflater;
	DesignerListAdapter mAdapter = new DesignerListAdapter();
	String mTitle = null;
	int mType;
	NetworkData.FindMemberData mData = NetworkData.getInstance().getNewFindMemberData();
	NetworkData.FindMemberBack mBack0 = NetworkData.getInstance().getNewFindMemberBack();
	NetworkData.FindMemberBack mBack1 = NetworkData.getInstance().getNewFindMemberBack();
	NetworkData.FindMemberBack mBack2 = NetworkData.getInstance().getNewFindMemberBack();
	NetworkData.FindMemberBack mBack3 = NetworkData.getInstance().getNewFindMemberBack();
	ArrayList<NetworkData.FindMemberItem> mList0 = new ArrayList<NetworkData.FindMemberItem>();
	ArrayList<NetworkData.FindMemberItem> mList1 = new ArrayList<NetworkData.FindMemberItem>();
	ArrayList<NetworkData.FindMemberItem> mList2 = new ArrayList<NetworkData.FindMemberItem>();
	ArrayList<NetworkData.FindMemberItem> mList3 = new ArrayList<NetworkData.FindMemberItem>();
	Drawable mDefaultAvatar;
	TextView mNoMoreTxt;
	
	private Handler mHandler = new Handler(){
		public void handleMessage(Message msg){
			//Intent intent;
			switch(msg.what){
			case 1:
				String error = (String)msg.obj;
				if("OK".equals(error)){
						mAdapter.notifyDataSetChanged();
				}else{
					Toast.makeText(ContactDesignerActivity.this, "��ȡ�б�ʧ�ܣ�"+error, Toast.LENGTH_LONG).show();
				}
				//mNoMoreTxt.setVisibility(View.VISIBLE);
				break;
			}
		}
	};
	
	public class getListThread extends Thread{
		String mSort;
		int mPage;
		int mLimit;
		int mTab = 0;
		public getListThread(String sort,int page,int limit,int tab){
			mSort = sort;
			mPage = page;
			mLimit = limit;
			mTab = tab;
		}
		public void run(){
			boolean success = false;
			mData.type = mType;
			mData.work_num = 0;
			mData.sort = mSort;
			mData.page = 0;
			mData.limit = mLimit;
			mData.keyword = "";
			NetworkData.FindMemberBack mBack;
			switch(mTab){
			case 0:
				mBack = mBack0;
				break;
			case 1:
				mBack = mBack1;
				break;
			case 2:
				mBack = mBack2;
				break;
			case 3:
				mBack = mBack3;
				break;
			default:
				mBack = mBack0;
				break;
			}
			mBack.memberInfo.clear();
			success = HTTPPost.FindMember(mData, mBack);
			Message msg = Message.obtain();
			msg.what = 1;
			if(success){
				msg.obj = "OK";
				if(mBack.memberInfo.size() > 0){
					int size = mBack.memberInfo.size();
					for(int i=0;i<size;i++){
						try {
							NetworkData.FindMemberItem item = mBack.memberInfo.get(i);
							Bitmap bmp;
						
							bmp = BitmapFactory.decodeStream(new URL(NetworkData.URL_SERVER+item.avatar).openStream());
							Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp,mDefaultAvatar.getIntrinsicWidth(), mDefaultAvatar.getIntrinsicHeight(), true);
							item.imgAvatar = thumbBmp;
						} catch (MalformedURLException e) {
							// TODO �Զ����ɵ� catch ��
							e.printStackTrace();
						} catch (IOException e) {
							// TODO �Զ����ɵ� catch ��
							e.printStackTrace();
						}catch(Exception e){
							
						}
					}
					switch(mTab){
					case 0:
						mList0.clear();
						mList0.addAll(mBack.memberInfo);
						break;
					case 1:
						mList1.clear();
						mList1.addAll(mBack.memberInfo);
						break;
					case 2:
						mList2.clear();
						mList2.addAll(mBack.memberInfo);
						break;
					case 3:
						mList3.clear();
						mList3.addAll(mBack.memberInfo);
						break;
					}
				}
			}else{
				msg.obj = mBack.error;
			}
			mHandler.sendMessage(msg);
		}
	}
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_contact_designer);

		mNoMoreTxt = (TextView)findViewById(R.id.contact_designer_nomore);
		mType = getIntent().getIntExtra("type", fragment_homepage.TYPE_DESIGNER);
		getTitleFromIntent(this.getIntent());
		initActionBar();
		initTabHost(this.getIntent());
		initListView(this.getIntent());
		
		mNoMoreTxt.setVisibility(View.GONE);
		mDefaultAvatar = getResources().getDrawable(R.drawable.avatar1);
	}
	
	public void getTitleFromIntent(Intent intent){
		mType = intent.getIntExtra("type", fragment_homepage.TYPE_DESIGNER);
		switch(mType){
		case fragment_homepage.TYPE_DESIGNER:
			mTitle = "��ϵ���ʦ";
			break;
		case fragment_homepage.TYPE_LABOR:
			mTitle = "Ѱ�Һù���";
			break;
		case fragment_homepage.TYPE_SUPRIOR:
			mTitle = "��������æ";
			break;
		}
		setTitle(mTitle);
	}
	
	public void initTabHost(Intent intent){
		mTabHost = (TabHost) this.findViewById(R.id.contact_designer_tabhost);
		mTabHost.setup();
		
		LayoutInflater lf = LayoutInflater.from(this);
		View view = lf.inflate(R.layout.layout_contact_tab_item, null);
		TextView text = (TextView) view.findViewById(R.id.contact_tab_title);
		text.setText("רҵˮƽ");
		mTabHost.addTab(mTabHost.newTabSpec("רҵˮƽ").setIndicator(view).setContent(R.id.contact_designer_list));
		view = lf.inflate(R.layout.layout_contact_tab_item, null);
		text = (TextView) view.findViewById(R.id.contact_tab_title);
		text.setText("����̬��");
		mTabHost.addTab(mTabHost.newTabSpec("����̬��").setIndicator(view).setContent(R.id.contact_designer_list));
		view = lf.inflate(R.layout.layout_contact_tab_item, null);
		text = (TextView) view.findViewById(R.id.contact_tab_title);
		text.setText("������");
		mTabHost.addTab(mTabHost.newTabSpec("������").setIndicator(view).setContent(R.id.contact_designer_list));
		
		
		switch(mType){
		case fragment_homepage.TYPE_DESIGNER:
			view = lf.inflate(R.layout.layout_contact_tab_item, null);
			text = (TextView) view.findViewById(R.id.contact_tab_title);
			text.setText("�շѱ�׼");
			mTabHost.addTab(mTabHost.newTabSpec("�շѱ�׼").setIndicator(view).setContent(R.id.contact_designer_list));
			break;
		case fragment_homepage.TYPE_LABOR:
		case fragment_homepage.TYPE_SUPRIOR:
			view = lf.inflate(R.layout.layout_contact_tab_item, null);
			text = (TextView) view.findViewById(R.id.contact_tab_title);
			text.setText("��ҵ����");
			mTabHost.addTab(mTabHost.newTabSpec("��ҵ����").setIndicator(view).setContent(R.id.contact_designer_list));
			break;
		}
		
		mTabHost.setOnTabChangedListener(this);
		
		onTabChanged("ȫ��");
	}

	public void initActionBar(){
		ImageView imageView = new ImageView(this);
		imageView.setImageResource(R.drawable.back);
		setLeftView(imageView);
		setTitle(mTitle);
		this.setOnLeftClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO �Զ����ɵķ������
				finish();
			}
			
		});
		
	}

	public void onResume(){
		super.onResume();
		mTabHost.setCurrentTab(1);
		mTabHost.setCurrentTab(0);
	}
	
	public void initListView(Intent intent){
		mInflater = LayoutInflater.from(this);
		DesignerListItem item = new DesignerListItem();
		mArrayList.add(item);
		item = new DesignerListItem();
		item.mIsHeart = true;
		mArrayList.add(item);
		mListView = (ListView) this.findViewById(R.id.contact_designer_list);
		mListView.setAdapter(mAdapter);
		mListView.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				NetworkData.FindMemberBack mBack;
				switch(mTabHost.getCurrentTab()){
				case 0:
					mBack = mBack0;
					break;
				case 1:
					mBack = mBack1;
					break;
				case 2:
					mBack = mBack2;
					break;
				case 3:
					mBack = mBack3;
					break;
				default:
					mBack = mBack0;
					break;
				}
				switch(mType){
				case fragment_homepage.TYPE_DESIGNER:
					// TODO �Զ����ɵķ������
					Intent intent = new Intent(ContactDesignerActivity.this,DesignerActivity.class);
					intent.putExtra("id", mBack.memberInfo.get(arg2).id);
					startActivity(intent);
					break;
				case fragment_homepage.TYPE_LABOR:
					Intent intentLabor = new Intent(ContactDesignerActivity.this,LaborActivity.class);
					intentLabor.putExtra("id", mBack.memberInfo.get(arg2).id);
					startActivity(intentLabor);
					break;
				case fragment_homepage.TYPE_SUPRIOR:
					Intent intentSuprior = new Intent(ContactDesignerActivity.this,SupriorActivity.class);
					intentSuprior.putExtra("id", mBack.memberInfo.get(arg2).id);
					startActivity(intentSuprior);
					break;
				}
			}
			
		});
	}
	
	@Override
	public void onTabChanged(String arg0) {
		// TODO �Զ����ɵķ������
		refreshTab();
		mListView = (ListView) mTabHost.getCurrentView().findViewById(R.id.contact_designer_list);
		mListView.setAdapter(mAdapter);
	}
	
	public void refreshTab(){
		int current = mTabHost.getCurrentTab();
		for (int i = 0; i < mTabHost.getTabWidget().getChildCount(); i++) {
            TextView tv=(TextView)mTabHost.getTabWidget().getChildAt(i).findViewById(R.id.contact_tab_title);
            //ImageView img = (ImageView)mTabHost.getTabWidget().getChildAt(i).findViewById(R.id.tips_tab_img);
            
            if(i == current){
            	tv.setTextColor(getResources().getColor(R.color.green));//�����������ɫ
            	//img.setBackgroundColor(getResources().getColor(R.color.green));
            	//mTabHost.getTabWidget().getChildAt(i).setBackgroundColor(getResources().getColor(R.color.yellow));
            }else{
            	tv.setTextColor(Color.GRAY);//�����������ɫ��
            	//img.setBackgroundColor(getResources().getColor(R.color.graylight));
            }
                //��ȡtabsͼƬ��
        }
		
		switch(current){
		case 0:
			//mAdapter.setArrayList(mArrayList);
			mAdapter.notifyDataSetChanged();
			if(mList0.size() == 0){
				new getListThread("rate",0,5,current).start();
				mNoMoreTxt.setVisibility(View.GONE);
			}
			break;
		case 1:
			//mAdapter.setArrayList(mArrayProject);
			mAdapter.notifyDataSetChanged();
			if(mList1.size() == 0){
				new getListThread("attud",0,5,current).start(); 
				mNoMoreTxt.setVisibility(View.GONE);
			}
			break;
		case 2:
			//mAdapter.setArrayList(mArraySoft);
			mAdapter.notifyDataSetChanged();
			if(mList2.size() == 0){
				new getListThread("case",0,5,current).start();
				mNoMoreTxt.setVisibility(View.GONE);
			}
			break;
		case 3:
			//mAdapter.setArrayList(mArrayDesign);
			mAdapter.notifyDataSetChanged();
			if(mList3.size() == 0){
				new getListThread("work",0,5,current).start(); 
				mNoMoreTxt.setVisibility(View.GONE);
			}
			break;
		case 4:
			//mAdapter.setArrayList(mArrayMateria);
			mAdapter.notifyDataSetChanged();
			break;
		case 5:
			//mAdapter.setArrayList(mArrayWindWater);
			mAdapter.notifyDataSetChanged();
			break;
		}
	}
	
	public class DesignerListAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO �Զ����ɵķ������
			if(mTabHost.getCurrentTab() == 0){
				return mList0.size();
			}else if(mTabHost.getCurrentTab() == 1){
				return mList1.size();
			}else if(mTabHost.getCurrentTab() == 2){
				return mList2.size();
			}else{
				return mList3.size();
			}
			
		}

		@Override
		public Object getItem(int arg0) {
			// TODO �Զ����ɵķ������
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			// TODO �Զ����ɵķ������
			return 0;
		}

		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2) {
			// TODO �Զ����ɵķ������
			arg1 = mInflater.inflate(R.layout.layout_contact_designer_item, null);
			TextView name = (TextView) arg1.findViewById(R.id.contact_designer_item_name);
			ImageView avatar = (ImageView) arg1.findViewById(R.id.contact_designer_avatar);
			TextView caseNum = (TextView) arg1.findViewById(R.id.contact_designer_cases);
			TextView rateAtt = (TextView) arg1.findViewById(R.id.contract_designer_RateAttu);
			ImageView love = (ImageView)arg1.findViewById(R.id.contact_designer_heart);
			ArrayList<NetworkData.FindMemberItem> items;
			if(mTabHost.getCurrentTab() == 0){
				items = mList0;
			}else if(mTabHost.getCurrentTab() == 1){
				items = mList1;
			}else if(mTabHost.getCurrentTab() == 2){
				items = mList2;
			}else{
				items = mList3;
			}
			if(arg0 >= items.size()){
				return null;
			}
			NetworkData.FindMemberItem item = items.get(arg0);
			name.setText(item.username);
			if(item.imgAvatar != null){
				avatar.setImageBitmap(item.imgAvatar);
			}
			caseNum.setText("��������"+item.case_num);
			rateAtt.setText("רҵ��"+item.rate_avg+"    ����"+item.attud_avg);
			
			return arg1;
		}
		
	}

	public class DesignerListItem{
		String mName = "������";
		int mCases = 8;
		float mSkills = 9.0f;
		float mService = 9.0f;
		int mPrice = 100;
		boolean mIsHeart = false;
		int mSupport = 50;
	}

	@Override
	protected void onNewIntent(Intent intent) {
		// TODO �Զ����ɵķ������
		super.onNewIntent(intent);
	}
}
