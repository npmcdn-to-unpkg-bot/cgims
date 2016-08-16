package com.mimi.cgims.service.impl;

import com.mimi.cgims.Constants;
import com.mimi.cgims.dao.IBaseDao;
import com.mimi.cgims.dao.IOrderDao;
import com.mimi.cgims.dao.IWorkmanDao;
import com.mimi.cgims.model.WorkmanModel;
import com.mimi.cgims.service.IWorkmanService;
import com.mimi.cgims.util.*;
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
    private IOrderDao orderDao;

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
            Random random = new Random();
            for (int i = 0; i < 100; i++) {
                Date today = DateUtil.randomDate("2014-01-01 00:00:00","2015-01-01 00:00:00");
                Calendar c = Calendar.getInstance();
                c.setTime(today);
                AutoNumUtil.setC(c);
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
                workman.setQq("1231412312");
                workman.setIdCardFace("http://upload.admin5.com/upimg/allimg/100926/1000401.jpg");
                workman.setIdCardBack("http://epaper.cnsq.com.cn/jjwb/res/1/10/2011-01/07/06/res01_attpic_brief.jpg");
                workman.setHeadImg("http://www.itotii.com/wp-content/uploads/2016/06/06/1465202291_jrhAYEHC.jpg");
                workman.setScore((float)(Math.round(random.nextFloat()*5*100))/100);

                if (Math.random() > 0.5) {
                    workman.setServiceType(Constants.SERVICE_TYPE_PSAZ);
                } else {
                    workman.setServiceType(Constants.SERVICE_TYPE_WX);
                }
                Map<String, String[]> serviceItems = Constants.getServiceItemsMap();
//                Map<String, List> newItemsMap = new HashMap<>();
                Map<String, String> newItemsMap = new HashMap<>();
                for (String key : serviceItems.keySet()) {
                    if (Math.random() > 0.5) {
//                        List<String> newItems = new ArrayList<>();
                        String newItems = "";
                        String[] items = serviceItems.get(key);
                        for (String item : items) {
                            if (Math.random() > 0.5) {
//                                newItems.add(item);
                                if(StringUtils.isNotBlank(newItems)){
                                    newItems+=",";
                                }
                                if(item.equals("其他")){
                                    item = "阿里山的风景阿拉山口大就发生的";
                                }
                                newItems+=item;
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
//                workman.setDescription("备注al升得啦分开始叫对方阿斯兰的风景阿阿斯兰的水电费暗杀的反馈收到风阿萨阿斯兰的水电费暗杀的反馈收到风阿萨斯兰的水电费暗杀的反馈收到风阿萨德暗杀发生大事阿萨德是阿萨德发斯蒂芬" + i);
                workman.setDescription("备注al升得啦分" + i);
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
        List<String> errors = commonCheck(workman, true);
        if (errors.isEmpty()) {
            return null;
        }
        return errors.get(0);
    }

    @Override
    public String checkUpdate(WorkmanModel workman) {
        List<String> errors = commonCheck(workman, false);
        if (errors.isEmpty()) {
            return null;
        }
        return errors.get(0);
    }

    private List<String> commonCheck(WorkmanModel workman, boolean isAdd) {
        List<String> errors = new ArrayList<>();
        String error;
        if(workman == null){
            errors.add("内容为空");
            return errors;
        }
        error = FormatUtil.checkFormat(workman.getWorkmanNumber(), FormatUtil.REGEX_ORDER_NUMBER, true, 0, FormatUtil.MAX_LENGTH_COMMON_SHORT_L3, "工号号");
        if (StringUtils.isNotBlank(error)) {
            errors.add(error);
        }
        error = FormatUtil.checkLengthOnly(workman.getHeadImg(), FormatUtil.MAX_LENGTH_COMMON_NORMAL_L2, "头像");
        if (StringUtils.isNotBlank(error)) {
            errors.add(error);
        }
        error = FormatUtil.checkLengthOnly(workman.getName(), FormatUtil.MAX_LENGTH_COMMON_SHORT_L3, "姓名");
        if (StringUtils.isNotBlank(error)) {
            errors.add(error);
        }
        error = FormatUtil.checkLengthOnly(workman.getPhoneNum(), FormatUtil.MAX_LENGTH_COMMON_SHORT_L3, "手机");
        if (StringUtils.isNotBlank(error)) {
            errors.add(error);
        }
        error = FormatUtil.checkLengthOnly(workman.getQq(), FormatUtil.MAX_LENGTH_COMMON_SHORT_L3, "QQ");
        if (StringUtils.isNotBlank(error)) {
            errors.add(error);
        }

        if(workman.getReceiveType()!=null && !ListUtil.contains(Constants.RECEIVE_TYPE_LIST,workman.getReceiveType())){
            errors.add("未知收款类型");
        }
        error = FormatUtil.checkLengthOnly(workman.getAlipayAccount(), FormatUtil.MAX_LENGTH_COMMON_SHORT_L3, "阿里账号");
        if (StringUtils.isNotBlank(error)) {
            errors.add(error);
        }
        error = FormatUtil.checkLengthOnly(workman.getBank(), FormatUtil.MAX_LENGTH_COMMON_SHORT_L3, "银行");
        if (StringUtils.isNotBlank(error)) {
            errors.add(error);
        }
        error = FormatUtil.checkLengthOnly(workman.getCardNum(), FormatUtil.MAX_LENGTH_COMMON_SHORT_L3, "银行卡号");
        if (StringUtils.isNotBlank(error)) {
            errors.add(error);
        }
        error = FormatUtil.checkLengthOnly(workman.getProvince(), FormatUtil.MAX_LENGTH_COMMON_SHORT_L3, "所在省");
        if (StringUtils.isNotBlank(error)) {
            errors.add(error);
        }
        error = FormatUtil.checkLengthOnly(workman.getCity(), FormatUtil.MAX_LENGTH_COMMON_SHORT_L3, "所在市");
        if (StringUtils.isNotBlank(error)) {
            errors.add(error);
        }
        error = FormatUtil.checkLengthOnly(workman.getArea(), FormatUtil.MAX_LENGTH_COMMON_SHORT_L3, "所在区");
        if (StringUtils.isNotBlank(error)) {
            errors.add(error);
        }
        error = FormatUtil.checkLengthOnly(workman.getAddress(), FormatUtil.MAX_LENGTH_COMMON_NORMAL_L3, "详细地址");
        if (StringUtils.isNotBlank(error)) {
            errors.add(error);
        }
        error = FormatUtil.checkLengthOnly(workman.getIdCardFace(), FormatUtil.MAX_LENGTH_COMMON_NORMAL_L2, "身份证正面");
        if (StringUtils.isNotBlank(error)) {
            errors.add(error);
        }
        error = FormatUtil.checkLengthOnly(workman.getIdCardBack(), FormatUtil.MAX_LENGTH_COMMON_NORMAL_L2, "身份证背面");
        if (StringUtils.isNotBlank(error)) {
            errors.add(error);
        }
        error = FormatUtil.checkLengthOnly(workman.getServiceType(), FormatUtil.MAX_LENGTH_COMMON_SHORT_L3, "服务类型");
        if (StringUtils.isNotBlank(error)) {
            errors.add(error);
        }
        error = FormatUtil.checkLengthOnly(workman.getServiceItems(), FormatUtil.MAX_LENGTH_COMMON_NORMAL_L3, "服务类目");
        if (StringUtils.isNotBlank(error)) {
            errors.add(error);
        }
        error = FormatUtil.checkLengthOnly(workman.getServiceArea(), FormatUtil.MAX_LENGTH_COMMON_NORMAL_L3, "服务区域");
        if (StringUtils.isNotBlank(error)) {
            errors.add(error);
        }
        error = FormatUtil.checkLengthOnly(workman.getWillingPickAddress(), FormatUtil.MAX_LENGTH_COMMON_NORMAL_L2, "推荐提货点");
        if (StringUtils.isNotBlank(error)) {
            errors.add(error);
        }
        error = FormatUtil.checkLengthOnly(workman.getLogistics(), FormatUtil.MAX_LENGTH_COMMON_NORMAL_L2, "提存物流");
        if (StringUtils.isNotBlank(error)) {
            errors.add(error);
        }
        error = FormatUtil.checkLengthOnly(workman.getStrength(), FormatUtil.MAX_LENGTH_COMMON_NORMAL_L3, "优势");
        if (StringUtils.isNotBlank(error)) {
            errors.add(error);
        }
        error = FormatUtil.checkLengthOnly(workman.getDescription(), FormatUtil.MAX_LENGTH_COMMON_LONG_L1, "备注");
        if (StringUtils.isNotBlank(error)) {
            errors.add(error);
        }
        if (errors.isEmpty()) {
            WorkmanModel wm = workmanDao.getByPhoneNum(workman.getPhoneNum());
            if (wm != null && !wm.getId().equals(workman.getId())) {
                errors.add("手机号码已存在");
            }
        }
        return errors;
    }

    @Override
    public WorkmanModel getByPhoneNum(String phoneNum) {
        return workmanDao.getByPhoneNum(phoneNum);
    }

    @Override
    public int getNewestCount(int year, int month, int day) {
        WorkmanModel workman = workmanDao.getNewest(year, month, day);
        if (workman != null) {
            return Integer.parseInt(workman.getWorkmanNumber().substring(8));
        }
        return 0;
    }
    @Override
    public void delete(String id){
        orderDao.cleanWorkmanId(id);
        super.delete(id);
    }

    @Override
    public void batchDelete(String ...ids){
        for(String id:ids){
            delete(id);
        }
    }
}
