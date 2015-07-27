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
	String mTitleList[] = new String[]{"΢��","����Ȧ","΢��"};
	int mImageId[] = new int[]{R.drawable.share_wechat,R.drawable.share_friends,R.drawable.share_weibo};
	ArrayList<HashMap<String,Object>> imagelist = new ArrayList<HashMap<String,Object>>();
	// IWXAPI �ǵ�����app��΢��ͨ�ŵ�openapi�ӿ�
	private IWXAPI api;
    // ΢�� Web ��Ȩ�࣬�ṩ��½�ȹ���  
    private WeiboAuth mWeiboAuth;
    /** ��ǰ DEMO Ӧ�õ� APP_KEY��������Ӧ��Ӧ��ʹ���Լ��� APP_KEY �滻�� APP_KEY */
    public static final String APP_KEY_WEIBO      = "2045436852";
    /** ��װ�� "access_token"��"expires_in"��"refresh_token"�����ṩ�����ǵĹ�����  */
    private Oauth2AccessToken mAccessToken;/** 
     * ��ǰ DEMO Ӧ�õĻص�ҳ��������Ӧ�ÿ���ʹ���Լ��Ļص�ҳ��
     * 
     * <p>
     * ע��������Ȩ�ص�ҳ���ƶ��ͻ���Ӧ����˵���û��ǲ��ɼ��ģ����Զ���Ϊ������ʽ������Ӱ�죬
     * ����û�ж��彫�޷�ʹ�� SDK ��֤��¼��
     * ����ʹ��Ĭ�ϻص�ҳ��https://api.weibo.com/oauth2/default.html
     * </p>
     */
    public static final String REDIRECT_URL = "http://www.sina.com";
    /**
     * Scope �� OAuth2.0 ��Ȩ������ authorize �ӿڵ�һ��������ͨ�� Scope��ƽ̨�����Ÿ����΢��
     * ���Ĺ��ܸ������ߣ�ͬʱҲ��ǿ�û���˽�������������û����飬�û����� OAuth2.0 ��Ȩҳ����Ȩ��
     * ѡ����Ӧ�õĹ��ܡ�
     * 
     * ����ͨ������΢������ƽ̨-->��������-->�ҵ�Ӧ��-->�ӿڹ������ܿ�������Ŀǰ������Щ�ӿڵ�
     * ʹ��Ȩ�ޣ��߼�Ȩ����Ҫ�������롣
     * 
     * Ŀǰ Scope ֧�ִ����� Scope Ȩ�ޣ��ö��ŷָ���
     * 
     * �й���Щ OpenAPI ��ҪȨ�����룬��鿴��http://open.weibo.com/wiki/%E5%BE%AE%E5%8D%9AAPI
     * ���� Scope ���ע�������鿴��http://open.weibo.com/wiki/Scope
     */
    public static final String SCOPE = 
            "email,direct_messages_read,direct_messages_write,"
            + "friendships_groups_read,friendships_groups_write,statuses_to_me_read,"
            + "follow_app_official_microblog," + "invitation_write";
    private SsoHandler mSsoHandler;
    private IWeiboShareAPI  mWeiboShareAPI = null;
    String title = "����С��";
    
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
		// TODO �Զ����ɵķ������
		final int pos = position;
		if(position == 0 || position == 1){//wechat
			String APP_ID = "wxd930ea5d5a258f4f";
			api = WXAPIFactory.createWXAPI(this, APP_ID, true);
			api.registerApp(APP_ID);
			api.handleIntent(getIntent(), this);
			if(!api.isWXAppInstalled()){
				Toast.makeText(this, "����δ��װ΢�ſͻ���", Toast.LENGTH_LONG).show();
				return;
			}
			/**
			 * ΢�ŷ���ͼƬ������
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
						msg.title = "����װ����";
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
			  // ����΢��ʵ��
	        mWeiboAuth = new WeiboAuth(this, APP_KEY_WEIBO, REDIRECT_URL, SCOPE);
	        mSsoHandler = new SsoHandler(Decorate_tips_share_window.this, mWeiboAuth);
	     // Web ��Ȩ
	        mSsoHandler.authorize(new AuthListener());
	        //mSsoHandler.anthorize(new AuthListener());
		}
	}
	
	/**
     * ΢����֤��Ȩ�ص��ࡣ
     * 1. SSO ��Ȩʱ����Ҫ�� {@link #onActivityResult} �е��� {@link SsoHandler#authorizeCallBack} ��
     *    �ûص��Żᱻִ�С�
     * 2. �� SSO ��Ȩʱ������Ȩ�����󣬸ûص��ͻᱻִ�С�
     * ����Ȩ�ɹ����뱣��� access_token��expires_in��uid ����Ϣ�� SharedPreferences �С�
     */
    class AuthListener implements WeiboAuthListener {
        
        @Override
        public void onComplete(Bundle values) {
            // �� Bundle �н��� Token
            mAccessToken = Oauth2AccessToken.parseAccessToken(values);
            if (mAccessToken.isSessionValid()) {
                
            	 // ��ʾ Token
                updateTokenView(false);
                
                // ���� Token �� SharedPreferences
                AccessTokenKeeper.writeAccessToken(Decorate_tips_share_window.this, mAccessToken);
                Toast.makeText(Decorate_tips_share_window.this,"΢����Ȩ�ɹ�", Toast.LENGTH_SHORT).show();
                
                WeiboShare();
            } else {
                // ���¼�������������յ� Code��
                // 1. ����δ��ƽ̨��ע���Ӧ�ó���İ�����ǩ��ʱ��
                // 2. ����ע���Ӧ�ó��������ǩ������ȷʱ��
                // 3. ������ƽ̨��ע��İ�����ǩ��������ǰ���Ե�Ӧ�õİ�����ǩ����ƥ��ʱ��
                String code = values.getString("code");
                String message = "΢����Ȩʧ��";
                if (!TextUtils.isEmpty(code)) {
                    message = message + "\nObtained the code: " + code;
                }
                Toast.makeText(Decorate_tips_share_window.this, message, Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onCancel() {
            Toast.makeText(Decorate_tips_share_window.this,"ȡ��΢����Ȩ", Toast.LENGTH_LONG).show();
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
	
	// ΢�ŷ������󵽵�����Ӧ��ʱ����ص����÷���
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

	// ������Ӧ�÷��͵�΢�ŵ�����������Ӧ�������ص����÷���
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
     * ��ʾ��ǰ Token ��Ϣ��
     *
     * @param hasExisted �����ļ����Ƿ��Ѵ��� token ��Ϣ���ҺϷ�
     */
    private void updateTokenView(boolean hasExisted) {
        String date = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new java.util.Date(mAccessToken.getExpiresTime()));
        String format = "Token��%1$s \n��Ч�ڣ�%2$s";
         
        String message = String.format(format, mAccessToken.getToken(), date);
        if (hasExisted) {
            message = "Token ������Ч���ڣ������ٴε�¼��" + "\n" + message;
        }
        //MyLog.toLog(message);
    }
    
	/**
     * �� SSO ��Ȩ Activity �˳�ʱ���ú��������á�
     *
     * @see {@link Activity#onActivityResult}
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
         
        // SSO ��Ȩ�ص�
        // ��Ҫ������ SSO ��½�� Activity ������д onActivityResult
        if (mSsoHandler != null) {
            mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
        }
    }
    
    public void WeiboShare(){
    	if(checkVersion()){
            try {
                // ���΢���ͻ��˻����Ƿ����������δ��װ΢���������Ի���ѯ���û�����΢���ͻ���
                if (mWeiboShareAPI.checkEnvironment(true)) {
                     
                    // ע�������Ӧ�� ��΢���ͻ����У�ע��ɹ����Ӧ�ý���ʾ��΢����Ӧ���б��С�
                    // ���ø��������ɷ���Ȩ����Ҫ�������룬������鿴 Demo ��ʾ
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
     * ������Ӧ�÷���������Ϣ��΢��������΢��������档
     * @see {@link #sendMultiMessage} ���� {@link #sendSingleMessage}
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
            Toast.makeText(this, "΢���ͻ��˲�֧�֣��������汾", Toast.LENGTH_SHORT).show();
        }
    }
    
    /**
     * ������Ӧ�÷���������Ϣ��΢��������΢��������档
     * ע�⣺�� {@link IWeiboShareAPI#getWeiboAppSupportAPI()} >= 10351 ʱ��֧��ͬʱ���������Ϣ��
     * ͬʱ���Է����ı���ͼƬ�Լ�����ý����Դ����ҳ�����֡���Ƶ�������е�һ�֣���
     *
     * @param hasText    ����������Ƿ����ı�
     * @param hasImage   ����������Ƿ���ͼƬ
     * @param hasWebpage ����������Ƿ�����ҳ
     * @param hasMusic   ����������Ƿ�������
     * @param hasVideo   ����������Ƿ�����Ƶ
     * @param hasVoice   ����������Ƿ�������
     */
    private void sendMultiMessage(boolean hasText, boolean hasImage, boolean hasWebpage,
            boolean hasMusic, boolean hasVideo, boolean hasVoice) {
         
        // 1. ��ʼ��΢���ķ�����Ϣ
        WeiboMultiMessage weiboMessage = new WeiboMultiMessage();
        if (hasText) {
            weiboMessage.textObject = getTextObj();
        }
         
        if (hasImage) {
            weiboMessage.imageObject = getImageObj();
        }
         
        // �û����Է�������ý����Դ����ҳ�����֡���Ƶ�������е�һ�֣�
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
         
        // 2. ��ʼ���ӵ�������΢������Ϣ����
        SendMultiMessageToWeiboRequest request = new SendMultiMessageToWeiboRequest();
        // ��transactionΨһ��ʶһ������
        request.transaction = String.valueOf(System.currentTimeMillis());
        request.multiMessage = weiboMessage;
         
        // 3. ����������Ϣ��΢��������΢���������
        mWeiboShareAPI.sendRequest(request);
    }
    
    /**
     * ������Ӧ�÷���������Ϣ��΢��������΢��������档
     * ��{@link IWeiboShareAPI#getWeiboAppSupportAPI()} < 10351 ʱ��ֻ֧�ַ�������Ϣ����
     * �ı���ͼƬ����ҳ�����֡���Ƶ�е�һ�֣���֧��Voice��Ϣ��
     *
     * @param hasText    ����������Ƿ����ı�
     * @param hasImage   ����������Ƿ���ͼƬ
     * @param hasWebpage ����������Ƿ�����ҳ
     * @param hasMusic   ����������Ƿ�������
     * @param hasVideo   ����������Ƿ�����Ƶ
     */
    private void sendSingleMessage(boolean hasText, boolean hasImage, boolean hasWebpage,
            boolean hasMusic, boolean hasVideo/*, boolean hasVoice*/) {
         
        // 1. ��ʼ��΢���ķ�����Ϣ
        // �û����Է����ı���ͼƬ����ҳ�����֡���Ƶ�е�һ��
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
         
        // 2. ��ʼ���ӵ�������΢������Ϣ����
        SendMessageToWeiboRequest request = new SendMessageToWeiboRequest();
        // ��transactionΨһ��ʶһ������
        request.transaction = String.valueOf(System.currentTimeMillis());
        request.message = weiboMessage;
         
        // 3. ����������Ϣ��΢��������΢���������
        mWeiboShareAPI.sendRequest(request);
    }
    
    /**
     * �����ı���Ϣ����
     *
     * @return �ı���Ϣ����
     */
    private TextObject getTextObj() {
        TextObject textObject = new TextObject();
        String  text = String.format("������ʹ��΢���ͻ��˷�������������", "��%1$s���������� �˲�һ�� %2$s��",
                "https://github.com/mobileresearch/weibo_android_sdk/blob/master/WeiboSDKDemo.apk");
        textObject.text =text;
        return textObject;
    }
 
    /**
     * ����ͼƬ��Ϣ����
     *
     * @return ͼƬ��Ϣ����
     */
    private ImageObject getImageObj() {
        ImageObject imageObject = new ImageObject();
        Bitmap shareImage = BitmapFactory.decodeResource(getResources(), R.drawable.tips_detail_image_preview);
        imageObject.setImageObject(shareImage);
        return imageObject;
    }
 
    /**
     * ������ý�壨��ҳ����Ϣ����
     *
     * @return ��ý�壨��ҳ����Ϣ����
     */
    private WebpageObject getWebpageObj() {
        WebpageObject mediaObject = new WebpageObject();
        mediaObject.identify = Utility.generateGUID();
        mediaObject.title =title;
        mediaObject.description = "this is share decription";
         
        // ���� Bitmap ���͵�ͼƬ����Ƶ������
       
        Bitmap shareImage = BitmapFactory.decodeResource(getResources(), R.drawable.tips_detail_image_preview);
        mediaObject.setThumbImage(shareImage);
        mediaObject.actionUrl = url;
        mediaObject.defaultText = "This is default text";
        return mediaObject;
    }
 
    /**
     * ������ý�壨���֣���Ϣ����
     *
     * @return ��ý�壨���֣���Ϣ����
     */
    private MusicObject getMusicObj() {
        // ����ý����Ϣ
        MusicObject musicObject = new MusicObject();
        musicObject.identify = Utility.generateGUID();
        musicObject.title =title;
        musicObject.description ="This is share de";
         
        Bitmap shareImage = BitmapFactory.decodeResource(getResources(), R.drawable.tips_detail_image_preview);
        // ���� Bitmap ���͵�ͼƬ����Ƶ������
        musicObject.setThumbImage(shareImage);
        musicObject.actionUrl = url;
        //musicObject.dataUrl = dataUrl;
        //musicObject.dataHdUrl =dataHdUrl;
        musicObject.duration = 10;
        musicObject.defaultText = "This is default text";
        return musicObject;
    }
    /**
     * ������ý�壨��Ƶ����Ϣ����
     *
     * @return ��ý�壨��Ƶ����Ϣ����
     */
    private VideoObject getVideoObj() {
        // ����ý����Ϣ
        VideoObject videoObject = new VideoObject();
        videoObject.identify = Utility.generateGUID();
        videoObject.title =title;
        videoObject.description =description;
         
        Bitmap shareImage = BitmapFactory.decodeResource(getResources(), R.drawable.tips_detail_image_preview);
        // ���� Bitmap ���͵�ͼƬ����Ƶ������
        videoObject.setThumbImage(shareImage);
        videoObject.actionUrl =url;
        //videoObject.dataUrl = dataUrl;
        //videoObject.dataHdUrl = dataHdUrl;
        videoObject.duration = 10;
        videoObject.defaultText = "This is default text";
        return videoObject;
    }
 
    /**
     * ������ý�壨��Ƶ����Ϣ����
     *
     * @return ��ý�壨���֣���Ϣ����
     */
    private VoiceObject getVoiceObj() {
        // ����ý����Ϣ
        VoiceObject voiceObject = new VoiceObject();
        voiceObject.identify = Utility.generateGUID();
        voiceObject.title =title;
        voiceObject.description = description;
         
        Bitmap shareImage = BitmapFactory.decodeResource(getResources(), R.drawable.tips_detail_image_preview);
        // ���� Bitmap ���͵�ͼƬ����Ƶ������
        voiceObject.setThumbImage(shareImage);
        voiceObject.actionUrl = url;
        //voiceObject.dataUrl = dataUrl;
        //voiceObject.dataHdUrl =dataHdUrl;
        voiceObject.duration = 10;
        voiceObject.defaultText ="this is default text";
        return voiceObject;
    }
    /**
     * �Ƿ��а�װ΢���ͻ���
     */
    public Boolean checkVersion(){
        // ����΢������ӿ�ʵ��
        mWeiboShareAPI = WeiboShareSDK.createWeiboAPI(this, APP_KEY_WEIBO);
        // ���δ��װ΢���ͻ��ˣ���������΢����Ӧ�Ļص�
        if (!mWeiboShareAPI.isWeiboAppInstalled()) {
            mWeiboShareAPI.registerWeiboDownloadListener(new IWeiboDownloadListener() {
                @Override
                public void onCancel() {
                    Toast.makeText(Decorate_tips_share_window.this, "ȡ������΢���ͻ���",  Toast.LENGTH_SHORT).show();
                }
            });
            return false;
        }
        return true;
    }
    
	@Override
	public void onResponse(BaseResponse baseResp) {
		// TODO �Զ����ɵķ������
		switch (baseResp.errCode) {
        case WBConstants.ErrorCode.ERR_OK:
            Toast.makeText(this, "����ɹ�", Toast.LENGTH_LONG).show();
            break;
        case WBConstants.ErrorCode.ERR_CANCEL:
            Toast.makeText(this, "����ȡ��", Toast.LENGTH_LONG).show();
            break;
        case WBConstants.ErrorCode.ERR_FAIL:
            Toast.makeText(this, "����ʧ��" + "Error Message: " + baseResp.errMsg, Toast.LENGTH_LONG).show();
            break;
        }
	}
}
