package com.mimi.sxp.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Email;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mimi.sxp.web.validator.DateFormat;

@Entity
@Table(name = "tbl_user")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
//@Indexed
//@Analyzer(impl=ComplexAnalyzer.class)//分词器  
public class UserModel extends AbstractModel {
	private static final long serialVersionUID = 6129619356724468683L;

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@Column(name = "id", nullable = false)
	private String id;
	
//	@Field(index=Index.YES, analyze=Analyze.YES, store=Store.NO)
	@Pattern(regexp = "[A-Za-z0-9_]{5,20}", message = "用户名格式有误")
	@Column(unique=true)
	private String name;
	
//	@Field(index=Index.YES, analyze=Analyze.YES, store=Store.NO)
	@Email(message = "邮箱格式有误")
	private String email;

//	@Field(index=Index.YES, analyze=Analyze.YES, store=Store.NO)
	@Pattern(regexp = "^(1[0-9]{10})|$", message = "电话格式有误")
	private String phoneNum;

	@Pattern(regexp = "[A-Za-z0-9]{6,200}", message = "密码格式有误")
	private String password;
	
	private String headImgUrl;
	
	private Boolean locked;

	@Column(updatable=false)
	@DateFormat(message = "创建日期有误")
	private Date createDate;

	@DateFormat(message = "更新日期有误")
	private Date updateDate;

	@Column(updatable=false)
	private String createrId;
	
	private String editorId;

//	@Field(index=Index.YES, analyze=Analyze.YES, store=Store.YES)
	private String description;

	@Column(updatable=false)
	private String salt;
	

	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "tr_user_role")
//	@IndexedEmbedded(depth=1)
	@JsonIgnore
	private List<RoleModel> roles;

	public String getHeadImgUrl() {
		return headImgUrl;
	}

	public void setHeadImgUrl(String headImgUrl) {
		this.headImgUrl = headImgUrl;
	}

	public Boolean getLocked() {
		return locked;
	}

	public void setLocked(Boolean locked) {
		this.locked = locked;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getCreaterId() {
		return createrId;
	}

	public void setCreaterId(String createrId) {
		this.createrId = createrId;
	}

	public String getEditorId() {
		return editorId;
	}

	public void setEditorId(String editorId) {
		this.editorId = editorId;
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

}
