package com.mimi.cgims.web.controller;

import com.cloopen.rest.sdk.CCPRestSmsSDK;
import com.geetest.sdk.java.GeetestLib;
import com.mimi.cgims.Config;
import com.mimi.cgims.Constants;
import com.mimi.cgims.model.WorkmanModel;
import com.mimi.cgims.service.IAliyunOSSService;
import com.mimi.cgims.service.IWorkmanService;
import com.mimi.cgims.service.impl.AliyunOSSService;
import com.mimi.cgims.util.*;
import net.sf.json.JSONException;
import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@Controller
public class WorkmanController {
    @Resource
    private CCPRestSmsSDK ytxAPI;
    @Resource
    private IWorkmanService workmanService;
    @Resource
    private IAliyunOSSService aliyunOSSService;
    @Resource
    private Config config;

    private String[] ignores = {"id", "workmanNumber", "cooperateTimes", "score"};

    @RequestMapping(value = "/html/workman/login", method = {RequestMethod.GET})
    public String workmanLogin(HttpServletRequest request) {
        return "workmanLogin";
    }

    @RequestMapping(value = "/html/workman/self/{id}", method = {RequestMethod.GET})
    public String workmanSelf(@PathVariable String id, HttpServletRequest request) {
        WorkmanModel workman = workmanService.get(id);
        request.setAttribute("workman",workman);
        request.setAttribute("provinces",CityUtil.provinces);
        return "workmanSelf";
    }


    @RequestMapping(value = "/workman/login", method = RequestMethod.POST)
    @ResponseBody
    public Object login(HttpServletRequest request,String phoneNum,String captchaText) {
        if(LoginUtil.checkPhoneCaptcha(request,phoneNum,captchaText)){
            WorkmanModel workman = workmanService.getByPhoneNum(phoneNum);
            if(workman==null){
                workman = new WorkmanModel();
                workman.setWorkmanNumber(AutoNumUtil.getWorkmanNum());
                workman.setPhoneNum(phoneNum);
                workmanService.add(workman);
            }
            LoginUtil.workmanLogin(request,workman);
            return ResultUtil.getSuccessResultMap(workman.getId());
        }else{
            return ResultUtil.getFailResultMap("验证码错误");
        }
    }

    @RequestMapping(value = "/workman/logout", method = RequestMethod.POST)
    @ResponseBody
    public Object logout(HttpServletRequest request) {
        LoginUtil.workmanLogout(request);
        return ResultUtil.getSuccessResultMap();
    }


    @RequestMapping(value = "/workman", method = RequestMethod.GET)
    @ResponseBody
    public Object search(String searchKeyword, String province, String city, String area, String serviceType, @RequestParam(defaultValue = "1") Integer curPage, @RequestParam(defaultValue = "10") Integer pageSize) {
        return workmanService.list4Page(searchKeyword, province, city, area, serviceType, curPage, pageSize);
    }

    @RequestMapping(value = {"/workman/self/{id}", "/workman/{id}"}, method = RequestMethod.GET)
    @ResponseBody
    public Object get(@PathVariable String id) {
        return ResultUtil.getSuccessResultMap(workmanService.get(id));
    }

    @RequestMapping(value = "/workman", method = RequestMethod.POST)
    @ResponseBody
    public Object add(WorkmanModel workman) {
        workman.setWorkmanNumber(AutoNumUtil.getWorkmanNum());
        String error = workmanService.checkAdd(workman);
        if (StringUtils.isNotBlank(error)) {
            return ResultUtil.getFailResultMap(error);
        }
        workmanService.add(workman);
        return ResultUtil.getSuccessResultMap();
    }


    @RequestMapping(value = {"/workman/self/{id}", "/workman/{id}"}, method = RequestMethod.POST)
    @ResponseBody
    public Object update(@PathVariable String id, WorkmanModel workman) {
        WorkmanModel newModel = workmanService.get(id);
        BeanUtils.copyProperties(workman, newModel, ignores);
        String error = workmanService.checkUpdate(newModel);
        if (StringUtils.isNotBlank(error)) {
            return ResultUtil.getFailResultMap(error);
        }
        workmanService.update(newModel);
        return ResultUtil.getSuccessResultMap();
    }

//    @RequestMapping(value = "/workman/{id}", method = RequestMethod.DELETE)
//    @ResponseBody
//    public Object delete(@PathVariable String id) {
//        workmanService.delete(id);
//        return ResultUtil.getResultMap(ResultUtil.RESULT_SUCCESS
//                , null, id);
//    }


//    @RequestMapping(value = "/workman/self/{id}", method = RequestMethod.GET)
//    @ResponseBody
//    public Object selfGet(@PathVariable String id) {
//        return ResultUtil.getResultMap(ResultUtil.RESULT_SUCCESS, null, workmanService.get(id));
//    }


    @RequestMapping(value = "/workman/phoneCaptcha", method = RequestMethod.POST)
    public
    @ResponseBody
    Object getPhoneCaptcha(HttpServletRequest request,
                           String phoneNum) {
            if(!GeetestUtil.valid(request)){
                return ResultUtil.getFailResultMap(Constants.SMOOTH_CAPTCHA_ERROR);
            }
        if (!FormatUtil.checkValueFormat(phoneNum, FormatUtil.REGEX_COMMON_PHONENUM, true)) {
            return ResultUtil.getFailResultMap("手机号码格式有误");
        }
        int rNum = (int) (Math.random() * 999999);
        String rNumStr = String.valueOf(rNum);
        while (rNumStr.length() < 6) {
            rNumStr = "0" + rNumStr;
        }
//        HashMap<String, Object> receiveMap = ytxAPI.sendTemplateSMS(
//                phoneNum, config.getYtxTemplateId(), new String[]{rNumStr, "15"});
//        if ("000000".equals(receiveMap.get("statusCode"))) {
//            request.getSession().setAttribute(Constants.ACCESS_PHONE_NUM,
//                    phoneNum);
//            request.getSession().setAttribute(
//                    Constants.ACCESS_PHONE_CAPTCHA, rNumStr);
//        } else {
//            return ResultUtil.getFailResultMap("发送验证码出错，请稍后重试");
//        }
        String captcha = "414141";
        LoginUtil.initPhoneCaptcha(request,phoneNum,captcha);
        return ResultUtil.getSuccessResultMap();
    }


    @RequestMapping(value = "/workman/initCaptcha", method = RequestMethod.GET)
    public
    @ResponseBody
    Object initCaptcha(HttpServletRequest request) {
        return GeetestUtil.getInitData(request);
    }

    @RequestMapping(value = {
            "/workman/upload/headImg"}, method = { RequestMethod.POST })
    public @ResponseBody
    Object uploadHeadImg(HttpServletRequest request,
                  @RequestParam("theFile") MultipartFile theFile) {
        return uploadAction(theFile,AliyunOSSService.ALIYUN_OSS_IMAGE_PARAMS_TYPE_HEAD_IMG);
    }

    @RequestMapping(value = {
            "/workman/upload/idCardBack",
            "/workman/upload/idCardFace"}, method = { RequestMethod.POST })
    public @ResponseBody
    Object upload(HttpServletRequest request,
                  @RequestParam("theFile") MultipartFile theFile) {
        return uploadAction(theFile,AliyunOSSService.ALIYUN_OSS_IMAGE_PARAMS_TYPE_IDENTITY);
    }
    private Map<String,Object> uploadAction(MultipartFile theFile,String returnSize){
        try {
            String path = aliyunOSSService.saveFileToServer(theFile);
            path = aliyunOSSService.addImgParams(path,
                    returnSize);
            return ResultUtil.getSuccessResultMap(path);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.getFailResultMap("保存图片失败");
        }
    }

}
