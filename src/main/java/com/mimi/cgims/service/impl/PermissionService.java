package com.mimi.cgims.service.impl;

import com.mimi.cgims.Constants;
import com.mimi.cgims.dao.IBaseDao;
import com.mimi.cgims.dao.IPermissionDao;
import com.mimi.cgims.dao.IRoleDao;
import com.mimi.cgims.model.PermissionModel;
import com.mimi.cgims.model.RoleModel;
import com.mimi.cgims.service.IPermissionService;
import com.mimi.cgims.util.ResultUtil;
import com.mimi.cgims.util.page.PageUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class PermissionService extends BaseService<PermissionModel, String> implements IPermissionService {

    private IPermissionDao permissionDao;

    @Resource
    private IRoleDao roleDao;

    @Resource
    @Override
    public void setBaseDao(IBaseDao<PermissionModel, String> baseDao) {
        this.baseDao = baseDao;
        this.permissionDao = (IPermissionDao) baseDao;
    }

    @Override
    protected void initAction() {
//        int count = permissionDao.count();
//        if(count == 0){
        Map<String, String> map = Constants.getPermissionMap();
        Map<String, String> desc = Constants.getPermissionDescMap();
        for (String key : map.keySet()) {
            PermissionModel pm = new PermissionModel();
            pm.setCode(key);
            pm.setName(map.get(key));
            pm.setDescription(desc.get(key));
            permissionDao.add(pm);
        }
//        }
    }

    @Override
    public List<PermissionModel> list(String searchKeyword, int targetPage, int pageSize) {
        return permissionDao.list(null, searchKeyword, targetPage, pageSize);
    }

    @Override
    public int count(String searchKeyword) {
        return permissionDao.count(null, searchKeyword);
    }

    @Override
    public Map<String, Object> list4Page(String searchKeyword, int targetPage, int pageSize) {
        int total = permissionDao.count(null, searchKeyword);
        targetPage = PageUtil.fitPage(total, targetPage, pageSize);
        List<PermissionModel> list = permissionDao.list(null, searchKeyword, targetPage, pageSize);
        int totalPage = PageUtil.getTotalPage(total, pageSize);
        return ResultUtil.getResultMap(total, totalPage, targetPage, pageSize, list);
    }

    @Override
    public List<PermissionModel> listByUserId(String id) {
        List<RoleModel> roles = roleDao.list(id, null, PageUtil.BEGIN_PAGE, PageUtil.MAX_PAGE_SIZE);
        List<PermissionModel> permissions = new ArrayList<>();
        for (RoleModel role : roles) {
            List<PermissionModel> tps = permissionDao.list(role.getId(), null, PageUtil.BEGIN_PAGE, PageUtil.MAX_PAGE_SIZE);
            for (PermissionModel tp : tps) {
                if (!permissions.contains(tp)) {
                    permissions.add(tp);
                }
            }
        }
        return permissions;
    }
}
