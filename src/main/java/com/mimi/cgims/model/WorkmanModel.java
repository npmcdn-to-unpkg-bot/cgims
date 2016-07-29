package com.mimi.cgims.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.*;
import org.hibernate.annotations.Cache;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "tbl_workman")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class WorkmanModel extends BaseModel {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @Column(name = "id", nullable = false)
    private String id;

    @Pattern(regexp = "[0-9]{12}", message = "工号格式有误")
    private String workmanNumber;

    @Size(max = 200)
    private String headImg;

    @Size(max = 50)
    private String name;

    @Size(max = 50)
    private String phoneNum;

    @Size(max = 50)
    private String qq;

    private Integer receiveType;

    @Size(max = 50)
    private String alipayAccount;//varchar	50	支付宝账号

    @Size(max = 50)
    private String bank;//	varchar	50	银行

    @Size(max = 50)
    private String cardNum;//	varchar	50	银行卡号

    private Date birthday;//	date		出生日期

    @Size(max = 50)
    private String province;//	varchar	50	所在省

    @Size(max = 50)
    private String city;//	varchar	50	所在市

    @Size(max = 50)
    private String area;//	varchar	50	所在区

    @Size(max = 500)
    private String address;//	varchar	500	详细地址

    @Size(max = 200)
    private String idCardFace;//varchar	200	身份证正面

    @Size(max = 200)
    private String idCardBack;//varchar	200	身份证反面

    @Size(max = 50)
    private String serviceType;//varchar	50	服务类型

    @Size(max = 500)
    private String serviceItems;//	varchar	500	服务类目

    @Size(max = 500)
    private String serviceArea;//varchar	500	服务区域

    private Integer teamPeopleNum;//int	8	团队人数

    private Integer truckNum;//	int	8	货车数量

    private Float tonnage;//	float	6,4	货车吨位

    @Size(max = 200)
    private String willingPickAddress;//varchar	200	推荐提货点

    @Size(max = 200)
    private String logistics;//	varchar	200	提存物流

    @Size(max = 500)
    private String strength;//varchar	500	优势

    @Size(max = 1000)
    private String description;//	varchar	1000	备注

    private Integer cooperateTimes;//	int	8	合作次数

    private Float score;//float	6,4	综合评分


    @JsonIgnore
    @OneToMany(targetEntity = OrderModel.class, cascade = CascadeType.ALL)
//    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "workman_id", updatable = false)
    private List<OrderModel> orders;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWorkmanNumber() {
        return workmanNumber;
    }

    public void setWorkmanNumber(String workmanNumber) {
        this.workmanNumber = workmanNumber;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public Integer getReceiveType() {
        return receiveType;
    }

    public void setReceiveType(Integer receiveType) {
        this.receiveType = receiveType;
    }

    public String getAlipayAccount() {
        return alipayAccount;
    }

    public void setAlipayAccount(String alipayAccount) {
        this.alipayAccount = alipayAccount;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getCardNum() {
        return cardNum;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIdCardFace() {
        return idCardFace;
    }

    public void setIdCardFace(String idCardFace) {
        this.idCardFace = idCardFace;
    }

    public String getIdCardBack() {
        return idCardBack;
    }

    public void setIdCardBack(String idCardBack) {
        this.idCardBack = idCardBack;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getServiceItems() {
        return serviceItems;
    }

    public void setServiceItems(String serviceItems) {
        this.serviceItems = serviceItems;
    }

    public String getServiceArea() {
        return serviceArea;
    }

    public void setServiceArea(String serviceArea) {
        this.serviceArea = serviceArea;
    }

    public Integer getTeamPeopleNum() {
        return teamPeopleNum;
    }

    public void setTeamPeopleNum(Integer teamPeopleNum) {
        this.teamPeopleNum = teamPeopleNum;
    }

    public Integer getTruckNum() {
        return truckNum;
    }

    public void setTruckNum(Integer truckNum) {
        this.truckNum = truckNum;
    }

    public Float getTonnage() {
        return tonnage;
    }

    public void setTonnage(Float tonnage) {
        this.tonnage = tonnage;
    }

    public String getWillingPickAddress() {
        return willingPickAddress;
    }

    public void setWillingPickAddress(String willingPickAddress) {
        this.willingPickAddress = willingPickAddress;
    }

    public String getLogistics() {
        return logistics;
    }

    public void setLogistics(String logistics) {
        this.logistics = logistics;
    }

    public String getStrength() {
        return strength;
    }

    public void setStrength(String strength) {
        this.strength = strength;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getCooperateTimes() {
        return cooperateTimes;
    }

    public void setCooperateTimes(Integer cooperateTimes) {
        this.cooperateTimes = cooperateTimes;
    }

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    public List<OrderModel> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderModel> orders) {
        this.orders = orders;
    }
}
