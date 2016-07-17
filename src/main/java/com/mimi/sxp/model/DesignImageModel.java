package com.mimi.sxp.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mimi.sxp.web.validator.DateFormat;

@Entity
@Table(name = "tbl_design_image")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DesignImageModel extends AbstractModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5896205716602124639L;

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@Column(name = "id", nullable = false)
	private String id;

	@Pattern(regexp = "[0-9a-zA-Z\u4E00-\u9FA5_-]{1,80}", message = "名格式有误")
	private String name;

    private String contentUrl;
    
    private String outLinkUrl;

	@Column(updatable = false)
	@DateFormat(message = "创建日期有误")
	private Date createDate;

	@DateFormat(message = "更新日期有误")
	private Date updateDate;

	@Column(updatable = false)
	private String createrId;

	private String editorId;

	private String description;

	@Column(nullable=false,columnDefinition="INT default 0")
	private int priority;

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "houseTypeId", updatable = false)
	private HouseTypeModel houseType;

	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "tr_di_product")
	@JsonIgnore
	private List<ProductModel> products;
	
	public List<ProductModel> getProducts() {
		return products;
	}

	public void setProducts(List<ProductModel> products) {
		this.products = products;
	}
	
	public String getOutLinkUrl() {
		return outLinkUrl;
	}

	public void setOutLinkUrl(String outLinkUrl) {
		this.outLinkUrl = outLinkUrl;
	}

	public String getContentUrl() {
		return contentUrl;
	}

	public void setContentUrl(String contentUrl) {
		this.contentUrl = contentUrl;
	}

	public HouseTypeModel getHouseType() {
		return houseType;
	}

	public void setHouseType(HouseTypeModel houseType) {
		this.houseType = houseType;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
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


}
