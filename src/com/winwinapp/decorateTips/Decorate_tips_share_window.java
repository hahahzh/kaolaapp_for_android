package com.winwinapp.decorateTips;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sina.weibo.sdk.api.ImageObject;
import com.sina.weibo.sdk.api.MusicObject;
import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.VideoObject;
import com.sina.weibo.sdk.api.VoiceObject;
import com.sina.weibo.sdk.api.WebpageObject;
import com.sina.weibo.sdk.api.WeiboMessage;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.api.share.BaseResponse;
import com.sina.weibo.sdk.api.share.IWeiboDownloadListener;
import com.sina.weibo.sdk.api.share.IWeiboHandler;
import com.sina.weibo.sdk.api.share.IWeiboShareAPI;
import com.sina.weibo.sdk.api.share.SendMessageToWeiboRequest;
import com.sina.weibo.sdk.api.share.SendMultiMessageToWeiboRequest;
import com.sina.weibo.sdk.api.share.WeiboShareSDK;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuth;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.constant.WBConstants;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.exception.WeiboShareException;
import com.sina.weibo.sdk.utils.Utility;
import com.tencent.mm.sdk.openapi.BaseReq;
import com.tencent.mm.sdk.openapi.BaseResp;
import com.tencent.mm.sdk.openapi.ConstantsAPI;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.SendMessageToWX;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.mm.sdk.openapi.WXImageObject;
import com.tencent.mm.sdk.openapi.WXMediaMessage;
import com.tencent.mm.sdk.openapi.WXWebpageObject;
import com.winwinapp.koala.R;
import com.winwinapp.util.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class Decorate_tips_share_window extends Activity implements OnItemClickListener,IWXAPIEventHandler,IWeiboHandler.Response{

	GridView mGridView;
	private static final int THUMB_SIZE = 150;
	String mTitleList[] = new String[]{"微信","朋友圈","微博"};
	int mImageId[] = new int[]{R.drawable.share_wechat,R.drawable.share_friends,R.drawable.share_weibo};
	ArrayList<HashMap<String,Object>> imagelist = new ArrayList<HashMap<String,Object>>();
	// IWXAPI 是第三方app和微信通信的openapi接口
	private IWXAPI api;
    // 微博 Web 授权类，提供登陆等功能  
    private WeiboAuth mWeiboAuth;
    /** 当前 DEMO 应用的 APP_KEY，第三方应用应该使用自己的 APP_KEY 替换该 APP_KEY */
    public static final String APP_KEY_WEIBO      = "2045436852";
    /** 封装了 "access_token"，"expires_in"，"refresh_token"，并提供了他们的管理功能  */
    private Oauth2AccessToken mAccessToken;/** 
     * 当前 DEMO 应用的回调页，第三方应用可以使用自己的回调页。
     * 
     * <p>
     * 注：关于授权回调页对移动客户端应用来说对用户是不可见的，所以定义为何种形式都将不影响，
     * 但是没有定义将无法使用 SDK 认证登录。
     * 建议使用默认回调页：https://api.weibo.com/oauth2/default.html
     * </p>
     */
    public static final String REDIRECT_URL = "http://www.sina.com";
    /**
     * Scope 是 OAuth2.0 授权机制中 authorize 接口的一个参数。通过 Scope，平台将开放更多的微博
     * 核心功能给开发者，同时也加强用户隐私保护，提升了用户体验，用户在新 OAuth2.0 授权页中有权利
     * 选择赋予应用的功能。
     * 
     * 我们通过新浪微博开放平台-->管理中心-->我的应用-->接口管理处，能看到我们目前已有哪些接口的
     * 使用权限，高级权限需要进行申请。
     * 
     * 目前 Scope 支持传入多个 Scope 权限，用逗号分隔。
     * 
     * 有关哪些 OpenAPI 需要权限申请，请查看：http://open.weibo.com/wiki/%E5%BE%AE%E5%8D%9AAPI
     * 关于 Scope 概念及注意事项，请查看：http://open.weibo.com/wiki/Scope
     */
    public static final String SCOPE = 
            "email,direct_messages_read,direct_messages_write,"
            + "friendships_groups_read,friendships_groups_write,statuses_to_me_read,"
            + "follow_app_official_microblog," + "invitation_write";
    private SsoHandler mSsoHandler;
    private IWeiboShareAPI  mWeiboShareAPI = null;
    String title = "考拉小匠";
    
    String url_pic = "http://www.zhbiao.com/uploads/20150210/35471423540429.jpg";
	String url = "http://120.26.197.206/index.php/Home/DecKms/detail/doc/16.html";
	String description = "This is share description";
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
	        mGridView.setOnItemClickListener(this);
 }

	@Override
	public void onItemClick(AdapterView<?> arg0, View view, int position, long arg3) {
		// TODO 自动生成的方法存根
		final int pos = position;
		if(position == 0 || position == 1){//wechat
			String APP_ID = "wxd930ea5d5a258f4f";
			api = WXAPIFactory.createWXAPI(this, APP_ID, true);
			api.registerApp(APP_ID);
			api.handleIntent(getIntent(), this);
			if(!api.isWXAppInstalled()){
				Toast.makeText(this, "您还未安装微信客户端", Toast.LENGTH_LONG).show();
				return;
			}
			/**
			 * 微信分享图片到好友
			 */
			new Thread(new Runnable() {

				@Override
				public void run() {
					

					try {
						WXWebpageObject webPage = new WXWebpageObject();
						webPage.webpageUrl = url;
						//WXImageObject imgObj = new WXImageObject();
						//imgObj.imageUrl = url;
						
						WXMediaMessage msg = new WXMediaMessage(webPage);
						//WXMediaMessage msg = new WXMediaMessage();
						//msg.mediaObject = imgObj;

						Bitmap bmp = BitmapFactory.decodeStream(new URL(url_pic)
								.openStream());
						Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp,
								THUMB_SIZE, THUMB_SIZE, true);
						//bmp.recycle();
						//msg.thumbData = Utils.bmpToByteArray(thumbBmp, true);
						msg.setThumbImage(thumbBmp);
						msg.title = "考拉装修网";
						msg.description = url;

						SendMessageToWX.Req req = new SendMessageToWX.Req();
						req.transaction = buildTransaction("img");
						req.message = msg;
						req.scene = 1- pos;//scene:0,moment; scene:1,friends
						api.sendReq(req);
						finish();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}).start();

			
		}else if(position == 2){//weibo
			  // 创建微博实例
	        mWeiboAuth = new WeiboAuth(this, APP_KEY_WEIBO, REDIRECT_URL, SCOPE);
	        mSsoHandler = new SsoHandler(Decorate_tips_share_window.this, mWeiboAuth);
	     // Web 授权
	        mSsoHandler.authorize(new AuthListener());
	        //mSsoHandler.anthorize(new AuthListener());
		}
	}
	
	/**
     * 微博认证授权回调类。
     * 1. SSO 授权时，需要在 {@link #onActivityResult} 中调用 {@link SsoHandler#authorizeCallBack} 后，
     *    该回调才会被执行。
     * 2. 非 SSO 授权时，当授权结束后，该回调就会被执行。
     * 当授权成功后，请保存该 access_token、expires_in、uid 等信息到 SharedPreferences 中。
     */
    class AuthListener implements WeiboAuthListener {
        
        @Override
        public void onComplete(Bundle values) {
            // 从 Bundle 中解析 Token
            mAccessToken = Oauth2AccessToken.parseAccessToken(values);
            if (mAccessToken.isSessionValid()) {
                
            	 // 显示 Token
                updateTokenView(false);
                
                // 保存 Token 到 SharedPreferences
                AccessTokenKeeper.writeAccessToken(Decorate_tips_share_window.this, mAccessToken);
                Toast.makeText(Decorate_tips_share_window.this,"微博授权成功", Toast.LENGTH_SHORT).show();
                
                WeiboShare();
            } else {
                // 以下几种情况，您会收到 Code：
                // 1. 当您未在平台上注册的应用程序的包名与签名时；
                // 2. 当您注册的应用程序包名与签名不正确时；
                // 3. 当您在平台上注册的包名和签名与您当前测试的应用的包名和签名不匹配时。
                String code = values.getString("code");
                String message = "微博授权失败";
                if (!TextUtils.isEmpty(code)) {
                    message = message + "\nObtained the code: " + code;
                }
                Toast.makeText(Decorate_tips_share_window.this, message, Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onCancel() {
            Toast.makeText(Decorate_tips_share_window.this,"取消微博授权", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onWeiboException(WeiboException e) {
            Toast.makeText(Decorate_tips_share_window.this,"Auth exception : " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
    
	private String buildTransaction(final String type) {
		return (type == null) ? String.valueOf(System.currentTimeMillis())
				: type + System.currentTimeMillis();
	}
	
	// 微信发送请求到第三方应用时，会回调到该方法
	@Override
	public void onReq(BaseReq req) {
		switch(req.getType()){
		case ConstantsAPI.COMMAND_GETMESSAGE_FROM_WX:
			Intent i = new Intent();
			//i.setClass(this, MetroHomeActivity.);
			break;
		case ConstantsAPI.COMMAND_SHOWMESSAGE_FROM_WX:
			break;
		}
	}

	// 第三方应用发送到微信的请求处理后的响应结果，会回调到该方法
	@Override
	public void onResp(BaseResp resp) {
		int result = 0;
		
		switch (resp.errCode) {
		case BaseResp.ErrCode.ERR_OK:
			result = R.string.errcode_success;
			break;
		case BaseResp.ErrCode.ERR_USER_CANCEL:
			result = R.string.errcode_cancel;
			break;
		case BaseResp.ErrCode.ERR_AUTH_DENIED:
			result = R.string.errcode_deny;
			break;
		default:
			result = R.string.errcode_unknown;
			break;
		}
		
		Toast.makeText(this, result, Toast.LENGTH_LONG).show();
		finish();
	}

	 /**
     * 显示当前 Token 信息。
     *
     * @param hasExisted 配置文件中是否已存在 token 信息并且合法
     */
    private void updateTokenView(boolean hasExisted) {
        String date = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new java.util.Date(mAccessToken.getExpiresTime()));
        String format = "Token：%1$s \n有效期：%2$s";
         
        String message = String.format(format, mAccessToken.getToken(), date);
        if (hasExisted) {
            message = "Token 仍在有效期内，无需再次登录。" + "\n" + message;
        }
        //MyLog.toLog(message);
    }
    
	/**
     * 当 SSO 授权 Activity 退出时，该函数被调用。
     *
     * @see {@link Activity#onActivityResult}
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
         
        // SSO 授权回调
        // 重要：发起 SSO 登陆的 Activity 必须重写 onActivityResult
        if (mSsoHandler != null) {
            mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
        }
    }
    
    public void WeiboShare(){
    	if(checkVersion()){
            try {
                // 检查微博客户端环境是否正常，如果未安装微博，弹出对话框询问用户下载微博客户端
                if (mWeiboShareAPI.checkEnvironment(true)) {
                     
                    // 注册第三方应用 到微博客户端中，注册成功后该应用将显示在微博的应用列表中。
                    // 但该附件栏集成分享权限需要合作申请，详情请查看 Demo 提示
                    mWeiboShareAPI.registerApp();
                     
                    sendMessage(false, false, true,false,  false, false);
                }
            } catch (WeiboShareException e) {
                e.printStackTrace();
                Toast.makeText(Decorate_tips_share_window.this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }
    
    /**
     * 第三方应用发送请求消息到微博，唤起微博分享界面。
     * @see {@link #sendMultiMessage} 或者 {@link #sendSingleMessage}
     */
    private void sendMessage(boolean hasText, boolean hasImage, boolean hasWebpage, boolean hasMusic, boolean hasVideo, boolean hasVoice) {
         
        if (mWeiboShareAPI.isWeiboAppSupportAPI()) {
            int supportApi = mWeiboShareAPI.getWeiboAppSupportAPI();
            if (supportApi >= 10351 /*ApiUtils.BUILD_INT_VER_2_2*/) {
                sendMultiMessage(hasText, hasImage, hasWebpage, hasMusic, hasVideo, hasVoice);
            } else {
                sendSingleMessage(hasText, hasImage, hasWebpage, hasMusic, hasVideo/*, hasVoice*/);
            }
        } else {
            Toast.makeText(this, "微博客户端不支持，请升级版本", Toast.LENGTH_SHORT).show();
        }
    }
    
    /**
     * 第三方应用发送请求消息到微博，唤起微博分享界面。
     * 注意：当 {@link IWeiboShareAPI#getWeiboAppSupportAPI()} >= 10351 时，支持同时分享多条消息，
     * 同时可以分享文本、图片以及其它媒体资源（网页、音乐、视频、声音中的一种）。
     *
     * @param hasText    分享的内容是否有文本
     * @param hasImage   分享的内容是否有图片
     * @param hasWebpage 分享的内容是否有网页
     * @param hasMusic   分享的内容是否有音乐
     * @param hasVideo   分享的内容是否有视频
     * @param hasVoice   分享的内容是否有声音
     */
    private void sendMultiMessage(boolean hasText, boolean hasImage, boolean hasWebpage,
            boolean hasMusic, boolean hasVideo, boolean hasVoice) {
         
        // 1. 初始化微博的分享消息
        WeiboMultiMessage weiboMessage = new WeiboMultiMessage();
        if (hasText) {
            weiboMessage.textObject = getTextObj();
        }
         
        if (hasImage) {
            weiboMessage.imageObject = getImageObj();
        }
         
        // 用户可以分享其它媒体资源（网页、音乐、视频、声音中的一种）
        if (hasWebpage) {
        	
            weiboMessage.mediaObject = getWebpageObj();
        }
        if (hasMusic) {
            weiboMessage.mediaObject = getMusicObj();
        }
        if (hasVideo) {
            weiboMessage.mediaObject = getVideoObj();
        }
        if (hasVoice) {
            weiboMessage.mediaObject = getVoiceObj();
        }
         
        // 2. 初始化从第三方到微博的消息请求
        SendMultiMessageToWeiboRequest request = new SendMultiMessageToWeiboRequest();
        // 用transaction唯一标识一个请求
        request.transaction = String.valueOf(System.currentTimeMillis());
        request.multiMessage = weiboMessage;
         
        // 3. 发送请求消息到微博，唤起微博分享界面
        mWeiboShareAPI.sendRequest(request);
    }
    
    /**
     * 第三方应用发送请求消息到微博，唤起微博分享界面。
     * 当{@link IWeiboShareAPI#getWeiboAppSupportAPI()} < 10351 时，只支持分享单条消息，即
     * 文本、图片、网页、音乐、视频中的一种，不支持Voice消息。
     *
     * @param hasText    分享的内容是否有文本
     * @param hasImage   分享的内容是否有图片
     * @param hasWebpage 分享的内容是否有网页
     * @param hasMusic   分享的内容是否有音乐
     * @param hasVideo   分享的内容是否有视频
     */
    private void sendSingleMessage(boolean hasText, boolean hasImage, boolean hasWebpage,
            boolean hasMusic, boolean hasVideo/*, boolean hasVoice*/) {
         
        // 1. 初始化微博的分享消息
        // 用户可以分享文本、图片、网页、音乐、视频中的一种
        WeiboMessage weiboMessage = new WeiboMessage();
        if (hasText) {
            weiboMessage.mediaObject = getTextObj();
        }
        if (hasImage) {
            weiboMessage.mediaObject = getImageObj();
        }
        if (hasWebpage) {
            weiboMessage.mediaObject = getWebpageObj();
        }
        if (hasMusic) {
            weiboMessage.mediaObject = getMusicObj();
        }
        if (hasVideo) {
            weiboMessage.mediaObject = getVideoObj();
        }
        /*if (hasVoice) {
            weiboMessage.mediaObject = getVoiceObj();
        }*/
         
        // 2. 初始化从第三方到微博的消息请求
        SendMessageToWeiboRequest request = new SendMessageToWeiboRequest();
        // 用transaction唯一标识一个请求
        request.transaction = String.valueOf(System.currentTimeMillis());
        request.message = weiboMessage;
         
        // 3. 发送请求消息到微博，唤起微博分享界面
        mWeiboShareAPI.sendRequest(request);
    }
    
    /**
     * 创建文本消息对象。
     *
     * @return 文本消息对象。
     */
    private TextObject getTextObj() {
        TextObject textObject = new TextObject();
        String  text = String.format("我正在使用微博客户端发博器分享文字", "【%1$s】（分享自 菜菜一号 %2$s）",
                "https://github.com/mobileresearch/weibo_android_sdk/blob/master/WeiboSDKDemo.apk");
        textObject.text =text;
        return textObject;
    }
 
    /**
     * 创建图片消息对象。
     *
     * @return 图片消息对象。
     */
    private ImageObject getImageObj() {
        ImageObject imageObject = new ImageObject();
        Bitmap shareImage = BitmapFactory.decodeResource(getResources(), R.drawable.tips_detail_image_preview);
        imageObject.setImageObject(shareImage);
        return imageObject;
    }
 
    /**
     * 创建多媒体（网页）消息对象。
     *
     * @return 多媒体（网页）消息对象。
     */
    private WebpageObject getWebpageObj() {
        WebpageObject mediaObject = new WebpageObject();
        mediaObject.identify = Utility.generateGUID();
        mediaObject.title =title;
        mediaObject.description = "this is share decription";
         
        // 设置 Bitmap 类型的图片到视频对象里
       
        Bitmap shareImage = BitmapFactory.decodeResource(getResources(), R.drawable.tips_detail_image_preview);
        mediaObject.setThumbImage(shareImage);
        mediaObject.actionUrl = url;
        mediaObject.defaultText = "This is default text";
        return mediaObject;
    }
 
    /**
     * 创建多媒体（音乐）消息对象。
     *
     * @return 多媒体（音乐）消息对象。
     */
    private MusicObject getMusicObj() {
        // 创建媒体消息
        MusicObject musicObject = new MusicObject();
        musicObject.identify = Utility.generateGUID();
        musicObject.title =title;
        musicObject.description ="This is share de";
         
        Bitmap shareImage = BitmapFactory.decodeResource(getResources(), R.drawable.tips_detail_image_preview);
        // 设置 Bitmap 类型的图片到视频对象里
        musicObject.setThumbImage(shareImage);
        musicObject.actionUrl = url;
        //musicObject.dataUrl = dataUrl;
        //musicObject.dataHdUrl =dataHdUrl;
        musicObject.duration = 10;
        musicObject.defaultText = "This is default text";
        return musicObject;
    }
    /**
     * 创建多媒体（视频）消息对象。
     *
     * @return 多媒体（视频）消息对象。
     */
    private VideoObject getVideoObj() {
        // 创建媒体消息
        VideoObject videoObject = new VideoObject();
        videoObject.identify = Utility.generateGUID();
        videoObject.title =title;
        videoObject.description =description;
         
        Bitmap shareImage = BitmapFactory.decodeResource(getResources(), R.drawable.tips_detail_image_preview);
        // 设置 Bitmap 类型的图片到视频对象里
        videoObject.setThumbImage(shareImage);
        videoObject.actionUrl =url;
        //videoObject.dataUrl = dataUrl;
        //videoObject.dataHdUrl = dataHdUrl;
        videoObject.duration = 10;
        videoObject.defaultText = "This is default text";
        return videoObject;
    }
 
    /**
     * 创建多媒体（音频）消息对象。
     *
     * @return 多媒体（音乐）消息对象。
     */
    private VoiceObject getVoiceObj() {
        // 创建媒体消息
        VoiceObject voiceObject = new VoiceObject();
        voiceObject.identify = Utility.generateGUID();
        voiceObject.title =title;
        voiceObject.description = description;
         
        Bitmap shareImage = BitmapFactory.decodeResource(getResources(), R.drawable.tips_detail_image_preview);
        // 设置 Bitmap 类型的图片到视频对象里
        voiceObject.setThumbImage(shareImage);
        voiceObject.actionUrl = url;
        //voiceObject.dataUrl = dataUrl;
        //voiceObject.dataHdUrl =dataHdUrl;
        voiceObject.duration = 10;
        voiceObject.defaultText ="this is default text";
        return voiceObject;
    }
    /**
     * 是否有安装微博客户端
     */
    public Boolean checkVersion(){
        // 创建微博分享接口实例
        mWeiboShareAPI = WeiboShareSDK.createWeiboAPI(this, APP_KEY_WEIBO);
        // 如果未安装微博客户端，设置下载微博对应的回调
        if (!mWeiboShareAPI.isWeiboAppInstalled()) {
            mWeiboShareAPI.registerWeiboDownloadListener(new IWeiboDownloadListener() {
                @Override
                public void onCancel() {
                    Toast.makeText(Decorate_tips_share_window.this, "取消下载微博客户端",  Toast.LENGTH_SHORT).show();
                }
            });
            return false;
        }
        return true;
    }
    
	@Override
	public void onResponse(BaseResponse baseResp) {
		// TODO 自动生成的方法存根
		switch (baseResp.errCode) {
        case WBConstants.ErrorCode.ERR_OK:
            Toast.makeText(this, "分享成功", Toast.LENGTH_LONG).show();
            break;
        case WBConstants.ErrorCode.ERR_CANCEL:
            Toast.makeText(this, "分享取消", Toast.LENGTH_LONG).show();
            break;
        case WBConstants.ErrorCode.ERR_FAIL:
            Toast.makeText(this, "分享失败" + "Error Message: " + baseResp.errMsg, Toast.LENGTH_LONG).show();
            break;
        }
	}
}
