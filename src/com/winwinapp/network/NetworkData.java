package com.winwinapp.network;

import java.util.ArrayList;
import java.util.Map;

import com.winwinapp.decorateTips.SubDecorateTips;

public class NetworkData {
	//public static String URL_BASE_API = "http://www.kaolaxj.com/index.php/Api";
	public static String URL_SERVER = "http://120.26.197.206";
	public static String URL_BASE_API = "http://120.26.197.206/index.php/Api";
	public static String URL_LOGIN = URL_BASE_API + "/Login/index.html";
	public static String URL_REGISTER = URL_BASE_API + "/Register/index.html";
	public static String URL_REGISTER_SEND_CODE = URL_BASE_API + "/Register/sendCode.html";
	public static String URL_REGISTER_RESET_PWD = URL_BASE_API + "/Login/findPassword.html";
	public static String URL_MEMBER_find = URL_BASE_API + "/Member/find.html";
	public static String URL_MEMBER_DETAIL = URL_BASE_API + "/Member/detail.html";
	public static String URL_MY_COLLECT = URL_BASE_API + "/Collect/find.html";
	public static String URL_ADD_COLLECT = URL_BASE_API + "/Member/addCollect.html";
	public static String URL_DEL_COLLECT = URL_BASE_API + "/Member/delCollect.html";
	public static String URL_PRIVATE_MESSAGE_LIST = URL_BASE_API + "/Message/privateList.html";
	public static String URL_PUBLIC_MESSAGE_LIST = URL_BASE_API + "/Message/publicList.html";
	public static String URL_PUBLIC_MESSAGE_INFO = URL_BASE_API + "/Message/publicInfo.html";
	public static String URL_PRIVATE_MESSAGE_INFO = URL_BASE_API + "/Message/pivateInfo.html";
	public static String URL_SEND_PUBLIC_MESSAGE = URL_BASE_API + "/Message/insert.html";
	public static String URL_SEND_PRIVATE_MESSAGE = URL_BASE_API + "/Message/reply.html";
	
	public static String URL_SEND_MESSAGE = URL_BASE_API + "/Message/insert.html";
	public static String URL_DECORATE_TIPS_DETAIL = URL_BASE_API + "/DecKms/docsDetail.html";
	public static String URL_BID_LIST = URL_BASE_API + "/BidProj/BidList.html";
	public static String URL_USER_BID_LIST = URL_BASE_API + "/BidProj/userBidList.html";
	public static String URL_BID_DETAIL = URL_BASE_API + "/BidProj/getOneBid.html";
	public static String URL_USER_BID_DETAIL = URL_BASE_API +"/BidProj/bidDetail.html";
	public static String URL_USER_BID_EDIT = URL_BASE_API +"/BidProj/edit.html";
	public static String URL_USER_BID_EDIT_FINISH = URL_BASE_API +"/BidProj/doEdit.html";
	public static String URL_BID_PUBLISH = URL_BASE_API + "/BidProj/insert.html";
	public static String URL_BID_VIE = URL_BASE_API + "/BidProj/vieBid.html";
	public static String URL_BID_ABORT = URL_BASE_API + "/BidProj/truncate.html";
	public static String URL_GET_REGIONS = URL_BASE_API +"/BidProj/getRegions.html";
	public static String URL_DELETE_PUBLIC_MSG = URL_BASE_API + "/Message/publicTruncate.html";
	public static String URL_DELETE_PRIVATE_MSG = URL_BASE_API + "/Message/privateTruncate.html";
	public static String URL_MY_CONTRACT_LIST = URL_BASE_API + "/Contract/contractList.html";
	public static String URL_MY_CONTRACT_DETAIL = URL_BASE_API + "/Contract/contractDetail.html";
	public static String URL_MY_CONTRACT_AGREE = URL_BASE_API + "/Contract/confirm.html";
	public static String URL_MY_CONTRACT_REJECT = URL_BASE_API + "/Contract/doReject.html";
	public static String URL_CONTRACT_UPDATE = URL_BASE_API + "/Contract/insertContract.html";
	public static String URL_DECORATE_LIST = URL_BASE_API + "/BidProj/decList.html";//装修列表
	public static String URL_OTHER_DECORATE_LIST = URL_BASE_API + "/Decorate/winDecList.html";
	public static String URL_OTHER_DECORATE_DETAIL = URL_BASE_API + "/Decorate/winDecDetail.html";
	
	public static String URL_SET_DEPOSIT = URL_BASE_API + "/Decorate/finishProjEnd.html";//设置保证金
	public static String URL_USER_EVALUE = URL_BASE_API + "/Decorate/userEvaluate.html";
	
	public static String URL_DECKMS = URL_BASE_API + "/DecKms/decKmsList.html";
	public static String URL_DECKMS_WEBPAGE = URL_SERVER + "/index.php/Home/DecKms/detail/doc/";
	public static final int USER_TYPE_MEMBER = 1;
	public static final int USER_TYPE_DESIGNER = 2;
	public static final int USER_TYPE_SUPERIOR = 3;
	public static final int USER_TYPE_LABOR = 4;
	private static NetworkData mInstance = new NetworkData();
	
	public static NetworkData getInstance(){
		return mInstance;
	}
	
	public CommonBack getCommonBack(){
		return new CommonBack();
	}
	
	public class CommonBack{
		public int code = -1;
		public String error = null;
	}
	
	public UserEvalueData getNewUserEvalueData(){
		return new UserEvalueData();
	}
	
	public class UserEvalueData{
		public int bid;
		public String s1 = "";//工长
		public String p1 = "";
		public String commend1 = "";//
		public String s2;//设计师服务态度
		public String p2;//设计师专业水平
		public String commend2;//设计师评语
		public String s3;//监理
		public String p3;
		public String commend3;//
	}
	
	public SetDepositData getNewSetDepositData(){
		return new SetDepositData();
	}
	
	public class SetDepositData{
		public int bid;
		public int grate;
	}
	
	public LoginData getNewLoginData(){
		return new LoginData();
	}
	
	public class LoginData {
		public String username;
		public String password;
		
		public LoginData(){
			username = null;
			password = null;
		}
	}
	
	public LoginBack getNewLoginBack(){
		return new LoginBack();
	}
	
	public class LoginBack{
		public int code;//0,success; -1 or other fail
		public String error;//error message,"username or password invalid"
		public String id;//the id for user,ie "2"
		public String sessid; // the code of sessid,ie "83j83u7cgpt6fu5vcn1qa43"
		public String username;
		
		public LoginBack(){
			code = 0;
			error = null;
			id = null;
			sessid = null;
		}
	}
	
	public RegisterData getNewRegisterData(){
		return new RegisterData();
	}
	
	public class RegisterData {
		public String username;
		public String password;
		public int type;//1,huiyuan; 2,designer; 3 superior; 4 labor;
		public String email;
		public String mobile;
	}
	
	public RegisterSendCodeData getNewRegisterSendCodeData(){
		return new RegisterSendCodeData();
	}
	
	public class RegisterSendCodeData {
		public String phone_mail;
		public int is_email;
		public String auth_code;
	}
	
	public RegisterBack getNewRegisterBack(){
		return new RegisterBack();
	}
	
	public class RegisterBack{
		public int code = -1;//ie, 0
		public String error ;
		public String id;
		public String sessid;
	}
	
	public class FindMemberData{
		int type;//the type to find, only 2,3,4
		String keyword;//the keyword  for find
		int work_num; //the worker's work age
		String sort;//sort:work(工作年限),attud(服务态度),rate(专业水平),case(案例数)
		int page;//current page number
		int limit;//the item number for each page
		public FindMemberData(){
			type = 2;
			keyword = null;
			work_num = 1;
			sort = null;
			page = 1;
			limit = 5;
		}
	}
	
	public class FindMemberItem{
		String username = null;
		int work_num = 0;
		int name_auth = 0;//实名认证:0,not auth;1,auth
		String avatar;//the location of avatar
		String casename;//the latest case name
		float rate_avg = 0.0f;//the rate average
		float attud_avg = 0.0f;//the attitude average
		int case_num = 0;
		String introduce = null;
	}
	
	public class FindMemberBack{
		int code = -1;
		int total = 0;
		ArrayList<FindMemberItem> memberInfo = new ArrayList<FindMemberItem>();
	}
	
	public class MemberDetailData{
		int uid;//the id of member
	}
	
	public class MemberDetailBack{
		String code = "-1";
		String username = null;
		String email = null;
		String qq = null;
		String mobile = null;
		int work_num = 0;
		int name_auth = 0;
		String avatar = null;
		String casename = null;
		float rate_avg = 0.0f;
		float attud_avg = 0.0f;
		int case_num = 0;
		String introduce = null;
	}
	
	public class MyCollectData{
		int typeid = 2;//only 2,3,4
		int page = 1;//current page number
		int limit = 5;//
		String sessid;
	}
	
	public class MyCollectBack extends FindMemberBack{
		
	}
	
	public class AddDelMyCollectData{
		int uid = 0;//
		String sessid = null;
	}
	
	public SendMessageData getNewSendMessageData(){
		return new SendMessageData();
	}
	
	public class SendMessageData{
		public int topic_id;
		public String content;
		public int rec_id;
	}
	
	//personal center ---> message, need to login in
	
	public PrivateMessageListData getNewPrivateMessageListData(){
		return new PrivateMessageListData();
	}
	/*
	 * type以某种方式搜索站内信，选择发送者，输入的keyword为用户名；
	 * 选择站内信内容，输入的keyword为消息的内容
	 * */
	public class PrivateMessageListData{
		public int type;//1,发信者;2,消息内容
		public String keyword;//用户名或者站内信内容
		public int limit; //每页显示的条数
		public int page; //current page
	}
	
	public class MessageListBase{
		public int code = -1;
		public String error = null;
		public int total = 0;//所有自己发送和接受私信条数
	}
	
	public class MessageListItemBase{
		public String id = null;//私信内容编号
		public String send_id;//发送私信用户编号
		public String content;//站内信内容
		public String type;//站内信类型：0，私信；1，公共消息；2，系统消息
		public String user_type;//为公共消息时，该字段为公共消息类型：0：私信或系统消息；1：会员；2，设计师；3：监理；4：工长
		public String topic_type;//话题编号。0，为系统消息，无回复；其它，主题编号，自增序列
		public String send_time;//发送时间
		public String bid_id;//招标编号
		public String is_agree;//是否同意。0，未决定；1，已同意；2，不同意；3，已签约
		public String is_delete;//私信发送者是否删除。0，删除；1，未删除
		public String m_id;//信息编号
		public String rec_id;//接收者用户编号
		public String msg_id;//站内信内容编号
		public String status;//接收者查看信息状态。0，删除；1，未读；2，已读
	}
	
	public PrivateMessageListItem getNewPrivateMessageListItem(){
		return new PrivateMessageListItem();
	}
	
	public class PrivateMessageListItem extends MessageListItemBase{
		public String topic_id;
		public String send_name;//发送信息用户名
		public String avatar;//发送信息用户头像，绝对地址
		public String rec_name;//接受信息用户名
		public int flag;//标识，1：未同意；2，未查看；3，显示回复数
		public String reply_num;//私信回复数
		public String bold_class;
		public String show_pic;
		public String max_time;
	}
	
	
	public PrivateMessageListBack getNewPrivateMessageListBack(){
		return new PrivateMessageListBack();
	}
	public class PrivateMessageListBack extends MessageListBase{
		public ArrayList<PrivateMessageListItem> items = new ArrayList<PrivateMessageListItem>();
	}
	
	public PrivateMessageInfoData getNewPrivateMessageInfoData(){
		return new PrivateMessageInfoData();
	}
	
	public class PrivateMessageInfoData{
		public int topic_id;
		public int msg_id;
		public int rec_id;
	}
	
	public PrivateMessageInfoBack getNewPrivateMessageInfoBack(){
		return new PrivateMessageInfoBack();
	}
	
	public class PrivateMessageInfoBack{
		public int code = 0;
		public String error;
		public int total = 0;
		public ArrayList<PrivateMessageInfoItem> items = new ArrayList<PrivateMessageInfoItem>();
	}
	
	public PrivateMessageInfoItem getNewPrivateMessageInfoItem(){
		return new PrivateMessageInfoItem();
	}
	
	public class PrivateMessageInfoItem extends MessageListItemBase{
		public String topic_id;
		public String send_name;
		public String avatar;
		public String rec_name;
		public int flag;
		public String user_id;
		public String user_name;
		public String accept_id;
		public String accept_name;
		public String show_pic;
	}
	
	public PublicMessageListData getNewPublicMessageListData(){
		return new PublicMessageListData();
	}
	
	public class PublicMessageListData{
		public String sessid = null;
		public int is_read = 0;//0.未读信息，1，已读
		public int limit = 5;//每页显示的条数
		public int page = 0;//current page
	}
	
	public class PublicMessageListBack extends MessageListBase{
		public ArrayList<PublicMessageListItem> items = new ArrayList<PublicMessageListItem>();
	}
	
	public class PublicMessageListItem extends MessageListItemBase{
		public String topic_id;
		public String bold_class;
		public String is_exist;//是否在信息表中存在，0----不存在，1----存在
	}
	
	public PublicMessageListItem getNewPublicMessageItem(){
		return new PublicMessageListItem();
	}
	
	public PublicMessageListBack getNewPublicMessageListBack(){
		return new PublicMessageListBack();
	}
	
	public PublicMessageInfoData getNewPublicMessageInfoData(){
		return new PublicMessageInfoData();
	}
	
	public class PublicMessageInfoData{
		public int msg_id = 0;
		public int is_exist = 0;//0,不存在;1,存在
	}
	
	public PublicMessageInfoItem getNewPublicMessageInfoItem(){
		return new PublicMessageInfoItem();
	}
	public class PublicMessageInfoItem extends MessageListItemBase{
		public String topic_id;
	}
	
	public PublicMessageInfoBack getNewPublicMessageInfoBack(){
		return new PublicMessageInfoBack();
	}
	
	public class PublicMessageInfoBack{
		public int code = 0;
		public String error;
		public PublicMessageInfoItem item = new PublicMessageInfoItem();
	}
	
	public class checkPrivateMessageData{
		int topic_id;//主题编号
		int msg_id;//站内信息编号
		int rec_id;//接收信息用户编号
	}
	
	public class CheckPrivateMessageBack extends MessageListBase{
		String topic_id;
		String send_name;
		String avatar;
		String rec_name;
		int flag;
		String user_id;
		String user_name;
		String accept_id;
		String accept_name;
	}
	
	public class DecorateTipsData{
		public int limit = 10;
		public int page = 0;
		public int cid = 0;//文档分类编号,8----装修宝典列表；9----装修论坛列表
	}
	
	public class DecorateTipsBack{
		public int code = -1;
		public String error = null;
		public int total = 0;
		public DecorateTipsItem items = new DecorateTipsItem();
	}
	
	public DecorateTipsData getDecorateTipsData(){
		return new DecorateTipsData();
	}
	
	public DecorateTipsBack getDecorateTipsBack(){
		return new DecorateTipsBack();
	}
	
	public class DecorateTipsItem extends MessageListBase{
		public String code;
		public String error;
		public int cid;
		public String choose_cat;
		public String docCats;
		public String keywords;
		public int total;
		public ArrayList<Map<String,String>> cats = new ArrayList<Map<String,String>>();
		public ArrayList<SubDecorateTips> subDecorate = new ArrayList<SubDecorateTips>();
	}
	
	public class DecorateTipDetailData{
		public int doc_id;
		public int cat_id;
	}
	
	public class DecorateTipDetailBack{
		public int code;
		public String error;
		public String doc_id;
		public String cat_id;
		public String title;
		public String content;
		public String author;
		public String keywords;
		public String is_open;
		public String add_time;
		public String scan_num;
		public String prev_id;
		public String prev_title;
		public String next_id;
		public String next_title;
		public String pcat_id;//父文档分类id
		public String pcat_name;//父文档名称
	}
	
	public DecorateTipsItem getDecorateTipItem(){
		return new DecorateTipsItem();
	}
	
	public DecorateTipDetailData getDecorateTipDetailData(){
		return new DecorateTipDetailData();
	}
	
	public DecorateTipDetailBack getDecorateTipDetailBack(){
		return new DecorateTipDetailBack();
	}
	
	public BidListData getNewBidListData(){
		return new BidListData();
	}
	
	public class BidListData{
		public int limit = 10;
		public int page = 0;
	}
	
	public BidListItem getNewBidListItem(){
		return new BidListItem();
	}
	
	public class BidListItem{
		public String bid_id;
		public String user_id;
		public String name;
		public String mobile;
		public String decorate_way;
		public String design_style;
		public String budget;
		public String house_type;
		public String space_type;
		public String area;
		public String biotope_name;
		public String province;
		public String city;
		public String district;
		public String requirement;
		public String title;
		public String publish_time;
		public String start_time;
		public String is_signed;
		public String need_designer;
		public String need_foreman;
		public String need_supervisor;
		public String bid_number;
		public String new_bidder;
		public String prov_rate;
		public String status;
		public String proj_stat;
		public String is_delete;
		public String bidder_type;
		public String complete_time;
		public String bidder_type_img;
		public String postion;
	}
	
	public BidListBack getNewBidListBack(){
		return new BidListBack();
	}
	
	public class BidListBack{
		public int code = -1;
		public String error = null;
		public int total = 0;
		public ArrayList<BidListItem> items = new ArrayList<BidListItem>();
	}
	
	public UserBidEditBack getNewUserBidEditBack(){
		return new UserBidEditBack();
	}
	
	public class UserBidEditBack extends BidListBack{
		public ArrayList<String> constDecorateWay = new ArrayList<String>();
		public ArrayList<String> constHouseType = new ArrayList<String>();
		public ArrayList<String> constDesignerStyle = new ArrayList<String>();
		public ArrayList<String> constBudget = new ArrayList<String>();
	}
	
	public BidDetailData getNewBidDetailData(){
		return new BidDetailData();
	}
	
	public class BidDetailData{
		public int bid_id = 3;
	}
	
	public BidPublishData getNewBidPublishData(){
		return new BidPublishData();
	}
	
	public ContractAgreeRejectData getNewContractAgreeRejectData(){
		return new ContractAgreeRejectData();
	}
	
	public class ContractAgreeRejectData{
		public int c_id = 0;
		public String reason;
	}
	
	public class BidPublishData{
		public String decorate_way = "0";
		public String design_style = "0";
		public int budget = 0;
		public int house_type = 2;
		public int room = 2;
		public int hall = 1;
		public int kitchen = 1;
		public int toilet = 1;
		public int balcony = 1;
		public int area = 120;
		public String biotope_name;
		public int province = 25;
		public int city = 321;
		public int district = 2715;
		public String req;
		public int need_designer = 1;
		public int need_forman = 1;
		public int need_supervisor = 1;
		public int new_bidder = 0;
	}
	
	public BidPostEditData getNewBidPostEditData(){
		return new BidPostEditData();
	}
	
	public class BidPostEditData{
		public int bid;
		public String decorate_way = "0";
		public String design_style = "0";
		public int budget = 0;
		public int house_type = 2;
		public int room = 2;
		public int hall = 1;
		public int kitchen = 1;
		public int toilet = 1;
		public int balcony = 1;
		public int area = 120;
		public String biotope_name;
		public int province = 25;
		public int city = 321;
		public int district = 2715;
		public String req;
		public int need_designer = 1;
		public int need_forman = 1;
		public int need_supervisor = 1;
		public int new_bidder = 0;
	}
	
	
	public UserBidDetailBidders getNewUserBidDetailBidders(){
		return new UserBidDetailBidders();
	}
	
	public class UserBidDetailBidders{
		public String bid;
		public String uid;
		public String username;
		public String name_auth;
		public String type;
		public String commend;
		public String msg;
		public String datetime;
		public String work_num;
		public String avatar;
		public String case_num;
		public String rate_avg;
		public String attud_avg;
	}
	
	public UserBidDetailBack getNewUserBidDetailBack(){
		return new UserBidDetailBack();
	}
	
	public class UserBidDetailBack{
		public int code = -1;
		public String error;
		public BidListItem bid_info = new BidListItem();
		public ArrayList<UserBidDetailBidders> designers = new ArrayList<UserBidDetailBidders>();
		public ArrayList<UserBidDetailBidders> labors = new ArrayList<UserBidDetailBidders>();
		public ArrayList<UserBidDetailBidders> superiors = new ArrayList<UserBidDetailBidders>();
		int des_cnt = 0;
		int frm_cnt = 0;
		int sup_cnt = 0;
		public String des_uid;
		public String frm_uid;
		public String sup_uid;
	}
	
	public BidVieData getNewBidVieData(){
		return new BidVieData();
	}
	
	public class BidVieData{//竞标（设计师，工长，监理）
		public int bidid = 10;
		public String bidmsg = "我打算竞标，能成功么?";
		public String sessid;
	}
	
	public BidAbortData getNewBidAbortData(){
		return new BidAbortData();
	}
	
	public class BidAbortData{
		public int bid;
	}
	
	public RegionsData getNewRegionsData(){
		return new RegionsData();
	}
	
	public class RegionsData{
		public int type = 1;//1,省； 2，市，3，区/县
		public int parent = 1;
	}
	
	public RegionsItem getNewRegionsItem(){
		return new RegionsItem();
	}
	
	public class RegionsItem{
		public String regions_id;
		public String regions_name;
	}
	
	public RegionsBack getNewRegionsBack(){
		return new RegionsBack();
	}
	
	public class RegionsBack{
		public int code;
		public String error;
		public ArrayList<RegionsItem> regions = new ArrayList<RegionsItem>();
	}
	
	public DeletePrivateMsgData getNewDeletePrivateMsgData(){
		return new DeletePrivateMsgData();
	}
	
	public class DeletePrivateMsgData{
		public ArrayList<Integer> mIds = new ArrayList<Integer>();
	}
	
	public DeletePublicMsgData getNewDeletePublicMsgData(){
		return new DeletePublicMsgData();
	}
	
	public class DeletePublicMsgData{
		public int id;
		public int is_exist;
		public int rec_id;
	}
	
	public MyContractListBack getNewMyContractListBack(){
		return new MyContractListBack();
	}
	
	public class MyContractListBack{
		public int code;
		public int total;
		public String error;
		public ArrayList<MyContractListItem> items = new ArrayList<MyContractListItem>();
	}
	
	public MyContractListItem getNewMyContractListItem(){
		return new MyContractListItem();
	}
	
	public class MyContractListItem{
		public String c_id;
		public String bid_id;
		public String owner_id;
		public String operate_id;
		public String contract_type;
		public String real_name;
		public String str_build_addr;
		public build_addr addr = new build_addr(); 
		public String decorate_way;
		public String str_start_date;
		public Date start_date = new Date();
		public String str_end_date;
		public Date end_date = new Date();
		public String total_amount;
		public String first_pay;
		public String second_pay;
		public String third_pay;
		public String fourth_pay;
		public String other_pay;
		public String str_liability;
		public Liability liability = new Liability();
		public String guide_num;
		public String plan;
		public String free_num;
		public String draft_day;
		public String final_day;
		public String create_time;
		public String is_confirm;
		public String is_check;
		public String is_paid;
		public String is_complete;
		public String prov_finish;
		public String title;
		public int unpaid_percent;
		public int first_percent;
		public int second_percent;
		public int third_percent;
		public int fourth_percent;
		public int add_day;
		public int normal_day;
		public int percent;
	}
	
	public class build_addr{
		public String province;
		public String city;
		public String district;
		public String road;
		public String lane;
		public String number;
		public String floor;
		public String room; 
	}
	
	public class Date{
		public String year;
		public String month;
		public String day;
	}
	
	public class Liability{
		public String unpaid_percent;
		public String first_percent;
		public String second_percent;
		public String third_percent;
		public String fourth_percent;
	}
	
	public MyContractDetailBack getNewMyContractDetailBack(){
		return new MyContractDetailBack();
	}
	
	public class MyContractDetailBack extends MyContractListItem{
		public int code;
		public String error;
		public String province;
		public String city;
		public String district;
		public String road;
		public String lane;
		public String number;
		public String floor;
		public String room;
		public String syear;
		public String smonth;
		public String sday;
		public String eyear;
		public String emonth;
		public String eday;
		public String user_type;
		public int year;
		public int month;
		public int day;
	}

}

