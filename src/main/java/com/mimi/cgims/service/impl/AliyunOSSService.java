package com.mimi.cgims.service.impl;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.*;
import com.cedarsoft.zip.ZipExtractor;
import com.mimi.cgims.Config;
import com.mimi.cgims.service.IAliyunOSSService;
import com.mimi.cgims.util.FileUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

@Service
public class AliyunOSSService implements IAliyunOSSService {
	@Resource
	private Config config;


	public static final String[] ALIYUN_OSS_TEST_IMG_URLS = {
			"http://img.uniqueid.cn/data/diy/mjc_base_template/assets/pictures/mjc_2000/bg_00.jpg",
			"http://img.uniqueid.cn/data/diy/mjc_base_template/assets/pictures/mjc_2000/bg_02.jpg",
			"http://img.uniqueid.cn/data/diy/mjc_base_template/assets/pictures/mjc_2000/bg_03.jpg",
			"http://img.uniqueid.cn/data/diy/mjc_base_template/assets/pictures/mjc_2000/bg_04.jpg",
			"http://img.uniqueid.cn/data/diy/mjc_base_template/assets/pictures/mjc_2000/bg_05.jpg",
			"http://img.uniqueid.cn/data/diy/mjc_base_template/assets/pictures/mjc_2000/bg_06.jpg",
			"http://img.uniqueid.cn/data/diy/mjc_base_template/assets/pictures/mjc_2000/bg_07.jpg",
			"http://img.uniqueid.cn/data/diy/mjc_base_template/assets/pictures/mjc_2000/bg_08.jpg",
			"http://img.uniqueid.cn/data/diy/mjc_base_template/assets/pictures/mjc_2000/bg_09.jpg",
			"http://img.uniqueid.cn/data/diy/mjc_base_template/assets/pictures/mjc_2000/bg_13.jpg"};

	public static final String[] ALIYUN_OSS_IMAGE_ALLOWED_SUFFIX = {"jpg",
			"jpeg", "png", "webp", "bmp"};
	public static final String ALIYUN_OSS_IMAGE_PARAMS_SPLIT = "@";
	public static final String ALIYUN_OSS_IMAGE_PARAMS_AND = "|";
	public static final String ALIYUN_OSS_IMAGE_WATER_MARK = "watermark=2&text=6ZqP5b-D6YWN&type=ZmFuZ3poZW5na2FpdGk&size=40&color=I2ZmZmZmZg&p=9&y=10&x=10&t=40";
	public static final String ALIYUN_OSS_IMAGE_PARAMS_TYPE_RING_SMALL_IMG = "1e_1280w_720h_1c_0i_1o_90Q_1x.{suffix}" + ALIYUN_OSS_IMAGE_PARAMS_AND + ALIYUN_OSS_IMAGE_WATER_MARK;
	public static final String ALIYUN_OSS_IMAGE_PARAMS_TYPE_RING_IMG = "1e_1920w_1080h_1c_0i_1o_90Q_1x.{suffix}" + ALIYUN_OSS_IMAGE_PARAMS_AND + ALIYUN_OSS_IMAGE_WATER_MARK;
	public static final String ALIYUN_OSS_IMAGE_PARAMS_TYPE_PHOTO_SMALL_IMG = "0e_100w_100h_0c_0i_1o_90Q_1x.{suffix}";
	public static final String ALIYUN_OSS_IMAGE_PARAMS_TYPE_PHOTO_IMG = "0e_800w_800h_1c_0i_0o_90Q_1x.{suffix}" + ALIYUN_OSS_IMAGE_PARAMS_AND + ALIYUN_OSS_IMAGE_WATER_MARK;
	public static final String ALIYUN_OSS_IMAGE_PARAMS_TYPE_BANNER = "1e_400w_300h_1c_0i_1o_90Q_1x.{suffix}" + ALIYUN_OSS_IMAGE_PARAMS_AND + ALIYUN_OSS_IMAGE_WATER_MARK;
	public static final String ALIYUN_OSS_IMAGE_PARAMS_TYPE_IDENTITY = "1e_400w_300h_1c_0i_1o_90Q_1x.{suffix}";
	public static final String ALIYUN_OSS_IMAGE_PARAMS_TYPE_AD = "1e_400w_100h_1c_0i_1o_90Q_1x.{suffix}";
	public static final String ALIYUN_OSS_IMAGE_PARAMS_TYPE_AD_SMALL = "1e_100w_100h_1c_0i_1o_90Q_1x.{suffix}";
	public static final String ALIYUN_OSS_IMAGE_PARAMS_TYPE_HEAD_IMG = "1e_100w_100h_1c_0i_1o_90Q_1x.{suffix}";
	public static final String ALIYUN_OSS_IMAGE_PARAMS_TYPE_NORMAL_PRE_IMG = "1e_120w_90h_1c_0i_1o_90Q_1x.{suffix}";
	public static final String ALIYUN_OSS_IMAGE_PARAMS_TYPE_INFO_IMG = "0e_400w_400h_1c_0i_0o_90Q_1x.{suffix}" + ALIYUN_OSS_IMAGE_PARAMS_AND + ALIYUN_OSS_IMAGE_WATER_MARK;
	public static final String ALIYUN_OSS_IMAGE_STYLE_SPLIT = "@!";
	public static final String ALIYUN_OSS_IMAGE_STYLE_TYPE_HEAD_IMG = "head-img";

	public static final String IMAGE_URLS_SPLIT_STRING = ",";
	
	private OSSClient oc = null;
	@Override
	public OSSClient getOSSClient(){
		if(oc==null){
			synchronized (this){
				if(oc==null){
					oc = new OSSClient(config.getAliyunOssEndpoint(),config.getAliyunOssAccessKeyId(), config.getAliyunOssAccessKeySecret());
				}
			}
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
		String path = config.getAliyunOssUploadUrlBase()+config.getAliyunOssUploadUrlImage()+getAutoBuildDirectory()+lastName;
		
		client.putObject(config.getAliyunOssImageBucket(), path, content, meta);
	    path = config.getAliyunOssCdnUrl()+path;
		return path;
	}

//	@Override
//	public String addImgStyle(String path, String style) {
//		if(StringUtils.isNotBlank(path) && StringUtils.isNotBlank(style)){
//			int index = path.lastIndexOf(AliyunOSSService.ALIYUN_OSS_IMAGE_STYLE_SPLIT);
//			if(index==-1){
//				path = path+AliyunOSSService.ALIYUN_OSS_IMAGE_STYLE_SPLIT+style;
//			}else{
//				path = path.substring(0, index)+AliyunOSSService.ALIYUN_OSS_IMAGE_STYLE_SPLIT+style;
//			}
//		}
//		return path;
//	}
//
//	@Override
//	public String clearImgStyle(String path) {
//		if(StringUtils.isNotBlank(path)){
//			int index = path.lastIndexOf(AliyunOSSService.ALIYUN_OSS_IMAGE_STYLE_SPLIT);
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
			for(int i=0;i<AliyunOSSService.ALIYUN_OSS_IMAGE_ALLOWED_SUFFIX.length;i++){
				if(AliyunOSSService.ALIYUN_OSS_IMAGE_ALLOWED_SUFFIX[i].equalsIgnoreCase(suffix)){
					params = params.replace("{suffix}", AliyunOSSService.ALIYUN_OSS_IMAGE_ALLOWED_SUFFIX[i]);
					path = path+AliyunOSSService.ALIYUN_OSS_IMAGE_PARAMS_SPLIT+params;
					break;
				}
			}
		}
		return path;
	}

	@Override
	public String clearImgParams(String path) {
		if(StringUtils.isNotBlank(path)){
			int index = path.lastIndexOf(AliyunOSSService.ALIYUN_OSS_IMAGE_PARAMS_SPLIT);
			if(index!=-1){
				path = path.substring(0, index);
			}
		}
		return path;
	}

	@Override
	public String batchAddImgParams(String paths, String params) {
		if(StringUtils.isNotBlank(paths) && StringUtils.isNotBlank(params)){
			String[] pathList = paths.split(AliyunOSSService.IMAGE_URLS_SPLIT_STRING);
			String newPaths = "";
			for(int j=0;j<pathList.length;j++){
				String path = addImgParams(pathList[j], params);
				newPaths = newPaths+path;
				if(j<pathList.length-1){
					newPaths = newPaths+AliyunOSSService.IMAGE_URLS_SPLIT_STRING;
				}
			}
			paths = newPaths;
		}
		return paths;
	}

	@Override
	public String batchClearImgParams(String paths) {
		if(StringUtils.isNotBlank(paths)){
			String[] pathList = paths.split(AliyunOSSService.IMAGE_URLS_SPLIT_STRING);
			String newPaths = "";
			for(int j=0;j<pathList.length;j++){
				String path = clearImgParams(pathList[j]);
				newPaths = newPaths+path;
				if(j<pathList.length-1){
					newPaths = newPaths+AliyunOSSService.IMAGE_URLS_SPLIT_STRING;
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
		String unzipPath = System.getProperty("java.io.tmpdir")+config.getProjectName()+"/zip"+midPath;
		
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

		String path = config.getAliyunOssUploadUrlBase()+type+midPath+lastName;
//		String path = "applicationData/test/upload/panoramaData"+midPath+lastName;
	    path = config.getAliyunOssCdnUrl()+path;
		return path;
	}
	
	private void saveAction(File sourceFile,String midPath,String type) throws FileNotFoundException{
		String targetPath = "";
		String sourcePath = sourceFile.getPath();
		sourcePath = sourcePath.replace("\\", "/");
		String[] paths = sourcePath.split(midPath);
//		targetPath = "c://testZip/applicationData/test/upload/panoramaData"+midPath;
//		targetPath = "applicationData/test/upload/panoramaData"+midPath;
		targetPath = config.getAliyunOssUploadUrlBase()+type+midPath;
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
				client.putObject(config.getAliyunOssImageBucket(), targetPath, is, meta);
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

	public List<String> getChildPathList(String basePath){
		if(StringUtils.isBlank(basePath)){
			return null;
		}
		String midPath = basePath.replace(config.getAliyunOssCdnUrl(), "");
//		String midPath = "images/birdeye/hjyy_0/hjyy_0_50/";
		List<String> pathList = new ArrayList<String>();
	    OSSClient client = getOSSClient();
		// 构造ListObjectsRequest请求
		ListObjectsRequest listObjectsRequest = new ListObjectsRequest(config.getAliyunOssImageBucket());

		// "/" 为文件夹的分隔符
		listObjectsRequest.setDelimiter("/");

		// 列出fun目录下的所有文件和文件夹
		listObjectsRequest.setPrefix(midPath);

		ObjectListing listing = client.listObjects(listObjectsRequest);

		// 遍历所有Object
		System.out.println("Objects:");
		for (OSSObjectSummary objectSummary : listing.getObjectSummaries()) {
			if(objectSummary.getKey().lastIndexOf("/")!=objectSummary.getKey().length()-1){
				pathList.add(config.getAliyunOssCdnUrl()+objectSummary.getKey());
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