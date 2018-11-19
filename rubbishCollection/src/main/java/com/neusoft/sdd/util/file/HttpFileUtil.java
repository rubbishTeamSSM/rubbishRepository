package com.neusoft.sdd.util.file;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

import com.neusoft.sdd.util.commonUtil.UUIDUtil;

/**
 * 
 * [简要描述]:Http实现文件的上传和下载
 * [详细描述]:<br/>
 *
 * @version [revision],2015-5-30
 * @since Neusoft 001
 */
public class HttpFileUtil {
	/**
	 * 部门：软件开发事业部
	 * 功能：下载文件
	 * 描述：从文件服务器通过nginx下载文件
	 * 作成者：朱庆锋
	 * 作成时间：2015-5-28 下午2:45:41
	 */
	public static File downFile(String path, String filename) {
		System.out.println("downFile():"+ path + "--------" + filename);
		int byteread = 0;
		InputStream in = null;
		OutputStream out = null;

		File tmpFile = new File(filename);
		try {
			out = new FileOutputStream(tmpFile);

			URL url = new URL(path);
			// 打开连接，这时候还未连接，等执行到connect()之后，才是真正建立连接
			URLConnection conn = url.openConnection();
			conn.connect();// 本方法不会自动重连
			in = conn.getInputStream();// 访问获取资源

			byte[] buffer = new byte[1204];
			while ((byteread = in.read(buffer)) != -1) {
				out.write(buffer, 0, byteread);
			}
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {

				if (null != in) {
					in.close();
				}
				
				if (null != out) {
					out.flush();
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return tmpFile;
	}
	
	/**
	 * 部门：软件开发事业部
	 * 功能：上传文件
	 * 描述：单文件上传
	 * 作成者：朱庆锋
	 * 作成时间：2015-5-28 下午4:08:38
	 */
	public static String uploadFile(String destPath, File file, String fileName){
		System.out.println("---uploadFile():" + destPath );

		OutputStream out = null;
        DataInputStream in = null;
		HttpURLConnection conn = null;
		
		String rn = "\r\n";
		String contentType = getContentType(fileName);
		String wjfwqm = UUIDUtil.uuidStr() + fileName;//文件服务器名
		String boundary = Long.toHexString(System.currentTimeMillis()); //boundary就是request头和上传文件内容的分隔符  

		try {
			URL url = new URL(destPath);

			conn = (HttpURLConnection)url.openConnection();// 对指定的URL获取一个新的连接

			// 设置每次传输的流大小，可以有效防止因为内存不足崩溃
            // 此方法用于在预先不知道内容长度时启用没有进行内部缓冲的 HTTP 请求正文的流。
			conn.setChunkedStreamingMode(128 * 1024);// 128K
			
			conn.setDoOutput(true);//设置该连接允许读取
			conn.setDoInput(true);// 设置该连接允许写入
			conn.setUseCaches(false);// 设置不能适用缓存
            conn.setConnectTimeout(30000);
            conn.setReadTimeout(30000); 
            conn.setRequestMethod("POST");
            conn.setRequestMethod("POST");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
            conn.setRequestProperty("Charsert", "UTF-8");//设置文件字符集
			conn.setRequestProperty("Content-Type", "multipart/form-data; boundary="+ boundary);//设置文件类型
			
			//conn.connect();// 本方法不会自动重连
			
			out = new DataOutputStream(conn.getOutputStream());// 向服务器端写数据
			
			StringBuilder sb = new StringBuilder();
			sb.append("Content-Disposition: form-data; name=file; filename=" + fileName).append(rn);
		    sb.append("Content-Type: " + contentType + ";charset=utf-8").append(rn).append(rn);
			
			out.write(sb.toString().getBytes());
			in = new DataInputStream(new FileInputStream(file));  

	        int byteread = 0;
            byte[] buffer = new byte[1024];
            
			while ((byteread = in.read(buffer)) != -1) {
				out.write(buffer, 0, byteread);
			}
			
			byte[] endData = ("\r\n--" + boundary + "--\r\n").getBytes();  
			out.write(endData);
			out.flush();
            out.close();
            in.close();
            
            
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != out) {
					out.flush();
					out.close();
				}
				if (null != in) {
					in.close();
				}
				 if (conn != null) {  
	                conn.disconnect();  
	                conn = null;  
	            }  
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return wjfwqm;
	}
	
	/**
	 * 
	 * [简要描述]:根据文件类型获取文件传输类型<br/>
	 * [详细描述]:<br/>
	 *
	 * @param fileName
	 * @return
	 * @exception
	 */
	private static String getContentType(String fileName){
	    String fileType = fileName.substring(fileName.lastIndexOf(".")+1);
	    
	    String contentType = "";
	    
	    if ("pdf".equalsIgnoreCase(fileType)) {
            contentType = " application/pdf--PDF "; //
        } else if ("word".equalsIgnoreCase(fileType)) {
            contentType = " application/msword--MSWORD  "; //
        } else if ("doc".equalsIgnoreCase(fileType)) {
            contentType = " application/msword--MSWORD  "; //
        } else if ("xls".equalsIgnoreCase(fileType)) {
            contentType = " application/vnd.ms-excel--MSEXCEL  "; //
        } else if ("bmp".equalsIgnoreCase(fileType)) {
            contentType = " aimage/bmp--BMP  "; //
        } else if ("txt".equalsIgnoreCase(fileType)) {
            contentType = " text/plain--TXT  "; //
        } else if ("xml".equalsIgnoreCase(fileType)) {
            contentType = " text/xml--XML  "; //
        } else if ("ppt".equalsIgnoreCase(fileType)) {
            contentType = " application/vnd.ms-powerpoint--MSPOWERPOINT "; //
        } else if ("html".equalsIgnoreCase(fileType)) {
            contentType = " text/html--HTML  "; //
        } else if ("gif".equalsIgnoreCase(fileType)) {
            contentType = " image/gif--GIF   "; //
        } else if ("jpeg".equalsIgnoreCase(fileType)) {
            contentType = " image/jpeg--JPEG   "; //
        } else if ("tif".equalsIgnoreCase(fileType)) {
            contentType = " image/tiff--TIFF  "; //
        } else if ("dcx".equalsIgnoreCase(fileType)) {
            contentType = " image/x-dcx--DCX   "; //
        } else if ("png".equalsIgnoreCase(fileType)) {
            contentType = " image/png"; //
        } else if ("jpg".equalsIgnoreCase(fileType)) {
            contentType = " image/pjpeg"; //
        } else if ("jsp".equalsIgnoreCase(fileType)) {
            contentType = " text/html"; //
        } else if ("EXE".equalsIgnoreCase(fileType)) {
            contentType = " application/octet-stream "; //
        } else if ("swf".equalsIgnoreCase(fileType)) {
            contentType = " application/x-shockwave-flash "; //
        }
	    
	    return contentType;
	}
	
	/**
	 * 
	 * [简要描述]:上传多个文件<br/>
	 * [详细描述]:<br/>
	 *
	 * @param destPath
	 * @param fileInfo
	 * @return
	 * @exception
	 */
	public static List<Map<String,Object>> uploadFileList(String destPath,List<Map<String,Object>> fileInfo){
	    System.out.println("---uploadFileList():" + destPath );
	    HttpURLConnection conn = null;
	    OutputStream out = null;
	    
	    try {
            String BOUNDARY = "---------7d4a6d158c9"; // 定义数据分隔线
            URL url = new URL(destPath);
            conn = (HttpURLConnection) url.openConnection();
            // 设置每次传输的流大小，可以有效防止因为内存不足崩溃
            // 此方法用于在预先不知道内容长度时启用没有进行内部缓冲的 HTTP 请求正文的流。
            conn.setChunkedStreamingMode(128 * 1024);// 128K
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);//设置该连接允许读取
            conn.setDoInput(true);// 设置该连接允许写入
            conn.setUseCaches(false);// 设置不能适用缓存
            conn.setConnectTimeout(30000);
            conn.setReadTimeout(30000); 
            conn.setRequestMethod("POST");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
            conn.setRequestProperty("Charsert", "UTF-8");//设置文件字符集
            conn.setRequestProperty("Content-Type",
                    "multipart/form-data; boundary=" + BOUNDARY);//设置文件类型
            conn.connect();
            out = new DataOutputStream(conn.getOutputStream());
            byte[] end_data = ("\r\n--" + BOUNDARY + "--\r\n").getBytes();// 定义最后数据分隔线
            
            for (int i = 0; i < fileInfo.size(); i++) {
                Map<String, Object> map = fileInfo.get(i);
                
                File file = new File((String)map.get("file"));
                
                //String wjfwqm = UUIDUtil.uuidStr() + "_" + file.getName();//文件服务器名
                String filaName = file.getName();
                String wjfwqm = UUIDUtil.uuidStr()+filaName.substring(filaName.lastIndexOf("."));
                String contentType = getContentType(filaName);
                
                StringBuilder sb = new StringBuilder();
                sb.append("--");
                sb.append(BOUNDARY);
                sb.append("\r\n");
                sb.append("Content-Disposition: form-data;name=\"file" + i
                        + "\";filename=\"" + wjfwqm + "\"\r\n");
                sb.append("Content-Type:"+contentType+"\r\n\r\n");

                DataInputStream in = new DataInputStream(new FileInputStream(file));
                byte[] data = sb.toString().getBytes("UTF-8");
                out.write(data);
                
                int bytes = 0;
                byte[] bufferOut = new byte[1024];
                
                while ((bytes = in.read(bufferOut)) != -1) {
                    out.write(bufferOut, 0, bytes);
                }
                out.write("\r\n".getBytes("UTF-8")); // 多个文件时，二个文件之间加入这个
                in.close();

                //将文件服务器名放入集合，以便插入数据库
                map.put("wjfwqm", wjfwqm);
                file.delete();
            }
            out.write(end_data);
            out.flush();
            out.close();

            // 定义BufferedReader输入流来读取URL的响应
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    conn.getInputStream()));
            String line = null;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            return fileInfo;
        } catch (Exception e) {
            System.out.println("发送POST请求出现异常！" + e);
            e.printStackTrace();
            return null;
        } finally {
            
            try
            {
                if (null != out)
                {
                    out.flush();
                    out.close();
                }
                
                if(null != conn){
                    conn.disconnect();  
                    conn = null;  
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

        }
	}
}
