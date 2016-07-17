package com.mimi.sxp.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.aliyun.oss.OSSClient;
import com.baidu.ueditor.define.State;

public interface IAliyunOSSService {

	public OSSClient getOSSClient();

	public String saveFileToServer(MultipartFile theFile) throws IOException;

	public State saveImageToServer(String url) throws IOException;

	public String upzipAndSaveFileToServer(MultipartFile theFile, String type) throws IOException;
	
	public List<String> getChildPathList(String basePath);

//	public String addImgStyle(String path, String style);
//	
//	public String clearImgStyle(String path);
	
	public String addImgParams(String path, String params);
	
	public String batchAddImgParams(String paths, String params);
	
	public String clearImgParams(String path);
	
	public String batchClearImgParams(String paths);
	
}

