package com.winwinapp.network;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.winwinapp.decorateTips.SubDecorateTips;
import com.winwinapp.koala.KoalaApplication;

public class JsonHandler {

	public static String createRegisterString(NetworkData.RegisterData data){
		String str = null;
		JSONObject jstring = new JSONObject();
		try {
			jstring.put("username", data.username);
			jstring.put("password", data.password);
			jstring.put("type", data.type);
			jstring.put("email", data.email);
			jstring.put("mobile", data.mobile);
			
			str = jstring.toString();
		} catch (JSONException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return str;
	}
	
	public static String createRegisterSendCodeString(NetworkData.RegisterSendCodeData data){
		String str = null;
		JSONObject jstring = new JSONObject();
		try {
			jstring.put("phone_mail", data.phone_mail);
			jstring.put("is_email", data.is_email);
			jstring.put("auth_code", data.auth_code);
			
			str = jstring.toString();
		} catch (JSONException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return str;
	}
	
	public static String createResetPWDSendCodeString(NetworkData.RegisterSendCodeData data){
		String str = null;
		JSONObject jstring = new JSONObject();
		try {
			jstring.put("phone_mail", data.phone_mail);
			//jstring.put("is_email", data.is_email);
			//jstring.put("auth_code", data.auth_code);
			
			str = jstring.toString();
		} catch (JSONException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return str;
	}
	
	public static String createResetPWDString(NetworkData.RegisterSendCodeData data){
		String str = null;
		JSONObject jstring = new JSONObject();
		try {
			jstring.put("phone_mail", data.phone_mail);
			//jstring.put("is_email", data.is_email);
			jstring.put("auth_code", data.auth_code);
			
			str = jstring.toString();
		} catch (JSONException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return str;
	}
	
	public static boolean parseRegister(String str,NetworkData.RegisterBack regBack){
		boolean success = false;
		try {
			JSONObject response = new JSONObject(str);
			int code = -1;
			code = response.getInt("code");
			regBack.code = code;
			if(code == 0){
				regBack.id = response.getString("id");
				regBack.sessid = response.getString("sessid");
				regBack.user_type = response.getInt("user_type");
				success = true;
			}else{
				regBack.error = response.getString("error");
				success = false;
			}
		} catch (JSONException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			return false;
		}
		
		return success;
	}
	
	public static String createAddDelString(NetworkData.AddDelMyCollectData data){
		String str = null;
		JSONObject jstring = new JSONObject();
		try {
			jstring.put("uid", data.uid);
			jstring.put("sessid", KoalaApplication.loginData.sessid);
			
			str = jstring.toString();
		} catch (JSONException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return str;
	}
	
	public static String createFindMemberDetailString(NetworkData.MemberDetailData data){
		String str = null;
		JSONObject jstring = new JSONObject();
		try {
			jstring.put("uid", data.uid);
			jstring.put("sessid", KoalaApplication.loginData.sessid);
			
			str = jstring.toString();
		} catch (JSONException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return str;
	}
	
	public static boolean parseFindMemberDetail(String str,NetworkData.MemberDetailBack back){
		boolean success = false;
		try {
			JSONObject response = new JSONObject(str);
			int code = -1;
			code = response.getInt("code");
			back.code = code;
			if(code == 0){
				JSONObject itemObject = response.getJSONObject("data");
				back.id = itemObject.getString("id");
				back.username = itemObject.getString("username");
				back.email = itemObject.getString("email");
				back.mobile = itemObject.getString("mobile");
				back.qq = itemObject.getString("qq");
				back.work_num = itemObject.getString("work_num");
				back.name_auth = itemObject.getString("name_auth");
				back.avatar = itemObject.getString("avatar");
				back.casename = itemObject.getString("casename");
				back.rate_avg = itemObject.getString("rate_avg");
				back.attud_avg = itemObject.getString("attud_avg");
				back.case_num = itemObject.getString("case_num");
				back.introduce = itemObject.getString("introduce");
				//项目经验
				try{
					JSONArray projects = itemObject.getJSONArray("project");
					if(null != projects && projects.length()>0){
						int len = projects.length();
						for(int i=0;i<len;i++){
							JSONObject jItem = projects.getJSONObject(i);
							NetworkData.ProjectExperienceItem item = NetworkData.getInstance().getNewProjectExperienceItem();
							item.area = jItem.getString("area");
							item.atud = jItem.getString("atud");
							item.biotope_name = jItem.getString("biotope_name");
							item.cmt = jItem.getString("cmt");
							item.datetime = jItem.getString("datetime");
							item.name = jItem.getString("name");
							item.rate = jItem.getString("rate");
							back.exps.add(item);
						}
					}
				}catch(JSONException e){
					
				}
				

				success = true;
			}else{
				back.error = response.getString("error");
				success = false;
			}
		} catch (JSONException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			back.error = e.toString();
			return false;
		}
		
		return success;
	}
	
	public static String createMyCollectString(NetworkData.MyCollectData data){
		String str = null;
		JSONObject jstring = new JSONObject();
		try {
			jstring.put("typeid", data.typeid);
			jstring.put("page", data.page);
			jstring.put("limit", data.limit);
			jstring.put("sessid", KoalaApplication.loginData.sessid);
			
			str = jstring.toString();
		} catch (JSONException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return str;
	}
	
	public static boolean parseMyCollect(String str,NetworkData.MyCollectBack back){
		boolean success = false;
		try {
			JSONObject response = new JSONObject(str);
			int code = -1;
			code = response.getInt("code");
			back.code = code;
			if(code == 0){
				int total = Integer.parseInt(response.getString("total"));
				JSONArray array = response.getJSONArray("data");
				total = array.length();
				back.total = total;
				for(int i=0;i<total;i++){
					JSONObject itemObject = array.getJSONObject(i);
					NetworkData.FindMemberItem item = NetworkData.getInstance().getNewFindMemberItem();
					item.id = itemObject.getString("id");
					item.username = itemObject.getString("username");
					item.work_num = itemObject.getString("work_num");
					item.name_auth = itemObject.getString("name_auth");
					item.avatar = itemObject.getString("avatar");
					item.casename = itemObject.getString("casename");
					item.rate_avg = itemObject.getString("rate_avg");
					item.attud_avg = itemObject.getString("attud_avg");
					item.case_num = itemObject.getString("case_num");
					item.introduce = itemObject.getString("introduce");
					
					back.memberInfo.add(item);
				}
				success = true;
			}else{
				back.error = response.getString("error");
				success = false;
			}
		} catch (JSONException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			back.error = e.toString();
			return false;
		}
		
		return success;
	}
	
	public static String createModifyAccountInfo(NetworkData.AccountInfoBack data){
		String str = null;
		JSONObject jstring = new JSONObject();
		try {
			jstring.put("id", data.id);
			jstring.put("qq", data.qq);
			jstring.put("work_num", data.work_num);
			jstring.put("city_id", data.city_id);
			jstring.put("introduce", data.introduce);
			jstring.put("sessid", KoalaApplication.loginData.sessid);
			
			str = jstring.toString();
		} catch (JSONException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return str;
	}
	
	public static boolean parseAdList(String str,NetworkData.GetAdListBack back){
		boolean success = false;
		try {
			JSONObject response = new JSONObject(str);
			int code = -1;
			code = response.getInt("code");
			back.code = code;
			if(code == 0){
				int total = 0;
				JSONObject data = response.getJSONObject("data");
				JSONArray banner = data.getJSONArray("banner");
				total = banner.length();
				for(int i=0;i<total;i++){
					JSONObject itemObject = banner.getJSONObject(i);
					NetworkData.AdItem item = NetworkData.getInstance().getNewAdItem();
					item.url = itemObject.getString("url");
					item.src = itemObject.getString("src");		
					item.width = itemObject.getString("width");
					item.height = itemObject.getString("height");
					back.banner.add(item);
				}
				JSONObject leftAd = data.getJSONObject("leftAd");
				//back.leftAd.url = leftAd.getString("url");
				back.leftAd.src = leftAd.getString("src");		
				back.leftAd.width = leftAd.getString("width");
				back.leftAd.height = leftAd.getString("height");
				
				success = true;
			}else{
				back.error = response.getString("error");
				success = false;
			}
		} catch (JSONException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			back.error = e.toString();
			return false;
		}
		
		return success;
	}
	
	public static boolean parseAccountInfo(String str,NetworkData.AccountInfoBack back){
		boolean success = false;
		try {
			JSONObject response = new JSONObject(str);
			int code = -1;
			code = response.getInt("code");
			back.code = code;
			if(code == 0){
				int total = 0;
				JSONArray array = response.getJSONArray("province");
				total = array.length();
				for(int i=0;i<total;i++){
					JSONObject itemObject = array.getJSONObject(i);
					NetworkData.RegionsItem item = NetworkData.getInstance().getNewRegionsItem();
					item.regions_id = itemObject.getString("region_id");
					item.regions_name = itemObject.getString("region_name");					
					back.provinces.add(item);
				}
				JSONObject data = response.getJSONObject("data");
				back.id = data.getString("id");
				back.username = data.getString("username");
				back.qq = data.getString("qq");
				back.password = data.getString("password");
				back.city_id = data.getString("city_id");
				back.work_num = data.getString("work_num");
				back.introduce = data.getString("introduce");
				success = true;
			}else{
				back.error = response.getString("error");
				success = false;
			}
		} catch (JSONException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			back.error = e.toString();
			return false;
		}
		
		return success;
	}
	
	public static String createAccountInfoString(){
		String str = null;
		JSONObject jstring = new JSONObject();
		try {
			//jstring.put("page", data.page);
			jstring.put("sessid", KoalaApplication.loginData.sessid);
			
			str = jstring.toString();
		} catch (JSONException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return str;
	}
	
	public static String createGeneratePriceString(NetworkData.GeneratePriceData data){
		String str = null;
		JSONObject jstring = new JSONObject();
		try {
			jstring.put("area", data.area);
			jstring.put("chu", data.chu);
			jstring.put("room", data.room);
			jstring.put("ting", data.ting);
			jstring.put("wei", data.wei);
			jstring.put("yangtai", data.yangtai);
			jstring.put("dec_way", data.dec_way);
			//jstring.put("page", data.page);
			//jstring.put("sessid", KoalaApplication.loginData.sessid);
			
			str = jstring.toString();
		} catch (JSONException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return str;
	}
	
	public static boolean parseGeneratePrice(String str,NetworkData.GeneratePriceBack back){
		boolean success = false;
		try {
			JSONObject response = new JSONObject(str);
			int code = 0;
			code = response.getInt("code");
			back.code = code;
			if(code == 0){
				JSONObject data = response.getJSONObject("data");
				JSONObject values = data.getJSONObject("items_val");
				back.chug_num = values.getInt("chug_num");
				back.chug_price = values.getInt("chug_price");
				back.youyj_num = values.getInt("youyj_num");
				back.youyj_price = values.getInt("youyj_price");
				back.jingsq_num = values.getInt("jingsq_num");
				back.jingsq_price = values.getInt("jingsq_price");
				back.diz_num = values.getInt("diz_num");
				back.diz_price = values.getInt("diz_price");
				back.qiangz_num = values.getInt("qiangz_num");
				back.qiangz_price = values.getInt("qiangz_price");
				back.shuic_num = values.getInt("shuic_num");
				back.shuic_price = values.getInt("shuic_price");
				back.resq_num = values.getInt("resq_num");
				back.resq_price = values.getInt("resq_price");
				back.jicdd_num = values.getInt("jicdd_num");
				back.jicdd_price = values.getInt("jicdd_price");
				back.zuobq_num = values.getInt("zuobq_num");
				back.zuobq_price = values.getInt("zuobq_price");
				back.yusg_num = values.getInt("yusg_num");
				back.yusg_price = values.getInt("yusg_price");
				back.lingyf_num = values.getInt("lingyf_num");
				back.lingyf_price = values.getInt("lingyf_price");
				back.lingylt_num = values.getInt("lingylt_num");
				back.lingylt_price = values.getInt("lingylt_price");
				back.fangdm_num = values.getInt("fangdm_num");
				back.fangdm_price = values.getInt("fangdm_price");
				back.dib_num = values.getInt("dib_num");
				back.dib_price = values.getInt("dib_price");
				back.tul_num = values.getInt("tul_num");
				back.tul_price = values.getInt("tul_price");
				back.mum_num = values.getInt("mum_num");
				back.mum_price = values.getInt("mum_price");
				back.yangtc_num = values.getInt("yangtc_num");
				back.yangtc_price = values.getInt("yangtc_price");
				back.kaig_num = values.getInt("kaig_num");
				back.kaig_price = values.getInt("kaig_price");
				
				JSONObject prices = data.getJSONObject("prices");
				back.base_price = prices.getDouble("base_price");
				back.access_price = prices.getDouble("access_price");
				back.worker_price = prices.getDouble("worker_price");
				back.final_price = prices.getDouble("final_price");
				
				success = true;
			}else{
				back.error = response.getString("error");
				success = false;
			}
		} catch (JSONException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			back.error = e.toString();
			return false;
		}
		
		return success;
	}
	
	public static String createBindBankFinishString(NetworkData.DoBindBankData data){
		String str = null;
		JSONObject jstring = new JSONObject();
		try {
			jstring.put("bank_name", data.bank_name);
			jstring.put("bank_no", data.bank_no);
			jstring.put("auth_code", data.auth_code);
			jstring.put("sessid", KoalaApplication.loginData.sessid);
			
			str = jstring.toString();
		} catch (JSONException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return str;
	}
	
	public static String createBindBankString(NetworkData.BindBankData data){
		String str = null;
		JSONObject jstring = new JSONObject();
		try {
			jstring.put("id", data.id);
			jstring.put("is_on", data.is_on);
			jstring.put("uid", data.uid);
			jstring.put("bank_add", data.bank_add);
			jstring.put("bank_name", data.bank_name);
			jstring.put("bank_no", data.bank_no);
			jstring.put("idcard", data.idcard);
			jstring.put("mobile", data.mobile);
			jstring.put("real_name", data.real_name);
			//jstring.put("page", data.page);
			jstring.put("sessid", KoalaApplication.loginData.sessid);
			
			str = jstring.toString();
		} catch (JSONException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return str;
	}
	
	public static boolean parseBindBank(String str,NetworkData.BindBankBack back){
		boolean success = false;
		try {
			JSONObject response = new JSONObject(str);
			int code = -1;
			code = response.getInt("code");
			back.code = code;
			if(code == 0){
				success = true;
			}else{
				back.error = response.getString("error");
				success = false;
			}
		} catch (JSONException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			return false;
		}
		
		return success;
	}
	
	public static String createSendShortMessageString(String mobile){
		String str = null;
		JSONObject jstring = new JSONObject();
		try {
			jstring.put("mobile", mobile);
			jstring.put("sessid", KoalaApplication.loginData.sessid);
			
			str = jstring.toString();
		} catch (JSONException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return str;
	}
	public static String createGetPayListString(NetworkData.GetPayListData data){
		String str = null;
		JSONObject jstring = new JSONObject();
		try {
			jstring.put("limit", data.limit);
			//jstring.put("page", data.page);
			jstring.put("sessid", KoalaApplication.loginData.sessid);
			
			str = jstring.toString();
		} catch (JSONException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return str;
	}
	
	public static boolean parseGetPayList(String str,NetworkData.GetPayListBack back){
		boolean success = false;
		try {
			JSONObject response = new JSONObject(str);
			int code = -1;
			code = response.getInt("code");
			back.code = code;
			if(code == 0){
				int total = Integer.parseInt(response.getString("total"));
				JSONArray array = response.getJSONArray("data");
				total = array.length();
				back.total = total;
				for(int i=0;i<total;i++){
					JSONObject itemObject = array.getJSONObject(i);
					NetworkData.PayListItem item = NetworkData.getInstance().getNewPayListItem();
					item.operate_time = itemObject.getString("operate_time");
					item.username = itemObject.getString("username");
					//item.bill_type = itemObject.getString("bill_type");
					item.bill_info = itemObject.getString("bill_info");
					item.amount = itemObject.getString("amount");
					//item.channel = itemObject.getString("channel");
					item.ret = itemObject.getString("ret");
					
					back.items.add(item);
				}
				success = true;
			}else{
				back.error = response.getString("error");
				success = false;
			}
		} catch (JSONException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			back.error = e.toString();
			return false;
		}
		
		return success;
	}
	
	public static String createFindMemberString(NetworkData.FindMemberData data){
		String str = null;
		JSONObject jstring = new JSONObject();
		try {
			jstring.put("type", data.type);
			jstring.put("keyword", data.keyword);
			jstring.put("work_num", data.work_num);
			jstring.put("sort", data.sort);
			jstring.put("page", data.page);
			jstring.put("limit",data.limit);
			jstring.put("sessid", KoalaApplication.loginData.sessid);
			
			str = jstring.toString();
		} catch (JSONException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return str;
	}
	
	public static boolean parseFindMember(String str,NetworkData.FindMemberBack back){
		boolean success = false;
		try {
			JSONObject response = new JSONObject(str);
			int code = -1;
			code = response.getInt("code");
			back.code = code;
			if(code == 0){
				int total = Integer.parseInt(response.getString("total"));
				JSONArray array = response.getJSONArray("data");
				total = array.length();
				back.total = total;
				for(int i=0;i<total;i++){
					JSONObject itemObject = array.getJSONObject(i);
					NetworkData.FindMemberItem item = NetworkData.getInstance().getNewFindMemberItem();
					item.id = itemObject.getString("id");
					item.username = itemObject.getString("username");
					item.work_num = itemObject.getString("work_num");
					item.name_auth = itemObject.getString("name_auth");
					item.avatar = itemObject.getString("avatar");
					item.casename = itemObject.getString("casename");
					item.rate_avg = itemObject.getString("rate_avg");
					item.attud_avg = itemObject.getString("attud_avg");
					item.case_num = itemObject.getString("case_num");
					item.introduce = itemObject.getString("introduce");
					
					back.memberInfo.add(item);
				}
				success = true;
			}else{
				back.error = response.getString("error");
				success = false;
			}
		} catch (JSONException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			back.error = e.toString();
			return false;
		}
		
		return success;
	}
	
	public static String createIdAuthenString(NetworkData.IdAuthenData data){
		String str = null;
		JSONObject jstring = new JSONObject();
		try {
			jstring.put("real_name", data.real_name);
			jstring.put("idcard", data.idcard);
			jstring.put("sessid", KoalaApplication.loginData.sessid);
			
			
			str = jstring.toString();
		} catch (JSONException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return str;
	}
	
	public static String createLoginString(NetworkData.LoginData data){
		String str = null;
		JSONObject jstring = new JSONObject();
		try {
			jstring.put("username", data.username);
			jstring.put("password", data.password);
			
			str = jstring.toString();
		} catch (JSONException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return str;
	}
	
	public static boolean parseCommonBack(String str,NetworkData.CommonBack back){
		boolean success = false;
		try {
			JSONObject response = new JSONObject(str);
			int code = -1;
			code = response.getInt("code");
			back.code = code;
			if(code == 0){
				success = true;
			}else{
				back.error = response.getString("error");
				success = false;
			}
		} catch (JSONException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			return false;
		}
		
		return success;
	}
	
	public static String createSendMessageString(NetworkData.SendMessageData data, int type){
		String str = null;
		JSONObject jstring = new JSONObject();
		try {
			jstring.put("content", data.content);
			jstring.put("rec_id", data.rec_id);
			jstring.put("sessid", KoalaApplication.loginData.sessid);
			if(type == 1){
				jstring.put("topic_id", data.topic_id);
			}
			
			str = jstring.toString();
		} catch (JSONException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return str;
	}
	
	public static String createPrivateMsgListString(NetworkData.PrivateMessageListData data){
		String str = null;
		JSONObject jstring = new JSONObject();
		try {
			jstring.put("type", data.type);
			jstring.put("keyword", data.keyword);
			jstring.put("limit", data.limit);
			jstring.put("page", data.page);
			jstring.put("sessid", KoalaApplication.loginData.sessid);
			
			str = jstring.toString();
		} catch (JSONException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return str;
	}
	
	public static boolean parsePrivateMsgList(String str,NetworkData.PrivateMessageListBack back){
		boolean success = false;
		try {
			JSONObject response = new JSONObject(str);
			int code = -1;
			code = response.getInt("code");
			back.code = code;
			if(code == 0){
				int total = Integer.parseInt(response.getString("total"));
				JSONArray array = response.getJSONArray("data");
				total = array.length();
				back.total = total;
				for(int i=0;i<total;i++){
					JSONObject itemObject = array.getJSONObject(i);
					NetworkData.PrivateMessageListItem item = NetworkData.getInstance().getNewPrivateMessageListItem();
					item.id = itemObject.getString("id");
					item.send_id = itemObject.getString("send_id");
					item.content = itemObject.getString("content");
					item.type = itemObject.getString("type");
					item.user_type = itemObject.getString("user_type");
					item.topic_id = itemObject.getString("topic_id");
					item.send_time = itemObject.getString("send_time");
					item.bid_id = itemObject.getString("bid_id");
					item.is_agree = itemObject.getString("is_agree");
					item.is_delete = itemObject.getString("is_delete");
					item.show_pic = itemObject.getString("show_pic");
					item.max_time = itemObject.getString("max_time");
					item.rec_name = itemObject.getString("rec_name");
					item.flag = itemObject.getInt("flag");
					item.reply_num = itemObject.getString("reply_num");
					item.avatar = itemObject.getString("avatar");
					item.send_name = itemObject.getString("send_name");
					item.m_id = itemObject.getString("m_id");
					item.rec_id = itemObject.getString("rec_id");
					item.msg_id = itemObject.getString("msg_id");
					item.status = itemObject.getString("status");
					item.bold_class = itemObject.getString("bold_class");
					back.items.add(item);
				}
				success = true;
			}else{
				back.error = response.getString("error");
				success = false;
			}
		} catch (JSONException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			back.error = e.toString();
			return false;
		}
		
		return success;
	}
	
	
	public static String createPrivateMsgInfoString(NetworkData.PrivateMessageInfoData data){
		String str = null;
		JSONObject jstring = new JSONObject();
		try {
			jstring.put("topic_id", data.topic_id);
			jstring.put("msg_id", data.msg_id);
			jstring.put("rec_id", data.rec_id);
			jstring.put("sessid", KoalaApplication.loginData.sessid);
			
			str = jstring.toString();
		} catch (JSONException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return str;
	}
	
	public static boolean parsePrivateMsgInfo(String str,NetworkData.PrivateMessageInfoBack back){
		boolean success = false;
		try {
			JSONObject response = new JSONObject(str);
			int code = -1;
			code = response.getInt("code");
			back.code = code;
			if(code == 0){
				int total = Integer.parseInt(response.getString("total"));
				back.total = total;
				JSONArray array = response.getJSONArray("data");
				for(int i=0;i<array.length();i++){
					JSONObject itemObject = array.getJSONObject(i);
					NetworkData.PrivateMessageInfoItem item = NetworkData.getInstance().getNewPrivateMessageInfoItem();
					item.id = itemObject.getString("id");
					item.send_id = itemObject.getString("send_id");
					item.content = itemObject.getString("content");
					item.type = itemObject.getString("type");
					item.user_type = itemObject.getString("user_type");
					item.topic_id = itemObject.getString("topic_id");
					item.send_time = itemObject.getString("send_time");
					item.bid_id = itemObject.getString("bid_id");
					item.is_agree = itemObject.getString("is_agree");
					item.is_delete = itemObject.getString("is_delete");
					item.m_id = itemObject.getString("m_id");
					item.rec_id = itemObject.getString("rec_id");
					item.msg_id = itemObject.getString("msg_id");
					item.status = itemObject.getString("status");
					item.show_pic = itemObject.getString("show_pic");
					item.send_name = itemObject.getString("send_name");
					item.avatar = itemObject.getString("avatar");
					item.rec_name = itemObject.getString("rec_name");
					item.flag = itemObject.getInt("flag");
					item.user_id = itemObject.getString("user_id");
					item.user_name = itemObject.getString("user_name");
					item.accept_id = itemObject.getString("accept_id");
					item.accept_name = itemObject.getString("accept_name");
					
					back.items.add(item);
				}
				success = true;
			}else{
				back.error = response.getString("error");
				success = false;
			}
		} catch (JSONException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			back.error = e.toString();
			return false;
		}
		
		return success;
	}
	
	public static String createPublicMsgListString(NetworkData.PublicMessageListData data){
		String str = null;
		JSONObject jstring = new JSONObject();
		try {
			jstring.put("is_read", data.is_read);
			jstring.put("page", data.page);
			jstring.put("limit", data.limit);
			jstring.put("sessid", data.sessid);
			
			str = jstring.toString();
		} catch (JSONException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return str;
	}
	
	public static String createPublicMsgListString(NetworkData.PublicMessageInfoData data){
		String str = null;
		JSONObject jstring = new JSONObject();
		try {
			jstring.put("is_exist", data.is_exist);
			jstring.put("msg_id", data.msg_id);
			jstring.put("sessid", KoalaApplication.loginData.sessid);
			
			str = jstring.toString();
		} catch (JSONException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return str;
	}
	
	public static boolean parsePublicMsgList(String str,NetworkData.PublicMessageListBack back){
		boolean success = false;
		try {
			JSONObject response = new JSONObject(str);
			int code = -1;
			code = response.getInt("code");
			back.code = code;
			if(code == 0){
				int total = Integer.parseInt(response.getString("total"));
				back.total = total;
				JSONArray array = response.getJSONArray("data");
				for(int i=0;i<total;i++){
					JSONObject itemObject = array.getJSONObject(i);
					NetworkData.PublicMessageListItem item = NetworkData.getInstance().getNewPublicMessageItem();
					item.id = itemObject.getString("id");
					item.send_id = itemObject.getString("send_id");
					item.content = itemObject.getString("content");
					item.type = itemObject.getString("type");
					item.user_type = itemObject.getString("user_type");
					item.topic_id = itemObject.getString("topic_id");
					item.send_time = itemObject.getString("send_time");
					item.bid_id = itemObject.getString("bid_id");
					item.is_agree = itemObject.getString("is_agree");
					item.is_delete = itemObject.getString("is_delete");
					item.m_id = itemObject.getString("m_id");
					item.rec_id = itemObject.getString("rec_id");
					item.msg_id = itemObject.getString("msg_id");
					item.status = itemObject.getString("status");
					item.is_exist = itemObject.getString("is_exist");
					item.bold_class = itemObject.getString("bold_class");
					back.items.add(item);
				}
				success = true;
			}else{
				back.error = response.getString("error");
				success = false;
			}
		} catch (JSONException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			back.error = e.toString();
			return false;
		}
		
		return success;
	}
	
	public static boolean parsePublicMsgInfo(String str,NetworkData.PublicMessageInfoBack back){
		boolean success = false;
		try {
			JSONObject response = new JSONObject(str);
			int code = -1;
			code = response.getInt("code");
			back.code = code;
			if(code == 0){
				JSONObject itemObject = response.getJSONObject("data");
				NetworkData.PublicMessageInfoItem item = back.item;
				item.id = itemObject.getString("id");
				item.send_id = itemObject.getString("send_id");
				item.content = itemObject.getString("content");
				item.type = itemObject.getString("type");
				item.user_type = itemObject.getString("user_type");
				item.topic_id = itemObject.getString("topic_id");
				item.send_time = itemObject.getString("send_time");
				item.bid_id = itemObject.getString("bid_id");
				item.is_agree = itemObject.getString("is_agree");
				item.is_delete = itemObject.getString("is_delete");
				success = true;
			}else{
				back.error = response.getString("error");
				success = false;
			}
		} catch (JSONException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			back.error = e.toString();
			return false;
		}
		
		return success;
	}
	
	public static String createBidListString(NetworkData.BidListData data){
		String str = null;
		JSONObject jstring = new JSONObject();
		try {
			//jstring.put("page", data.page);
			jstring.put("limit", data.limit);
			
			str = jstring.toString();
		} catch (JSONException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return str;
	}
	
	public static String createDecorateTipDeailString(NetworkData.DecorateTipDetailData data){
		String str = null;
		JSONObject jstring = new JSONObject();
		try {
			//jstring.put("page", data.page);
			jstring.put("doc_id", data.doc_id);
			
			str = jstring.toString();
		} catch (JSONException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return str;
	}
	
	public static boolean parseBidList(String str,NetworkData.BidListBack back){
		boolean success = false;
		try {
			JSONObject response = new JSONObject(str);
			int code = -1;
			code = response.getInt("code");
			back.code = code;
			if(code == 0){
				int total = Integer.parseInt(response.getString("total"));
				JSONArray array = response.getJSONArray("data");
				total = Math.min(total, array.length());
				back.total = total;
				for(int i=0;i<total;i++){
					JSONObject itemObject = array.getJSONObject(i);
					NetworkData.BidListItem item = NetworkData.getInstance().getNewBidListItem();
					item.bid_id = itemObject.getString("bid_id");
					item.user_id = itemObject.getString("user_id");
					item.name = itemObject.getString("name");
					item.mobile = itemObject.getString("mobile");
					item.decorate_way = itemObject.getString("decorate_way");
					item.design_style = itemObject.getString("design_style");
					item.budget = itemObject.getString("budget");
					item.house_type = itemObject.getString("house_type");
					item.space_type = itemObject.getString("space_type");
					item.area = itemObject.getString("area");
					item.biotope_name = itemObject.getString("biotope_name");
					item.province = itemObject.getString("province");
					item.city = itemObject.getString("city");
					item.district = itemObject.getString("district");
					item.requirement = itemObject.getString("requirement");
					item.title = itemObject.getString("title");
					
					item.publish_time = itemObject.getString("publish_time");
					item.complete_time = itemObject.getString("complete_time");
					item.is_signed = itemObject.getString("is_signed");
					item.need_designer = itemObject.getString("need_designer");
					item.need_foreman = itemObject.getString("need_foreman");
					item.need_supervisor = itemObject.getString("need_supervisor");
					item.bid_number = itemObject.getString("bid_number");
					item.new_bidder = itemObject.getString("new_bidder");
					item.prov_rate = itemObject.getString("prov_rate");
					item.status = itemObject.getString("status");
					item.proj_stat = itemObject.getString("proj_stat");
					item.is_delete = itemObject.getString("is_delete");
					item.start_time = itemObject.getString("start_time");
					item.bidder_type = itemObject.getString("bidder_type");
					item.bidder_type_img = itemObject.getString("bidder_type_img");
					back.items.add(item);
				}
				success = true;
			}else{
				back.error = response.getString("error");
				success = false;
			}
		} catch (JSONException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			back.error = e.toString();
			return false;
		}
		
		return success;
	}
	
	public static boolean parseDecorateTipDetail(String str,NetworkData.DecorateTipDetailBack back){
		boolean success = false;
		try {
			JSONObject response = new JSONObject(str);
			int code = -1;
			code = response.getInt("code");
			back.code = code;
			if(code == 0){
				JSONObject data = response.getJSONObject("data");
				JSONObject doc = data.getJSONObject("doc");
				
				back.doc_id = doc.getString("doc_id");
				back.cat_id = doc.getString("cat_id");
				back.title = doc.getString("title");
				back.content = doc.getString("content");
				back.author = doc.getString("author");
				back.keywords = doc.getString("keywords");
				back.is_open = doc.getString("is_open");
				back.add_time = doc.getString("add_time");
				back.scan_num = doc.getString("scan_num");
//				item.is_hot = itemObject.getString("is_hot");
//				item.cat_name = itemObject.getString("cat_name");
//				item.cat_desc = itemObject.getString("cat_desc");
//				item.parent_id = itemObject.getString("parent_id");
//				item.is_delete = itemObject.getString("is_delete");
//				item.prevDoc = itemObject.getString("prevDoc");
					
				success = true;
			}else{
				back.error = response.getString("error");
				success = false;
			}
		} catch (JSONException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			back.error = e.toString();
			return false;
		}
		
		return success;
	}
	
	public static String createDecorateTipListString(NetworkData.DecorateTipsData data){
		String str = null;
		JSONObject jstring = new JSONObject();
		try {
			jstring.put("limit", data.limit);
//			jstring.put("page", data.page);
			jstring.put("cid", data.cid);
			str = jstring.toString();
		} catch (JSONException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return str;
	}
	
	public static boolean parseDecorateTipList(String str,NetworkData.DecorateTipsBack back){
		boolean success = false;
		try {
			back.items = null;
			JSONObject response = new JSONObject(str);
			int code = -1;
			code = response.getInt("code");
			back.code = code;
			if(code == 1){
				
				JSONObject datas = response.getJSONObject("data");
				
				int total = Integer.parseInt(datas.getString("total"));
				back.total = total;
				
				NetworkData.DecorateTipsItem item = NetworkData.getInstance().getDecorateTipItem();
				item.cid = datas.getInt("cid");
					
				JSONArray catArray = datas.getJSONArray("docCats");
				for(int i=0;i<catArray.length();i++){
					JSONObject cat = catArray.getJSONObject(i);
					Map<String,String> map = new HashMap<String,String>();
					map.put(cat.getString("cat_id"), cat.getString("cat_name"));
					item.cats.add(map);
				}
				
				JSONArray docList = datas.getJSONArray("docList");
				
				for(int i=0;i<docList.length();i++){
					JSONObject itemObject = docList.getJSONObject(i);
					SubDecorateTips sdt = new SubDecorateTips();
					sdt.doc_id = itemObject.getString("doc_id");
					sdt.cat_id = itemObject.getString("cat_id");
					sdt.title = itemObject.getString("title");
					sdt.add_time = itemObject.getString("add_time");
					sdt.scan_num = itemObject.getString("scan_num");
					item.subDecorate.add(sdt);
				}
				back.items = item;
				success = true;
			}else{
				back.error = response.getString("error");
				success = false;
			}
		} catch (JSONException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			back.error = e.toString();
			return false;
		}
		
		return success;
	}
	
	public static String createUserBidListString(NetworkData.BidListData data){
		String str = null;
		JSONObject jstring = new JSONObject();
		try {
			jstring.put("limit", data.limit);
			jstring.put("sessid", KoalaApplication.loginData.sessid);
			
			str = jstring.toString();
		} catch (JSONException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return str;
	}
	
	public static boolean parseUserBidList(String str,NetworkData.BidListBack back){
		boolean success = false;
		try {
			JSONObject response = new JSONObject(str);
			int code = -1;
			code = response.getInt("code");
			back.code = code;
			if(code == 0){
				int total = Integer.parseInt(response.getString("total"));
				JSONArray array = response.getJSONArray("data");
				total = Math.min(total, array.length());
				back.total = total;
				for(int i=0;i<total;i++){
					JSONObject itemObject = array.getJSONObject(i);
					NetworkData.BidListItem item = NetworkData.getInstance().getNewBidListItem();
					item.bid_id = itemObject.getString("bid_id");
					item.user_id = itemObject.getString("user_id");
					item.name = itemObject.getString("name");
					item.mobile = itemObject.getString("mobile");
					item.decorate_way = itemObject.getString("decorate_way");
					item.design_style = itemObject.getString("design_style");
					item.budget = itemObject.getString("budget");
					item.house_type = itemObject.getString("house_type");
					item.space_type = itemObject.getString("space_type");
					item.area = itemObject.getString("area");
					item.biotope_name = itemObject.getString("biotope_name");
					item.province = itemObject.getString("province");
					item.city = itemObject.getString("city");
					item.district = itemObject.getString("district");
					item.requirement = itemObject.getString("requirement");
					item.title = itemObject.getString("title");
					
					item.publish_time = itemObject.getString("publish_time");
					item.complete_time = itemObject.getString("complete_time");
					item.is_signed = itemObject.getString("is_signed");
					item.need_designer = itemObject.getString("need_designer");
					item.need_foreman = itemObject.getString("need_foreman");
					item.need_supervisor = itemObject.getString("need_supervisor");
					item.bid_number = itemObject.getString("bid_number");
					item.new_bidder = itemObject.getString("new_bidder");
					item.prov_rate = itemObject.getString("prov_rate");
					item.status = itemObject.getString("status");
					item.proj_stat = itemObject.getString("proj_stat");
					item.is_delete = itemObject.getString("is_delete");
					item.start_time = itemObject.getString("start_time");
					item.bidder_type = itemObject.getString("bidder_type");
					item.bidder_type_img = itemObject.getString("bidder_type_img");
					back.items.add(item);
				}
				success = true;
			}else{
				back.error = response.getString("error");
				success = false;
			}
		} catch (JSONException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			back.error = e.toString();
			return false;
		}
		
		return success;
	}
	
	public static boolean parseOtherDecorateList(String str,NetworkData.BidListBack back){
		boolean success = false;
		try {
			JSONObject response = new JSONObject(str);
			int code = -1;
			code = response.getInt("code");
			back.code = code;
			if(code == 0){
				int total = Integer.parseInt(response.getString("total"));
				JSONArray array = response.getJSONArray("data");
				total = Math.min(total, array.length());
				back.total = total;
				for(int i=0;i<total;i++){
					JSONObject itemObject = array.getJSONObject(i);
					NetworkData.BidListItem item = NetworkData.getInstance().getNewBidListItem();
					item.bid_id = itemObject.getString("bid_id");
					item.user_id = itemObject.getString("user_id");
					item.name = itemObject.getString("name");
					item.mobile = itemObject.getString("mobile");
					item.decorate_way = itemObject.getString("decorate_way");
					item.design_style = itemObject.getString("design_style");
					item.budget = itemObject.getString("budget");
					item.house_type = itemObject.getString("house_type");
					item.space_type = itemObject.getString("space_type");
					item.area = itemObject.getString("area");
					item.biotope_name = itemObject.getString("biotope_name");
					item.province = itemObject.getString("province");
					item.city = itemObject.getString("city");
					item.district = itemObject.getString("district");
					item.requirement = itemObject.getString("requirement");
					item.title = itemObject.getString("title");
					
					item.publish_time = itemObject.getString("publish_time");
					item.complete_time = itemObject.getString("complete_time");
					item.is_signed = itemObject.getString("is_signed");
					item.need_designer = itemObject.getString("need_designer");
					item.need_foreman = itemObject.getString("need_foreman");
					item.need_supervisor = itemObject.getString("need_supervisor");
					item.bid_number = itemObject.getString("bid_number");
					item.new_bidder = itemObject.getString("new_bidder");
					item.prov_rate = itemObject.getString("prov_rate");
					item.status = itemObject.getString("status");
					item.proj_stat = itemObject.getString("proj_stat");
					item.is_delete = itemObject.getString("is_delete");
					item.start_time = itemObject.getString("start_time");
					item.bidder_type = itemObject.getString("bidder_type");
					//item.bidder_type_img = itemObject.getString("bidder_type_img");
					back.items.add(item);
				}
				success = true;
			}else{
				back.error = response.getString("error");
				success = false;
			}
		} catch (JSONException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			back.error = e.toString();
			return false;
		}
		
		return success;
	}
	
	public static String createUserEvalueString(NetworkData.UserEvalueData data){
		String str = null;
		JSONObject jstring = new JSONObject();
		try {
			jstring.put("bid", data.bid);
			jstring.put("s1", data.s1);
			jstring.put("p1", data.p1);
			jstring.put("commend1", data.commend1);
			jstring.put("s2", data.s2);
			jstring.put("p2", data.p2);
			jstring.put("commend2", data.commend2);
			jstring.put("s3", data.s3);
			jstring.put("p3", data.p3);
			jstring.put("commend3", data.commend3);
			jstring.put("sessid", KoalaApplication.loginData.sessid);
			
			str = jstring.toString();
		} catch (JSONException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return str;
	}
	
	public static String createSetDepositString(NetworkData.SetDepositData data){
		String str = null;
		JSONObject jstring = new JSONObject();
		try {
			jstring.put("bid", data.bid);
			jstring.put("grate", data.grate);
			jstring.put("sessid", KoalaApplication.loginData.sessid);
			
			str = jstring.toString();
		} catch (JSONException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return str;
	}
	
	public static String createBidDetailString(NetworkData.BidDetailData data){
		String str = null;
		JSONObject jstring = new JSONObject();
		try {
			jstring.put("bid_id", data.bid_id);
			
			str = jstring.toString();
		} catch (JSONException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return str;
	}
	
	public static boolean parseBidDetail(String str,NetworkData.BidListBack back){
		boolean success = false;
		try {
			JSONObject response = new JSONObject(str);
			int code = -1;
			code = response.getInt("code");
			back.code = code;
			if(code == 0){
				back.total = 1;
				JSONObject itemObject = response.getJSONObject("data");
				//JSONArray array = response.getJSONArray("data");
				//for(int i=0;i<total;i++){
					//JSONObject itemObject = array.getJSONObject(i);
				NetworkData.BidListItem item = NetworkData.getInstance().getNewBidListItem();
				item.bid_id = itemObject.getString("bid_id");
				item.user_id = itemObject.getString("user_id");
				item.name = itemObject.getString("name");
				item.mobile = itemObject.getString("mobile");
				item.decorate_way = itemObject.getString("decorate_way");
				item.design_style = itemObject.getString("design_style");
				item.budget = itemObject.getString("budget");
				item.house_type = itemObject.getString("house_type");
				item.space_type = itemObject.getString("space_type");
				item.area = itemObject.getString("area");
				item.biotope_name = itemObject.getString("biotope_name");
				item.province = itemObject.getString("province");
				item.city = itemObject.getString("city");
				item.district = itemObject.getString("district");
				item.requirement = itemObject.getString("requirement");
				item.title = itemObject.getString("title");
				
				item.publish_time = itemObject.getString("publish_time");
				item.complete_time = itemObject.getString("complete_time");
				item.is_signed = itemObject.getString("is_signed");
				item.need_designer = itemObject.getString("need_designer");
				item.need_foreman = itemObject.getString("need_foreman");
				item.need_supervisor = itemObject.getString("need_supervisor");
				item.bid_number = itemObject.getString("bid_number");
				item.new_bidder = itemObject.getString("new_bidder");
				item.prov_rate = itemObject.getString("prov_rate");
				item.status = itemObject.getString("status");
				item.proj_stat = itemObject.getString("proj_stat");
				item.is_delete = itemObject.getString("is_delete");
				//item.start_time = itemObject.getString("start_time");
				item.postion = itemObject.getString("position");
				item.bidder_type = itemObject.getString("bidder_type");
				//item.bidder_type_img = itemObject.getString("bidder_type_img");
				back.items.add(item);
				//}
				success = true;
			}else{
				back.error = response.getString("error");
				success = false;
			}
		} catch (JSONException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			back.error = e.toString();
			return false;
		}
		
		return success;
	}
	
	public static String createUserBidDetailString(NetworkData.BidDetailData data){
		String str = null;
		JSONObject jstring = new JSONObject();
		try {
			jstring.put("bid", data.bid_id);
			jstring.put("sessid", KoalaApplication.loginData.sessid);
			str = jstring.toString();
		} catch (JSONException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return str;
	}
	
	public static boolean parseUserBidDetail(String str,NetworkData.UserBidDetailBack back){
		boolean success = false;
		try {
			JSONObject response = new JSONObject(str);
			int code = -1;
			code = response.getInt("code");
			back.code = code;
			try{
				back.error = response.getString("error");
			}catch (JSONException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
				success = true;
			}
			if(success){//Need to modify in the future
				JSONObject itemObject = response.getJSONObject("data");
				JSONObject itemBidInfo = itemObject.getJSONObject("bid_info");
				JSONObject itemBidders = itemObject.getJSONObject("bidders");
				JSONArray itemDesigners = itemBidders.getJSONArray("d");
				JSONArray itemLabors = itemBidders.getJSONArray("f");
				JSONArray itemSuperiors = itemBidders.getJSONArray("s");
				JSONObject itemBidderNum = itemObject.getJSONObject("bidder_num");
				JSONObject itemWinner = itemObject.getJSONObject("winners");
				//JSONArray array = response.getJSONArray("data");
				//for(int i=0;i<total;i++){
					//JSONObject itemObject = array.getJSONObject(i);
				NetworkData.BidListItem item = back.bid_info;
				item.bid_id = itemBidInfo.getString("bid_id");
				item.user_id = itemBidInfo.getString("user_id");
				item.name = itemBidInfo.getString("name");
				item.mobile = itemBidInfo.getString("mobile");
				item.decorate_way = itemBidInfo.getString("decorate_way");
				item.design_style = itemBidInfo.getString("design_style");
				item.budget = itemBidInfo.getString("budget");
				item.house_type = itemBidInfo.getString("house_type");
				item.space_type = itemBidInfo.getString("space_type");
				item.area = itemBidInfo.getString("area");
				item.biotope_name = itemBidInfo.getString("biotope_name");
				item.province = itemBidInfo.getString("province");
				item.city = itemBidInfo.getString("city");
				item.district = itemBidInfo.getString("district");
				item.requirement = itemBidInfo.getString("requirement");
				item.title = itemBidInfo.getString("title");
				item.publish_time = itemBidInfo.getString("publish_time");
				item.complete_time = itemBidInfo.getString("complete_time");
				item.is_signed = itemBidInfo.getString("is_signed");
				item.need_designer = itemBidInfo.getString("need_designer");
				item.need_foreman = itemBidInfo.getString("need_foreman");
				item.need_supervisor = itemBidInfo.getString("need_supervisor");
				item.bid_number = itemBidInfo.getString("bid_number");
				item.new_bidder = itemBidInfo.getString("new_bidder");
				item.prov_rate = itemBidInfo.getString("prov_rate");
				item.status = itemBidInfo.getString("status");
				item.proj_stat = itemBidInfo.getString("proj_stat");
				item.is_delete = itemBidInfo.getString("is_delete");
				item.postion = itemBidInfo.getString("position");
				item.bidder_type = itemBidInfo.getString("bidder_type");
				
				back.des_cnt = itemDesigners.length();
				NetworkData.UserBidDetailBidders bidder;
				for(int i=0;i<itemDesigners.length();i++){
					itemObject = itemDesigners.getJSONObject(i);
					bidder = NetworkData.getInstance().getNewUserBidDetailBidders();
					bidder.bid = itemObject.getString("bid");
					bidder.uid = itemObject.getString("uid");
					bidder.username = itemObject.getString("username");
					bidder.type = itemObject.getString("type");
					bidder.name_auth = itemObject.getString("name_auth");
					bidder.commend = itemObject.getString("commend");
					bidder.msg = itemObject.getString("msg");
					bidder.datetime = itemObject.getString("datetime");
					bidder.work_num = itemObject.getString("work_num");
					bidder.avatar = itemObject.getString("avatar");
					bidder.case_num = itemObject.getString("case_num");
					bidder.rate_avg = itemObject.getString("rate_avg");
					bidder.attud_avg = itemObject.getString("attud_avg");
					back.designers.add(bidder);
				}
				back.frm_cnt = itemLabors.length();
				for(int i=0;i<itemLabors.length();i++){
					itemObject = itemLabors.getJSONObject(i);
					bidder = NetworkData.getInstance().getNewUserBidDetailBidders();
					bidder.bid = itemObject.getString("bid");
					bidder.uid = itemObject.getString("uid");
					bidder.username = itemObject.getString("username");
					bidder.type = itemObject.getString("type");
					bidder.name_auth = itemObject.getString("name_auth");
					bidder.commend = itemObject.getString("commend");
					bidder.msg = itemObject.getString("msg");
					bidder.datetime = itemObject.getString("datetime");
					bidder.work_num = itemObject.getString("work_num");
					bidder.avatar = itemObject.getString("avatar");
					bidder.case_num = itemObject.getString("case_num");
					bidder.rate_avg = itemObject.getString("rate_avg");
					bidder.attud_avg = itemObject.getString("attud_avg");
					back.labors.add(bidder);
				}
				back.sup_cnt = itemSuperiors.length();
				for(int i=0;i<itemSuperiors.length();i++){
					itemObject = itemSuperiors.getJSONObject(i);
					bidder = NetworkData.getInstance().getNewUserBidDetailBidders();
					bidder.bid = itemObject.getString("bid");
					bidder.uid = itemObject.getString("uid");
					bidder.username = itemObject.getString("username");
					bidder.type = itemObject.getString("type");
					bidder.name_auth = itemObject.getString("name_auth");
					bidder.commend = itemObject.getString("commend");
					bidder.msg = itemObject.getString("msg");
					bidder.datetime = itemObject.getString("datetime");
					bidder.work_num = itemObject.getString("work_num");
					bidder.avatar = itemObject.getString("avatar");
					bidder.case_num = itemObject.getString("case_num");
					bidder.rate_avg = itemObject.getString("rate_avg");
					bidder.attud_avg = itemObject.getString("attud_avg");
					back.superiors.add(bidder);
				}
				
				back.des_uid = itemWinner.getString("des_uid");
				back.frm_uid = itemWinner.getString("frm_uid");
				back.sup_uid = itemWinner.getString("sup_uid");
				success = true;
			}else{
				back.error = response.getString("error");
				success = false;
			}
		} catch (JSONException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			back.error = e.toString();
			return false;
		}
		
		return success;
	}
	
	public static String createBidVieString(NetworkData.BidVieData data){
		String str = null;
		JSONObject jstring = new JSONObject();
		try {
			jstring.put("bidid", data.bidid);
			jstring.put("bidmsg", data.bidmsg);
			jstring.put("sessid", KoalaApplication.loginData.sessid);
			str = jstring.toString();
		} catch (JSONException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return str;
	}
	
	public static String createBidAbortString(NetworkData.BidAbortData data){
		String str = null;
		JSONObject jstring = new JSONObject();
		try {
			jstring.put("bidid", data.bid);
			jstring.put("sessid", KoalaApplication.loginData.sessid);
			str = jstring.toString();
		} catch (JSONException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return str;
	}
	
	public static String createUserBidEditString(NetworkData.BidDetailData data){
		String str = null;
		JSONObject jstring = new JSONObject();
		try {
			jstring.put("bid", data.bid_id);
			jstring.put("sessid", KoalaApplication.loginData.sessid);
			str = jstring.toString();
		} catch (JSONException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return str;
	}
	
	public static boolean parseUserBidEdit(String str,NetworkData.UserBidEditBack back){
		boolean success = false;
		try {
			JSONObject response = new JSONObject(str);
			int code = -1;
			code = response.getInt("code");
			back.code = code;
			try{
				back.error = response.getString("error");
			}catch (JSONException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
				success = true;
			}
			if(success){
				back.total = 1;
				JSONObject itemObject = response.getJSONObject("data");
				//JSONArray array = response.getJSONArray("data");
				//for(int i=0;i<total;i++){
					//JSONObject itemObject = array.getJSONObject(i);
				NetworkData.BidListItem item = NetworkData.getInstance().getNewBidListItem();
				item.bid_id = itemObject.getString("bid_id");
				item.user_id = itemObject.getString("user_id");
				item.name = itemObject.getString("name");
				item.mobile = itemObject.getString("mobile");
				item.decorate_way = itemObject.getString("decorate_way");
				item.design_style = itemObject.getString("design_style");
				item.budget = itemObject.getString("budget");
				item.house_type = itemObject.getString("house_type");
				item.space_type = itemObject.getString("space_type");
				item.area = itemObject.getString("area");
				item.biotope_name = itemObject.getString("biotope_name");
				item.province = itemObject.getString("province");
				item.city = itemObject.getString("city");
				item.district = itemObject.getString("district");
				item.requirement = itemObject.getString("requirement");
				item.title = itemObject.getString("title");
				
				item.publish_time = itemObject.getString("publish_time");
				item.complete_time = itemObject.getString("complete_time");
				item.is_signed = itemObject.getString("is_signed");
				item.need_designer = itemObject.getString("need_designer");
				item.need_foreman = itemObject.getString("need_foreman");
				item.need_supervisor = itemObject.getString("need_supervisor");
				item.bid_number = itemObject.getString("bid_number");
				item.new_bidder = itemObject.getString("new_bidder");
				item.prov_rate = itemObject.getString("prov_rate");
				item.status = itemObject.getString("status");
				item.proj_stat = itemObject.getString("proj_stat");
				item.is_delete = itemObject.getString("is_delete");
				//item.start_time = itemObject.getString("start_time");
				item.postion = itemObject.getString("position");
				item.bidder_type = itemObject.getString("bidder_type");
				//item.bidder_type_img = itemObject.getString("bidder_type_img");
				back.items.add(item);
				
				JSONObject cst = itemObject.getJSONObject("const");
				JSONArray cst_dw = cst.getJSONArray("decorate_way");
				for(int i=0;i<cst_dw.length();i++){
					back.constDecorateWay.add(cst_dw.getString(0));
				}
				JSONArray cst_ht = cst.getJSONArray("house_type");
				for(int i=0;i<cst_ht.length();i++){
					back.constHouseType.add(cst_ht.getString(0));
				}
				JSONArray cst_bt = cst.getJSONArray("budget");
				for(int i=0;i<cst_bt.length();i++){
					back.constBudget.add(cst_bt.getString(0));
				}
				JSONArray cst_ds = cst.getJSONArray("design_style");
				for(int i=0;i<cst_ds.length();i++){
					back.constDesignerStyle.add(cst_ds.getString(0));
				}
				//}
				success = true;
			}else{
				back.error = response.getString("error");
				success = false;
			}
		} catch (JSONException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			back.error = e.toString();
			return false;
		}
		
		return success;
	}
	
	public static String createBidPublishString(NetworkData.BidPublishData data){
		String str = null;
		JSONObject jstring = new JSONObject();
		try {
			jstring.put("decorate_way", data.decorate_way);
			jstring.put("design_style", data.design_style);
			jstring.put("budget", data.budget);
			jstring.put("house_type", data.house_type);
			jstring.put("room", data.room);
			jstring.put("hall", data.hall);
			jstring.put("kitchen", data.kitchen);
			jstring.put("toilet", data.toilet);
			jstring.put("balcony", data.balcony);
			jstring.put("area", data.area);
			jstring.put("biotope_name", data.biotope_name);
			jstring.put("province", data.province);
			jstring.put("city", data.city);
			jstring.put("district", data.district);
			jstring.put("req", data.req);
			jstring.put("need_designer", data.need_designer);
			jstring.put("need_forman", data.need_forman);
			jstring.put("need_supervisor", data.need_supervisor);
			jstring.put("new_bidder", data.new_bidder);
			
			jstring.put("sessid", KoalaApplication.loginData.sessid);
			str = jstring.toString();
		} catch (JSONException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return str;
	}
	
	public static String createUserBidEditPostString(NetworkData.BidPostEditData data){
		String str = null;
		JSONObject jstring = new JSONObject();
		try {
			jstring.put("bid", data.bid);
			jstring.put("decorate_way", data.decorate_way);
			jstring.put("design_style", data.design_style);
			jstring.put("budget", data.budget);
			jstring.put("house_type", data.house_type);
			jstring.put("room", data.room);
			jstring.put("hall", data.hall);
			jstring.put("kitchen", data.kitchen);
			jstring.put("toilet", data.toilet);
			jstring.put("balcony", data.balcony);
			jstring.put("area", data.area);
			jstring.put("biotope_name", data.biotope_name);
			jstring.put("province", data.province);
			jstring.put("city", data.city);
			jstring.put("district", data.district);
			jstring.put("req", data.req);
			jstring.put("need_designer", data.need_designer);
			jstring.put("need_forman", data.need_forman);
			jstring.put("need_supervisor", data.need_supervisor);
			jstring.put("new_bidder", data.new_bidder);
			
			jstring.put("sessid", KoalaApplication.loginData.sessid);
			str = jstring.toString();
		} catch (JSONException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return str;
	}
	
	public static boolean parseBidPublish(String str,NetworkData.CommonBack back){
		boolean success = false;
		try {
			JSONObject response = new JSONObject(str);
			int code = -1;
			code = response.getInt("code");
			back.code = code;
			if(code == 0){
				success = true;
			}else{
				back.error = response.getString("error");
				success = false;
			}
		} catch (JSONException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			return false;
		}
		
		return success;
	}
	
	public static boolean parseLoginBack(String str,NetworkData.LoginBack loginBack){
		boolean success = false;
		try {
			JSONObject response = new JSONObject(str);
			int code = -1;
			code = response.getInt("code");
			loginBack.code = code;
			if(code == 0){
				loginBack.id = response.getString("id");
				loginBack.sessid = response.getString("sessid");
				success = true;
			}else{
				loginBack.error = response.getString("error");
				success = false;
			}
		} catch (JSONException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			return false;
		}
		
		return success;
	}
	
	public static String createRegionsString(NetworkData.RegionsData data){
		String str = null;
		JSONObject jstring = new JSONObject();
		try {
			jstring.put("type", data.type);
			jstring.put("parent", data.parent);
			
			str = jstring.toString();
		} catch (JSONException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return str;
	}
	
	public static boolean parseRegions(String str, NetworkData.RegionsBack back){
		boolean success = false;
		try {
			JSONObject response = new JSONObject(str);
			int code = -1;
			code = response.getInt("code");
			back.code = code;
			if(code == 0){
				JSONArray array = response.getJSONArray("data");
				int total = array.length();
				for(int i=0;i<total;i++){
					JSONObject itemObject = array.getJSONObject(i);
					NetworkData.RegionsItem item = NetworkData.getInstance().getNewRegionsItem();
					item.regions_id = itemObject.getString("region_id");
					item.regions_name = itemObject.getString("region_name");
					back.regions.add(item);
				}
				success = true;
			}else{
				back.error = response.getString("error");
				success = false;
			}
		} catch (JSONException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			back.error = e.toString();
			return false;
		}
		
		return success;
	}
	
	public static String createDeletePublicMsgString(NetworkData.DeletePublicMsgData data){
		String str = null;
		JSONObject jstring = new JSONObject();
		try {
			jstring.put("id", data.id);
			jstring.put("is_exist", data.is_exist);
			jstring.put("rec_id", data.rec_id);
			jstring.put("sessid", KoalaApplication.loginData.sessid);
			
			str = jstring.toString();
		} catch (JSONException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return str;
	}
	
	public static boolean parseDeletePublicMsg(String str,NetworkData.CommonBack back){
		boolean success = false;
		try {
			JSONObject response = new JSONObject(str);
			int code = -1;
			code = response.getInt("code");
			back.code = code;
			if(code == 0){
				success = true;
			}else{
				back.error = response.getString("error");
				success = false;
			}
		} catch (JSONException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			return false;
		}
		
		return success;
	}
	
	public static String createDeletePrivateMsgString(NetworkData.DeletePrivateMsgData data){
		String str = null;
		JSONObject jstring = new JSONObject();
		JSONArray arr = new JSONArray();
		try {
			int len = data.mIds.size();
			
			for(int i=0;i<len;i++){
				arr.put(data.mIds.get(i).intValue());
			}
			jstring.put("msg_id", arr);
			jstring.put("sessid", KoalaApplication.loginData.sessid);
			str = jstring.toString();
		} catch (JSONException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return str;
	}
	
	public static boolean parseDeletePrivateMsg(String str,NetworkData.CommonBack back){
		boolean success = false;
		try {
			JSONObject response = new JSONObject(str);
			int code = -1;
			code = response.getInt("code");
			back.code = code;
			if(code == 0){
				success = true;
			}else{
				back.error = response.getString("error");
				success = false;
			}
		} catch (JSONException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			return false;
		}
		
		return success;
	}
	
	public static boolean parseMyContractList(String str,NetworkData.MyContractListBack back){
		boolean success = false;
		try {
			JSONObject response = new JSONObject(str);
			int code = -1;
			code = response.getInt("code");
			back.code = code;
			if(code == 0){
				int total = Integer.parseInt(response.getString("total"));
				JSONArray array = response.getJSONArray("data");
				total = Math.min(total, array.length());
				back.total = total;
				for(int i=0;i<total;i++){
					JSONObject itemObject = array.getJSONObject(i);
					NetworkData.MyContractListItem item = NetworkData.getInstance().getNewMyContractListItem();
					item.c_id = itemObject.getString("c_id");
					item.bid_id = itemObject.getString("bid_id");
					item.owner_id = itemObject.getString("owner_id");
					item.contract_type = itemObject.getString("contract_type");
					item.real_name = itemObject.getString("real_name");
					item.str_build_addr = itemObject.getString("build_addr");
					/*JSONObject build_addr = itemObject.getJSONObject("build_addr");
					if(build_addr != null){
						item.addr.province = build_addr.getString("provice");
						item.addr.city = build_addr.getString("city");
						item.addr.district = build_addr.getString("district");
						item.addr.road = build_addr.getString("road");
						item.addr.lane = build_addr.getString("lane");
						item.addr.number = build_addr.getString("number");
						item.addr.floor = build_addr.getString("floor");
						item.addr.road = build_addr.getString("road");
					}*/
					
					
					item.decorate_way = itemObject.getString("decorate_way");
					item.str_start_date = itemObject.getString("start_date");
					/*JSONObject start_date = itemObject.getJSONObject("start_date");
					if(start_date != null){
						item.start_date.year = start_date.getString("year");
						item.start_date.month = start_date.getString("month");
						item.start_date.day = start_date.getString("day");
					}*/
					item.str_end_date = itemObject.getString("end_date");
					/*JSONObject end_date = itemObject.getJSONObject("end_date");
					if(end_date != null){
						item.end_date.year = end_date.getString("year");
						item.end_date.month = end_date.getString("month");
						item.end_date.day = end_date.getString("day");
					}*/
					
					item.total_amount = itemObject.getString("total_amount");
					item.first_pay = itemObject.getString("first_pay");
					item.second_pay = itemObject.getString("second_pay");
					item.third_pay = itemObject.getString("third_pay");
					item.fourth_pay = itemObject.getString("fourth_pay");
					item.other_pay = itemObject.getString("other_pay");
					item.str_liability = itemObject.getString("liability");
					/*JSONObject liability = itemObject.getJSONObject("liability");
					if(liability != null){
						item.liability.unpaid_percent = liability.getString("unpaid_percent");
						item.liability.first_percent = liability.getString("first_percent");
						item.liability.second_percent = liability.getString("second_percent");
						item.liability.third_percent = liability.getString("third_percent");
						item.liability.fourth_percent = liability.getString("fourth_percent");
					}*/
					
					item.guide_num = itemObject.getString("guide_num");
					item.plan = itemObject.getString("plan");
					item.free_num = itemObject.getString("free_num");
					item.draft_day = itemObject.getString("draft_day");
					item.final_day = itemObject.getString("final_day");
					item.create_time = itemObject.getString("create_time");
					item.is_confirm = itemObject.getString("is_confirm");
					item.is_check = itemObject.getString("is_check");
					
					item.is_paid = itemObject.getString("is_paid");
					item.is_complete = itemObject.getString("is_complete");
					item.prov_finish = itemObject.getString("prov_finish");
					item.title = itemObject.getString("title");
					back.items.add(item);
				}
				success = true;
			}else{
				back.error = response.getString("error");
				success = false;
			}
		} catch (JSONException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			back.error = e.toString();
			return false;
		}
		
		return success;
	}
	
	public static String createMyContractDetailString(NetworkData.BidDetailData data){
		String str = null;
		JSONObject jstring = new JSONObject();
		try {
			jstring.put("id", data.bid_id);
			jstring.put("sessid", KoalaApplication.loginData.sessid);
			str = jstring.toString();
		} catch (JSONException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return str;
	}
	
	public static String createMyContractAgreeString(NetworkData.ContractAgreeRejectData data){
		String str = null;
		JSONObject jstring = new JSONObject();
		try {
			jstring.put("c_id", data.c_id);
			jstring.put("sessid", KoalaApplication.loginData.sessid);
			str = jstring.toString();
		} catch (JSONException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return str;
	}
	
	public static String createMyContractRejectString(NetworkData.ContractAgreeRejectData data){
		String str = null;
		JSONObject jstring = new JSONObject();
		try {
			jstring.put("c_id", data.c_id);
			jstring.put("reason", data.reason);
			jstring.put("sessid", KoalaApplication.loginData.sessid);
			str = jstring.toString();
		} catch (JSONException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return str;
	}
	
	public static boolean parseMyContractDetail(String str,NetworkData.MyContractDetailBack back){
		boolean success = false;
		try {
			JSONObject response = new JSONObject(str);
			int code = -1;
			code = response.getInt("code");
			back.code = code;
			try{
				back.error = response.getString("error");
			}catch (JSONException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
				success = true;
			}
			if(success){
				//for(int i=0;i<total;i++){
				JSONObject itemObject = response.getJSONObject("data");
				back.c_id = itemObject.getString("c_id");
				back.bid_id = itemObject.getString("bid_id");
				back.owner_id = itemObject.getString("owner_id");
				back.contract_type = itemObject.getString("contract_type");
				back.real_name = itemObject.getString("real_name");
				back.str_build_addr = itemObject.getString("build_addr");
				/*JSONObject build_addr = itemObject.getJSONObject("build_addr");
				if(build_addr != null){
					item.addr.province = build_addr.getString("provice");
					item.addr.city = build_addr.getString("city");
					item.addr.district = build_addr.getString("district");
					item.addr.road = build_addr.getString("road");
					item.addr.lane = build_addr.getString("lane");
					item.addr.number = build_addr.getString("number");
					item.addr.floor = build_addr.getString("floor");
					item.addr.road = build_addr.getString("road");
				}*/
				
				
				back.decorate_way = itemObject.getString("decorate_way");
				back.str_start_date = itemObject.getString("start_date");
				/*JSONObject start_date = itemObject.getJSONObject("start_date");
				if(start_date != null){
					item.start_date.year = start_date.getString("year");
					item.start_date.month = start_date.getString("month");
					item.start_date.day = start_date.getString("day");
				}*/
				back.str_end_date = itemObject.getString("end_date");
				/*JSONObject end_date = itemObject.getJSONObject("end_date");
				if(end_date != null){
					item.end_date.year = end_date.getString("year");
					item.end_date.month = end_date.getString("month");
					item.end_date.day = end_date.getString("day");
				}*/
				
				back.total_amount = itemObject.getString("total_amount");
				back.first_pay = itemObject.getString("first_pay");
				back.second_pay = itemObject.getString("second_pay");
				back.third_pay = itemObject.getString("third_pay");
				back.fourth_pay = itemObject.getString("fourth_pay");
				back.other_pay = itemObject.getString("other_pay");
				back.str_liability = itemObject.getString("liability");
				try{
					JSONObject liability = itemObject.getJSONObject("liability");
					if(liability != null){
						back.liability.unpaid_percent = liability.getString("unpaid_percent");
						back.liability.first_percent = liability.getString("first_percent");
						back.liability.second_percent = liability.getString("second_percent");
						back.liability.third_percent = liability.getString("third_percent");
						back.liability.fourth_percent = liability.getString("fourth_percent");
					}
				}catch (JSONException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
				
				back.guide_num = itemObject.getString("guide_num");
				back.plan = itemObject.getString("plan");
				back.free_num = itemObject.getString("free_num");
				back.draft_day = itemObject.getString("draft_day");
				back.final_day = itemObject.getString("final_day");
				back.create_time = itemObject.getString("create_time");
				back.is_confirm = itemObject.getString("is_confirm");
				back.is_check = itemObject.getString("is_check");
				
				back.is_paid = itemObject.getString("is_paid");
				back.is_complete = itemObject.getString("is_complete");
				back.prov_finish = itemObject.getString("prov_finish");
				back.title = itemObject.getString("title");
				
				back.province = itemObject.getString("provice");
				back.city = itemObject.getString("city");
				back.district = itemObject.getString("district");
				back.road = itemObject.getString("road");
				back.lane = itemObject.getString("lane");
				back.number = itemObject.getString("number");
				back.floor = itemObject.getString("floor");
				back.room = itemObject.getString("room");
				try{
					back.syear = itemObject.getString("syear");
					back.smonth = itemObject.getString("smonth");
					back.sday = itemObject.getString("sday");
					back.eyear = itemObject.getString("eyear");
					back.emonth = itemObject.getString("emonth");
					back.eday = itemObject.getString("eday");
				}catch (JSONException e) {
					// TODO 自动生成的 catch 块
				}
				back.user_type = itemObject.getString("user_type");
				//}
				success = true;
			}else{
				back.error = response.getString("error");
				success = false;
			}
		} catch (JSONException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			back.error = e.toString();
			return false;
		}
		
		return success;
	}
	
	public static boolean parseDesignerContractDetail(String str,NetworkData.MyContractDetailBack back){
		boolean success = false;
		try {
			JSONObject response = new JSONObject(str);
			int code = -1;
			code = response.getInt("code");
			back.code = code;
			try{
				back.error = response.getString("error");
			}catch (JSONException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
				success = true;
			}
			if(success){
				JSONObject itemObject = response.getJSONObject("data");
				back.c_id = itemObject.getString("c_id");
				back.bid_id = itemObject.getString("bid_id");
				back.owner_id = itemObject.getString("owner_id");
				back.contract_type = itemObject.getString("contract_type");
				back.real_name = itemObject.getString("real_name");
				//back.str_build_addr = itemObject.getString("build_addr");
				JSONObject build_addr = itemObject.getJSONObject("build_addr");
				if(build_addr != null){
					back.addr.province = build_addr.getString("provice");
					back.addr.city = build_addr.getString("city");
					back.addr.district = build_addr.getString("district");
					back.addr.road = build_addr.getString("road");
					back.addr.lane = build_addr.getString("lane");
					back.addr.number = build_addr.getString("number");
					back.addr.floor = build_addr.getString("floor");
					back.addr.road = build_addr.getString("road");
				}
				
				
				back.decorate_way = itemObject.getString("decorate_way");
				//back.str_start_date = itemObject.getString("start_date");
				JSONObject start_date = itemObject.getJSONObject("start_date");
				if(start_date != null){
					back.start_date.year = start_date.getString("year");
					back.start_date.month = start_date.getString("month");
					back.start_date.day = start_date.getString("day");
				}
				//back.str_end_date = itemObject.getString("end_date");
				/*JSONObject end_date = itemObject.getJSONObject("end_date");
				if(end_date != null){
					item.end_date.year = end_date.getString("year");
					item.end_date.month = end_date.getString("month");
					item.end_date.day = end_date.getString("day");
				}*/
				
				back.total_amount = itemObject.getString("total_amount");
				back.first_pay = itemObject.getString("first_pay");
				back.second_pay = itemObject.getString("second_pay");
				back.third_pay = itemObject.getString("third_pay");
				back.fourth_pay = itemObject.getString("fourth_pay");
				back.other_pay = itemObject.getString("other_pay");
				back.str_liability = itemObject.getString("liability");
				try{
					JSONObject liability = itemObject.getJSONObject("liability");
					if(liability != null){
						back.liability.unpaid_percent = liability.getString("unpaid_percent");
						back.liability.first_percent = liability.getString("first_percent");
						back.liability.second_percent = liability.getString("second_percent");
						back.liability.third_percent = liability.getString("third_percent");
						back.liability.fourth_percent = liability.getString("fourth_percent");
					}
				}catch (JSONException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
				
				back.guide_num = itemObject.getString("guide_num");
				back.plan = itemObject.getString("plan");
				back.free_num = itemObject.getString("free_num");
				back.draft_day = itemObject.getString("draft_day");
				back.final_day = itemObject.getString("final_day");
				back.create_time = itemObject.getString("create_time");
				back.is_confirm = itemObject.getString("is_confirm");
				back.is_check = itemObject.getString("is_check");
				
				back.is_paid = itemObject.getString("is_paid");
				back.is_complete = itemObject.getString("is_complete");
				back.prov_finish = itemObject.getString("prov_finish");
				back.title = itemObject.getString("title");
				
				back.province = itemObject.getString("provice");
				back.city = itemObject.getString("city");
				back.district = itemObject.getString("district");
				back.road = itemObject.getString("road");
				back.lane = itemObject.getString("lane");
				back.number = itemObject.getString("number");
				back.floor = itemObject.getString("floor");
				back.room = itemObject.getString("room");
				back.user_type = itemObject.getString("user_type");
				//}
				success = true;
			}else{
				back.error = response.getString("error");
				success = false;
			}
		} catch (JSONException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			back.error = e.toString();
			return false;
		}
		
		return success;
	}
	
	public static String createUpdateDesignerContractString(NetworkData.MyContractDetailBack data){
		String str = null;
		JSONObject jstring = new JSONObject();
		try {
			jstring.put("c_id", data.c_id);
			jstring.put("real_name", data.real_name);
			jstring.put("sessid", KoalaApplication.loginData.sessid);
			jstring.put("province", data.province);
			
			jstring.put("city", data.city);
			jstring.put("district", data.district);
			jstring.put("road", data.road);
			jstring.put("lane", data.lane);
			jstring.put("number", data.number);
			jstring.put("floor", data.floor);
			jstring.put("room", data.room);
			jstring.put("total_amount", data.total_amount);
			jstring.put("first_pay", data.first_pay);
			jstring.put("second_pay", data.second_pay);
			jstring.put("year", data.year);
			jstring.put("month", data.month);
			jstring.put("day", data.day);
			jstring.put("guide_num", data.guide_num);
			jstring.put("plan", data.plan);
			jstring.put("free_num", data.free_num);
			jstring.put("draft_day", data.draft_day);
			jstring.put("final_day", data.final_day);
			jstring.put("unpaid_percent", data.unpaid_percent);
			jstring.put("first_percent", data.first_percent);
			jstring.put("second_percent", data.second_percent);
			jstring.put("third_percent", data.third_percent);
			jstring.put("fourth_percent", data.fourth_percent);
			str = jstring.toString();
		} catch (JSONException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return str;
	}
	
	public static String parseGeo(String str){
		String city = null;
		String status;
		try {
			JSONObject jstring = new JSONObject(str);
			status = jstring.getString("status");
			if("OK".equals(status)){
				JSONObject itemObject = jstring.getJSONObject("result");
				JSONObject address = itemObject.getJSONObject("addressComponent");
				city = address.getString("city");
			}
		} catch (JSONException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			return null;
		}
		return city;
	}

}
