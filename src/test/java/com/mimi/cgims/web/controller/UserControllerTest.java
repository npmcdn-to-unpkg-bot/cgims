package com.mimi.cgims.web.controller;

import com.mimi.cgims.Constants;
import com.mimi.cgims.common.BaseJunit4Test;
import com.mimi.cgims.model.RoleModel;
import com.mimi.cgims.model.UserModel;
import com.mimi.cgims.util.ListUtil;
import com.mimi.cgims.util.LoginUtil;
import com.mimi.cgims.util.ResultUtil;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static org.junit.Assert.*;

public class UserControllerTest extends BaseJunit4Test {
    @Test
    public void search() throws Exception {
        Map<String, Object> userMap = (Map<String, Object>) userController.search("", 1, 1000);
        Map<String, Object> userMap2 = (Map<String, Object>) userController.search("1", 1, 1000);
        List<UserModel> users = (List<UserModel>) ResultUtil.getDatas(userMap);
        List<UserModel> users2 = (List<UserModel>) ResultUtil.getDatas(userMap2);

        assertContain(users, false, false, false, false);
        assertTrue(users.size() != users2.size());
        for(UserModel user:users){
            assertTrue(StringUtils.isBlank(user.getPassword()));
        }
    }

    @Test
    public void get() throws Exception {
        List<UserModel> users = userDao.list();
        List<UserModel> nUsers = new ArrayList<>();
        for (UserModel user : users) {
            nUsers.add((UserModel) ResultUtil.getResult((Map<String, Object>) userController.get(user.getId())));
        }
        for(UserModel user:nUsers){
            assertTrue(StringUtils.isBlank(user.getPassword()));
        }
        assertContain(nUsers, false, false, true, true);
    }

    private void assertContain(UserModel user, boolean co, boolean cm, boolean cs, boolean cr) {
        List<UserModel> list = new ArrayList<>();
        list.add(user);
        assertContain(list, co,cm,cs,cr);
    }


    private void assertContain(List<UserModel> users, boolean co, boolean cm, boolean cs, boolean cr) {
        boolean containOrder = false;
        boolean containMaster = false;
        boolean containSlave = false;
        boolean containRole = false;
        for (UserModel user : users) {
            containRole = containRole || ListUtil.isNotEmpty(user.getRoles());
            containMaster = containMaster || ListUtil.isNotEmpty(user.getMasters());
            containOrder = containOrder || ListUtil.isNotEmpty(user.getOrders());
            containSlave = containSlave || ListUtil.isNotEmpty(user.getSlaves());
        }
        assertEquals(containOrder, co);
        assertEquals(containMaster, cm);
        assertEquals(containSlave, cs);
        assertEquals(containRole, cr);
    }

    @Test
    public void add() throws Exception {
        List<RoleModel> roles = roleDao.list();
        List<UserModel> slaves = userDao.list();
        List<UserModel> nSlaves = randomList(slaves);
        List<RoleModel> nRoles = randomList(roles);
        String slaveIds = ListUtil.buildIds(nSlaves);
        String roleIds = ListUtil.buildIds(nRoles);

        UserModel user = new UserModel();
        user.setName("test名称");
        user.setLoginName("testLoginName");
        user.setPassword("123123");
        user.setPhoneNum("14213214231");
        user.setIdentity("442132132412132123");
        user.setDescription("test描述");
        Object result = userController.add(user, roleIds, slaveIds);
        String id = (String) ResultUtil.getResult((Map<String, Object>) result);
        UserModel nUser = userService.getWithDatas(id);
        assertEquals(nUser.getDescription(), user.getDescription());
        assertEquals(nUser.getPassword(), LoginUtil.buildPassword("123123"));
        assertEquals(nUser.getPhoneNum(), user.getPhoneNum());
        assertEquals(nUser.getName(), user.getName());
        assertEquals(nUser.getLoginName(), user.getLoginName());
        assertEquals(nUser.getIdentity(), user.getIdentity());
        assertEquals(nUser.getRoles().size(), nRoles.size());
        assertEquals(nUser.getSlaves().size(), nSlaves.size());

        UserModel eUser1 = new UserModel();
        eUser1.setLoginName("testLoginName");
        eUser1.setPassword("123123");

        UserModel eUser2 = new UserModel();
        eUser2.setLoginName("testLoginName2");


        UserModel eUser3 = new UserModel();

        UserModel eUser4 = null;

        List<UserModel> eUsers = new ArrayList<>();
        eUsers.add(eUser1);
        eUsers.add(eUser2);
        eUsers.add(eUser3);
        eUsers.add(eUser4);
        for (UserModel eu : eUsers) {
            assertEquals(ResultUtil.getStatus((Map<String, Object>) userController.add(eu, null, null)), ResultUtil.RESULT_FAIL);
        }

    }

    @Test
    public void update() throws Exception {
        String name = "test名称";
        String loginName = "testLoginName";
        String phoneNum = "14213214231";
        String identity = "442132132412132123";
        String description = "test描述";
        String password = "321321";
        List<RoleModel> roles = roleDao.list();
        List<UserModel> users = userDao.list();
        List<UserModel> nSlaves = randomList(users);
        List<RoleModel> nRoles = randomList(roles);
        String slaveIds = ListUtil.buildIds(nSlaves);
        String roleIds = ListUtil.buildIds(nRoles);

        List<UserModel> raUsers = new ArrayList<>();

        while(raUsers.size()<10){
            raUsers = randomList(users);
        }
        for(int i=0;i<10;i++){
            UserModel oldUser = raUsers.get(i);
            String oldId = oldUser.getId();
            String oldLoginName = oldUser.getLoginName();
            String oldPassword = oldUser.getPassword();

            UserModel u1 = new UserModel();
            u1.setName(name);
            u1.setPhoneNum(phoneNum);
            u1.setIdentity(identity);
            u1.setDescription(description);
            u1.setId(oldId);
            if(i<5){
                u1.setPassword(password);
            }else{
                u1.setPassword(null);
            }
            if(i%2==0){
                u1.setLoginName(Constants.USER_LOGIN_NAME_ADMIN);
            }else if(i%3==0){
                u1.setLoginName(loginName);
                u1.setId("afalskf");
            }else{
                u1.setLoginName(oldLoginName);
            }
            Object result;
            if(i<=1){
                result = userController.update(oldId,u1,roleIds,slaveIds);
            }else if(i<=3){
                result = userController.update(oldId,u1,null,slaveIds);
            }else if(i<=5) {
                result = userController.update(oldId, u1, roleIds, null);
            }else {
                result = userController.update(oldId, u1, null, null);
            }
            if(i%2==0 || i==9){
                assertResultFail(result);
            }else{
                assertResultSuccess(result);
                UserModel nUser = userDao.get(oldId);
                assertEquals(name,nUser.getName());
                assertEquals(phoneNum,nUser.getPhoneNum());
                assertEquals(identity,nUser.getIdentity());
                assertEquals(description,nUser.getDescription());
                switch(i){
                    case 1:
                        assertEquals(LoginUtil.buildPassword(password),nUser.getPassword());
                        assertEquals(nRoles.size(),nUser.getRoles().size());
                        assertEquals(nSlaves.size(),nUser.getSlaves().size());
                        break;
                    case 3:
                        assertEquals(LoginUtil.buildPassword(password),nUser.getPassword());
                        assertEquals(loginName,nUser.getLoginName());
                        assertEquals(0,nUser.getRoles().size());
                        assertEquals(nSlaves.size(),nUser.getSlaves().size());
                        break;
                    case 5:
                        assertEquals(oldPassword,nUser.getPassword());
                        assertEquals(nRoles.size(),nUser.getRoles().size());
                        assertEquals(0,nUser.getSlaves().size());
                        break;
                    case 7:
                        assertEquals(oldPassword,nUser.getPassword());
                        assertEquals(0,nUser.getRoles().size());
                        assertEquals(0,nUser.getSlaves().size());
                        break;
                }
            }
        }
    }

//    @Test
//    public void delete() throws Exception {
//        List<RoleModel> roles = roleDao.list();
//        List<UserModel> slaves = userDao.list();
//        List<UserModel> nSlaves = randomList(slaves);
//        List<RoleModel> nRoles = randomList(roles);
//        String slaveIds = ListUtil.buildIds(nSlaves);
//        String roleIds = ListUtil.buildIds(nRoles);
//
//        List<UserModel> users = userDao.list();
//        for (UserModel tu : users) {
//            if (!LoginUtil.isAdmin(tu.getLoginName())) {
//                int oCount = orderDao.count();
//                int oRoles = roleDao.count();
//                int oUsers = userDao.count();
//                assertResultSuccess(userController.delete(tu.getId()));
//                oUsers--;
//                assertEquals(oCount, orderDao.count());
//                assertEquals(oRoles, roleDao.count());
//                assertEquals(oUsers, userDao.count());
//            } else {
//                assertResultFail(userController.update(tu.getId(), tu, roleIds, slaveIds));
//            }
//        }
//    }

    @Test
    public void batch() throws Exception {

        Random random = new Random();
        List<RoleModel> roles = roleDao.list();
        List<UserModel> slaves = userDao.list();
        List<UserModel> nSlaves = randomList(slaves);
        List<RoleModel> nRoles = randomList(roles);
        List<UserModel> nUsers = randomList(slaves);
        int orderNum = orderDao.count();
        String slaveIds = ListUtil.buildIds(nSlaves);
        String roleIds = ListUtil.buildIds(nRoles);
        String userIds = ListUtil.buildIds(nUsers);
        int count = slaves.size();
        count -= nUsers.size();
        assertResultSuccess(userController.batch(userIds));
        assertEquals(count, userDao.count());
        assertEquals(orderNum,orderDao.count());
    }


    @Test
    public void login() throws Exception{
        String[] loginNames = {Constants.USER_LOGIN_NAME_ADMIN,"loginName1","loginName2",null,"asldfjs"};
        String[] passwords = {Constants.USER_PASSWORD,"123123","asdfksf","123123",null};
        boolean[] results = {true,true,false,false,false};
        for(int i=0;i<loginNames.length;i++){
            HttpServletRequest request = new MockHttpServletRequest();
            assertFalse(LoginUtil.isUserLogined(request));
            UserModel user = new UserModel();
            user.setLoginName(loginNames[i]);
            user.setPassword(passwords[i]);
            if(results[i]){
                assertResultSuccess(userController.login(request, user));
                assertTrue(LoginUtil.isUserLogined(request));
            }else{
                assertResultFail(userController.login(request, user));
                assertFalse(LoginUtil.isUserLogined(request));
            }
        }
    }

    @Test
    public void logout() throws Exception {
        HttpServletRequest request = new MockHttpServletRequest();
        assertFalse(LoginUtil.isUserLogined(request));
        UserModel user = new UserModel();
        user.setLoginName(Constants.USER_LOGIN_NAME_ADMIN);
        user.setPassword(Constants.USER_PASSWORD);
        assertResultSuccess(userController.login(request, user));
        assertTrue(LoginUtil.isUserLogined(request));
        assertResultSuccess(userController.logout(request));
        assertFalse(LoginUtil.isUserLogined(request));
    }

    @Test
    public void userSelfGet() throws Exception {
        HttpServletRequest request = new MockHttpServletRequest();
        assertFalse(LoginUtil.isUserLogined(request));
        UserModel user = new UserModel();
        user.setLoginName(Constants.USER_LOGIN_NAME_ADMIN);
        user.setPassword(Constants.USER_PASSWORD);
        assertResultSuccess(userController.login(request, user));
        assertTrue(LoginUtil.isUserLogined(request));
        Object map = userController.userSelfGet(request);
        assertResultSuccess(map);
        UserModel nu = (UserModel) ResultUtil.getResult((Map<String, Object>) map);
        assertContain(nu,false,false,true,true);
        assertTrue(StringUtils.isBlank(nu.getPassword()));
    }

    @Test
    public void userSelfUpdate() throws Exception {
        String[] loginNames = {Constants.USER_LOGIN_NAME_ADMIN,"loginName1","loginName2",null,"asldfjs"};
        String[] passwords = {Constants.USER_PASSWORD,"123123","214daf","123123",null};

        String password = "321312";
        String name = "test名称";
        String loginName = "testLoginName";
        String phoneNum = "14213214231";
        String identity = "442132132412132123";
        String description = "test描述";

        boolean[] results = {true,true,false,false,false};
        for(int i=0;i<loginNames.length;i++){
            HttpServletRequest request = new MockHttpServletRequest();
            assertFalse(LoginUtil.isUserLogined(request));
            UserModel user = new UserModel();
            user.setLoginName(loginNames[i]);
            user.setPassword(passwords[i]);
            if(results[i]){
                assertResultSuccess(userController.login(request, user));
                assertTrue(LoginUtil.isUserLogined(request));
                UserModel sUser = new UserModel();

                sUser.setLoginName(loginName);
                sUser.setName(name);
                sUser.setPhoneNum(phoneNum);
                sUser.setIdentity(identity);
                sUser.setId("sdfsf");
                sUser.setDescription(description);

                if(i==0){
                    sUser.setPassword(password);
                    assertResultSuccess(userController.userSelfUpdate(request,sUser));
                    UserModel rUser = userDao.get(LoginUtil.getCurUserId(request));
                    assertEquals(LoginUtil.buildPassword(password), rUser.getPassword());
                    assertEquals(name, rUser.getName());
                    assertEquals(Constants.USER_LOGIN_NAME_ADMIN, rUser.getLoginName());
                    assertEquals(phoneNum, rUser.getPhoneNum());
                    assertEquals(identity, rUser.getIdentity());
                    assertEquals(description, rUser.getDescription());
                    assertFalse(ListUtil.isEmpty(rUser.getRoles()));
                }else {
                    sUser.setPassword(null);
                    assertResultSuccess(userController.userSelfUpdate(request,sUser));
                    UserModel rUser = userDao.get(LoginUtil.getCurUserId(request));
                    assertEquals(LoginUtil.buildPassword("123123"), rUser.getPassword());
                    assertEquals(name, rUser.getName());
                    assertNotEquals(loginName,rUser.getLoginName());
                    assertEquals(phoneNum, rUser.getPhoneNum());
                    assertEquals(identity, rUser.getIdentity());
                    assertEquals(description, rUser.getDescription());
                    assertFalse(ListUtil.isEmpty(rUser.getSlaves()));
                }

            }else{
                assertResultFail(userController.login(request, user));
                assertFalse(LoginUtil.isUserLogined(request));
                UserModel sUser = new UserModel();

                sUser.setLoginName(loginName);
                sUser.setName(name);
                sUser.setPhoneNum(phoneNum);
                sUser.setIdentity(identity);
                sUser.setId("sdfsf");
                sUser.setDescription(description);
                assertResultFail(userController.userSelfUpdate(request,sUser));
            }
        }
    }

}