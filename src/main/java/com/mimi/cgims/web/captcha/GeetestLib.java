package com.mimi.cgims.web.captcha;


import org.springframework.stereotype.Repository;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 * Java SDK
 *
 * @author Zheng
 * @time 2014年7月10日 下午3:29:09
 */
@Repository
public class GeetestLib {

    /**
     * SDK版本编号
     */
    // private final int verCode = 8;

    /**
     * SDK版本名称
     */
    private final String verName = "2.15.5.22.1";
    private final String sdkLang = "java";// SD的语言类型

    private final String baseUrl = "api.geetest.com";
    private final String api_url = "http://" + baseUrl;
    private final String https_api_url = "https://" + baseUrl;// 一些页面是https
    private final int defaultIsMobile = 0;
    private final int defaultMobileWidth = 260;// the default width of the
    // mobile capthca

    /**
     * 公钥
     */
    private String captchaId = "";

    /**
     * 私钥
     */
    private String privateKey = "";

    /**
     * the challenge
     */
    private String challengeId = "";

    /**
     * set the own private pictures,default is ""
     */
    private String picId = "";

    /**
     * he captcha product type,default is 'embed'
     */
    private String productType = "embed";

    /**
     * is secure
     */
    private Boolean isHttps = false;

    public Boolean getIsHttps() {
        return isHttps;
    }

    public void setIsHttps(Boolean isHttps) {
        this.isHttps = isHttps;
    }

    /**
     * when the productType is popup,it needs to set the submitbutton
     */
    private String submitBtnId = "submit-button";

    public String getSubmitBtnId() {
        return submitBtnId;
    }

    public void setSubmitBtnId(String submitBtnId) {
        this.submitBtnId = submitBtnId;
    }

    /**
     * 是否是移动端的
     */
    private int isMobile = defaultIsMobile;// 1--true,0-false

    public String getChallengeId() {
        return challengeId;
    }

    public void setChallengeId(String challengeId) {
        this.challengeId = challengeId;
    }

    /**
     * 获取版本编号
     *
     * @return
     * @author Zheng
     * @email dreamzsm@gmail.com
     * @time 2014年7月11日 上午11:07:11
     */
    public String getVersionInfo() {
        return verName;
    }

    // public void setCaptcha_id(String captcha_id) {
    // this.captcha_id = captcha_id;
    // }

    /**
     * 一个无参构造函数
     */
    public GeetestLib() {
    }

    public void init(String privateKey){
        this.privateKey = privateKey;
    }

    public String getPicId() {
        return picId;
    }

    public void setPicId(String picId) {
        this.picId = picId;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public int getIsMobile() {
        return isMobile;
    }

    public void setIsMobile(int isMobile) {
        this.isMobile = isMobile;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public GeetestLib(String privateKey) {
        this.privateKey = privateKey;
    }

    // public GeetestLib(String privateKey, String captcha_id) {
    // this.privateKey = privateKey;
    // this.captcha_id = captcha_id;
    // }

    // public int getVerCode() {
    // return verCode;
    // }

    public String getVerName() {
        return verName;
    }

    public String getCaptchaId() {
        return captchaId;
    }

    public void setCaptchaId(String captchaId) {
        this.captchaId = captchaId;
    }

    /**
     * processing before the captcha display on the web front
     *
     * @return
     */
    public int preProcess() {

        // first check the server status , to handle failback
        // if (getGtServerStatus() != 1) {
        // return 0;
        // }

        // just check the server side register
        if (registerChallenge() != 1) {
            return 0;
        }

        return 1;

    }

    /**
     * generate the dynamic front source
     *
     * @param different product display mode :float,embed,popup
     * @return
     */
    public String getGtFrontSource() {

        String base_path = "";
        if (this.isHttps) {
            base_path = this.https_api_url;
        } else {
            base_path = this.api_url;
        }

        String frontSource = String.format(
                "<script type=\"text/javascript\" src=\"%s/get.php?"
                        + "gt=%s&challenge=%s", base_path, this.captchaId,
                this.challengeId);

        if (this.productType.equals("popup")) {
            frontSource += String.format("&product=%s&popupbtnid=%s",
                    this.productType, this.submitBtnId);
        } else {
            frontSource += String.format("&product=%s", this.productType);
        }

        frontSource += "\"></script>";

        return frontSource;
    }

    /**
     * 获取极验的服务器状态
     *
     * @return
     * @author Zheng
     * @email dreamzsm@gmail.com
     * @time 2014年7月10日 下午7:12:38
     */
    public int getGtServerStatus() {

        try {
            final String GET_URL = api_url + "/check_status.php";
            if (readContentFromGet(GET_URL).equals("ok")) {
                return 1;
            } else {
//				System.out.println("gServer is Down");
                return 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * generate a random num
     *
     * @return
     */
    public int getRandomNum() {

        int rand_num = (int) (Math.random() * 100);
        // System.out.print(rand_num);
        return rand_num;
    }

    /**
     * Register the challenge
     *
     * @return
     */
    public int registerChallenge() {
        try {
            String GET_URL = api_url + "/register.php?gt=" + this.captchaId;

            // if (this.productType.equals("popup")) {
            // GET_URL += String.format("&product=%s&popupbtnid=%s",
            // this.productType, this.submitBtnId);
            // } else {
            // GET_URL += String.format("&product=%s", this.productType);
            // }

            // System.out.print(GET_URL);
            String result_str = readContentFromGet(GET_URL);
            // System.out.println(result_str);
            if (32 == result_str.length()) {
                this.challengeId = result_str;
                return 1;
            } else {
//				System.out.println("gServer register challenge failed");
                return 0;
            }
        } catch (Exception e) {
            gtlog("exception:register api:");
            // e.printStackTrace();
        }
        return 0;
    }

    /**
     * 读取服务器
     *
     * @param getURL
     * @return
     * @throws IOException
     * @author Zheng dreamzsm@gmail.com
     * @time 2014年7月10日 下午7:11:11
     */
    private String readContentFromGet(String getURL) throws IOException {

        URL getUrl = new URL(getURL);
        HttpURLConnection connection = (HttpURLConnection) getUrl
                .openConnection();

        connection.setConnectTimeout(2000);// 设置连接主机超时（单位：毫秒）
        connection.setReadTimeout(2000);// 设置从主机读取数据超时（单位：毫秒）

        // 建立与服务器的连接，并未发送数据

        connection.connect();
        // 发送数据到服务器并使用Reader读取返回的数据
        StringBuffer sBuffer = new StringBuffer();

        InputStream inStream = null;
        byte[] buf = new byte[1024];
        inStream = connection.getInputStream();
        for (int n; (n = inStream.read(buf)) != -1; ) {
            sBuffer.append(new String(buf, 0, n, "UTF-8"));
        }
        inStream.close();
        connection.disconnect();// 断开连接

        return sBuffer.toString();
    }

    /**
     * 判断一个表单对象值是否为空
     *
     * @param gtObj
     * @return
     * @time 2014年7月10日 下午5:54:25
     */
    private boolean objIsEmpty(Object gtObj) {
        if (gtObj != null) {
            return false;
        }
        // && gtObj.toString().trim().length() > 0

        return true;
    }

    /**
     * 检查客户端的请求是否为空--三个只要有一个为空，则判断不合法
     *
     * @param request
     * @return
     * @time 2014年7月10日 下午5:46:34
     */
    public boolean resquestIsLegal(HttpServletRequest request) {

        if (objIsEmpty(request.getParameter("geetest_challenge"))) {
            return false;
        }

        if (objIsEmpty(request.getParameter("geetest_validate"))) {
            return false;
        }

        if (objIsEmpty(request.getParameter("geetest_seccode"))) {
            return false;
        }

        return true;
    }

    /**
     * 检验验证请求 传入的参数为request--vCode 8之后不再更新,不推荐使用
     *
     * @param request
     * @return
     * @time 2014年7月10日 下午6:34:55
     */
    public boolean validateRequest(HttpServletRequest request) {

        boolean gtResult = this.validate(
                request.getParameter("geetest_challenge"),
                request.getParameter("geetest_validate"),
                request.getParameter("geetest_seccode"));

        return gtResult;
    }

    /**
     * 增强版的验证信息,提供了更多的验证返回结果信息，以让客户服务器端有不同的数据处理。
     *
     * @param challenge
     * @param validate
     * @param seccode
     * @return
     */
    public String enhencedValidateRequest(HttpServletRequest request) {

        String challenge = request.getParameter("geetest_challenge");
        String validate = request.getParameter("geetest_validate");
        String seccode = request.getParameter("geetest_seccode");

        String host = baseUrl;
        String path = "/validate.php";
        int port = 80;
        String query = "seccode=" + seccode + "&sdk=" + this.sdkLang + "_"
                + this.verName;
        String response = "";

        gtlog(query);
        try {
            if (validate.length() <= 0) {
                return "fail";
            }

            if (!checkResultByPrivate(challenge, validate)) {
                return "fail";
            }

            response = postValidate(host, path, query, port);
            gtlog("response: " + response);
        } catch (Exception e) {
            e.printStackTrace();
        }

        gtlog("md5: " + md5Encode(seccode));

        if (response.equals(md5Encode(seccode))) {
            return "success";
        } else {
            return response;
        }

    }

    /**
     * the old api use before version code 8(not include)
     *
     * @param challenge
     * @param validate
     * @param seccode
     * @return
     * @time 2014122_171529 by zheng
     */
    public boolean validate(String challenge, String validate, String seccode) {
        String host = baseUrl;
        String path = "/validate.php";
        int port = 80;
        if (validate.length() > 0 && checkResultByPrivate(challenge, validate)) {
            String query = "seccode=" + seccode;
            String response = "";
            try {
                response = postValidate(host, path, query, port);
                gtlog(response);
            } catch (Exception e) {
                e.printStackTrace();
            }

            gtlog("md5: " + md5Encode(seccode));

            if (response.equals(md5Encode(seccode))) {
                return true;
            }
        }
        return false;

    }

    /**
     * Print out log message Use to Debug
     *
     * @param message
     * @time 2014122_151829 by zheng
     */
    public void gtlog(String message) {
//		System.out.println("gtlog: " + message);
    }

    private boolean checkResultByPrivate(String origin, String validate) {
        String encodeStr = md5Encode(privateKey + "geetest" + origin);
        return validate.equals(encodeStr);
    }

    private String postValidate(String host, String path, String data, int port)
            throws Exception {
        String response = "error";
        // data=fixEncoding(data);
        InetAddress addr = InetAddress.getByName(host);
        Socket socket = new Socket(addr, port);
        BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(
                socket.getOutputStream(), "UTF8"));
        wr.write("POST " + path + " HTTP/1.0\r\n");
        wr.write("Host: " + host + "\r\n");
        wr.write("Content-Type: application/x-www-form-urlencoded\r\n");
        wr.write("Content-Length: " + data.length() + "\r\n");
        wr.write("\r\n"); // 以空行作为分割
        // 发送数据
        wr.write(data);
        wr.flush();
        // 读取返回信息
        BufferedReader rd = new BufferedReader(new InputStreamReader(
                socket.getInputStream(), "UTF-8"));
        String line;
        while ((line = rd.readLine()) != null) {
//			System.out.println(line);
            response = line;
        }
        wr.close();
        rd.close();
        socket.close();
        return response;
    }

    /**
     * 转为UTF8编码
     *
     * @param str
     * @return
     * @throws UnsupportedEncodingException
     * @time 2014年7月10日 下午3:29:45
     */
    private String fixEncoding(String str) throws UnsupportedEncodingException {
        String tempStr = new String(str.getBytes("UTF-8"));
        return URLEncoder.encode(tempStr, "UTF-8");
    }

    /**
     * md5 加密
     *
     * @param plainText
     * @return
     * @time 2014年7月10日 下午3:30:01
     */
    public String md5Encode(String plainText) {
        String re_md5 = new String();
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte b[] = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }

            re_md5 = buf.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return re_md5;
    }

}
