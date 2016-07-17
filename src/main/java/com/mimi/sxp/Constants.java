package com.mimi.sxp;

public class Constants {
	public static final String PROJECT_NAME = "sxp";
	
	public static final String ROLE_DEFAULT_ADMIN_NAME = "超级管理员";
	public static final String USER_DEFAULT_ADMIN_NAME = "admin";
	public static final String USER_DEFAULT_ADMIN_PASSWORD = "123456";
	public static final String MI_HEAD_IMG_DEFAULT_URL = "/assets/img/ui/logo.jpg";
	
	public static String COMMAND = "command";

	public static final String GEETEST_ID = "61658dd2f0dfc210aeb78192ea4fde05";
	public static final String GEETEST_KEY = "abceaa2fd512253fb743ba7e06780635";
	


	public static final String ALIYUN_OSS_IMAGE_BUCKET = "bluemimi4common";
	public static final String ALIYUN_OSS_ACCESS_KEY_ID = "8yCPOEyqRL87gGa3";
	public static final String ALIYUN_OSS_ACCESS_KEY_SECRET = "oTYhHWx8jTQVV03MiCucmLAI396PUS";
	public static final String ALIYUN_OSS_ENDPOINT = "http://oss-cn-hangzhou.aliyuncs.com";
	public static final String ALIYUN_OSS_CDN_URL = "http://img.uniqueid.cn/";
	
//	public static final String ALIYUN_OSS_UPLOAD_HEAD_URL = "applicationData/test/upload";
	public static final String ALIYUN_OSS_UPLOAD_HEAD_URL = "applicationData/sxp/upload";
	public static final String ALIYUN_OSS_UPLOAD_IMAGE_URL = "/images";
	public static final String ALIYUN_OSS_UPLOAD_PANORAMA_URL = "/panoramaData";
	public static final String ALIYUN_OSS_UPLOAD_RING_URL = "/ringData";

//	public static final String ALIYUN_OSS_IMAGE_BUCKET = "h0758";
//	public static final String ALIYUN_OSS_ACCESS_KEY_ID = "2LRpdxG01eHaZbdN";
//	public static final String ALIYUN_OSS_ACCESS_KEY_SECRET = "jySfraVNJBpDFXwf4YwnCusdWOao9A";
//	public static final String ALIYUN_OSS_ENDPOINT = "http://oss-cn-shenzhen.aliyuncs.com";
//	public static final String ALIYUN_OSS_CDN_URL = "http://mimage.h0758.net/";
	

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
			"http://img.uniqueid.cn/data/diy/mjc_base_template/assets/pictures/mjc_2000/bg_13.jpg" };

	public static final String[] ALIYUN_OSS_IMAGE_ALLOWED_SUFFIX = { "jpg",
			"jpeg", "png", "webp", "bmp" };
	public static final String ALIYUN_OSS_IMAGE_PARAMS_SPLIT = "@";
	public static final String ALIYUN_OSS_IMAGE_PARAMS_AND = "|";
	public static final String ALIYUN_OSS_IMAGE_WATER_MARK = "watermark=2&text=6ZqP5b-D6YWN&type=ZmFuZ3poZW5na2FpdGk&size=40&color=I2ZmZmZmZg&p=9&y=10&x=10&t=40";
	public static final String ALIYUN_OSS_IMAGE_PARAMS_TYPE_RING_SMALL_IMG = "1e_1280w_720h_1c_0i_1o_90Q_1x.{suffix}"+ALIYUN_OSS_IMAGE_PARAMS_AND+ALIYUN_OSS_IMAGE_WATER_MARK;
	public static final String ALIYUN_OSS_IMAGE_PARAMS_TYPE_RING_IMG = "1e_1920w_1080h_1c_0i_1o_90Q_1x.{suffix}"+ALIYUN_OSS_IMAGE_PARAMS_AND+ALIYUN_OSS_IMAGE_WATER_MARK;
	public static final String ALIYUN_OSS_IMAGE_PARAMS_TYPE_PHOTO_SMALL_IMG = "0e_100w_100h_0c_0i_1o_90Q_1x.{suffix}";
	public static final String ALIYUN_OSS_IMAGE_PARAMS_TYPE_PHOTO_IMG = "0e_800w_800h_1c_0i_0o_90Q_1x.{suffix}"+ALIYUN_OSS_IMAGE_PARAMS_AND+ALIYUN_OSS_IMAGE_WATER_MARK;
	public static final String ALIYUN_OSS_IMAGE_PARAMS_TYPE_BANNER = "1e_400w_300h_1c_0i_1o_90Q_1x.{suffix}"+ALIYUN_OSS_IMAGE_PARAMS_AND+ALIYUN_OSS_IMAGE_WATER_MARK;
	public static final String ALIYUN_OSS_IMAGE_PARAMS_TYPE_AD = "1e_400w_100h_1c_0i_1o_90Q_1x.{suffix}";
	public static final String ALIYUN_OSS_IMAGE_PARAMS_TYPE_AD_SMALL = "1e_100w_100h_1c_0i_1o_90Q_1x.{suffix}";
	public static final String ALIYUN_OSS_IMAGE_PARAMS_TYPE_HEAD_IMG = "1e_100w_100h_1c_0i_1o_90Q_1x.{suffix}";
	public static final String ALIYUN_OSS_IMAGE_PARAMS_TYPE_NORMAL_PRE_IMG = "1e_120w_90h_1c_0i_1o_90Q_1x.{suffix}";
	public static final String ALIYUN_OSS_IMAGE_PARAMS_TYPE_INFO_IMG = "0e_400w_400h_1c_0i_0o_90Q_1x.{suffix}"+ALIYUN_OSS_IMAGE_PARAMS_AND+ALIYUN_OSS_IMAGE_WATER_MARK;
	public static final String ALIYUN_OSS_IMAGE_STYLE_SPLIT = "@!";
	public static final String ALIYUN_OSS_IMAGE_STYLE_TYPE_HEAD_IMG = "head-img";

	public static final String IMAGE_URLS_SPLIT_STRING = ",";

	public static final String MI_IDS_SPLIT_STRING = "/";
}
