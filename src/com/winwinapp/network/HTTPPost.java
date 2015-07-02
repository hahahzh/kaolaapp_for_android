package com.winwinapp.network;

import android.text.TextUtils;

public class HTTPPost {

	public static boolean RequestDecorateTips(NetworkData.DecorateTipsData data,NetworkData.DecorateTipsBack result){
		String postData = JsonHandler.createTipsListString(data);
		String back = HTTPBase.HTTPSend(NetworkData.URL_DECORATE_TIPS_LIST, postData, HTTPBase.HTTP_TYPE_POST);
		if(TextUtils.isDigitsOnly(back)){
			
		}else{
			result.code = "-1";
			result.error = "网络不可用";
		}
		return true;
	}
	
	public static boolean sendLoginData(NetworkData.LoginData loginData,NetworkData.LoginBack loginBack){
		boolean success = false;
		String postData = JsonHandler.createLoginString(loginData);
		String back = HTTPBase.HTTPSend(NetworkData.URL_LOGIN, postData, HTTPBase.HTTP_TYPE_POST);
		if(!TextUtils.isEmpty(back)){
			success = JsonHandler.parseLoginBack(back, loginBack);
		}else{
			loginBack.code = -1;
			loginBack.error = "网络不可用";
		}
		return success;
	}
	
	public static boolean RequestPublicMsgList(NetworkData.PublicMessageListData data, NetworkData.PublicMessageListBack msgBack){
		boolean success = false;
		String postData = JsonHandler.createPublicMsgListString(data);
		String back = HTTPBase.HTTPSend(NetworkData.URL_PUBLIC_MESSAGE_LIST, postData, HTTPBase.HTTP_TYPE_POST);
		if(!TextUtils.isEmpty(back)){
			success = JsonHandler.parsePublicMsgList(back, msgBack);
		}else{
			msgBack.code = -1;
			msgBack.error = "网络不可用";
		}
		return success;
	}
	
	public static boolean RequestPrivateMsgList(NetworkData.PrivateMessageListData data, NetworkData.PrivateMessageListBack msgBack){
		boolean success = false;
		String postData = JsonHandler.createPrivateMsgListString(data);
		String back = HTTPBase.HTTPSend(NetworkData.URL_PRIVATE_MESSAGE_LIST, postData, HTTPBase.HTTP_TYPE_POST);
		if(!TextUtils.isEmpty(back)){
			success = JsonHandler.parsePrivateMsgList(back, msgBack);
		}else{
			msgBack.code = -1;
			msgBack.error = "网络不可用";
		}
		return success;
	}
	
	public static boolean RequestPublicMsgInfo(NetworkData.PublicMessageInfoData data,NetworkData.PublicMessageInfoBack msgBack){
		boolean success = false;
		String postData = JsonHandler.createPublicMsgListString(data);
		String back = HTTPBase.HTTPSend(NetworkData.URL_PUBLIC_MESSAGE_INFO, postData, HTTPBase.HTTP_TYPE_POST);
		if(!TextUtils.isEmpty(back)){
			success = JsonHandler.parsePublicMsgInfo(back, msgBack);
		}else{
			msgBack.code = -1;
			msgBack.error = "网络不可用";
		}
		return success;
	}
	
	public static boolean RequestPrivateMsgInfo(NetworkData.PrivateMessageInfoData data,NetworkData.PrivateMessageInfoBack msgBack){
		boolean success = false;
		String postData = JsonHandler.createPrivateMsgInfoString(data);
		String back = HTTPBase.HTTPSend(NetworkData.URL_PRIVATE_MESSAGE_INFO, postData, HTTPBase.HTTP_TYPE_POST);
		if(!TextUtils.isEmpty(back)){
			success = JsonHandler.parsePrivateMsgInfo(back, msgBack);
		}else{
			msgBack.code = -1;
			msgBack.error = "网络不可用";
		}
		return success;
	}
	
	public static boolean RequestBidList(NetworkData.BidListData data,NetworkData.BidListBack msgBack){
		boolean success = false;
		String postData = JsonHandler.createBidListString(data);
		String back = HTTPBase.HTTPSend(NetworkData.URL_BID_LIST, postData, HTTPBase.HTTP_TYPE_POST);
		if(!TextUtils.isEmpty(back)){
			success = JsonHandler.parseBidList(back, msgBack);
		}else{
			msgBack.code = -1;
			msgBack.error = "网络不可用";
		}
		return success;
	}
	
	public static boolean RequestBidDetail(NetworkData.BidDetailData data,NetworkData.BidListBack msgBack){
		boolean success = false;
		String postData = JsonHandler.createBidDetailString(data);
		String back = HTTPBase.HTTPSend(NetworkData.URL_BID_DETAIL, postData, HTTPBase.HTTP_TYPE_POST);
		if(!TextUtils.isEmpty(back)){
			success = JsonHandler.parseBidDetail(back, msgBack);
		}else{
			msgBack.code = -1;
			msgBack.error = "网络不可用";
		}
		return success;
	}
	
	public static boolean PublishBids(NetworkData.BidPublishData data,NetworkData.CommonBack msgBack){
		boolean success = false;
		String postData = JsonHandler.createBidPublishString(data);
		String back = HTTPBase.HTTPSend(NetworkData.URL_BID_PUBLISH, postData, HTTPBase.HTTP_TYPE_POST);
		if(!TextUtils.isEmpty(back)){
			success = JsonHandler.parseBidPublish(back, msgBack);
		}else{
			msgBack.code = -1;
			msgBack.error = "网络不可用";
		}
		return success;
	}
	
	public static boolean RequestUserBidList(NetworkData.BidListData data,NetworkData.BidListBack msgBack){
		boolean success = false;
		String postData = JsonHandler.createUserBidListString(data);
		String back = HTTPBase.HTTPSend(NetworkData.URL_USER_BID_LIST, postData, HTTPBase.HTTP_TYPE_POST);
		if(!TextUtils.isEmpty(back)){
			success = JsonHandler.parseUserBidList(back, msgBack);
		}else{
			msgBack.code = -1;
			msgBack.error = "网络不可用";
		}
		return success;
	}
	
	public static boolean RequestUserBidDetail(NetworkData.BidDetailData data,NetworkData.UserBidDetailBack msgBack){
		boolean success = false;
		String postData = JsonHandler.createUserBidDetailString(data);
		String back = HTTPBase.HTTPSend(NetworkData.URL_USER_BID_DETAIL, postData, HTTPBase.HTTP_TYPE_POST);
		if(!TextUtils.isEmpty(back)){
			success = JsonHandler.parseUserBidDetail(back, msgBack);
		}else{
			msgBack.code = -1;
			msgBack.error = "网络不可用";
		}
		return success;
	}
	
	public static boolean GetRegions(NetworkData.RegionsData data,NetworkData.RegionsBack msgBack){
		boolean success = false;
		String postData = JsonHandler.createRegionsString(data);
		String back = HTTPBase.HTTPSend(NetworkData.URL_GET_REGIONS, postData, HTTPBase.HTTP_TYPE_POST);
		if(!TextUtils.isEmpty(back)){
			success = JsonHandler.parseRegions(back, msgBack);
		}else{
			msgBack.code = -1;
			msgBack.error = "网络不可用";
		}
		return success;
	}
	
	public static boolean RequestUserBidEdit(NetworkData.BidDetailData data,NetworkData.UserBidEditBack msgBack){
		boolean success = false;
		String postData = JsonHandler.createUserBidEditString(data);
		String back = HTTPBase.HTTPSend(NetworkData.URL_USER_BID_EDIT, postData, HTTPBase.HTTP_TYPE_POST);
		if(!TextUtils.isEmpty(back)){
			success = JsonHandler.parseUserBidEdit(back, msgBack);
		}else{
			msgBack.code = -1;
			msgBack.error = "网络不可用";
		}
		return success;
	}
}
