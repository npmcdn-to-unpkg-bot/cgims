package com.mimi.cgims.web.controller;

import com.mimi.cgims.Constants;
import com.mimi.cgims.common.BaseJunit4Test;
import com.mimi.cgims.model.PermissionModel;
import com.mimi.cgims.model.RoleModel;
import com.mimi.cgims.model.UserModel;
import com.mimi.cgims.util.ListUtil;
import com.mimi.cgims.util.ResultUtil;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RoleControllerTest extends BaseJunit4Test {
    @Test
    public void search() throws Exception {
        Map<String, Object> roleMap = (Map<String, Object>) roleController.search("", 1, 1000);
        Map<String, Object> roleMap2 = (Map<String, Object>) roleController.search("财", 1, 1000);
        List<RoleModel> roles = (List<RoleModel>) ResultUtil.getDatas(roleMap);
        List<RoleModel> roles2 = (List<RoleModel>) ResultUtil.getDatas(roleMap2);

        assertContain(roles, false, false);
        assertTrue(roles.size() != roles2.size());
    }

    private void assertContain(RoleModel role, boolean cu, boolean cp) {
        List<RoleModel> list = new ArrayList<>();
        list.add(role);
        assertContain(list, cu, cp);
    }


    private void assertContain(List<RoleModel> roles, boolean cu, boolean cp) {
        boolean containUser = false;
        boolean containPermission = false;
        for (RoleModel role : roles) {
            containUser = containUser || ListUtil.isNotEmpty(role.getUsers());
            containPermission = containPermission || ListUtil.isNotEmpty(role.getPermissions());
        }
        assertEquals(containUser, cu);
        assertEquals(containPermission, cp);
    }

    @Test
    public void get() throws Exception {
        List<RoleModel> roles = roleDao.list();
        List<RoleModel> nRoles = new ArrayList<>();
        for (RoleModel role : roles) {
            nRoles.add((RoleModel) ResultUtil.getResult((Map<String, Object>) roleController.get(role.getId())));
        }
        assertContain(nRoles, false, true);
    }

    @Test
    public void add() throws Exception {
        List<PermissionModel> permissions = permissionDao.list();
        List<PermissionModel> rPermissions = randomList(permissions);
        String permissionIds = ListUtil.buildIds(rPermissions);
        String name = "testRoleName";
        String description = "testDescription";
        for (int i = 0; i < 10; i++) {
            RoleModel role = new RoleModel();
            role.setDescription(description + i);
            if (i % 2 == 0) {
                role.setName(name + i);
            } else {
                role.setName(Constants.ROLE_NAME_ADMIN);
            }
            if (i > 6) {
                role = null;
            }
            Object result;
            RoleModel nRole;
            if (i % 3 == 0) {
                result = roleController.add(role, permissionIds);
            } else {
                result = roleController.add(role, null);
            }
            switch (i) {
                case 2:
                case 4:
                    nRole = roleDao.get((String) ResultUtil.getResult((Map<String, Object>) result));
                    assertResultSuccess(result);
                    assertEquals(name+i,nRole.getName());
                    assertEquals(description+i,nRole.getDescription());
                    assertEquals(0,nRole.getPermissions().size());
                    break;
                case 0:
                case 6:
                    nRole = roleDao.get((String) ResultUtil.getResult((Map<String, Object>) result));
                    assertResultSuccess(result);
                    assertEquals(name+i,nRole.getName());
                    assertEquals(description+i,nRole.getDescription());
                    assertEquals(rPermissions.size(),nRole.getPermissions().size());
                    break;
                default:
                    assertResultFail(result);
            }
        }
    }

    @Test
    public void update() throws Exception {
        List<RoleModel> roles = roleDao.list();
        List<PermissionModel> permissions = permissionDao.list();
        List<PermissionModel> rPermissions = randomList(permissions);
        String permissionIds = ListUtil.buildIds(rPermissions);
        String name = "testRoleName";
        String description = "testDescription";
        for(RoleModel role:roles){
            if(Constants.ROLE_NAME_ADMIN.equals(role.getName())){
                roles.remove(role);
                roles.add(role);
                break;
            }
        }
        for (int i = 0; i < 4; i++) {
            RoleModel oldRole = roles.get(i);
            String oldId = oldRole.getId();
            String oldName = oldRole.getName();
            RoleModel role = new RoleModel();
            role.setDescription(description + i);
            if (i % 2 == 0) {
                role.setName(name + i);
            } else {
                role.setName(Constants.ROLE_NAME_ADMIN);
            }
            Object result;
            RoleModel nRole;
            if(i<2){
                result = roleController.update(oldId,role, permissionIds);
            } else {
                result = roleController.update(oldId,role, null);
            }
            switch (i) {
                case 0:
                    nRole = roleDao.get(oldId);
                    assertResultSuccess(result);
                    assertEquals(name+i,nRole.getName());
                    assertEquals(description+i,nRole.getDescription());
                    assertEquals(rPermissions.size(),nRole.getPermissions().size());
                    break;
                case 2:
                    nRole = roleDao.get(oldId);
                    assertResultSuccess(result);
                    assertEquals(name+i,nRole.getName());
                    assertEquals(description+i,nRole.getDescription());
                    assertEquals(0,nRole.getPermissions().size());
                    break;
                default:
                    assertResultFail(result);
            }
        }
    }

    @Test
    public void delete() throws Exception {
        List<RoleModel> roles = roleDao.list();
        int uCount = userDao.count();
        int pCount = permissionDao.count();
        int rCount  = roles.size();
        for(RoleModel role:roles){
            if(Constants.ROLE_NAME_ADMIN.equals(role.getName())){
                assertResultFail(roleController.delete(role.getId()));
            }else{
                assertResultSuccess(roleController.delete(role.getId()));
                rCount--;
            }
        }
        assertEquals(uCount,userDao.count());
        assertEquals(pCount, permissionDao.count());
        assertEquals(1, roleDao.count());
        assertEquals(1,rCount);
    }

    @Test
    public void batch() throws Exception {
        List<RoleModel> roles = roleDao.list();
        int uCount = userDao.count();
        int pCount = permissionDao.count();
        int rCount  = roles.size();
        String fullIds = ListUtil.buildIds(roles);
        for(RoleModel role:roles){
            if(Constants.ROLE_NAME_ADMIN.equals(role.getName())){
                roles.remove(role);
                break;
            }
        }
        String cleanIds = ListUtil.buildIds(roles);
        assertResultFail(roleController.batch(fullIds));
        assertEquals(rCount, roleDao.count());

        assertResultSuccess(roleController.batch(cleanIds));
        assertResultFail(roleController.batch(fullIds));
        assertEquals(1, roleDao.count());

        assertEquals(uCount,userDao.count());
        assertEquals(pCount, permissionDao.count());

    }

}