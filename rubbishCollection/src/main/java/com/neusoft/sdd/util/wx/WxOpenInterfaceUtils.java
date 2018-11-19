package com.neusoft.sdd.util.wx;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.ParseException;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.neusoft.sdd.base.model.wx.dto.APPUser;
import com.neusoft.sdd.base.model.wx.pojo.ChildMenu;
import com.neusoft.sdd.base.model.wx.pojo.Menu;
import com.neusoft.sdd.util.commonUtil.GsonUtil;

/**
 * [简要描述]:<br/>
 * [详细描述]:<br/>
 * 
 * @version [revision],2015年5月19日
 * @since Neusoft 001
 */
public class WxOpenInterfaceUtils {
    
    /**
     * 部门：软件开发事业部
     * 功能：获取公众号token
     * 描述：略
     * 作成者：朱庆锋
     * 作成时间：2015-10-23
     */
	public static String getOpenToken() {
		//System.out.println("--------------------getOpenToken   start-----------------");
		String open_token = PropertiesUtils.getValue("open_token");
		String open_timestamp = PropertiesUtils.getValue("open_timestamp");
		String open_expiresIn = PropertiesUtils.getValue("open_expiresIn");

		if (StringUtils.isBlank(open_timestamp)
				|| System.currentTimeMillis() - Long.parseLong(open_timestamp) > (Integer
						.parseInt(open_expiresIn) * 1000 - 500)) {

            String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + PropertiesUtils.getValue("appId")
                    + "&secret=" + PropertiesUtils.getValue("appSecret");

            String respText = HttpUtils.httpGet(url);

            //System.out.println(respText);
            Map<String, Object> tokenMap = new HashMap<String, Object>();

			try {
				tokenMap = JsonUtils.parseJSON2Map(respText);
				open_token = tokenMap.get("access_token").toString();
				open_timestamp = String.valueOf(System.currentTimeMillis());
				open_expiresIn = tokenMap.get("expires_in").toString();

				PropertiesUtils.setValue("open_token", open_token);
				PropertiesUtils.setValue("open_timestamp", open_timestamp);
				PropertiesUtils.setValue("open_expiresIn", open_expiresIn);
			} catch (ParseException e) {
				e.printStackTrace();
			}

		}
		//System.out.println("--------------------getOpenToken   start-----------------"+open_token);
        return open_token;
    }

    /**
     * 部门：软件开发事业部
     * 功能：获取公众号js-sdk签名
     * 描述：略
     * 作成者：朱庆锋
     * 作成时间：2015-10-23
     */
	public static String getOpenJsTicket() {
        String url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=" + getOpenToken() + "&type=jsapi";

        String open_jsTicket = PropertiesUtils.getValue("open_jsTicket");
        String open_jsTicketTimeStamp = PropertiesUtils.getValue("open_jsTicketTimeStamp");
        String open_jsExpiresIn = PropertiesUtils.getValue("open_jsExpiresIn");

		if (StringUtils.isBlank(open_jsTicket)
				|| System.currentTimeMillis()
						- Long.parseLong(open_jsTicketTimeStamp) > (Integer
						.parseInt(open_jsExpiresIn) * 1000 - 500)) {

			String respText = HttpUtils.httpGet(url);
			//System.out.println(respText);
			Map<String, Object> ticketMap = new HashMap<String, Object>();

			try {
				ticketMap = JsonUtils.parseJSON2Map(respText);
				open_jsTicket = ticketMap.get("ticket").toString();
				open_jsTicketTimeStamp = String.valueOf(System
						.currentTimeMillis());
				open_jsExpiresIn = ticketMap.get("expires_in").toString();

				PropertiesUtils.setValue("open_jsTicket", open_jsTicket);
				PropertiesUtils.setValue("open_jsTicketTimeStamp",
						open_jsTicketTimeStamp);
				PropertiesUtils.setValue("open_jsExpiresIn", open_jsExpiresIn);
			} catch (ParseException e) {
				e.printStackTrace();
			}

		}
        return open_jsTicket;
    }
    
    /**
     * 部门：软件开发事业部
     * 功能：获取公众号用户身份信息
     * 描述：略
     * 作成者：朱庆锋
     * 作成时间：2015-10-23
     */
    public static APPUser getOauthUser(String code){
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + PropertiesUtils.getValue("appId")
                + "&secret=" + PropertiesUtils.getValue("appSecret")
                + "&code=" + code + "&grant_type=authorization_code";
        
        String respText = HttpUtils.httpGet(url);
        
        //System.out.println(respText);
        
        APPUser obj = GsonUtil.toBean(respText, APPUser.class);

		if (StringUtils.isNotBlank(obj.getErrorcode())) {
			obj = new APPUser();
		}

        return obj;
    }
    
    public static APPUser getAppUserBy(String access_token, String openid){
    	System.out.print("---------------getAppUserBy   start---------------------");
        String url = "https://api.weixin.qq.com/sns/userinfo?access_token=" + access_token + "&openid="
                + openid + "&lang=zh_CN";
        
        String respText = HttpUtils.httpGet(url);
        
        System.out.println(respText);

        APPUser obj = GsonUtil.toBean(respText, APPUser.class);

		if (StringUtils.isNotBlank(obj.getErrorcode())) {
			obj = new APPUser();
		} else {
			obj.setAccess_token(access_token);
		}
		System.out.print("---------------getAppUserBy   end---------------------");
        return obj;
    }
    
    public static APPUser getAppUserInfo(String access_token, String openid){
    	//System.out.print("---------------getAppUserInfo   start---------------------");
        String url = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=" + access_token + "&openid="
                + openid + "&lang=zh_CN";
        
        String respText = HttpUtils.httpGet(url);
        
        //System.out.println(respText);

        APPUser obj = GsonUtil.toBean(respText, APPUser.class);

		if (StringUtils.isNotBlank(obj.getErrorcode())) {
			obj = new APPUser();
		} else {
			obj.setAccess_token(access_token);
		}
		//System.out.print("---------------getAppUserInfo   end---------------------");
        return obj;
    }
    
    /**
     * 部门：软件开发事业部
     * 功能：刷新access_token
     * 描述：略
     * 作成者：朱庆锋
     * 作成时间：2015-10-23
     */
	public static Map<String, Object> refreshAccessToken(String refresh_token) {
        String url = "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=" + PropertiesUtils.getValue("appId")
                + "&grant_type=refresh_token&refresh_token=" + refresh_token;
        
        String respText = HttpUtils.httpGet(url);
        
        //System.out.println(respText);
        
        return JsonUtils.parseJSON2Map(respText);
    }
	
	
	/**
	 * 部门：软件开发事业部
	 * 功能：推送公众号的消息
	 * 描述：客服接口
	 * 作成者：朱庆锋
	 * 作成时间：2015-11-6
	 */
	public static <T> void sendMpMessage(T t) {
		ObjectMapper mapper = new ObjectMapper();
		String jsonMsg = null;
		try {
			jsonMsg = mapper.writeValueAsString(t);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		//System.out.println(jsonMsg);
		String url = "";
		try {
			url = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token="
					+ getOpenToken();
		} catch (Exception e) {
			e.printStackTrace();
		}
		HttpUtils.postJson(url, jsonMsg);

	}
	
	
	/**
	 * 部门：软件开发事业部
	 * 功能：创建菜单
	 * 描述：略
	 * 作成者：朱庆锋
	 * 作成时间：2015-11-13
	 * @throws Exception 
	 */
	public static void createMenu(String menuJson) throws Exception{
		String url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token="
				+ getOpenToken();
		HttpUtils.postJson(url, menuJson);
	}
	
	/**
	 * 部门：软件开发事业部
	 * 功能：删除菜单
	 * 描述：略
	 * 作成者：朱庆锋
	 * 作成时间：2015-11-13
	 */
	public static void deleteMenu() throws Exception{
		String url = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token="
				+ getOpenToken();
		HttpUtils.httpGet(url);
	}
	
	/**
	 * 部门：软件开发事业部
	 * 功能：获取自定义菜单配置接口
	 * 描述：略
	 * 作成者：朱庆锋
	 * 作成时间：2015-11-13
	 */
	public static String getCurrentSelfmenuInfo() throws Exception{
		String url = "https://api.weixin.qq.com/cgi-bin/get_current_selfmenu_info?access_token="
				+ getOpenToken();
		
		String respText = HttpUtils.httpGet(url);
		
		return respText;
	}
	
	/**
	 * 创建菜单
	 * 部门：软件开发事业部
	 * 描述：略
	 * 作成者：严惠惠
	 * 作成时间：2016-10-8
	 */
	public void createMenu()
	{
		String domain = "158298r00h.imwork.net";
        //关于我们
        List<ChildMenu> list1 = new ArrayList<ChildMenu>();
        
        ChildMenu childM11 = new ChildMenu();
        childM11.setName("产品介绍");
        childM11.setType("click");
        childM11.setKey("PRODUCTS_INFO");

        ChildMenu childM12 = new ChildMenu();
        childM12.setName("公司简介");
        childM12.setType("view");
        childM12.setUrl("http://kxcx.yuantong-online.com:8089/mobile/m/company.html");
        
        list1.add(childM11);
        list1.add(childM12);

        Menu menu1 = new Menu();
        menu1.setName("关于我们");
        menu1.setSub_button(list1);

        
        //网上业务
        List<ChildMenu> list2 = new ArrayList<ChildMenu>();
        
        ChildMenu childM21 = new ChildMenu();
        childM21.setName("电管家介绍");
        childM21.setType("click");
        childM21.setKey("ELECTRIC_INFO");

        ChildMenu childM22 = new ChildMenu();
        childM22.setName("预约电管家");
        childM22.setType("view");
        childM22.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx77ecaf383e4dd423&redirect_uri=http%3a%2f%2f"+domain+"%2fzhongda_mp%2felHousekeeper%2felHousekeeperPage%2forderelHouseKeeper.do&response_type=code&scope=snsapi_base&state=123#wechat_redirect");
        
        ChildMenu childM23 = new ChildMenu();
        childM23.setName("网上下单");
        childM23.setType("click");
        childM23.setKey("ONLINE_ORDER");
        
        ChildMenu childM24 = new ChildMenu();
        childM24.setName("库存查询");
        childM24.setType("view");
        childM24.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx77ecaf383e4dd423&redirect_uri=http%3a%2f%2f"+domain+"%2fzhongda_mp%2fstockCheck%2fstockCheckPage%2fstockCheck.do&response_type=code&scope=snsapi_base&state=123#wechat_redirect");

        
        list2.add(childM21);
        list2.add(childM22);
        list2.add(childM23);
        list2.add(childM24);
        
        Menu menu2 = new Menu();
        menu2.setName("网上业务");
        menu2.setSub_button(list2);

        //我的
        List<ChildMenu> list3 = new ArrayList<ChildMenu>();
        
        ChildMenu childM31 = new ChildMenu();
        childM31.setName("我的账号");
        childM31.setType("view");
        childM31.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx77ecaf383e4dd423&redirect_uri=http%3a%2f%2f"+domain+"%2fzhongda_mp%2fmyAccount%2fmyAccountPage%2fmyAccount.do&response_type=code&scope=snsapi_base&state=123#wechat_redirect");

        ChildMenu childM32 = new ChildMenu();
        childM32.setName("我的福利");
        childM32.setType("view");
        childM32.setUrl("");
        
        ChildMenu childM33 = new ChildMenu();
        childM33.setName("我的活动");
        childM33.setType("view");
        childM33.setUrl("");
        
        list3.add(childM31);
        list3.add(childM32);
        list3.add(childM33);
        
        Menu menu3 = new Menu();
        menu3.setName("我的");
        menu3.setSub_button(list3);
        
        List<Menu> button = new ArrayList<Menu>();
        button.add(menu1);
        button.add(menu2);
        button.add(menu3);
        
        Map<String, Object> menuMap = new HashMap<String, Object>();
        menuMap.put("button", button);
        
        System.err.println(GsonUtil.toJson(menuMap));
        try {
            WxOpenInterfaceUtils.createMenu(GsonUtil.toJson(menuMap));
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	
	/**
	 * 根据经度纬度获取地址
	 * 部门：软件开发事业部
	 * 描述：略
	 * 作成者：严惠惠
	 * 作成时间：2016-10-10
	 */
	 public static String getAddress(String latitude, String longitude){
	        String url = "http://apis.map.qq.com/ws/geocoder/v1/?location="+latitude+","+longitude+"&key=HC3BZ-W7SHU-GUTVY-2MOH3-HZMC5-F7F3D&get_poi=0";
	        return HttpUtils.httpGet(url);
	    }
	 
	
	public static void main(String[] args) {
		    Map<String, String> menuMap = new HashMap<String, String>();
	        menuMap.put("pCode", "8055323927");
	        System.err.println(GsonUtil.toJson(menuMap));
	        System.err.println(HttpUtils.httpPost("http://weixin.yuantong-online.com:8012/ds/getP.aspx",  HttpUtils.getPostForm(menuMap)));
	}
}
