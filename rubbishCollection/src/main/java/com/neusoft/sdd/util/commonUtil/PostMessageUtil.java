package com.neusoft.sdd.util.commonUtil;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.neusoft.sdd.base.systemLog.SystemControllerLog;
import com.neusoft.sdd.util.wx.HttpUtils;

public class PostMessageUtil {
	public static final String APPSECRET = "";
	public static final String APPKEY = "";
	public static final String API_CUST_CODE = "";
	
	/**
	 * 
	 * 部门：软件开发事业部
	 * 功能：调用erp接口
	 * 描述：参数flag=1是外商，flag = 2 是供应商
	 * 作成者：李万迪
	 * 作成时间：2017-6-13
	 */
	@SuppressWarnings("unchecked")
	@SystemControllerLog(description="调用erp接口")
	public static String postForeignMessage(int flag,Map<String, String> message){
		Map<String, String> item  = new HashMap<String, String>();
		Map<String, Object> request  = new HashMap<String, Object>();
		Map<String, String> heads  = new HashMap<String, String>();
		Map<String, Object> data = new HashMap<String, Object>();
		if(flag == 1){
			//item
			String sign_cust_code = "";//签约客户编码
			String cust_address = message.get("ADDRESS");//地址
			String cust_country = "";//国家
			String cust_province = "";//省
			String cust_city = "";//城市
			String cust_county = "";//区县
			String cust_linkphone = message.get("LINKMAN_TEL");//联系电话
			String cust_telephone = "";//电话
			String cust_zipcode = "";//邮政编码
			String cust_email = message.get("EMAIL");//email
			String cust_fax = message.get("FAX_NO");//传真
			String cust_credit_no = "TAX_NO";//社会信用号码
			String cust_tax_type = "";//纳税人分类
			String cust_tax_no = "";//纳税人识别号
			String bank_code = "";//银行编码
			String bank_desc = "";//银行描述
			String bank_account = "";//银行账号
			String bank_address = "";//开户行地址
			String cust_account_group = "";//账户组
			String cust_type = message.get("FOREIGN_MERCHANT_TYPE_CODE");//客户分类
			String cust_industry = "";//所属行业
			String cust_legal_person = "";//法人代表
			String supply_code = "";//供应商代码
			String goods_owner_code = "";//货主编码
			String customer_source = "";//客户来源
			item.put("sign_cust_code", sign_cust_code);
			item.put("cust_address", cust_address);
			item.put("cust_country", cust_country);
			item.put("cust_province", cust_province);
			item.put("cust_city", cust_city);
			item.put("cust_county", cust_county);
			item.put("cust_linkphone", cust_linkphone);
			item.put("cust_telephone", cust_telephone);
			item.put("cust_zipcode", cust_zipcode);
			item.put("cust_email", cust_email);
			item.put("cust_fax", cust_fax);
			item.put("cust_credit_no", cust_credit_no);
			item.put("cust_tax_type", cust_tax_type);
			item.put("cust_tax_no", cust_tax_no);
			item.put("bank_code", bank_code);
			item.put("bank_desc", bank_desc);
			item.put("bank_account", bank_account);
			item.put("bank_address", bank_address);
			item.put("cust_account_group", cust_account_group);
			item.put("cust_type", cust_type);
			item.put("cust_industry", cust_industry);
			item.put("cust_legal_person", cust_legal_person);
			item.put("supply_code", supply_code);
			item.put("goods_owner_code", goods_owner_code);
			item.put("customer_source", customer_source);
			//request
			String cust_name = message.get("FOREIGN_MERCHANT_NAME_CN");
			request.put("cust_name", cust_name);
			request.put("cust_credit_no", cust_credit_no);
			request.put("item", item);
		}else{
			//item
			String supply_address = message.get("ADDRESS");//地址
			String supply_country = "";//国家
			String supply_province = "";//省
			String supply_city = "";//城市
			String supply_county = "";//区县
			String supply_linkphone = message.get("LINKMAN_TEL");//联系电话
			String supply_telephone = "";//电话
			String supply_zipcode = "";//邮政编码
			String supply_email = message.get("EMAIL");//email
			String supply_fax = message.get("FAX_NO");//传真
			String supply_credit_no = message.get("TAX_NO");//社会信用号码
			String supply_tax_type = "";//纳税人分类
			String supply_tax_no = "";//纳税人识别号
			String bank_code =""; //银行编码
			String bank_desc = "";//银行描述
			String bank_account = message.get("BANK_ACC");//银行账号
			String bank_address = "";//开户行地址
			String supply_account_group = "";//账户组
			String supply_type = message.get("SUPPLY_TYPE_CODE");//客户分类
			String supply_industry = "";//所属行业
			String supply_legal_person = "";//法人代表
			String supply_code = message.get("SUPPLY_CODE");//供应商代码
			String goods_owner_code = "";//货主编码
			String supply_source = "";//客户来源
			item.put("supply_address", supply_address);
			item.put("supply_country", supply_country);
			item.put("supply_province", supply_province);
			item.put("supply_city", supply_city);
			item.put("supply_county", supply_county);
			item.put("supply_linkphone", supply_linkphone);
			item.put("supply_telephone", supply_telephone);
			item.put("supply_zipcode", supply_zipcode);
			item.put("supply_email", supply_email);
			item.put("supply_fax", supply_fax);
			item.put("supply_credit_no", supply_credit_no);
			item.put("supply_tax_type", supply_tax_type);
			item.put("supply_tax_no", supply_tax_no);
			item.put("bank_code", bank_code);
			item.put("bank_desc", bank_desc);
			item.put("bank_account", bank_account);
			item.put("bank_address", bank_address);
			item.put("supply_account_group", supply_account_group);
			item.put("supply_type", supply_type);
			item.put("supply_industry", supply_industry);
			item.put("supply_legal_person", supply_legal_person);
			item.put("supply_code", supply_code);
			item.put("goods_owner_code", goods_owner_code);
			item.put("supply_source", supply_source);
			//request
			String cust_name = message.get("FOREIGN_MERCHANT_NAME_CN");
			request.put("cust_name", cust_name);
			request.put("supply_credit_no", supply_credit_no);
			request.put("item", item);
		}
		
		
		
		//heads
		String tsStr = DateUtil.getDateString(new Date());//timeStemp
		String sign =EncodeUtil.encode(APPKEY+APPSECRET+tsStr);//sign 验签  AppKey(应用标识)+AppSecret(应用密钥)+timestamp
		heads.put("api_cust_code", APPSECRET);
		heads.put("sign", sign);
		heads.put("timestamp", tsStr);
		//data
		data.put("heads", heads);
		data.put("request", request);
		//cmd
		String cmd = "";
		if(flag == 1){
			cmd = "ZJMI_EXPORT_CUSTCOMP";
		}else{
			cmd = "ZJMI_EXPORT_CUSTCOMP";
		}
		//param
		Map<String, String> param  = new HashMap<String, String>();
		param.put("data", GsonUtil.toJson(data));
		param.put("cmd", cmd);
		//发送
		String url = "http://192.168.251.232:8089/api/erp/allinpayDeclare";
		String result = HttpUtils.httpPost(url,  HttpUtils.getPostForm(param));
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap = new Gson().fromJson(result, new TypeToken<Map<String, Object>>(){}.getType());
		Map<String, String> response = (Map<String, String>) queryMap.get("response");
		String flagResponse = response.get("flag");
		return flagResponse;
	}
}
