package com.mimi.cgims.common;

import com.mimi.cgims.model.OrderModel;
import com.mimi.cgims.model.UserModel;
import com.mimi.cgims.util.ResultUtil;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
//@Transactional
public class JunitDemo extends BaseJunit4Test {

    @Test
    public void getDeleteTest() {
       List<UserModel> users =  userDao.list();
        int total =  orderDao.count();
        int userSize = userDao.count();
        assertEquals(userSize>0,true);
        int dCount = 0;
        for(UserModel user:users){
            List<OrderModel> orders = orderDao.list(null,null,null,user.getId(),null,null,null,1,100);
            if(orders!=null && !orders.isEmpty()){
                Map<String ,Object> map = (Map<String, Object>) userController.delete(user.getId());
                if(1==ResultUtil.getStatus(map)){
                    dCount++;
                }else{
                    System.out.println(ResultUtil.getMsg(map));
                }
                assertEquals(total,orderDao.count());
            }
        }
        assertEquals(dCount>0,true);
        assertEquals(userSize-dCount,userDao.count());
        assertEquals(total,orderDao.count());
//        CCPRestSmsSDK kk = (CCPRestSmsSDK) applicationContext.getBean("com.cloopen.rest.sdk.CCPRestSmsSDK");
//        assertEquals(2, 1 + 1);
//        assertEquals(ytx,kk);
//        GeetestLib k2 = (GeetestLib) applicationContext.getBean("geetestLibBean");
////        com.mimi.cgims.web.captcha.GeetestLib
//        for(String str:applicationContext.getAliases("com.mimi.cgims.web.captcha.GeetestLib")){
//            System.out.println(str);
//        }
    }
}
