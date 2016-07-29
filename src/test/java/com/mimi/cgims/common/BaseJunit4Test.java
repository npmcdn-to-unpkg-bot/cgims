package com.mimi.cgims.common;

import com.mimi.cgims.dao.*;
import com.mimi.cgims.listener.InitData;
import com.mimi.cgims.service.*;
import com.mimi.cgims.util.ListUtil;
import com.mimi.cgims.util.ResultUtil;
import com.mimi.cgims.web.controller.RoleController;
import com.mimi.cgims.web.controller.UserController;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)  //使用junit4进行测试
@ContextConfiguration
        ({"classpath:test.xml", "classpath:spring-servlet.xml"}) //加载配置文件
//({"classpath:test.xml","classpath:ehcache_hibernate.xml"}) //加载配置文件
public class BaseJunit4Test extends AbstractTransactionalJUnit4SpringContextTests {//AbstractJUnit4SpringContextTests {

    @Resource
    protected UserController userController;

    @Resource
    protected RoleController roleController;

    @Resource
    protected IPermissionService permissionService;
    @Resource
    protected IRoleService roleService;
    @Resource
    protected IUserService userService;
    @Resource
    protected IOrderService orderService;
    @Resource
    protected IWorkmanService workmanService;

    @Resource
    protected IPermissionDao permissionDao;
    @Resource
    protected IRoleDao roleDao;
    @Resource
    protected IUserDao userDao;
    @Resource
    protected IOrderDao orderDao;
    @Resource
    protected IWorkmanDao workmanDao;
    @Resource
    protected InitData initData;

    @Before
    public void before() {
        initData.init();
        initData.initTestData();
    }

    public List randomList(List list){
        Random random = new Random();
        List nList = new ArrayList<>();
        if(ListUtil.isNotEmpty(list)){
            for(Object obj:list){
                if(random.nextBoolean()){
                    nList.add(obj);
                }
            }
            if(nList.isEmpty()){
                nList.add(list.get(0));
            }
        }
        return nList;
    }

    public void assertResultSuccess(Object obj){
        assertEquals(ResultUtil.RESULT_SUCCESS,ResultUtil.getStatus((Map<String, Object>) obj));
    }

    public void assertResultFail(Object obj){
        assertEquals(ResultUtil.RESULT_FAIL,ResultUtil.getStatus((Map<String, Object>) obj));
    }
}  