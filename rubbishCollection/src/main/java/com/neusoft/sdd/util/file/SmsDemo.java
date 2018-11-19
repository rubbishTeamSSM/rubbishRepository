package com.neusoft.sdd.util.file;

import java.util.HashMap;
import java.util.Map;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.neusoft.sdd.util.commonUtil.GsonUtil;
/**
 * Created on 17/6/7.
 * 短信API产品的DEMO程序,工程中包含了一个SmsDemo类，直接通过
 * 执行main函数即可体验短信产品API功能(只需要将AK替换成开通了云通信-短信产品功能的AK即可)
 * 工程依赖了2个jar包(存放在工程的libs目录下)
 * 1:aliyun-java-sdk-core.jar
 * 2:aliyun-java-sdk-dysmsapi.jar
 *
 * 备注:Demo工程编码采用UTF-8
 * 国际短信发送请勿参照此DEMO
 */
public class SmsDemo {

    //产品名称:云通信短信API产品,开发者无需替换
    static final String product = "Dysmsapi";
    //产品域名,开发者无需替换
    static final String domain = "dysmsapi.aliyuncs.com";

    // TODO 此处需要替换成开发者自己的AK(在阿里云访问控制台寻找)
    static final String accessKeyId = "LTAIlTeT9ZLVKOc5";
    static final String accessKeySecret = "yVoSOCMdgji9mYcvEdQRS7IaHxUEDF";

    /**
     * 短信验证码
     */
    public static void sendSms(String mobile,String note) throws ClientException{
        //可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");

        //初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);

        //组装请求对象-具体描述见控制台-文档部分内容
        SendSmsRequest request = new SendSmsRequest();
        //必填:待发送手机号
        request.setPhoneNumbers(mobile);
        //必填:短信签名-可在短信控制台中找到
        request.setSignName("海信地产");
        //必填:短信模板-可在短信控制台中找到
        request.setTemplateCode("SMS_103345014");
        request.setTemplateParam("{\"code\":"+note+"}");
        acsClient.getAcsResponse(request);
    }
    
    /**
     * 部门：软件开发事业部
     * 功能：工单超时未接单
     * 描述：mobile：被通知人手机号、userName：工单处理人姓名、areaName：小区名称、phone：工单处理人联系电话
     * 作成者：朱庆锋
     * 作成时间：2017-12-15
     */
    public static void sendMsg(String mobile, String userName, String areaName, String phone) throws ClientException{
        //可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");

        //初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);

        //组装请求对象-具体描述见控制台-文档部分内容
        SendSmsRequest request = new SendSmsRequest();
        //必填:待发送手机号
        request.setPhoneNumbers(mobile);
        //必填:短信签名-可在短信控制台中找到
        request.setSignName("海信地产");
        //必填:短信模板-可在短信控制台中找到
        request.setTemplateCode("SMS_117522269");
        
        StringBuffer buf = new StringBuffer();
    	buf.append(userName);
        if(null != phone && !"".equals(phone)){
        	buf.append("（").append(phone).append("）");
        } else {
        	buf.append("岗位");
        }
        
        Map<String, String> msg = new HashMap<String, String>();
        msg.put("name", buf.toString());
        msg.put("areaName", areaName);
        
        request.setTemplateParam(GsonUtil.toJson(msg));
        acsClient.getAcsResponse(request);
    }

/*    public static void main(String[] args) {
        try {
			sendSms("18351932268","001450");
		} catch (ClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }*/

}
