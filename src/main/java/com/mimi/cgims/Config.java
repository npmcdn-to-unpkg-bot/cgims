package com.mimi.cgims;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;


@Repository
public class Config {

    @Value("${test.init_data:false}")
    private boolean initTestData;

    @Value("${system.projectName}")
    private String projectName;

    @Value("${aliyun.oss.imageBucket}")
    private String aliyunOssImageBucket;

    @Value("${aliyun.oss.accessKeyId}")
    private String aliyunOssAccessKeyId;

    @Value("${aliyun.oss.accessKeySecret}")
    private String aliyunOssAccessKeySecret;

    @Value("${aliyun.oss.endpoint}")
    private String aliyunOssEndpoint;

    @Value("${aliyun.oss.cdnUrl}")
    private String aliyunOssCdnUrl;

    @Value("${aliyun.oss.uploadUrl.base}")
    private String aliyunOssUploadUrlBase;

    @Value("${aliyun.oss.uploadUrl.image}")
    private String aliyunOssUploadUrlImage;


    @Value("${ytx.accountSid}")
    private String ytxAccountSid;

    @Value("${ytx.authToken}")
    private String ytxAuthToken;

    @Value("${ytx.serverIp}")
    private String ytxServerIp;

    @Value("${ytx.serverPort}")
    private String ytxServerPort;

    @Value("${ytx.appId}")
    private String ytxAppId;

    @Value("${geetest.key}")
    private String geetestKey;

    @Value("${geetest.id}")
    private String geetestId;

    public String getGeetestId() {
        return geetestId;
    }

    public String getGeetestKey() {
        return geetestKey;
    }

    public String getYtxAppId() {
        return ytxAppId;
    }

    public String getYtxAccountSid() {
        return ytxAccountSid;
    }

    public String getYtxAuthToken() {
        return ytxAuthToken;
    }

    public String getYtxServerIp() {
        return ytxServerIp;
    }

    public String getYtxServerPort() {
        return ytxServerPort;
    }

    public boolean isInitTestData() {
        return initTestData;
    }

    public String getAliyunOssImageBucket() {
        return aliyunOssImageBucket;
    }

    public String getAliyunOssAccessKeyId() {
        return aliyunOssAccessKeyId;
    }

    public String getAliyunOssAccessKeySecret() {
        return aliyunOssAccessKeySecret;
    }

    public String getAliyunOssEndpoint() {
        return aliyunOssEndpoint;
    }

    public String getAliyunOssCdnUrl() {
        return aliyunOssCdnUrl;
    }

    public String getAliyunOssUploadUrlBase() {
        return aliyunOssUploadUrlBase;
    }

    public String getAliyunOssUploadUrlImage() {
        return aliyunOssUploadUrlImage;
    }

    public String getProjectName() {
        return projectName;
    }
}
