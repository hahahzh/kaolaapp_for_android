package com.winwinapp.network;

import android.text.TextUtils;

public class HTTPPost {

//	public static boolean RequestDecorateTips(NetworkData.DecorateTipsData data,NetworkData.DecorateTipsBack result){
//		String postData = JsonHandler.createTipsListString(data);
//		String back = HTTPBase.HTTPSend(NetworkData.URL_DECORATE_TIPS_LIST, postData, HTTPBase.HTTP_TYPE_POST);
//		if(TextUtils.isDigitsOnly(back)){
//			
//		}else{
//			result.code = "-1";
//			result.error = "网络不可用";
//		}
//		return true;
//	}
	
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
	
	public static boolean RequestDecorateTipList(NetworkData.DecorateTipsData data,NetworkData.DecorateTipsBack msgBack){
		boolean success = false;
		String postData = JsonHandler.createDecorateTipListString(data);
		String back = HTTPBase.HTTPSend(NetworkData.URL_DECKMS, postData, HTTPBase.HTTP_TYPE_POST);
		if(!TextUtils.isEmpty(back)){
			success = JsonHandler.parseDecorateTipList(back, msgBack);
		}else{
			msgBack.code = -1;
			msgBack.error = "网络不可用";
		}
		return success;
	}
	
	public static boolean RequestDecorateTipDetail(NetworkData.DecorateTipDetailData data,NetworkData.DecorateTipDetailBack msgBack){
		boolean success = false;
		String postData = JsonHandler.createDecorateTipDeailString(data);
		String back = HTTPBase.HTTPSend(NetworkData.URL_DECORATE_TIPS_DETAIL, postData, HTTPBase.HTTP_TYPE_POST);
		if(!TextUtils.isEmpty(back)){
			success = JsonHandler.parseDecorateTipDetail(back, msgBack);
		}else{
			msgBack.code = -1;
			msgBack.error = "网络不可用";
		}
		return success;
	}
	
	public static boolean userEvalue(NetworkData.UserEvalueData data,NetworkData.CommonBack msgBack){
		boolean success = false;
		String postData = JsonHandler.createUserEvalueString(data);
		String back = HTTPBase.HTTPSend(NetworkData.URL_USER_EVALUE, postData, HTTPBase.HTTP_TYPE_POST);
		if(!TextUtils.isEmpty(back)){
			success = JsonHandler.parseCommonBack(back, msgBack);
			if(msgBack.code == 1){
				success = true;
			}
		}else{
			msgBack.code = -1;
			msgBack.error = "网络不可用";
		}
		return success;
	}
	
	public static boolean setDeposit(NetworkData.SetDepositData data,NetworkData.CommonBack msgBack){
		boolean success = false;
		String postData = JsonHandler.createSetDepositString(data);
		String back = HTTPBase.HTTPSend(NetworkData.URL_SET_DEPOSIT, postData, HTTPBase.HTTP_TYPE_POST);
		if(!TextUtils.isEmpty(back)){
			success = JsonHandler.parseCommonBack(back, msgBack);
			if(msgBack.code == 1){
				success = true;
			}
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
	
	public static boolean RequestDecorateList(NetworkData.BidListData data,NetworkData.BidListBack msgBack){
		boolean success = false;
		String postData = JsonHandler.createUserBidListString(data);
		String back = HTTPBase.HTTPSend(NetworkData.URL_DECORATE_LIST, postData, HTTPBase.HTTP_TYPE_POST);
		if(!TextUtils.isEmpty(back)){
			success = JsonHandler.parseUserBidList(back, msgBack);
		}else{
			msgBack.code = -1;
			msgBack.error = "网络不可用";
		}
		return success;
	}
	
	public static boolean RequestOtherDecorateList(NetworkData.BidListData data,NetworkData.BidListBack msgBack){
		boolean success = false;
		String postData = JsonHandler.createUserBidListString(data);
		String back = HTTPBase.HTTPSend(NetworkData.URL_OTHER_DECORATE_LIST, postData, HTTPBase.HTTP_TYPE_POST);
		if(!TextUtils.isEmpty(back)){
			success = JsonHandler.parseOtherDecorateList(back, msgBack);
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
	
	public static boolean RequestBid(NetworkData.BidVieData data,NetworkData.CommonBack msgBack){
		boolean success = false;
		String postData = JsonHandler.createBidVieString(data);
		String back = HTTPBase.HTTPSend(NetworkData.URL_BID_VIE, postData, HTTPBase.HTTP_TYPE_POST);
		if(!TextUtils.isEmpty(back)){
			success = JsonHandler.parseCommonBack(back, msgBack);
		}else{
			msgBack.code = -1;
			msgBack.error = "网络不可用";
		}
		return success;
	}
	
	public static boolean RequestBidAbort(NetworkData.BidAbortData data,NetworkData.CommonBack msgBack){
		boolean success = false;
		String postData = JsonHandler.createBidAbortString(data);
		String back = HTTPBase.HTTPSend(NetworkData.URL_BID_ABORT, postData, HTTPBase.HTTP_TYPE_POST);
		if(!TextUtils.isEmpty(back)){
			success = JsonHandler.parseCommonBack(back, msgBack);
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
	
	public static boolean PostUserBidEdit(NetworkData.BidPostEditData data,NetworkData.CommonBack msgBack){
		boolean success = false;
		String postData = JsonHandler.createUserBidEditPostString(data);
		String back = HTTPBase.HTTPSend(NetworkData.URL_USER_BID_EDIT_FINISH, postData, HTTPBase.HTTP_TYPE_POST);
		if(!TextUtils.isEmpty(back)){
			success = JsonHandler.parseCommonBack(back, msgBack);
		}else{
			msgBack.code = -1;
			msgBack.error = "网络不可用";
		}
		return success;
	}
	
	public static boolean DeletePublicMsg(NetworkData.DeletePublicMsgData data,NetworkData.CommonBack msgBack){
		boolean success = false;
		String postData = JsonHandler.createDeletePublicMsgString(data);
		String back = HTTPBase.HTTPSend(NetworkData.URL_DELETE_PUBLIC_MSG, postData, HTTPBase.HTTP_TYPE_POST);
		if(!TextUtils.isEmpty(back)){
			success = JsonHandler.parseDeletePublicMsg(back, msgBack);
		}else{
			msgBack.code = -1;
			msgBack.error = "网络不可用";
		}
		return success;
	}
	
	public static boolean DeletePrivateMsg(NetworkData.DeletePrivateMsgData data,NetworkData.CommonBack msgBack){
		boolean success = false;
		String postData = JsonHandler.createDeletePrivateMsgString(data);
		String back = HTTPBase.HTTPSend(NetworkData.URL_DELETE_PRIVATE_MSG, postData, HTTPBase.HTTP_TYPE_POST);
		if(!TextUtils.isEmpty(back)){
			success = JsonHandler.parseDeletePrivateMsg(back, msgBack);
		}else{
			msgBack.code = -1;
			msgBack.error = "网络不可用";
		}
		return success;
	}
	
	public static boolean RegisterUser(NetworkData.RegisterData data,NetworkData.RegisterBack msgBack){
		boolean success = false;
		String postData = JsonHandler.createRegisterString(data);
		String back = HTTPBase.HTTPSend(NetworkData.URL_REGISTER, postData, HTTPBase.HTTP_TYPE_POST);
		if(!TextUtils.isEmpty(back)){
			success = JsonHandler.parseRegister(back, msgBack);
		}else{
			msgBack.code = -1;
			msgBack.error = "网络不可用";
		}
		return success;
	}
	
	public static boolean RegisterSendCode(NetworkData.RegisterSendCodeData data,NetworkData.CommonBack msgBack){
		boolean success = false;
		String postData = JsonHandler.createRegisterSendCodeString(data);
		String back = HTTPBase.HTTPSend(NetworkData.URL_REGISTER_SEND_CODE, postData, HTTPBase.HTTP_TYPE_POST);
		if(!TextUtils.isEmpty(back)){
			success = JsonHandler.parseCommonBack(back, msgBack);
		}else{
			msgBack.code = -1;
			msgBack.error = "网络不可用";
		}
		return success;
	}
	
	public static boolean ResetPWDSendCode(NetworkData.RegisterSendCodeData data,NetworkData.CommonBack msgBack){
		boolean success = false;
		String postData = JsonHandler.createResetPWDSendCodeString(data);
		String back = HTTPBase.HTTPSend(NetworkData.URL_REGISTER_SEND_CODE, postData, HTTPBase.HTTP_TYPE_POST);
		if(!TextUtils.isEmpty(back)){
			success = JsonHandler.parseCommonBack(back, msgBack);
		}else{
			msgBack.code = -1;
			msgBack.error = "网络不可用";
		}
		return success;
	}
	
	public static boolean ResetPWD(NetworkData.RegisterSendCodeData data,NetworkData.CommonBack msgBack){
		boolean success = false;
		String postData = JsonHandler.createResetPWDString(data);
		String back = HTTPBase.HTTPSend(NetworkData.URL_REGISTER_RESET_PWD, postData, HTTPBase.HTTP_TYPE_POST);
		if(!TextUtils.isEmpty(back)){
			success = JsonHandler.parseCommonBack(back, msgBack);
		}else{
			msgBack.code = -1;
			msgBack.error = "网络不可用";
		}
		return success;
	}
	
	public static boolean RequestMyContractList(NetworkData.BidListData data,NetworkData.MyContractListBack msgBack){
		boolean success = false;
		String postData = JsonHandler.createUserBidListString(data);
		String back = HTTPBase.HTTPSend(NetworkData.URL_MY_CONTRACT_LIST, postData, HTTPBase.HTTP_TYPE_POST);
		if(!TextUtils.isEmpty(back)){
			success = JsonHandler.parseMyContractList(back, msgBack);
		}else{
			msgBack.code = -1;
			msgBack.error = "网络不可用";
		}
		return success;
	}
	
	public static boolean RequestMyContractDetail(NetworkData.BidDetailData data,NetworkData.MyContractDetailBack msgBack){
		boolean success = false;
		String postData = JsonHandler.createMyContractDetailString(data);
		String back = HTTPBase.HTTPSend(NetworkData.URL_MY_CONTRACT_DETAIL, postData, HTTPBase.HTTP_TYPE_POST);
		if(!TextUtils.isEmpty(back)){
			success = JsonHandler.parseMyContractDetail(back, msgBack);
		}else{
			msgBack.code = -1;
			msgBack.error = "网络不可用";
		}
		return success;
	}
	
	public static boolean RequestDesignerContractDetail(NetworkData.BidDetailData data,NetworkData.MyContractDetailBack msgBack){
		boolean success = false;
		String postData = JsonHandler.createMyContractDetailString(data);
		String back = HTTPBase.HTTPSend(NetworkData.URL_MY_CONTRACT_DETAIL, postData, HTTPBase.HTTP_TYPE_POST);
		if(!TextUtils.isEmpty(back)){
			success = JsonHandler.parseDesignerContractDetail(back, msgBack);
		}else{
			msgBack.code = -1;
			msgBack.error = "网络不可用";
		}
		return success;
	}
	
	public static boolean UpdateDesignerContract(NetworkData.MyContractDetailBack data,NetworkData.CommonBack msgBack){
		boolean success = false;
		String postData = JsonHandler.createUpdateDesignerContractString(data);
		String back = HTTPBase.HTTPSend(NetworkData.URL_CONTRACT_UPDATE, postData, HTTPBase.HTTP_TYPE_POST);
		if(!TextUtils.isEmpty(back)){
			success = JsonHandler.parseCommonBack(back, msgBack);
		}else{
			msgBack.code = -1;
			msgBack.error = "网络不可用";
		}
		return success;
	}
	
	public static boolean SendMessage(NetworkData.SendMessageData data,NetworkData.CommonBack msgBack, int type){//0,public; 1,private
		boolean success = false;
		String url = null;
		if(type == 0){
			url = NetworkData.URL_SEND_PUBLIC_MESSAGE;
		}else if(type == 1){
			url = NetworkData.URL_SEND_PRIVATE_MESSAGE;
		}
		String postData = JsonHandler.createSendMessageString(data,type);
		String back = HTTPBase.HTTPSend(url, postData, HTTPBase.HTTP_TYPE_POST);
		if(!TextUtils.isEmpty(back)){
			success = JsonHandler.parseBidPublish(back, msgBack);
		}else{
			msgBack.code = -1;
			msgBack.error = "网络不可用";
		}
		return success;
	}
	
	public static boolean SendMyContractAgree(NetworkData.ContractAgreeRejectData data,NetworkData.CommonBack msgBack){
		boolean success = false;
		String postData = JsonHandler.createMyContractAgreeString(data);
		String back = HTTPBase.HTTPSend(NetworkData.URL_MY_CONTRACT_AGREE, postData, HTTPBase.HTTP_TYPE_POST);
		if(!TextUtils.isEmpty(back)){
			success = JsonHandler.parseCommonBack(back, msgBack);
		}else{
			msgBack.code = -1;
			msgBack.error = "网络不可用";
		}
		return success;
	}
	
	public static boolean SendMyContractReject(NetworkData.ContractAgreeRejectData data,NetworkData.CommonBack msgBack){
		boolean success = false;
		String postData = JsonHandler.createMyContractRejectString(data);
		String back = HTTPBase.HTTPSend(NetworkData.URL_MY_CONTRACT_REJECT, postData, HTTPBase.HTTP_TYPE_POST);
		if(!TextUtils.isEmpty(back)){
			success = JsonHandler.parseCommonBack(back, msgBack);
		}else{
			msgBack.code = -1;
			msgBack.error = "网络不可用";
		}
		return success;
	}
	
}
