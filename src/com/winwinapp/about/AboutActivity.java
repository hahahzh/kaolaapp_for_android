package com.winwinapp.about;


import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.xmlpull.v1.XmlPullParser;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.winwinapp.koala.ActionBarActivity;
import com.winwinapp.koala.R;
import com.winwinapp.login.LoginPageActivity;
import com.winwinapp.login.ResetPWD1Activity;

public class AboutActivity extends ActionBarActivity {

	private static final int UPDATA_CLIENT = 1;
	private static final int DOWN_ERROR = 2;
	private static final int GET_UNDATAINFO_ERROR = 3;
	private Context context = AboutActivity.this;
	private View l1;
	private View l2;
	private View l3;
	private static String ServerUrl = "http://";//address of app server
	private LinearLayout layoutProcess;
	int versionCode = 1;
	String versionName = "1.0";
	UpdataInfo info;
	TextView mVersionNameTxt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_about);
		
		initActionBar();
		
		l1 = (View)findViewById(R.id.about_l1);
		l2 = (LinearLayout)findViewById(R.id.about_l2);
		l3 = (LinearLayout)findViewById(R.id.about_l3);
		mVersionNameTxt = (TextView)findViewById(R.id.about_version_name);
		
		try {
			PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), 0);
			versionCode = info.versionCode;
			versionName = info.versionName;
			mVersionNameTxt.setText("-----V"+versionName+"-----");
		} catch (NameNotFoundException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		
		layoutProcess = (LinearLayout)findViewById(R.id.login_status);
		l1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Bundle bundle = new Bundle();
				Intent intent = new Intent(AboutActivity.this, FuncDescActivity.class);
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
		
		l2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showLatestVersionDialog();
			}
		});
		
		l3.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Bundle bundle = new Bundle();
				Intent intent = new Intent(AboutActivity.this, FeedbackActivity.class);
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
		
	}
	
	public void initActionBar(){
		ImageView imageView = new ImageView(this);
		imageView.setImageResource(R.drawable.back);
		setLeftView(imageView);
		setTitle("关于");
		this.setOnLeftClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO 自动生成的方法存根
				finish();
			}
			
		});
	}
	
	private void showLatestVersionDialog(){
		AlertDialog.Builder builer = new Builder(this) ;   
	    builer.setTitle("版本升级");  
	    builer.setMessage("已经是最新版本");  
	    //当点确定按钮时从服务器上下载 新的apk 然后安装   
	    builer.setPositiveButton("确定", new DialogInterface.OnClickListener() {  
	    public void onClick(DialogInterface dialog, int which) {  
	            //finish(); 
	        }
    
	    }); 
	    AlertDialog dialog = builer.create();  
	    dialog.show();
	}
	/* 
	 *  
	 * 弹出对话框通知用户更新程序  
	 *  
	 * 弹出对话框的步骤： 
	 *  1.创建alertDialog的builder.   
	 *  2.要给builder设置属性, 对话框的内容,样式,按钮 
	 *  3.通过builder 创建一个对话框 
	 *  4.对话框show()出来   
	 */  
	protected void showUpdataDialog() {  
	    AlertDialog.Builder builer = new Builder(this) ;   
	    builer.setTitle("版本升级");  
	    builer.setMessage(info.getDescription());  
	    //当点确定按钮时从服务器上下载 新的apk 然后安装   
	    builer.setPositiveButton("确定", new DialogInterface.OnClickListener() {  
	    public void onClick(DialogInterface dialog, int which) {  
	            downLoadApk();  
	        }
    
	    });  
	    //当点取消按钮时进行登录  
	    builer.setNegativeButton("取消", new DialogInterface.OnClickListener() {  
	        public void onClick(DialogInterface dialog, int which) {  
	            // TODO Auto-generated method stub  
	            //LoginMain();
	        	//finish();
	        }  
	    });  
	    AlertDialog dialog = builer.create();  
	    dialog.show();
	}  
	  
	/* 
	 * 从服务器中下载APK 
	 */  
	protected void downLoadApk() {  
	    final ProgressDialog pd;    //进度条对话框  
	    pd = new  ProgressDialog(this);  
	    pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);  
	    pd.setMessage("正在下载更新");  
	    pd.show();  
	    new Thread(){  
	        @Override  
	        public void run() {  
	            try {  
	                File file = getFileFromServer(info.getUrl(), pd);  
	                sleep(3000);  
	                installApk(file);  
	                pd.dismiss(); //结束掉进度条对话框  
	            } catch (Exception e) {  
	                Message msg = new Message();  
	                msg.what = DOWN_ERROR;  
	                handler.sendMessage(msg);  
	                e.printStackTrace();  
	            }  
	        }}.start();  
	}  
	  
	//安装apk   
	protected void installApk(File file) {  
	    Intent intent = new Intent();  
	    //执行动作  
	    intent.setAction(Intent.ACTION_VIEW);  
	    //执行的数据类型  
	    intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");  
	    startActivity(intent);  
	}  
	
	/* 
	 * 从服务器获取xml解析并进行比对版本号  
	 */  
	public class CheckVersionTask implements Runnable{  
	  
	    public void run() {  
	        try {  
	            //从资源文件获取服务器 地址   
	            String path = ServerUrl;  
	            //包装成url的对象   
	            URL url = new URL(path);  
	            HttpURLConnection conn =  (HttpURLConnection) url.openConnection();   
	            conn.setConnectTimeout(5000);  
	            InputStream is =conn.getInputStream();   
	            info =  getUpdataInfo(is);  
	              
	            if(info.getVersion().equals(versionName)){   
	                //LoginMain();  //版本相同
	            }else{  
	                //Log.i(TAG,"版本号不同 ,提示用户升级 ");  
	                Message msg = new Message();  
	                msg.what = UPDATA_CLIENT;  
	                handler.sendMessage(msg);  
	            }  
	        } catch (Exception e) {  
	            // 待处理   
	            Message msg = new Message();  
	            msg.what = GET_UNDATAINFO_ERROR;  
	            handler.sendMessage(msg);  
	            e.printStackTrace();  
	        }   
	    }  
	}  
	  
	Handler handler = new Handler(){  
	      
	    @Override  
	    public void handleMessage(Message msg) {  
	        // TODO Auto-generated method stub  
	        super.handleMessage(msg);  
	        switch (msg.what) {  
	        case UPDATA_CLIENT:  
	            //对话框通知用户升级程序   
	            showUpdataDialog();  
	            break;  
	        case GET_UNDATAINFO_ERROR:  
	            //服务器超时   
	            Toast.makeText(getApplicationContext(), "获取服务器更新信息失败", 1).show();  
	            //LoginMain();  
	            break;    
	        case DOWN_ERROR:  
	            //下载apk失败  
	            Toast.makeText(getApplicationContext(), "下载新版本失败", 1).show();  
	            //LoginMain();  
	            break;    
	        }  
	    }  
	};  
	
	/* 
	 * 用pull解析器解析服务器返回的xml文件 (xml封装了版本号) 
	 */  
	public static UpdataInfo getUpdataInfo(InputStream is) throws Exception{  
	    XmlPullParser  parser = Xml.newPullParser();    
	    parser.setInput(is, "utf-8");//设置解析的数据源   
	    int type = parser.getEventType();  
	    UpdataInfo info = new UpdataInfo();//实体  
	    while(type != XmlPullParser.END_DOCUMENT ){  
	        switch (type) {  
	        case XmlPullParser.START_TAG:  
	            if("version".equals(parser.getName())){  
	                info.setVersion(parser.nextText()); //获取版本号  
	            }else if ("url".equals(parser.getName())){  
	                info.setUrl(parser.nextText()); //获取要升级的APK文件  
	            }else if ("description".equals(parser.getName())){  
	                info.setDescription(parser.nextText()); //获取该文件的信息  
	            }  
	            break;  
	        }  
	        type = parser.next();  
	    }  
	    return info;  
	}  
	
	public static File getFileFromServer(String path, ProgressDialog pd) throws Exception{  
	    //如果相等的话表示当前的sdcard挂载在手机上并且是可用的  
	    if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){  
	        URL url = new URL(path);  
	        HttpURLConnection conn =  (HttpURLConnection) url.openConnection();  
	        conn.setConnectTimeout(5000);  
	        //获取到文件的大小   
	        pd.setMax(conn.getContentLength());  
	        InputStream is = conn.getInputStream();  
	        File file = new File(Environment.getExternalStorageDirectory(), "updata.apk");  
	        FileOutputStream fos = new FileOutputStream(file);  
	        BufferedInputStream bis = new BufferedInputStream(is);  
	        byte[] buffer = new byte[1024];  
	        int len ;  
	        int total=0;  
	        while((len =bis.read(buffer))!=-1){  
	            fos.write(buffer, 0, len);  
	            total+= len;  
	            //获取当前下载量  
	            pd.setProgress(total);  
	        }  
	        fos.close();  
	        bis.close();  
	        is.close();  
	        return file;  
	    }  
	    else{  
	        return null;  
	    }  
	}  
	
}
