package com.mimi.cgims.service.impl;

import com.mimi.cgims.Constants;
import com.mimi.cgims.dao.IBaseDao;
import com.mimi.cgims.dao.IPermissionDao;
import com.mimi.cgims.dao.IRoleDao;
import com.mimi.cgims.model.PermissionModel;
import com.mimi.cgims.model.RoleModel;
import com.mimi.cgims.service.IRoleService;
import com.mimi.cgims.util.FormatUtil;
import com.mimi.cgims.util.ResultUtil;
import com.mimi.cgims.util.page.PageUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class RoleService extends BaseService<RoleModel, String> implements IRoleService {
    private IRoleDao roleDao;

    @Resource
    private IPermissionDao permissionDao;

    @Resource
    @Override
    public void setBaseDao(IBaseDao<RoleModel, String> baseDao) {
        this.baseDao = baseDao;
        this.roleDao = (IRoleDao) baseDao;
    }

    @Override
    protected void initAction() {
//        int count = roleDao.count();
//        if (count == 0) {
            String[] roleNames = Constants.ROLE_NAMES;
            String[] roleDescs = Constants.ROLE_DESCRIPTIONS;
            String[][] rolePermissions = Constants.ROLE_PERMISSIONS;
            List<PermissionModel> permissions = permissionDao.list();
            for (int i = 0; i < roleNames.length; i++) {
                RoleModel role = new RoleModel();
                role.setName(roleNames[i]);
                role.setDescription(roleDescs[i]);
                List<PermissionModel> tempList = new ArrayList<>();
                for (String permissionCode : rolePermissions[i]) {
                    for (PermissionModel permissionModel : permissions) {
                        if (permissionCode.equals(permissionModel.getCode())) {
                            tempList.add(permissionModel);
                            break;
                        }
                    }
                }
                role.setPermissions(tempList);
                String id = roleDao.add(role);
                if(roleNames[i].equals(Constants.ROLE_NAME_ADMIN)){
                    Constants.ROLE_ADMIN_ID = id;
                }
            }
//        }
    }

    @Override
    public RoleModel getWithPermissions(String id) {
        RoleModel role = roleDao.get(id);
        List<PermissionModel> permissionModels = permissionDao.list(id,null, PageUtil.BEGIN_PAGE, PageUtil.MAX_PAGE_SIZE);
        role.setPermissions(permissionModels);
        return role;
    }

    @Override
    public String checkAdd(RoleModel role) {
        List<String> errors = commonCheck(role);
        if(errors.isEmpty()) {
            return null;
        }
        return errors.get(0);
    }

    @Override
    public String checkUpdate(RoleModel role) {
        List<String> errors = commonCheck(role);
        if(errors.isEmpty()) {
            return null;
        }
        return errors.get(0);
    }

    @Override
    public RoleModel getByName(String name) {
        return roleDao.getByName(name);
    }

    private List<String> commonCheck(RoleModel role){
        List<String> errors = new ArrayList<>();
        String error;
        if(role == null){
            errors.add("内容为空");
            return errors;
        }
         error = FormatUtil.checkFormat(role.getName(),FormatUtil.REGEX_COMMON_NAME,true,0,FormatUtil.MAX_LENGTH_COMMON_SHORT_L3,"角色名称");
        if(StringUtils.isNotBlank(error)){
            errors.add(error);
        }
         error = FormatUtil.checkLengthOnly(role.getDescription(),0,FormatUtil.MAX_LENGTH_COMMON_NORMAL_L2,"描述");
        if(StringUtils.isNotBlank(error)){
            errors.add(error);
        }
        if(errors.isEmpty()){
            RoleModel tr = roleDao.getByName(role.getName());
//            List<RoleModel> list = roleDao.list(null,role.getName(),PageUtil.BEGIN_PAGE,1);
//            if(list!=null && !list.isEmpty() && !list.get(0).getId().endsWith(role.getId())){
            if(tr!=null && !tr.getId().equals(role.getId())){
                errors.add("角色名已存在");
            }
        }
        return errors;
    }

    @Override
    public Map<String,Object> list4Page(String searchKeyword, int targetPage, int pageSize) {
        int total = roleDao.count(null,searchKeyword);
        targetPage = PageUtil.fitPage(total, targetPage, pageSize);
        List<RoleModel> list = roleDao.list(null,searchKeyword, targetPage, pageSize);
        int totalPage = PageUtil.getTotalPage(total, pageSize);
        return ResultUtil.getResultMap(total, totalPage, targetPage, pageSize, list);
    }
}
