package com.neusoft.sdd.util.wx;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.ParseException;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.neusoft.sdd.base.model.wx.pojo.CallBackInfo;
import com.neusoft.sdd.base.model.wx.pojo.ResultInfo;

/**
 * [简要描述]:<br/>
 * [详细描述]:<br/>
 * 
 * @version [revision],2015年5月19日
 * @since Neusoft 001
 */
public class WxEnterpriseInterfaceUtils {
	
	public static String getToken() {
		String token = PropertiesUtils.getValue("wxToken");
		String wxTimeStamp = PropertiesUtils.getValue("wxTimeStamp");
		String expiresIn = PropertiesUtils.getValue("expiresIn");

		if (StringUtils.isBlank(wxTimeStamp)
				|| System.currentTimeMillis() - Long.parseLong(wxTimeStamp) > (Integer
						.parseInt(expiresIn) * 1000 - 500)) {

            String url = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=" + PropertiesUtils.getValue("corpID")
                    + "&corpsecret=" + PropertiesUtils.getValue("secret");

            // String url =
            // "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=wxbec5ed1a233b5d3d&corpsecret=bJLSJGucE4UMyahpkxJaWd5R7LX6wkAACLSD-Oc0bxAN3Qo_ptn8SJkKS48KgMvO";
            String respText = HttpUtils.httpGet(url);

            System.out.println(respText);
            Map<String, Object> tokenMap = new HashMap<String, Object>();

			try {
				tokenMap = JsonUtils.parseJSON2Map(respText);
				token = tokenMap.get("access_token").toString();
				wxTimeStamp = String.valueOf(System.currentTimeMillis());
				expiresIn = tokenMap.get("expires_in").toString();
				PropertiesUtils.setValue("wxToken", token);
				PropertiesUtils.setValue("wxTimeStamp", wxTimeStamp);
				PropertiesUtils.setValue("expiresIn", expiresIn);
			} catch (ParseException e) {
				e.printStackTrace();
			}

		}

        return token;
    }

	public static String getJsTicket() {
        String url = "https://qyapi.weixin.qq.com/cgi-bin/get_jsapi_ticket?access_token=" + getToken();

        String ticket = PropertiesUtils.getValue("wxJsTicket");
        String wxJsTicketTimeStamp = PropertiesUtils.getValue("wxJsTicketTimeStamp");
        String wxJsexpiresIn = PropertiesUtils.getValue("wxJsexpiresIn");

		if (StringUtils.isBlank(ticket)
				|| System.currentTimeMillis()
						- Long.parseLong(wxJsTicketTimeStamp) > (Integer
						.parseInt(wxJsexpiresIn) * 1000 - 500)) {

            String respText = HttpUtils.httpGet(url);
            System.out.println(respText);
            Map<String, Object> ticketMap = new HashMap<String, Object>();

			try {
                ticketMap = JsonUtils.parseJSON2Map(respText);
                ticket = ticketMap.get("ticket").toString();
                wxJsTicketTimeStamp = String.valueOf(System.currentTimeMillis());
                wxJsexpiresIn = ticketMap.get("expires_in").toString();
                PropertiesUtils.setValue("wxJsTicket", ticket);
                PropertiesUtils.setValue("wxJsTicketTimeStamp", wxJsTicketTimeStamp);
                PropertiesUtils.setValue("wxJsexpiresIn", wxJsexpiresIn);
			} catch (ParseException e) {
				e.printStackTrace();
			}

		}
		return ticket;
	}

	public static Map<String, Object> uploadMedia(String path) {
		return HttpUtils.upload(path, getToken());
	}

	public static void importOrg(CallBackInfo callBack, String mediaId,
			String accessToken) {
		HttpUtils.importOrg(callBack, mediaId, accessToken);
	}

	public static void createOrg(String orgJson) {
        String url = "https://qyapi.weixin.qq.com/cgi-bin/department/create?access_token=" + getToken();
        HttpUtils.postJson(url, orgJson);
    }

	public static void importAllUser(CallBackInfo callBack, String mediaId,
			String accessToken) {
		HttpUtils.importAllUser(callBack, mediaId, accessToken);
	}

	public static void batchDelUsers(String userJson) {
        String url = "https://qyapi.weixin.qq.com/cgi-bin/user/batchdelete?access_token=" + getToken();
        HttpUtils.postJson(url, userJson);
    }

	public static ResultInfo queryUserByDepartment(String departmentid,
			String child, String status) {
        String url = "https://qyapi.weixin.qq.com/cgi-bin/user/simplelist?access_token=" + getToken()
                + "&department_id=" + departmentid + "&fetch_child=" + child + "&status=" + status;
        String respText = HttpUtils.httpGet(url);
        ObjectMapper mapper = new ObjectMapper();
        ResultInfo result = new ResultInfo();
		try {
			result = mapper.readValue(respText, ResultInfo.class);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        return result;

    }
    
	public static Map<String, Object> getUserInfoById(String userId) {
        String url = "https://qyapi.weixin.qq.com/cgi-bin/user/get?access_token=" + getToken() + "&userid=" + userId;
        String respText = HttpUtils.httpGet(url);
        System.out.println(respText);
        return JsonUtils.parseJSON2Map(respText);
        
    }

	public static void createWxMenu(String agentid, String meunJson) {
        String url = "https://qyapi.weixin.qq.com/cgi-bin/menu/create?access_token=" + getToken() + "&agentid="
                + agentid;
        HttpUtils.postJson(url, meunJson);
    }

	public static <T> void sendMessage(T t) {
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
        System.out.println(jsonMsg);
        String url = "https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token=" + getToken();
        HttpUtils.postJson(url, jsonMsg);
    }
    
    /**
     * 部门：软件开发事业部
     * 功能：从微信服务器下载媒体文件
     * 描述：mediaId 媒体id,savePath 保存路径, 返回具体文件路径
     * 作成者：朱庆锋
     * 作成时间：2015-10-23
     */
	public static String downLoadMedia(String mediaId, String savePath) {
        String url = "https://qyapi.weixin.qq.com/cgi-bin/media/get?access_token="+ getToken() +"&media_id=" + mediaId;
        return  HttpUtils.download(url, savePath);
    }
    
    /**
     * 部门：软件开发事业部
     * 功能：企业根据员工code获取id
     * 描述：略
     * 作成者：朱庆锋
     * 作成时间：2015-10-23
     */
	public static Map<String, Object> getUserByCode(String code, String agentId) {
        String url = "https://qyapi.weixin.qq.com/cgi-bin/user/getuserinfo?access_token=" + getToken() + "&code="
                + code + "&agentid=" + agentId;
        String respText = HttpUtils.httpGet(url);
        System.out.println(respText);
        return JsonUtils.parseJSON2Map(respText);
    }
    
}
