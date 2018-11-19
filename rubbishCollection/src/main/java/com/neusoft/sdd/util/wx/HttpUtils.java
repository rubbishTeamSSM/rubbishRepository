package com.neusoft.sdd.util.wx;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.net.ssl.SSLContext;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.neusoft.sdd.base.model.wx.pojo.CallBackInfo;
import com.neusoft.sdd.base.model.wx.pojo.OrgInfo;
import com.neusoft.sdd.util.commonUtil.GsonUtil;

public class HttpUtils {

	public static final int cache = 10 * 1024;

	public static final String root;
	static {
		root = System.getProperty("java.io.tmpdir");

	}

	public static void main(String[] args) {
		
		//ע��ӿ�
		/*Map<String, String> addMap  = new HashMap<String, String>();
		addMap.put("sysid", "ZWJwWC1aSkNZVEpTREdNUllYR0NT");
		addMap.put("cmd", "sysdwbd/newzc");
		addMap.put("zh", "test01");
		addMap.put("mm", "123");
		addMap.put("dwmc", "neu");
		addMap.put("mobile", "13011111111");
		addMap.put("callback", "jsonpCallback");
		System.out.println("�������"+GsonUtil.toJson(addMap));
		HttpUtils.httpPost("http://edi.ecloud123.com/edi/http/golden-ebp",  HttpUtils.getPostForm(addMap));	*/
		
		//��¼�ӿ�
		/*Map<String, String> loginMap  = new HashMap<String, String>();
		loginMap.put("sysid", "ZWJwWC1aSkNZVEpTREdNUllYR0NT");
		loginMap.put("cmd", "sysdwbd/querysf");
		loginMap.put("zh", "test01");
		loginMap.put("mm", "123");
		loginMap.put("callback", "jsonpCallback");
		System.out.println("�������"+GsonUtil.toJson(loginMap));
		HttpUtils.httpPost("http://edi.ecloud123.com/edi/http/golden-ebp",  HttpUtils.getPostForm(loginMap));	
		*/
		
		//�˻���Ϣ��ѯ�ӿ�
	/*	Map<String, String> accountqueryMap  = new HashMap<String, String>();
		accountqueryMap.put("sysid", "ZWJwWC1aSkNZVEpTREdNUllYR0NT");
		accountqueryMap.put("cmd", "sysdwbd/query");
		accountqueryMap.put("mobile", "13011111111");
		accountqueryMap.put("callback", "jsonpCallback");
		HttpUtils.httpPost("http://edi.ecloud123.com/edi/http/golden-ebp",  HttpUtils.getPostForm(accountqueryMap));	
	*/
		//������ѯ�ӿ�
		/*Map<String, String> order  = new HashMap<String, String>();
		order.put("sysid", "ZWJwWC1aSkNZVEpTREdNUllYR0NT");
		order.put("cmd", "xstdmxytgm/query");
		order.put("mobile", "13011111111");
		order.put("callback", "jsonpCallback");
		System.out.println("�������"+GsonUtil.toJson(order));
		HttpUtils.httpPost("http://edi.ecloud123.com/edi/http/golden-ebp",  HttpUtils.getPostForm(order));*/
		
		//�����ӿ�
		Map<String, String> wuliu  = new HashMap<String, String>();
		wuliu.put("sysid", "ZWJwWC1aSkNZVEpTREdNUllYR0NT");
		wuliu.put("cmd", "ckdmxytgm/query");
		wuliu.put("mobile", "13011111111");
		wuliu.put("xstdhm", "XS2E1109-02129");
		wuliu.put("callback", "jsonpCallback");
		System.out.println("�������"+GsonUtil.toJson(wuliu));
		String r = HttpUtils.httpPost("http://edi.ecloud123.com/edi/http/golden-ebp",  HttpUtils.getPostForm(wuliu));
		
		System.err.println(r);
		
		//HttpUtils.postJson("http://edi.ecloud123.com/edi/http/golden-ebp", GsonUtil.toJson(addMap));
		// 1D4nE0UC1o7v4jvKgxJaHi40oI9sGHlNz2d_o4DnMuFLsCs9q-9aOmYOK6HJz0Zf9bcZG9ETRCpI_QfpI-xdQSA

	/*	WxEnterpriseInterfaceUtils
				.downLoadMedia(
						"1D4nE0UC1o7v4jvKgxJaHi40oI9sGHlNz2d_o4DnMuFLsCs9q-9aOmYOK6HJz0Zf9bcZG9ETRCpI_QfpI-xdQSA",
						"D://tttt.jpg");*/

		// ������Ϣ
		// NewsMsg msg = new NewsMsg();
		// Article atricle = new Article();
		// atricle.setTitle("����ͼ����Ϣ@all");
		// atricle.setDescription("ÿ�춼���·���1");
		// atricle.setUrl("http://img2.3lian.com/2014/f4/99/d/106.jpg");
		// atricle.setPicurl("http://img2.3lian.com/2014/f4/99/d/106.jpg");
		//
		// Article atricle1 = new Article();
		// atricle1.setTitle("����ͼ����Ϣ@all");
		// atricle1.setDescription("ÿ�춼���·���2");
		// atricle1.setUrl("http://img2.3lian.com/2014/f4/99/d/106.jpg");
		// atricle1.setPicurl("http://img2.3lian.com/2014/f4/99/d/106.jpg");
		//
		// Map<String, Article[]> articles = new HashMap<String, Article[]>();
		// articles.put("articles", new Article[] { atricle, atricle1 });
		// msg.setTouser("@all");
		// msg.setAgentid("10");
		// msg.setNews(articles);
		// msg.setMsgtype("news");
		// msg.setSafe("0");
		// WxInterfaceUtils.sendMessage(msg);

		// CallBackInfo callBack = new CallBackInfo();
		// callBack.setUrl("http://yuanxiaobin876.vicp.net/wxOrgApp/servlet/IndexServlet");
		// callBack.setToken("2XVlVRq9gyRmeoh7kkU1xzY09vqISV");
		// callBack.setEncodingaeskey("RY33FYj4cu4j4apURFuBjsdKDEDxXfb45GnRpu5AFyv");
		// WxInterfaceUtils.getToken();
		//
		// ObjectMapper mapper = new ObjectMapper();
		// Menu menu = new Menu();
		// menu.setName("ͨѶ¼");
		// menu.setUrl("http://yuanxiaobin876.vicp.net/wxOrgApp/queryUser.jsp");
		// menu.setType("view");
		// List<Menu> button = new ArrayList<Menu>();
		// button.add(menu);
		// Map<String,Object> menuMap = new HashMap<String, Object>();
		// menuMap.put("button", button);
		//
		// // // �����˵�
		// try
		// {
		// String menuJson = mapper.writeValueAsString(menuMap);
		// System.out.println(menuJson);
		// WxInterfaceUtils.createWxMenu("3", menuJson);
		// }
		// catch (JsonGenerationException e)
		// {
		// e.printStackTrace();
		// }
		// catch (JsonMappingException e)
		// {
		// e.printStackTrace();
		// }
		// catch (IOException e)
		// {
		// e.printStackTrace();
		// }
		// ����ɾ���û�

		// WxInterfaceUtils.batchDelUsers(DBHelp.getBatchDelUser());

		// WxInterfaceUtils.queryUserByDepartment("1", "1", "0");

		// ���������û�
		// String token = WxInterfaceUtils.getToken();
		// //
		// Map<String, Object> result =
		// WxInterfaceUtils.uploadMedia("E://eee.jpg");
		//
		// String mediaId = result.get("media_id").toString();
		//
		// WxInterfaceUtils.importAllUser(callBack, mediaId, token);

		// OrgInfo orgInfo = new OrgInfo();
		// orgInfo.setId("13");
		// orgInfo.setName("�й���");
		// orgInfo.setParentid("1");
		// String jsonStr = JsonUtils.parseObject2JSON(orgInfo);
		// System.out.println(jsonStr);
		// WxInterfaceUtils.createOrg(jsonStr);

		// try
		// {
		// readXls();
		// }
		// catch (IOException e)
		// {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

		// for (int i = 2; i < 600; i++)
		// {
		// System.out.println("UPDATE bmbak SET bmparentid = '"+i+"' where bmparentid = (select bmid from bmbak_copy where id = '"+i+"');");
		//
		// }

	}

	public static void importAllUser(CallBackInfo callBack, String mediaId,
			String accessToken) {
		String url = "https://qyapi.weixin.qq.com/cgi-bin/batch/replaceuser?access_token="
				+ accessToken;
		requestToWx(callBack, mediaId, url);
	}

	/**
	 * [��Ҫ����]:<br/>
	 * [��ϸ����]:<br/>
	 * 
	 * @param callBack
	 * @param mediaId
	 * @param url
	 * @exception
	 */
	private static void requestToWx(CallBackInfo callBack, String mediaId,
			String url) {
		Map<String, Object> reqParm = new HashMap<String, Object>();
		reqParm.put("callback", callBack);
		reqParm.put("media_id", mediaId);

		ObjectMapper mapper = new ObjectMapper();
		String json;
		try {
			json = mapper.writeValueAsString(reqParm);
			System.out.println(json);
			postJson(url, json);

		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void importOrg(CallBackInfo callBack, String mediaId,
			String accessToken) {
		String url = "https://qyapi.weixin.qq.com/cgi-bin/batch/replaceparty?access_token="
				+ accessToken;
		requestToWx(callBack, mediaId, url);
	}

	/**
	 * HttpClient连接SSL
	 */
	@SuppressWarnings("deprecation")
	public void ssl() {
		CloseableHttpClient httpclient = null;
		try {
			KeyStore trustStore = KeyStore.getInstance(KeyStore
					.getDefaultType());
			FileInputStream instream = new FileInputStream(new File(
					"d:\\tomcat.keystore"));
			try {
				// 加载keyStore d:\\tomcat.keystore
				trustStore.load(instream, "123456".toCharArray());
			} catch (CertificateException e) {
				e.printStackTrace();
			} finally {
				try {
					instream.close();
				} catch (Exception ignore) {
				}
			}
			SSLContext sslcontext = SSLContexts
					.custom()
					.loadTrustMaterial(trustStore,
							new TrustSelfSignedStrategy()).build();
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
					sslcontext,
					new String[]{"TLSv1"},
					null,
					SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
			httpclient = HttpClients.custom().setSSLSocketFactory(sslsf)
					.build();
			HttpGet httpget = new HttpGet(
					"https://localhost:8443/myDemo/Ajax/serivceJ.action");
			System.out.println("executing request" + httpget.getRequestLine());
			CloseableHttpResponse response = httpclient.execute(httpget);
			try {
				HttpEntity entity = response.getEntity();
				System.out.println("----------------------------------------");
				System.out.println(response.getStatusLine());
				if (entity != null) {
					System.out.println("Response content length: "
							+ entity.getContentLength());
					System.out.println(EntityUtils.toString(entity));
					EntityUtils.consume(entity);
				}
			} finally {
				response.close();
				// ADD BY W_CHEN 20150803-START
				httpget.releaseConnection();
				// ADD BY W_CHEN 20150803-END
			}
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (KeyManagementException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (KeyStoreException e) {
			e.printStackTrace();
		} finally {
			if (httpclient != null) {
				try {
					httpclient.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void postForm() {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost httppost = new HttpPost(
				"http://localhost:8080/myDemo/Ajax/serivceJ.action");
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		formparams.add(new BasicNameValuePair("username", "admin"));
		formparams.add(new BasicNameValuePair("password", "123456"));
		UrlEncodedFormEntity uefEntity;
		try {
			uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");
			httppost.setEntity(uefEntity);
			System.out.println("executing request " + httppost.getURI());
			CloseableHttpResponse response = httpclient.execute(httppost);
			try {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					System.out
							.println("--------------------------------------");
					System.out.println("Response content: "
							+ EntityUtils.toString(entity, "UTF-8"));
					System.out
							.println("--------------------------------------");
				}
			} finally {
				response.close();
				// ADD BY W_CHEN 20150803-START
				httppost.releaseConnection();
				// ADD BY W_CHEN 20150803-END
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void post() {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost httppost = new HttpPost(
				"http://localhost:8080/myDemo/Ajax/serivceJ.action");
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		formparams.add(new BasicNameValuePair("type", "house"));
		UrlEncodedFormEntity uefEntity;
		try {
			uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");
			httppost.setEntity(uefEntity);
			System.out.println("executing request " + httppost.getURI());
			CloseableHttpResponse response = httpclient.execute(httppost);
			try {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					System.out
							.println("--------------------------------------");
					System.out.println("Response content: "
							+ EntityUtils.toString(entity, "UTF-8"));
					System.out
							.println("--------------------------------------");
				}
			} finally {
				response.close();
				// ADD BY W_CHEN 20150803-START
				httppost.releaseConnection();
				// ADD BY W_CHEN 20150803-END
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 发�??get请�??
	 */
	@SuppressWarnings("deprecation")
	public static String httpGet(String url) {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpEntity entity = null;
		String respText = null;

		try {
			HttpGet httpget = new HttpGet(url);
			System.out.println("executing request " + httpget.getURI());
			CloseableHttpResponse response = httpclient.execute(httpget);
			try {
				entity = response.getEntity();
				System.out.println("--------------------------------------");
				System.out.println(response.getStatusLine());
				if (entity != null) {
					respText = EntityUtils.toString(entity, HTTP.UTF_8);
					System.out.println("Response content length: "
							+ entity.getContentLength());
					System.out.println("Response content: " + respText);
				}
				System.out.println("------------------------------------");
			} finally {
				response.close();
				// ADD BY W_CHEN 20150803-START
				httpget.releaseConnection();
				// ADD BY W_CHEN 20150803-END
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 关闭连接,释放资源
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return respText;
	}
	
	
	public static String httpPost(String url, List<NameValuePair> formparams) {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost httppost = new HttpPost(url);
		/*List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		formparams.add(new BasicNameValuePair("pCode", code));*/
		UrlEncodedFormEntity uefEntity;
		String respText = null;
		try {
			uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");
			httppost.setEntity(uefEntity);
			System.out.println("executing request " + httppost.getURI());
			CloseableHttpResponse response = httpclient.execute(httppost);
			try {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					respText = EntityUtils.toString(entity, HTTP.UTF_8);
					System.out.println("--------------------------------------");
					System.out.println("Response content: "+ respText);
					System.out.println("--------------------------------------");
				}
				
				
			} finally {
				response.close();
				// ADD BY W_CHEN 20150803-START
				httppost.releaseConnection();
				// ADD BY W_CHEN 20150803-END
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		//return respText.substring(14, respText.length()-1);
		return respText;
	}
	public static String httpPostNoEntity(String url) {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost httppost = new HttpPost(url);
		/*List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		formparams.add(new BasicNameValuePair("pCode", code));*/
		UrlEncodedFormEntity uefEntity;
		String respText = null;
		try {
			System.out.println("executing request " + httppost.getURI());
			CloseableHttpResponse response = httpclient.execute(httppost);
			try {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					respText = EntityUtils.toString(entity, HTTP.UTF_8);
					System.out.println("--------------------------------------");
					System.out.println("Response content: "+ respText);
					System.out.println("--------------------------------------");
				}
				
				
			} finally {
				response.close();
				// ADD BY W_CHEN 20150803-START
				httppost.releaseConnection();
				// ADD BY W_CHEN 20150803-END
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		//return respText.substring(14, respText.length()-1);
		return respText;
	}
	 public static List<NameValuePair> getPostForm(Map<String, String> params){  
         
	        List<NameValuePair> nvps = new ArrayList <NameValuePair>();  
	        Set<String> keySet = params.keySet();  
	        for(String key : keySet) {  
	            nvps.add(new BasicNameValuePair(key, params.get(key)));  
	        }  
	          
	        return nvps;  
	    }
	 public static List<NameValuePair> getPostFormObject(Map<String, Object> params){  
         
	        List<NameValuePair> nvps = new ArrayList <NameValuePair>();  
	        Set<String> keySet = params.keySet();  
	        for(String key : keySet) {  
	            nvps.add(new BasicNameValuePair(key, String.valueOf(params.get(key)) ));  
	        }  
	          
	        return nvps;  
	    }

	/**
	 * 上传文件
	 */
	public static Map<String, Object> upload(String path, String accessToken) {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			HttpPost httppost = new HttpPost(
					"https://qyapi.weixin.qq.com/cgi-bin/media/upload");

			FileBody bin = new FileBody(new File(path));
			StringBody access_token = new StringBody(accessToken,
					ContentType.TEXT_PLAIN);
			StringBody type = new StringBody("file", ContentType.TEXT_PLAIN);
			HttpEntity reqEntity = MultipartEntityBuilder.create()
					.addPart("bin", bin).addPart("access_token", access_token)
					.addPart("type", type).build();
			httppost.setEntity(reqEntity);

			System.out
					.println("executing request " + httppost.getRequestLine());
			CloseableHttpResponse response = httpclient.execute(httppost);
			try {
				System.out.println("----------------------------------------");
				System.out.println(response.getStatusLine());
				HttpEntity resEntity = response.getEntity();
				if (resEntity != null) {
					System.out.println("Response content length: "
							+ resEntity.getContentLength());
					result = JsonUtils.parseJSON2Map(EntityUtils
							.toString(resEntity));
				}
				EntityUtils.consume(resEntity);
			} finally {
				response.close();
				// ADD BY W_CHEN 20150803-START
				httppost.releaseConnection();
				// ADD BY W_CHEN 20150803-END
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return result;
	}

	/**
	 * 上传文件
	 */
	public static String uploadHttp(String path, String url) {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		String result = null;
		try {
			HttpPost httppost = new HttpPost(url);

			FileBody bin = new FileBody(new File(path));
			StringBody type = new StringBody("file", ContentType.TEXT_PLAIN);
			HttpEntity reqEntity = MultipartEntityBuilder.create()
					.addPart("bin", bin).addPart("type", type).build();
			httppost.setEntity(reqEntity);

			System.out
					.println("executing request " + httppost.getRequestLine());
			CloseableHttpResponse response = httpclient.execute(httppost);
			try {
				System.out.println("----------------------------------------");
				System.out.println(response.getStatusLine());
				HttpEntity resEntity = response.getEntity();
				if (resEntity != null) {
					System.out.println("Response content length: "
							+ resEntity.getContentLength());
					// System.out.println(EntityUtils.toString(resEntity));
					result = EntityUtils.toString(resEntity);
				}
				EntityUtils.consume(resEntity);
			} finally {
				response.close();
				// ADD BY W_CHEN 20150803-START
				httppost.releaseConnection();
				// ADD BY W_CHEN 20150803-END
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return result;
	}

	@SuppressWarnings({"deprecation", "unused"})
	public static void postJson(String url, String json) {
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);
		String response = null;
		try {
			StringEntity s = new StringEntity(json, "UTF-8");
			s.setContentEncoding("UTF-8");
			s.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,
					"application/json"));
			post.setEntity(s);
			System.out.println(s.toString());
			post.addHeader("Content-Type", "application/Json;charset=UTF-8");
			HttpResponse res = client.execute(post);
			if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				HttpEntity entity = res.getEntity();
				String charset = EntityUtils.getContentCharSet(entity);
				response = EntityUtils.toString(entity);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			// ADD BY W_CHEN 20150803-START
			post.releaseConnection();
			// ADD BY W_CHEN 20150803-END
		}

		System.out.println(response);
	}

	@SuppressWarnings("unused")
	private static void readXls() throws IOException {
		InputStream is = new FileInputStream("D:\\byminfo.xls");
		HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);

		OrgInfo orgInfo = null;

		for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
			HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
			if (hssfSheet == null) {
				continue;
			}

			for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
				HSSFRow hssfRow = hssfSheet.getRow(rowNum);
				if (hssfRow == null) {
					continue;
				}

				orgInfo = new OrgInfo();

				// hssfRow.getLastCellNum()
				for (int cellNum = 0; cellNum <= 3; cellNum++) {

					HSSFCell hssfCell = hssfRow.getCell(cellNum);
					if (hssfCell == null) {
						continue;
					}

					if (cellNum == 0) {
						orgInfo.setId(getValue(hssfCell));
					}

					if (cellNum == 1) {
						orgInfo.setName(getValue(hssfCell));
					}

					// if(cellNum == 2)
					// {
					//
					// orgInfo.setOrder(order+"");
					// order++;
					// }

					if (cellNum == 3) {
						orgInfo.setParentid(getValue(hssfCell));
					}

				}

				String jsonStr = JsonUtils.parseObject2JSON(orgInfo);
				System.out.println(jsonStr);
				WxEnterpriseInterfaceUtils.createOrg(jsonStr);

				System.out.println();
			}
		}
		// ADD BY W_CHEN 20150803-START
		is.close();
		// ADD BY W_CHEN 20150803-END
	}

	@SuppressWarnings("static-access")
	private static String getValue(HSSFCell hssfCell) {
		if (hssfCell.getCellType() == hssfCell.CELL_TYPE_BOOLEAN) {
			return String.valueOf(hssfCell.getBooleanCellValue());
		} else if (hssfCell.getCellType() == hssfCell.CELL_TYPE_NUMERIC) {
			return String.valueOf(hssfCell.getNumericCellValue());
		} else {
			return String.valueOf(hssfCell.getStringCellValue());
		}
	}

	/**
	 * ���url�����ļ������浽filepath��
	 * 
	 * @param url
	 * @param filepath
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static String download(String url, String filepath) {
		try {
			HttpClient client = new DefaultHttpClient();
			HttpGet httpget = new HttpGet(url);
			HttpResponse response = client.execute(httpget);

			HttpEntity entity = response.getEntity();
			InputStream is = entity.getContent();
			if (filepath == null) {
				filepath = getFilePath(response);
			}

			File file = new File(filepath);
			file.getParentFile().mkdirs();
			FileOutputStream fileout = new FileOutputStream(file);
			/**
			 * ���ʵ������Ч�� ���û������С
			 */
			byte[] buffer = new byte[cache];
			int ch = 0;
			while ((ch = is.read(buffer)) != -1) {
				fileout.write(buffer, 0, ch);
			}
			is.close();
			fileout.flush();
			fileout.close();
			// ADD BY W_CHEN 20150803-START
			httpget.releaseConnection();
			// ADD BY W_CHEN 20150803-END

		} catch (Exception e) {
			e.printStackTrace();
		}
		return filepath;
	}

	/**
	 * ��ȡresponseҪ���ص��ļ���Ĭ��·��
	 * 
	 * @param response
	 * @return
	 */
	public static String getFilePath(HttpResponse response) {
		String filepath = root;
		String filename = getFileName(response);

		if (filename != null) {
			filepath += "/" + filename;
		} else {
			filepath += "/" + getRandomFileName();
		}
		return filepath;
	}

	/**
	 * ��ȡresponse header��Content-Disposition�е�filenameֵ
	 * 
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unused")
	public static String getFileName(HttpResponse response) {
		Header[] headers = response.getAllHeaders();
		Header contentHeader = response.getFirstHeader("Content-Disposition");
		String filename = null;
		if (contentHeader != null) {
			HeaderElement[] values = contentHeader.getElements();
			if (values.length == 1) {
				NameValuePair param = values[0].getParameterByName("filename");
				if (param != null) {
					try {
						// filename = new
						// String(param.getValue().toString().getBytes(),
						// "utf-8");
						// filename=URLDecoder.decode(param.getValue(),"utf-8");
						filename = param.getValue();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		return filename;
	}

	/**
	 * ��ȡ����ļ���
	 * 
	 * @return
	 */
	public static String getRandomFileName() {
		return String.valueOf(System.currentTimeMillis());
	}
}
