package com.mimi.cgims.model;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "tbl_order")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class OrderModel implements Serializable {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @Column(name = "id", nullable = false)
    private String id;

    @Pattern(regexp = "[0-9]{12}", message = "订单号码格式有误")
    @Column(unique = true)
    private String orderNumber;

    private Integer orderStatus;

    private Integer serviceType;

    @Size(max = 50)
    private String customerName;

    @Size(max = 50)
    private String customerPhoneNum;

    @Size(max = 50)
    private String customerTel;

    @Size(max = 200)
    private String customerAddress;

    @Size(max = 500)
    private String productInfo;

    @Size(max = 500)
    private String productImgs;

    @Size(max = 500)
    private String logisticsInfo;

    @Size(max = 500)
    private String logisticsImgs;

    @Size(max = 1000)
    private String repairInfo;

    @Size(max = 500)
    private String repairImgs;

    private Boolean isChecked;

    @Size(max = 50)
    private String checkInfo;

    @Size(max = 50)
    private String shopInfo;

    private Integer orderPrice;

    
    private Integer servicePrice;

    
    private Integer profit;

    @Size(max = 200)
    private String priceChangeReason;

    private Integer judgment;

    @Size(max = 200)
    private String judegReason;

    @Size(max = 1000)
    private String description;

    private Date createDate;

    private Date compleDate;

    private Boolean orderPriceChanged;

    private Boolean servicePriceChanged;

    @ManyToOne(targetEntity = UserModel.class)
    @JoinColumn(name = "user_id", updatable = false)
    private UserModel user;

    @ManyToOne(targetEntity = WorkmanModel.class)
    @JoinColumn(name = "workman_id", updatable = false)
    private WorkmanModel workman;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Integer getServiceType() {
        return serviceType;
    }

    public void setServiceType(Integer serviceType) {
        this.serviceType = serviceType;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerPhoneNum() {
        return customerPhoneNum;
    }

    public void setCustomerPhoneNum(String customerPhoneNum) {
        this.customerPhoneNum = customerPhoneNum;
    }

    public String getCustomerTel() {
        return customerTel;
    }

    public void setCustomerTel(String customerTel) {
        this.customerTel = customerTel;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getProductInfo() {
        return productInfo;
    }

    public void setProductInfo(String productInfo) {
        this.productInfo = productInfo;
    }

    public String getProductImgs() {
        return productImgs;
    }

    public void setProductImgs(String productImgs) {
        this.productImgs = productImgs;
    }

    public String getLogisticsInfo() {
        return logisticsInfo;
    }

    public void setLogisticsInfo(String logisticsInfo) {
        this.logisticsInfo = logisticsInfo;
    }

    public String getLogisticsImgs() {
        return logisticsImgs;
    }

    public void setLogisticsImgs(String logisticsImgs) {
        this.logisticsImgs = logisticsImgs;
    }

    public String getRepairInfo() {
        return repairInfo;
    }

    public void setRepairInfo(String repairInfo) {
        this.repairInfo = repairInfo;
    }

    public String getRepairImgs() {
        return repairImgs;
    }

    public void setRepairImgs(String repairImgs) {
        this.repairImgs = repairImgs;
    }

    public Boolean getChecked() {
        return isChecked;
    }

    public void setChecked(Boolean checked) {
        isChecked = checked;
    }

    public String getCheckInfo() {
        return checkInfo;
    }

    public void setCheckInfo(String checkInfo) {
        this.checkInfo = checkInfo;
    }

    public String getShopInfo() {
        return shopInfo;
    }

    public void setShopInfo(String shopInfo) {
        this.shopInfo = shopInfo;
    }

    public Integer getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(Integer orderPrice) {
        this.orderPrice = orderPrice;
    }

    public Integer getServicePrice() {
        return servicePrice;
    }

    public void setServicePrice(Integer servicePrice) {
        this.servicePrice = servicePrice;
    }

    public Integer getProfit() {
        return profit;
    }

    public void setProfit(Integer profit) {
        this.profit = profit;
    }

    public String getPriceChangeReason() {
        return priceChangeReason;
    }

    public void setPriceChangeReason(String priceChangeReason) {
        this.priceChangeReason = priceChangeReason;
    }

    public Integer getJudgment() {
        return judgment;
    }

    public void setJudgment(Integer judgment) {
        this.judgment = judgment;
    }

    public String getJudegReason() {
        return judegReason;
    }

    public void setJudegReason(String judegReason) {
        this.judegReason = judegReason;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getCompleDate() {
        return compleDate;
    }

    public void setCompleDate(Date compleDate) {
        this.compleDate = compleDate;
    }

    public Boolean getOrderPriceChanged() {
        return orderPriceChanged;
    }

    public void setOrderPriceChanged(Boolean orderPriceChanged) {
        this.orderPriceChanged = orderPriceChanged;
    }

    public Boolean getServicePriceChanged() {
        return servicePriceChanged;
    }

    public void setServicePriceChanged(Boolean servicePriceChanged) {
        this.servicePriceChanged = servicePriceChanged;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public WorkmanModel getWorkman() {
        return workman;
    }

    public void setWorkman(WorkmanModel workman) {
        this.workman = workman;
    }
}
