package com.winwinapp.network;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
				back.total = total;
				JSONArray array = response.getJSONArray("data");
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
				for(int i=0;i<total;i++){
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
			if(code == 0 || code == -1){//Need to modify in the future
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
			if(code == 0 || code == -1){
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
	
	public static String createTipsListString(NetworkData.DecorateTipsData data){
		String str = null;
		JSONObject jstring = new JSONObject();
		try {
			jstring.put("cat_id", data.cat_id);
			jstring.put("limit", data.limit);
			
			str = jstring.toString();
		} catch (JSONException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return str;
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
					item.regions_id = itemObject.getString("regions_id");
					item.regions_name = itemObject.getString("regions_name");
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
}
