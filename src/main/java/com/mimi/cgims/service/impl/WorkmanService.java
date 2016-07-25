package com.mimi.cgims.service.impl;

import com.mimi.cgims.Constants;
import com.mimi.cgims.dao.IBaseDao;
import com.mimi.cgims.dao.IWorkmanDao;
import com.mimi.cgims.model.WorkmanModel;
import com.mimi.cgims.service.IWorkmanService;
import com.mimi.cgims.util.AutoNumUtil;
import com.mimi.cgims.util.CityUtil;
import com.mimi.cgims.util.ResultUtil;
import com.mimi.cgims.util.page.PageUtil;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class WorkmanService extends BaseService<WorkmanModel, String> implements IWorkmanService {

    private IWorkmanDao workmanDao;

    @Resource
    @Override
    public void setBaseDao(IBaseDao<WorkmanModel, String> baseDao) {
        this.baseDao = baseDao;
        this.workmanDao = (IWorkmanDao) baseDao;
    }

    @Override
    public void initTestData() {
        int count = workmanDao.count();
        if (count == 0) {
            for (int i = 0; i < 100; i++) {
                WorkmanModel workman = new WorkmanModel();
                workman.setName("名称" + i);
                workman.setWorkmanNumber(AutoNumUtil.getWorkmanNum());
                workman.setAddress("地址阿斯兰的风景撒开房间死定了房间爱上了对方" + i);
                if (Math.random() > 0.5) {
                    workman.setAlipayAccount("2dfoajsdf@qe.com");
                    workman.setReceiveType(Constants.RECEIVE_TYPE_ALIPAY);
                } else {
                    workman.setBank("银行" + i);
                    workman.setCardNum("6665332312351235" + i);
                    workman.setReceiveType(Constants.RECEIVE_TYPE_BANK);
                }
                workman.setBirthday(new Date());
                workman.setProvince(CityUtil.getRandomProvince());
                workman.setCity(CityUtil.getRandomCity(workman.getProvince()));
                workman.setArea(CityUtil.getRandomArea(workman.getProvince(), workman.getCity()));
                workman.setCooperateTimes((int) (Math.random() * 10));
                workman.setPhoneNum("14213423124");
                workman.setQq("12314123123" + i);
                workman.setIdCardFace("http://upload.admin5.com/upimg/allimg/100926/1000401.jpg");
                workman.setIdCardBack("http://epaper.cnsq.com.cn/jjwb/res/1/10/2011-01/07/06/res01_attpic_brief.jpg");
                workman.setHeadImg("http://www.itotii.com/wp-content/uploads/2016/06/06/1465202291_jrhAYEHC.jpg");

                if (Math.random() > 0.5) {
                    workman.setServiceType(Constants.SERVICE_TYPE_PSAZ);
                } else {
                    workman.setServiceType(Constants.SERVICE_TYPE_WX);
                }
                Map<String, String[]> serviceItems = Constants.getServiceItemsMap();
                Map<String, List> newItemsMap = new HashMap<>();
                for (String key : serviceItems.keySet()) {
                    if (Math.random() > 0.5) {
                        List<String> newItems = new ArrayList<>();
                        String[] items = serviceItems.get(key);
                        for (String item : items) {
                            if (Math.random() > 0.5) {
                                newItems.add(item);
                            }
                        }
                        if (!newItems.isEmpty()) {
                            newItemsMap.put(key, newItems);
                        }
                    }
                }
                workman.setServiceItems(JSONObject.fromObject(newItemsMap).toString());
                String serviceAreasStr = "";
                for (int j = 0; j < 10; j++) {
                    if (Math.random() > 0.5) {
                        String ta = CityUtil.getRandomArea(workman.getProvince(), workman.getCity());
                        if (!serviceAreasStr.contains(ta)) {
                            if (StringUtils.isNotBlank(serviceAreasStr)) {
                                serviceAreasStr += Constants.SPLIT_STRING_PARAMS;
                            }
                            serviceAreasStr += ta;
                        }
                    }
                }
                workman.setServiceArea(serviceAreasStr);
                workman.setTeamPeopleNum((int) (Math.random() * 10));
                workman.setTruckNum((int) (Math.random() * 10));
                workman.setTonnage((float) (Math.random() * 2 + 1));
                workman.setWillingPickAddress("推荐提货点阿拉山口都放假拉升的" + i);
                workman.setLogistics("提存物流收到风手动发斯蒂芬" + i);
                workman.setStrength("优势阿里上飞机安乐死的解放路上对方就流口水" + i);
                workman.setDescription("备注al升得啦分开始叫对方阿斯兰的风景阿阿斯兰的水电费暗杀的反馈收到风阿萨阿斯兰的水电费暗杀的反馈收到风阿萨斯兰的水电费暗杀的反馈收到风阿萨德暗杀发生大事阿萨德是阿萨德发斯蒂芬" + i);
                workmanDao.add(workman);
            }
        }
    }

    @Override
    protected void initAction() {
    }

    @Override
    public Map<String, Object> list4Page(String searchKeyword, String province, String city, String area, String serviceType, int targetPage, int pageSize) {
        int total = workmanDao.count(searchKeyword, province, city, area, serviceType);
        targetPage = PageUtil.fitPage(total, targetPage, pageSize);
        List<WorkmanModel> list = workmanDao.list(searchKeyword, province, city, area, serviceType, targetPage, pageSize);
        int totalPage = PageUtil.getTotalPage(total, pageSize);
        return ResultUtil.getResultMap(total, totalPage, targetPage, pageSize, list);
    }

    @Override
    public String checkAdd(WorkmanModel workman) {
        // TODO: 2016/7/23
        return null;
    }

    @Override
    public String checkUpdate(WorkmanModel workman) {
        // TODO: 2016/7/23
        return null;
    }
}
