package com.mimi.cgims.web.controller;

import com.cloopen.rest.sdk.CCPRestSmsSDK;
import com.mimi.cgims.Constants;
import com.mimi.cgims.model.WorkmanModel;
import com.mimi.cgims.service.IWorkmanService;
import com.mimi.cgims.util.LoginUtil;
import com.mimi.cgims.util.ResultUtil;
import com.mimi.cgims.web.captcha.GeetestLib;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@Controller
public class WorkmanController {
    @Resource
    private CCPRestSmsSDK ytxAPI;
    @Resource
    private GeetestLib geetest;
    @Resource
    private IWorkmanService workmanService;

    @RequestMapping(value = "/workman", method = RequestMethod.GET)
    @ResponseBody
    public Object search(String searchKeyword, String province, String city, String area, String serviceType, @RequestParam(defaultValue = "1") Integer curPage, @RequestParam(defaultValue = "10") Integer pageSize) {
        return workmanService.list4Page(searchKeyword, province, city, area, serviceType, curPage, pageSize);
    }

    @RequestMapping(value = {"/workman/self/{id}","/workman/{id}"}, method = RequestMethod.GET)
    @ResponseBody
    public Object get(@PathVariable String id) {
        return ResultUtil.getResultMap(ResultUtil.RESULT_SUCCESS, null, workmanService.get(id));
    }

    @RequestMapping(value = "/workman", method = RequestMethod.POST)
    @ResponseBody
    public Object add(WorkmanModel workman) {
        String error = workmanService.checkAdd(workman);
        if (StringUtils.isNotBlank(error)) {
            return ResultUtil.getFailResultMap(error);
        }
        workmanService.add(workman);
        return ResultUtil.getSuccessResultMap();
    }


    @RequestMapping(value = {"/workman/self/{id}","/workman/{id}"}, method = RequestMethod.PATCH)
    @ResponseBody
    public Object get(@PathVariable String id, WorkmanModel workman) {
        String error = workmanService.checkUpdate(workman);
        if (StringUtils.isNotBlank(error)) {
            return ResultUtil.getFailResultMap(error);
        }
        WorkmanModel newModel = workmanService.get(id);
        BeanUtils.copyProperties(workman, newModel, "id", "workmanNumber");
        workmanService.update(newModel);
        return ResultUtil.getSuccessResultMap();
    }

    @RequestMapping(value = "/workman/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public Object delete(@PathVariable String id) {
        workmanService.delete(id);
        return ResultUtil.getResultMap(ResultUtil.RESULT_SUCCESS
                , null, id);
    }


//    @RequestMapping(value = "/workman/self/{id}", method = RequestMethod.GET)
//    @ResponseBody
//    public Object selfGet(@PathVariable String id) {
//        return ResultUtil.getResultMap(ResultUtil.RESULT_SUCCESS, null, workmanService.get(id));
//    }


    @RequestMapping(value = "/workman/phoneCaptcha", method = RequestMethod.GET)
    public @ResponseBody Object getPhoneCaptcha(HttpServletRequest request,
                                                String phoneNum) {
        boolean result = geetest.validateRequest(request);
        String err = "";
        if (result) {
            int rNum = (int) (Math.random() * 999999);
            String rNumStr = String.valueOf(rNum);
            while (rNumStr.length() < 6) {
                rNumStr = "0" + rNumStr;
            }
            HashMap<String, Object> receiveMap = ytxAPI.sendTemplateSMS(
                    phoneNum, "27380", new String[] { rNumStr, "15" });
            if ("000000".equals(receiveMap.get("statusCode"))) {
                request.getSession().setAttribute(Constants.ACCESS_PHONE_NUM,
                        phoneNum);
                request.getSession().setAttribute(
                        Constants.ACCESS_PHONE_CAPTCHA, rNumStr);
            } else {
                result = false;
                err ="发送验证码出错，请稍后重试";
            }

            // request.getSession().setAttribute(Constants.ACCESS_PHONE_NUM,
            // phoneNum);
            // request.getSession().setAttribute(Constants.ACCESS_PHONE_CAPTCHA,
            // "414141");
        } else {
            err = Constants.SMOOTH_CAPTCHA_ERROR;
        }
        int status = result?ResultUtil.RESULT_SUCCESS:ResultUtil.RESULT_FAIL;
        return ResultUtil.getResultMap(status,err);
    }
}
