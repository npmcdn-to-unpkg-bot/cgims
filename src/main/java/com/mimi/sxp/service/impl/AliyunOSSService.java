package com.mimi.sxp.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ListObjectsRequest;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;
import com.baidu.ueditor.PathFormat;
import com.baidu.ueditor.define.AppInfo;
import com.baidu.ueditor.define.BaseState;
import com.baidu.ueditor.define.MIMEType;
import com.baidu.ueditor.define.State;
import com.cedarsoft.zip.ZipExtractor;
import com.mimi.sxp.Constants;
import com.mimi.sxp.service.IAliyunOSSService;
import com.mimi.sxp.util.FileUtil;

@Service
public class AliyunOSSService implements IAliyunOSSService {
//	private String accessKeyId = "8yCPOEyqRL87gGa3";
//	private String accessKeySecret = "oTYhHWx8jTQVV03MiCucmLAI396PUS";
//    // 以杭州为例
//	private String endpoint = "http://oss-cn-hangzhou.aliyuncs.com";
	
	public static void main(String args[]){
//		AliyunOSSService ai = new AliyunOSSService();
////		ai.getOSSClient();
////		System.out.println(ai.accessKeyId);
////		ai.listObjects("bluemimi4common");
//		try {
//			ai.putObject("bluemimi4common", "test/waw.gif", "D:\\material\\images\\gif\\houseLoading.gif");
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
//		List<String> list = new ArrayList<String>();
//		list.add("a/sdf");
//		list.add("gd/asdf");
//		list.add("a4afsd");
//		list.add("21/e");
//		list.add("03/1");
//		list.add("1/52");
//		Collections.sort(list);
//		for(String str:list){
//			System.out.println(str);
//		}
		
		AliyunOSSService as = new AliyunOSSService();
		as.getChildPathList("");
	}
	
	private OSSClient oc = null;
	@Override
	public OSSClient getOSSClient(){
		if(oc==null){
			oc = new OSSClient(Constants.ALIYUN_OSS_ENDPOINT,Constants.ALIYUN_OSS_ACCESS_KEY_ID, Constants.ALIYUN_OSS_ACCESS_KEY_SECRET);
		}
		return oc;
	}
	
	public void putObject(String bucketName, String key, String filePath) throws FileNotFoundException {

	    // 初始化OSSClient
	    OSSClient client = getOSSClient();
	    // 获取指定文件的输入流
	    File file = new File(filePath);
	    InputStream content = new FileInputStream(file);

	    // 创建上传Object的Metadata
	    ObjectMetadata meta = new ObjectMetadata();

	    // 必须设置ContentLength
	    meta.setContentLength(file.length());

	    // 上传Object.
	    PutObjectResult result = client.putObject(bucketName, key, content, meta);

	    // 打印ETag
	    System.out.println(result.getETag());
	}

	public void listObjects(String bucketName) {

	    // 初始化OSSClient
	    OSSClient client = getOSSClient();

	    // 获取指定bucket下的所有Object信息
	    ObjectListing listing = client.listObjects(bucketName);

	    // 遍历所有Object
	    for (OSSObjectSummary objectSummary : listing.getObjectSummaries()) {
	        System.out.println(objectSummary.getKey());
	    }
	}

	@Override
	public String saveFileToServer(MultipartFile theFile) throws IOException {
		 // 初始化OSSClient
	    OSSClient client = getOSSClient();

	    // 获取指定文件的输入流
	    InputStream content = theFile.getInputStream();

	    // 创建上传Object的Metadata
	    ObjectMetadata meta = new ObjectMetadata();

	    // 必须设置ContentLength
	    meta.setContentLength(theFile.getSize());
	    meta.setContentType(theFile.getContentType());

//		String fileName = theFile.getOriginalFilename();
//		fileName = UUID.randomUUID().toString()
//				+ fileName.substring(fileName.lastIndexOf("."));
//		Calendar cal = Calendar.getInstance();
//		cal.setTime(new Date());
//		cal.getTimeInMillis();
//		int year = cal.get(Calendar.YEAR);
//		int month = cal.get(Calendar.MONTH) + 1;
//		int day = cal.get(Calendar.DAY_OF_MONTH);
//		int hour = cal.get(Calendar.HOUR_OF_DAY);
//		String path = "applicationData/test/upload/images/" + year + "/" + month + "/" + day
//				+ "/" + hour + "/"+fileName;
		String lastName = theFile.getOriginalFilename();
		lastName = lastName.substring(lastName.lastIndexOf("."));
		String path = Constants.ALIYUN_OSS_UPLOAD_HEAD_URL+Constants.ALIYUN_OSS_UPLOAD_IMAGE_URL+getAutoBuildDirectory()+lastName;
		
		client.putObject(Constants.ALIYUN_OSS_IMAGE_BUCKET, path, content, meta);
	    path = Constants.ALIYUN_OSS_CDN_URL+path;
		return path;
	}

//	@Override
//	public String addImgStyle(String path, String style) {
//		if(StringUtils.isNotBlank(path) && StringUtils.isNotBlank(style)){
//			int index = path.lastIndexOf(Constants.ALIYUN_OSS_IMAGE_STYLE_SPLIT);
//			if(index==-1){
//				path = path+Constants.ALIYUN_OSS_IMAGE_STYLE_SPLIT+style;
//			}else{
//				path = path.substring(0, index)+Constants.ALIYUN_OSS_IMAGE_STYLE_SPLIT+style;
//			}
//		}
//		return path;
//	}
//
//	@Override
//	public String clearImgStyle(String path) {
//		if(StringUtils.isNotBlank(path)){
//			int index = path.lastIndexOf(Constants.ALIYUN_OSS_IMAGE_STYLE_SPLIT);
//			if(index!=-1){
//				path = path.substring(0, index);
//			}
//		}
//		return path;
//	}

	@Override
	public String addImgParams(String path, String params) {
		if(StringUtils.isNotBlank(path) && StringUtils.isNotBlank(params)){
			path = clearImgParams(path);
			String suffix = path.substring(path.lastIndexOf(".")+1);
			for(int i=0;i<Constants.ALIYUN_OSS_IMAGE_ALLOWED_SUFFIX.length;i++){
				if(Constants.ALIYUN_OSS_IMAGE_ALLOWED_SUFFIX[i].equalsIgnoreCase(suffix)){
					params = params.replace("{suffix}", Constants.ALIYUN_OSS_IMAGE_ALLOWED_SUFFIX[i]);
					path = path+Constants.ALIYUN_OSS_IMAGE_PARAMS_SPLIT+params;
					break;
				}
			}
		}
		return path;
	}

	@Override
	public String clearImgParams(String path) {
		if(StringUtils.isNotBlank(path)){
			int index = path.lastIndexOf(Constants.ALIYUN_OSS_IMAGE_PARAMS_SPLIT);
			if(index!=-1){
				path = path.substring(0, index);
			}
		}
		return path;
	}

	@Override
	public String batchAddImgParams(String paths, String params) {
		if(StringUtils.isNotBlank(paths) && StringUtils.isNotBlank(params)){
			String[] pathList = paths.split(Constants.IMAGE_URLS_SPLIT_STRING);
			String newPaths = "";
			for(int j=0;j<pathList.length;j++){
				String path = addImgParams(pathList[j], params);
				newPaths = newPaths+path;
				if(j<pathList.length-1){
					newPaths = newPaths+Constants.IMAGE_URLS_SPLIT_STRING;
				}
			}
			paths = newPaths;
		}
		return paths;
	}

	@Override
	public String batchClearImgParams(String paths) {
		if(StringUtils.isNotBlank(paths)){
			String[] pathList = paths.split(Constants.IMAGE_URLS_SPLIT_STRING);
			String newPaths = "";
			for(int j=0;j<pathList.length;j++){
				String path = clearImgParams(pathList[j]);
				newPaths = newPaths+path;
				if(j<pathList.length-1){
					newPaths = newPaths+Constants.IMAGE_URLS_SPLIT_STRING;
				}
			}
			paths = newPaths;
		}
		return paths;
	}

	@Override
	public String upzipAndSaveFileToServer(MultipartFile theFile,String type)
			throws IOException {
		String midPath = getAutoBuildDirectory();
		String unzipPath = System.getProperty("java.io.tmpdir")+Constants.PROJECT_NAME+"/zip"+midPath;
		
		ZipExtractor ze = new ZipExtractor();
		File fi = new File(unzipPath);
		if(!fi.isDirectory()){
			fi.mkdirs();
		}
		ze.extract(fi, theFile.getInputStream());
		String lastName = "/";
		for(File cf:fi.listFiles()){
			if(cf.isFile()){
				if(cf.getName().toLowerCase().contains(".html") && !cf.getName().toLowerCase().contains("_editor.html")){
					lastName += cf.getName();
					break;
				}
			}
		}
		saveAction(fi,midPath,type);
		
		FileUtil.deleteDir(fi);

		String path = Constants.ALIYUN_OSS_UPLOAD_HEAD_URL+type+midPath+lastName;
//		String path = "applicationData/test/upload/panoramaData"+midPath+lastName;
	    path = Constants.ALIYUN_OSS_CDN_URL+path;
		return path;
	}
	
	private void saveAction(File sourceFile,String midPath,String type) throws FileNotFoundException{
		String targetPath = "";
		String sourcePath = sourceFile.getPath();
		sourcePath = sourcePath.replace("\\", "/");
		String[] paths = sourcePath.split(midPath);
//		targetPath = "c://testZip/applicationData/test/upload/panoramaData"+midPath;
//		targetPath = "applicationData/test/upload/panoramaData"+midPath;
		targetPath = Constants.ALIYUN_OSS_UPLOAD_HEAD_URL+type+midPath;
		if(paths.length>1){
			targetPath+=paths[1];
		}
//		File targetFile = new File(targetPath);
		if(sourceFile.isDirectory()){
//			if(!targetFile.exists()){
//				targetFile.mkdirs();
//			}
			for(File child:sourceFile.listFiles()){
				saveAction(child,midPath,type);
			}
		}else{
		    OSSClient client = getOSSClient();
			FileInputStream is = new FileInputStream(sourceFile);
			try {
//				FileUtil.copy(is, targetPath);
			    ObjectMetadata meta = new ObjectMetadata();
			    meta.setContentLength(sourceFile.length());
			    meta.setContentType(Files.probeContentType(Paths.get(sourcePath)));
				client.putObject(Constants.ALIYUN_OSS_IMAGE_BUCKET, targetPath, is, meta);
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public String getAutoBuildDirectory(){
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.getTimeInMillis();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		int day = cal.get(Calendar.DAY_OF_MONTH);
		int hour = cal.get(Calendar.HOUR_OF_DAY);

		String uuid = UUID.randomUUID().toString();
		String midPath = "/"+ year + "/" + month + "/" + day
				+ "/" + hour + "/" +uuid;
		return midPath;
	}

	@Override
	public State saveImageToServer(String urlStr) throws IOException {

		HttpURLConnection connection = null;
		URL url = null;
		String suffix = null;
		
		try {
			url = new URL( urlStr );

			
			connection = (HttpURLConnection) url.openConnection();
		
			connection.setInstanceFollowRedirects( true );
			connection.setUseCaches( true );
		
			
			suffix = MIMEType.getSuffix( connection.getContentType() );

			List<String> allowSuffix = new ArrayList<String>();
			allowSuffix.add(".jpg");
			allowSuffix.add(".jpeg");
			allowSuffix.add(".png");
			allowSuffix.add(".gif");
			if ( !allowSuffix.contains( suffix ) ) {
				return new BaseState( false, AppInfo.NOT_ALLOW_FILE_TYPE );
			}
			
			if ( connection.getContentLength()>25 * 1024 * 1024 ) {
				return new BaseState( false, AppInfo.MAX_SIZE );
			}
			
			
//			String savePath = this.getPath( this.savePath, this.filename, suffix );
//			String physicalPath = this.rootPath + savePath;
//			State state = StorageManager.saveFileByInputStream( connection.getInputStream(), physicalPath );

			// 初始化OSSClient
		    OSSClient client = getOSSClient();

		    // 获取指定文件的输入流
		    InputStream content = connection.getInputStream();

		    // 创建上传Object的Metadata
		    ObjectMetadata meta = new ObjectMetadata();

		    // 必须设置ContentLength
		    meta.setContentLength(connection.getContentLength());
		    meta.setContentType(connection.getContentType());

			String path = Constants.ALIYUN_OSS_UPLOAD_HEAD_URL+Constants.ALIYUN_OSS_UPLOAD_IMAGE_URL+getAutoBuildDirectory()+suffix;
			
			client.putObject(Constants.ALIYUN_OSS_IMAGE_BUCKET, path, content, meta);
		    path = Constants.ALIYUN_OSS_CDN_URL+path;


			String fileName = path.substring(path.lastIndexOf("/")+1);
			path = addImgParams(path,
					Constants.ALIYUN_OSS_IMAGE_PARAMS_TYPE_INFO_IMG);
		    State state = new BaseState(true);
			state.putInfo( "size", connection.getContentLength() );
			state.putInfo( "title", fileName );
			state.putInfo( "url", PathFormat.format( path ) );
			state.putInfo( "source", urlStr );
			
			return state;
			
		} catch ( Exception e ) {
			return new BaseState( false, AppInfo.REMOTE_FAIL );
		}
	}
	
	public List<String> getChildPathList(String basePath){
		if(StringUtils.isBlank(basePath)){
			return null;
		}
		String midPath = basePath.replace(Constants.ALIYUN_OSS_CDN_URL, "");
//		String midPath = "images/birdeye/hjyy_0/hjyy_0_50/";
		List<String> pathList = new ArrayList<String>();
	    OSSClient client = getOSSClient();
		// 构造ListObjectsRequest请求
		ListObjectsRequest listObjectsRequest = new ListObjectsRequest(Constants.ALIYUN_OSS_IMAGE_BUCKET);

		// "/" 为文件夹的分隔符
		listObjectsRequest.setDelimiter("/");

		// 列出fun目录下的所有文件和文件夹
		listObjectsRequest.setPrefix(midPath);

		ObjectListing listing = client.listObjects(listObjectsRequest);

		// 遍历所有Object
		System.out.println("Objects:");
		for (OSSObjectSummary objectSummary : listing.getObjectSummaries()) {
			if(objectSummary.getKey().lastIndexOf("/")!=objectSummary.getKey().length()-1){
				pathList.add(Constants.ALIYUN_OSS_CDN_URL+objectSummary.getKey());
			}
//		    System.out.println(objectSummary.getKey());
		}
		
		Collections.sort(pathList);
		
//		for(String path:pathList){
//			System.out.println(path);
//		}
		
		// 遍历所有CommonPrefix
//		System.out.println("\nCommonPrefixs:");
//		for (String commonPrefix : listing.getCommonPrefixes()) {
//		    System.out.println(commonPrefix);
//		}
		
		return pathList;
	}
	
}