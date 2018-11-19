package com.neusoft.sdd.util.commonUtil;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.neusoft.sdd.util.wx.HttpUtils;
/**
  * 部门：软件开发事业部
  * 功能：客户-供应商对接接口工具类
  * 描述：略
  * 作成者：仇立学
  * 作成时间：2017-7-19
 */
public class PostCustomerOrSupplierUtil {
	public static String appkey = "";//应用标识
	public static String appsecret = "";//应用密钥
    private static String erp_url = "";//连接ERP地址
	static {
		new PropertiesUtil();
		Properties pu = PropertiesUtil.loadProperties("ywt.properties");
		appkey = pu.getProperty("APPKEY");
		appsecret = pu.getProperty("APPSECRET");
		erp_url = pu.getProperty("ERP_URL");
	}
	/**
	  * 部门：软件开发事业部
	  * 功能：客户接收接口(外商)
	  * 描述：略
	  * @param message
	  * @return
	  * 作成者：仇立学
	  * 作成时间：2017-7-19
	 */
	public static Map<String, Object> customerReceive(Map<String, Object> message,List<Map<String,String>> foreignCusBankList) {
		Map<String, Object> data = new HashMap<String, Object>();
		Map<String, Object> head = new HashMap<String, Object>();
		Map<String, Object> info = new HashMap<String, Object>();
		String tsStr = DateUtil.getDateString(new Date());//timeStemp
		String sign = EncodeUtil.encode(appkey + appsecret + tsStr);//sign 验签  AppKey(应用标识)+AppSecret(应用密钥)+timestamp
		//head
		head.put("appkey", appkey);
		head.put("sign", sign.toLowerCase());
		head.put("timestamp", tsStr);
		//info
		info.put("company_code", "5260");//公司编码(必填)
		info.put("company_name", "浙江物产中大供应链服务有限公司");//所属公司名称(必填)
		info.put("cust_uuid", message.get("ID"));//客户唯一键(必填)
		info.put("cust_name", message.get("FOREIGN_MERCHANT_NAME_CN"));//客户名称(必填)
		info.put("search_key", "");//检索项
		info.put("cust_address", message.get("ADDRESS"));//客户地址
		info.put("is_contracted", 0);//是否 合约客户(默认0，否=0，是=1)(必填)
		info.put("customer_source", "");//客户来源
		info.put("cust_country", message.get("COUNTRY_ID"));//客户国家
		info.put("cust_province", message.get("PROVINCE_ID"));//客户省份
		info.put("cust_city", message.get("CITY_ID"));//客户城市
		info.put("cust_county", message.get("COUNTY_ID"));//客户区县
		info.put("cust_linkphone", message.get("LINKMAN_TEL"));//联系电话
		info.put("cust_zipcode", "");//邮政编码
		info.put("cust_email", message.get("EMAIL"));//E_MAIL
		info.put("cust_fax", message.get("FAX_NO"));//传真
		info.put("cust_credit_no", "");//组织机构
		info.put("sign_cust_code", "");//签约客户
		info.put("cust_tax_type", "10410010");//纳税人分类（必填）( 一般纳税人)
		info.put("cust_telephone", "");//电话
		info.put("cust_tax_no", "");//纳税人识别号
		info.put("bank_code", "");//银行编号
		info.put("bank_account", "");//银行账号
		info.put("bank_name", "");//银行描述
		info.put("bank_address", "");//开户地址
		info.put("cust_account_group", "10430010");//账户组（必填）(ZJMI-贸易往来类客户)
		info.put("cust_type", "10420010");//客户分类（必填）(流通类法人)
		info.put("cust_industry", "10441100");//所属行业（必填）(其它行业)
		info.put("cust_legal_person", "");//法人代表
		info.put("sap_company_code", "");//SAP公司代表
		info.put("supply_code", "");//供应商代码
		info.put("need_send_paybill", 0);//是否需要推送支付单（默认0）
		info.put("need_send_tci", 0);//是否需要推送TCI（默认0）
		info.put("need_send_wms", 0);//是否需要推送WMS（默认0）
		data.put("head", head);
		if (null != foreignCusBankList && !foreignCusBankList.isEmpty()) {
			info.put("bank_list", foreignCusBankList);
		}
		data.put("info", info);
		Map<String, String> param = new HashMap<String, String>();
		param.put("data", GsonUtil.toJson(data));
		param.put("cmd", "SAVE_CUST");
		Map<String, Object> responseMap = sendERP(param);
		return responseMap;
	}
	/**
	  * 部门：软件开发事业部
	  * 功能：供应商接收接口
	  * 描述：略
	  * 作成者：仇立学
	  * 作成时间：2017-7-19
	 */
	public static Map<String, Object> supplierReceive(Map<String, Object> message,List<Map<String,String>> supplierBankList) {
		Map<String, Object> data = new HashMap<String, Object>();
		Map<String, Object> head = new HashMap<String, Object>();
		Map<String, Object> info = new HashMap<String, Object>();
		String tsStr = DateUtil.getDateString(new Date());//timeStemp
		String sign = EncodeUtil.encode(appkey + appsecret + tsStr);//sign 验签  AppKey(应用标识)+AppSecret(应用密钥)+timestamp
		//head
		head.put("appkey", appkey);
		head.put("sign", sign.toLowerCase());
		head.put("timestamp", tsStr);
		//info
		info.put("company_code", "5260");//公司编码(必填)
		info.put("company_name", "浙江物产中大供应链服务有限公司");//所属公司名称(必填)
		info.put("supply_uuid", message.get("ID"));//供应商唯一键(必填)
		info.put("supply_name", message.get("SUPPLY_NAME_CN"));//供方名称(必填)
		info.put("search_key", "");//检索项
		info.put("supply_address", message.get("ADDRESS"));//供方地址
		info.put("is_carrier", "");//是否承运商(否=0，是=1)
		info.put("supply_country", message.get("COUNTRY_ID"));//供方国家
		info.put("supply_province", message.get("PROVINCE_ID"));//供方省份
		info.put("supply_city", message.get("CITY_ID"));//供方城市
		info.put("supply_county", message.get("COUNTY_ID"));//供方区县
		info.put("supply_linkphone", message.get("LINKMAN_TEL"));//联系电话
		info.put("supply_telephone", "");//电话
		info.put("supply_zipcode", "");//邮政编码
		info.put("supply_email", message.get("EMAIL"));//E_MAIL
		info.put("supply_fax", message.get("FAX"));//传真
		info.put("supply_credit_no", "");//组织机构
		info.put("supply_tax_type", "");//纳税人分类
		info.put("supply_tax_no", "");//纳税人识别号
		info.put("supply_account_group", "10470010");//账户组（必填）(ZJMI-贸易往来类供应商)
		info.put("supply_type", "10420010");//供应商分类（必填）(流通类法人)
		info.put("supply_industry", "10441100");//所属行业（必填）(其它行业)
		info.put("cust_code", "");//客户编码
		info.put("cust_legal_person", "");//法人代表
		data.put("head", head);
		//判断集合是否为空
		if (null != supplierBankList && !supplierBankList.isEmpty()) {
			info.put("bank_list", supplierBankList);
		}
		data.put("info", info);
		Map<String, String> param = new HashMap<String, String>();
		param.put("data", GsonUtil.toJson(data));
		param.put("cmd", "SAVE_SPPLY");
		//发送
		Map<String, Object> responseMap = sendERP(param);
		return responseMap;
	}

	/**
	  * 部门：软件开发事业部
	  * 功能：发送到ERP
	  * 描述：略
	  * @param param
	  * @return
	  * 作成者：仇立学
	  * 作成时间：2017-7-19
	 */
	private static Map<String, Object> sendERP(Map<String, String> param) {
		try {
			String result = HttpUtils.httpPost(erp_url, HttpUtils.getPostForm(param));
			if( null == result){
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("status", 0);
				map.put("message", "连接ERP被拒绝，请检查连接ERP的地址是否正确!");
				return map;
			}
			Map<String, Object> queryMap = new Gson().fromJson(result, new TypeToken<Map<String, Object>>(){}.getType());
			return queryMap;
		} catch (Exception e) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("status", 0);
			map.put("message", "ERP返回数据异常!");
			return map;
		}
	}
}
