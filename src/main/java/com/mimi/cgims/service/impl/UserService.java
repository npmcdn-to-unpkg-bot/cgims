package com.mimi.cgims.service.impl;

import com.mimi.cgims.Constants;
import com.mimi.cgims.dao.IBaseDao;
import com.mimi.cgims.dao.IPermissionDao;
import com.mimi.cgims.dao.IRoleDao;
import com.mimi.cgims.dao.IUserDao;
import com.mimi.cgims.model.PermissionModel;
import com.mimi.cgims.model.RoleModel;
import com.mimi.cgims.model.UserModel;
import com.mimi.cgims.service.IUserService;
import com.mimi.cgims.util.FormatUtil;
import com.mimi.cgims.util.MD5Util;
import com.mimi.cgims.util.ResultUtil;
import com.mimi.cgims.util.page.PageUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class UserService extends BaseService<UserModel,String> implements IUserService{
    private IUserDao userDao;

    @Resource
    private IRoleDao roleDao;
    @Resource
    private IPermissionDao permissionDao;
    @Resource
    @Override
    public void setBaseDao(IBaseDao<UserModel, String> baseDao) {
        this.baseDao = baseDao;
        this.userDao = (IUserDao)baseDao;
    }

    @Override
    protected void initAction(){
        int count = userDao.count();
        if (count == 0) {
            UserModel user = new UserModel();
            user.setLoginName(Constants.USER_LOGIN_NAME_ADMIN);
            user.setName(Constants.USER_NAME_ADMIN);
            user.setDescription(Constants.USER_DESCRIPTION_ADMIN);
            List<RoleModel> roles = roleDao.list(null,Constants.ROLE_NAME_ADMIN,1,1);
            user.setRoles(roles);
            user.setPassword(buildPassword(Constants.USER_PASSWORD));
            userDao.add(user);
        }
    }

    @Override
    public UserModel getWithRoles(String id) {
        UserModel user = get(id);
        List<RoleModel> roles = roleDao.list(id,null, PageUtil.BEGIN_PAGE,PageUtil.MAX_PAGE_SIZE);
        user.setRoles(roles);
        return user;
    }

    @Override
    public Map<String,Object> list4Page(String searchKeyword, int targetPage, int pageSize) {
        int total = userDao.count(searchKeyword);
        targetPage = PageUtil.fitPage(total, targetPage, pageSize);
        List<UserModel> list = userDao.list(searchKeyword, targetPage, pageSize);
        int totalPage = PageUtil.getTotalPage(total, pageSize);
        return ResultUtil.getResultMap(total, totalPage, targetPage, pageSize, list);
    }

    @Override
    public String computePwd(String password) {
        return buildPassword(password);
    }

    @Override
    public String checkAdd(UserModel user) {
        List<String> errors = commonCheck(user,true);
        if(errors.isEmpty()) {
            return null;
        }
        return errors.get(0);
    }

    private List<String> commonCheck(UserModel user,boolean isAdd){
        List<String> errors = new ArrayList<>();
        String error;
        if(user == null){
            errors.add("内容为空");
        }
        error = FormatUtil.checkFormat(user.getLoginName(),FormatUtil.REGEX_COMMON_NAME,true,0,FormatUtil.MAX_LENGTH_COMMON_SHORT_L3,"登录名");
        if(StringUtils.isNotBlank(error)){
            errors.add(error);
        }
        error = FormatUtil.checkLengthOnly(user.getName(),0,FormatUtil.MAX_LENGTH_COMMON_SHORT_L3,"姓名");
        if(StringUtils.isNotBlank(error)){
            errors.add(error);
        }
        error = FormatUtil.checkFormat(user.getPhoneNum(),FormatUtil.REGEX_COMMON_PHONENUM,false,"电话");
        if(StringUtils.isNotBlank(error)){
            errors.add(error);
        }
        error = FormatUtil.checkFormat(user.getIdentity(),FormatUtil.REGEX_COMMON_IDENTITY,false,"身份证");
        if(StringUtils.isNotBlank(error)){
            errors.add(error);
        }
        error = FormatUtil.checkFormat(user.getPassword(),FormatUtil.REGEX_NO_NEED,isAdd,FormatUtil.MIN_LENGTH_COMMON,FormatUtil.MAX_LENGTH_COMMON_SHORT_L3,"密码");
        if(StringUtils.isNotBlank(error)){
            errors.add(error);
        }
        error = FormatUtil.checkLengthOnly(user.getDescription(),0,FormatUtil.MAX_LENGTH_COMMON_NORMAL_L2,"描述");
        if(StringUtils.isNotBlank(error)){
            errors.add(error);
        }
        if(errors.isEmpty()){
            UserModel tu = userDao.getByLoginName(user.getLoginName());
            if(tu!=null && !tu.getId().equals(user.getId())){
                errors.add("登录名已存在");
            }
        }
        return errors;
    }

    @Override
    public String checkUpdate(UserModel user) {
        List<String> errors = commonCheck(user,false);
        if(errors.isEmpty()) {
            return null;
        }
        return errors.get(0);
    }

    @Override
    public String login(HttpServletRequest request, String loginName, String password) {
        String error;
        error = FormatUtil.checkFormat(loginName,FormatUtil.REGEX_COMMON_NAME,true,0,FormatUtil.MAX_LENGTH_COMMON_SHORT_L3,"登录名");
        if(StringUtils.isNotBlank(error)){
            return error;
        }
        error = FormatUtil.checkFormat(password,FormatUtil.REGEX_NO_NEED,true,FormatUtil.MIN_LENGTH_COMMON,FormatUtil.MAX_LENGTH_COMMON_SHORT_L3,"密码");
        if(StringUtils.isNotBlank(error)){
            return error;
        }
        UserModel user = userDao.getByLoginName(loginName);
        if(user==null){
            return "找不到该用户";
        }
        if(!user.getPassword().equals(buildPassword(password))){
            return "密码错误";
        }
        List<RoleModel> roles = roleDao.list(user.getId(),null,PageUtil.BEGIN_PAGE,PageUtil.MAX_PAGE_SIZE);
        List<PermissionModel> permissions = new ArrayList<>();
        String permissionCodes = "";
        for(RoleModel role:roles){
            List<PermissionModel> tps = permissionDao.list(role.getId(),null,PageUtil.BEGIN_PAGE,PageUtil.MAX_PAGE_SIZE);
            for(PermissionModel tp:tps){
                if(!permissions.contains(tp)){
                    if(StringUtils.isNotBlank(permissionCodes)){
                        permissionCodes+=Constants.SPLIT_STRING_IDS;
                    }
                    permissionCodes+=tp.getCode();
                    permissions.add(tp);
                }
            }
        }
        request.getSession().setAttribute(Constants.COOKIE_NAME_USER_ID,user.getId());
        request.getSession().setAttribute(Constants.COOKIE_NAME_LOGIN_NAME,user.getLoginName());
        request.getSession().setAttribute(Constants.COOKIE_NAME_USER_NAME,user.getName());
        request.getSession().setAttribute(Constants.COOKIE_NAME_PERMISSION_CODES,permissionCodes);
        return null;
    }

    @Override
    public void logout(HttpServletRequest request) {
        request.getSession().removeAttribute(Constants.COOKIE_NAME_USER_ID);
        request.getSession().removeAttribute(Constants.COOKIE_NAME_LOGIN_NAME);
        request.getSession().removeAttribute(Constants.COOKIE_NAME_USER_NAME);
        request.getSession().removeAttribute(Constants.COOKIE_NAME_PERMISSION_CODES);
    }

    private String buildPassword(String password){
        return MD5Util.MD5(MD5Util.MD5(password+Constants.PROJECT_NAME));
    }
}
