package com.neusoft.sdd.util.commonUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;

public class OSSUtil {
	static String END_POINT = OSSClientProperties.ossCliendEndPoint;
	static String BUCKET_NAME = OSSClientProperties.bucketName;
	static String ACCESS_KEY_ID = OSSClientProperties.key;
	static String ACCESS_KEY_SECRET = OSSClientProperties.secret;
	
	static OSSClient client = null;
	
    // 如果Bucket不存在，则创建它。
    private static void ensureBucket(OSSClient client, String bucketName) throws OSSException, ClientException
    {

        if (client.doesBucketExist(bucketName))
        {
            return;
        }

        // 创建bucket
        client.createBucket(bucketName);
    }

    // 把Bucket设置为所有人可读
    private static void setBucketPublicReadable(String bucketName) throws OSSException, ClientException
    {
    	// 初始化OSSClient
    	OSSClient client = new OSSClient(END_POINT,ACCESS_KEY_ID, ACCESS_KEY_SECRET);
    	
        // 创建bucket
        client.createBucket(bucketName);

        // 设置bucket的访问权限，public-read-write权限
        client.setBucketAcl(bucketName, CannedAccessControlList.PublicRead);
    }
    
    /**
     * 上传一个Object
     * @param bucketName
     * @param key
     * @param filePath
     * @throws FileNotFoundException
     */
	public static void putObject(String key, String filePath)
			throws FileNotFoundException {

    	//创建Bucket
    	//ensureBucket(client, bucketName);
    	// 初始化OSSClient
    	OSSClient client = new OSSClient(END_POINT,ACCESS_KEY_ID, ACCESS_KEY_SECRET);
    	
    	// 获取指定文件的输入流
    	File file = new File(filePath);
    	InputStream content = new FileInputStream(file);
    	
    	// 创建上传Object的Metadata
    	ObjectMetadata meta = new ObjectMetadata();
    	// 必须设置ContentLength
    	meta.setContentLength(file.length());
    	
    	// 判断设置上传类型
    	if(filePath.toLowerCase().endsWith("jpg")){//jpg
    	    meta.setContentType("image/jpeg");
    	} else if(filePath.toLowerCase().endsWith("mp3")){//mp3
            meta.setContentType("audio/mp3");
        } else if(filePath.toLowerCase().endsWith("mp4")){//mp4
            meta.setContentType("video/mpeg4");
        }
    	
    	// 上传Object.
    	PutObjectResult result = client.putObject(BUCKET_NAME, key, content, meta);
    	
    	// 打印ETag
    	System.out.println(result.getETag());
    }
    

	/**
	 *  下载文件
	 * 
	 * @param Objectkey  上传到OSS起的名
	 * @param filename 文件下载到本地保存的路径
	 * @throws OSSException
	 * @throws ClientException
	 */
	public static void downloadFile(String Objectkey, String filename) throws OSSException, ClientException {
    	// 初始化OSSClient
    	OSSClient client = new OSSClient(END_POINT,ACCESS_KEY_ID, ACCESS_KEY_SECRET);
    	
        client.getObject(new GetObjectRequest(BUCKET_NAME, Objectkey),
                new File(filename));
    }
	
	/**
	 * 删除一个Bucket和其中的Objects
	 * 
	 * @param bucketName  Bucket名
	 * @throws OSSException
	 * @throws ClientException
	 */
	private static void deleteBucket(String bucketName)throws OSSException, ClientException{
    	// 初始化OSSClient
    	OSSClient client = new OSSClient(END_POINT,ACCESS_KEY_ID, ACCESS_KEY_SECRET);
    	
		ObjectListing ObjectListing = client.listObjects(bucketName);
		List<OSSObjectSummary> listDeletes = ObjectListing.getObjectSummaries();
		for(int i = 0; i < listDeletes.size(); i++){
			String objectName = listDeletes.get(i).getKey();
			System.out.println("objectName = " + objectName);
			//如果不为空，先删除bucket下的文件
			client.deleteObject(bucketName, objectName);
		}
		client.deleteBucket(bucketName);
	}
	
	/**
	 * 批量删除Object
	 * 
	 * @param objList Object的key(目录路径/文件名)
	 */
	public static void delelteObjectList(List<String> objList){
    	// 初始化OSSClient
    	OSSClient client = new OSSClient(END_POINT,ACCESS_KEY_ID, ACCESS_KEY_SECRET);
    	
    	for(int i = 0; i < objList.size(); i++){
    		client.deleteObject(BUCKET_NAME, objList.get(i));
    	}
	}
	

    /**
     * 删除Object
     * 
     * @param key 目录路径/文件名
     */
    public static void delelteObject(String key){
        // 初始化OSSClient
        OSSClient client = new OSSClient(END_POINT,ACCESS_KEY_ID, ACCESS_KEY_SECRET);
        
        client.deleteObject(BUCKET_NAME, key);
    }
}
