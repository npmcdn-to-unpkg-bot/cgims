package com.mimi.cgims.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.annotation.Signed;
import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "tbl_user")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class UserModel extends BaseModel{
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @Column(name = "id", nullable = false)
    private String id;

    @Pattern(regexp = "[A-Za-z0-9_]{5,20}", message = "登录名格式有误")
    @Column(unique = true)
    private String loginName;

    @Pattern(regexp = "[0-9a-zA-Z\u4E00-\u9FA5_-]{0,50}", message = "姓名格式有误")
    private String name;

    @Pattern(regexp = "^(1[0-9]{10})|$", message = "电话格式有误")
    private String phoneNum;

    @Pattern(regexp = "[A-Za-z0-9]{6,200}", message = "密码格式有误")
    private String password;

    @Pattern(regexp = "[A-Za-z0-9]{15,18}", message = "身份证格式有误")
    private String identity;

    @Size(max = 200)
    private String description;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "tr_user_role")
//    @JsonIgnore
    private List<RoleModel> roles;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<OrderModel> orders;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "slaves")
    @JsonIgnore
    private List<UserModel> masters;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "tr_user_user")
//    @JsonIgnore
    private List<UserModel> slaves;

    public List<OrderModel> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderModel> orders) {
        this.orders = orders;
    }

    public List<UserModel> getMasters() {
        return masters;
    }

    public void setMasters(List<UserModel> masters) {
        this.masters = masters;
    }

    public List<UserModel> getSlaves() {
        return slaves;
    }

    public void setSlaves(List<UserModel> slaves) {
        this.slaves = slaves;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<RoleModel> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleModel> roles) {
        this.roles = roles;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }
}
