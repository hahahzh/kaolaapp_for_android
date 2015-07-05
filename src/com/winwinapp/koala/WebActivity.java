package com.winwinapp.koala;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.Toast;

public class WebActivity extends ActionBarActivity {

	public String mTitle = "���ҳ��";
	WebView mWebView;
	public String mUrl = "http://120.26.197.206/index.php";
	public final static String mDefaultUrl = "http://120.26.197.206/index.php";
	int mPage = -1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO �Զ����ɵķ������
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_webpage);
		
		initActionBar();
		
		mPage = getIntent().getIntExtra("page", -1);
		Toast.makeText(this, "page "+ mPage+" is clicked", Toast.LENGTH_LONG).show();
		mUrl = getIntent().getStringExtra("url");
		if(mUrl == null){
			mUrl = mDefaultUrl;
		}
		initWebView();
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
	
	public void initWebView(){
		mWebView = (WebView)findViewById(R.id.webView);
		mWebView.loadUrl(mUrl);
		mWebView.setWebViewClient(new WebViewClient(){
	           @Override
	        public boolean shouldOverrideUrlLoading(WebView view, String url) {
	            // TODO Auto-generated method stub
	               //����ֵ��true��ʱ�����ȥWebView�򿪣�Ϊfalse����ϵͳ�����������������
	             view.loadUrl(url);
	            return true;
	        }
	       });
	}

	
}
