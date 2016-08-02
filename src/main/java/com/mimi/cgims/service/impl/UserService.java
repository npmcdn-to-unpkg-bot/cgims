package com.mimi.cgims.service.impl;

import com.mimi.cgims.Constants;
import com.mimi.cgims.dao.IBaseDao;
import com.mimi.cgims.dao.IOrderDao;
import com.mimi.cgims.dao.IRoleDao;
import com.mimi.cgims.dao.IUserDao;
import com.mimi.cgims.model.OrderModel;
import com.mimi.cgims.model.RoleModel;
import com.mimi.cgims.model.UserModel;
import com.mimi.cgims.service.IUserService;
import com.mimi.cgims.util.*;
import com.mimi.cgims.util.page.PageUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
public class UserService extends BaseService<UserModel, String> implements IUserService {
    private IUserDao userDao;

    @Resource
    private IRoleDao roleDao;

    @Resource
    private IOrderDao orderDao;

    @Resource
    @Override
    public void setBaseDao(IBaseDao<UserModel, String> baseDao) {
        this.baseDao = baseDao;
        this.userDao = (IUserDao) baseDao;
    }

    @Override
    protected void initAction() {
        UserModel user = new UserModel();
        user.setLoginName(Constants.USER_LOGIN_NAME_ADMIN);
        user.setName(Constants.USER_NAME_ADMIN);
        user.setDescription(Constants.USER_DESCRIPTION_ADMIN);
        List<RoleModel> roles = roleDao.list();
        List<RoleModel> tr = new ArrayList<>();
        for (RoleModel role : roles) {
            if (Constants.ROLE_NAME_ADMIN.equals(role.getName())) {
                tr.add(role);
            }
        }
        user.setRoles(tr);
        user.setPassword(LoginUtil.buildPassword(Constants.USER_PASSWORD));
        userDao.add(user);

    }

    @Override
    public void initTestData() {
        int count = userDao.count();
        if (count == 1) {
            List<RoleModel> roles = roleDao.list();
            for (int i = 0; i < 30; i++) {
                List<RoleModel> tr = new ArrayList<>();
                for (RoleModel role : roles) {
                    if (Math.random() > 0.5) {
                        tr.add(role);
                    }
                }
                UserModel user = new UserModel();
                user.setLoginName("loginName" + i);
                user.setName("名称" + i);
                user.setDescription("描述" + i);
                user.setPhoneNum("14121321213");
                user.setIdentity("442132132121321213");
                user.setRoles(tr);
                user.setPassword(LoginUtil.buildPassword("123123"));
                userDao.add(user);
            }
            List<UserModel> users = userDao.list();
            Random random = new Random();
            for(UserModel user:users){
                List<UserModel> slaves = new ArrayList<>();
                for(UserModel slave:users){
                    if(random.nextBoolean()){
                        slaves.add(slave);
                    }
                }
                user.setSlaves(slaves);
                userDao.update(user);
            }
        }
    }


    @Override
    public UserModel getWithDatas(String id) {
        UserModel user = get(id);
//        List<RoleModel> roles = roleDao.list(id, null, PageUtil.BEGIN_PAGE, PageUtil.MAX_PAGE_SIZE);
//        user.setRoles(roles);

//        List<RoleModel> roles = user.getRoles();
//        List<UserModel> slaves = user.getSlaves();
//        DaoUtil.cleanLazyDataUsers(slaves);
//        DaoUtil.cleanLazyDataRoles(roles);

        List<RoleModel> roles = new ArrayList<>();
        List<UserModel> slaves = new ArrayList<>();
        if(ListUtil.isNotEmpty(user.getRoles())){
            for(RoleModel role:user.getRoles()){
                RoleModel nr = new RoleModel();
                nr.setId(role.getId());
                nr.setName(role.getName());
                roles.add(nr);
            }
        }
        if(ListUtil.isNotEmpty(user.getSlaves())){
            for(UserModel slave:user.getSlaves()){
                UserModel ns = new UserModel();
                ns.setId(slave.getId());
                ns.setName(slave.getName());
                slaves.add(ns);
            }
        }
        DaoUtil.cleanLazyData(user);
        user.setRoles(roles);
        user.setSlaves(slaves);
        return user;
    }

    @Override
    public Map<String, Object> list4Page(String searchKeyword, int targetPage, int pageSize) {
        int total = userDao.count(searchKeyword);
        targetPage = PageUtil.fitPage(total, targetPage, pageSize);
        List<UserModel> list = userDao.list(searchKeyword, targetPage, pageSize);
        int totalPage = PageUtil.getTotalPage(total, pageSize);
        return ResultUtil.getResultMap(total, totalPage, targetPage, pageSize, list);
    }

    @Override
    public String checkAdd(UserModel user) {
        List<String> errors = commonCheck(user, true);
        if (errors.isEmpty()) {
            return null;
        }
        return errors.get(0);
    }

    private List<String> commonCheck(UserModel user, boolean isAdd) {
        List<String> errors = new ArrayList<>();
        String error;
        if (user == null) {
            errors.add("内容为空");
            return errors;
        }
        error = FormatUtil.checkFormat(user.getLoginName(), FormatUtil.REGEX_COMMON_NAME, true, 0, FormatUtil.MAX_LENGTH_COMMON_SHORT_L3, "登录名");
        if (StringUtils.isNotBlank(error)) {
            errors.add(error);
        }
        error = FormatUtil.checkLengthOnly(user.getName(), 0, FormatUtil.MAX_LENGTH_COMMON_SHORT_L3, "姓名");
        if (StringUtils.isNotBlank(error)) {
            errors.add(error);
        }
        error = FormatUtil.checkFormat(user.getPhoneNum(), FormatUtil.REGEX_COMMON_PHONENUM, false, "电话");
        if (StringUtils.isNotBlank(error)) {
            errors.add(error);
        }
        error = FormatUtil.checkFormat(user.getIdentity(), FormatUtil.REGEX_COMMON_IDENTITY, false, "身份证");
        if (StringUtils.isNotBlank(error)) {
            errors.add(error);
        }
        error = FormatUtil.checkFormat(user.getPassword(), FormatUtil.REGEX_NO_NEED, isAdd, FormatUtil.MIN_LENGTH_COMMON, FormatUtil.MAX_LENGTH_COMMON_SHORT_L3, "密码");
        if (StringUtils.isNotBlank(error)) {
            errors.add(error);
        }
        error = FormatUtil.checkLengthOnly(user.getDescription(), 0, FormatUtil.MAX_LENGTH_COMMON_NORMAL_L2, "描述");
        if (StringUtils.isNotBlank(error)) {
            errors.add(error);
        }
        if (errors.isEmpty()) {
            UserModel tu = userDao.getByLoginName(user.getLoginName());
            if (tu != null && !tu.getId().equals(user.getId())) {
                errors.add("登录名已存在");
            }
        }
        return errors;
    }

    @Override
    public String checkUpdate(UserModel user) {
        List<String> errors = commonCheck(user, false);
        if (errors.isEmpty()) {
            return null;
        }
        return errors.get(0);
    }

    @Override
    public String login(UserModel user) {
        String loginName = user.getLoginName();
        String password = user.getPassword();
        String error;
        error = FormatUtil.checkFormat(loginName, FormatUtil.REGEX_COMMON_NAME, true, 0, FormatUtil.MAX_LENGTH_COMMON_SHORT_L3, "登录名");
        if (StringUtils.isNotBlank(error)) {
            return error;
        }
        error = FormatUtil.checkFormat(password, FormatUtil.REGEX_NO_NEED, true, FormatUtil.MIN_LENGTH_COMMON, FormatUtil.MAX_LENGTH_COMMON_SHORT_L3, "密码");
        if (StringUtils.isNotBlank(error)) {
            return error;
        }
        UserModel tu = userDao.getByLoginName(loginName);
        if (tu == null) {
            return "找不到该用户";
        }
        if (!tu.getPassword().equals(LoginUtil.buildPassword(password))) {
            return "密码错误";
        }
        user.setId(tu.getId());
        user.setName(tu.getName());
        return null;
    }

    @Override
    public void delete(String id){
        orderDao.cleanUserId(id);
        super.delete(id);
    }

    @Override
    public void batchDelete(String ...ids){
        for(String id:ids){
            delete(id);
        }
    }
}
