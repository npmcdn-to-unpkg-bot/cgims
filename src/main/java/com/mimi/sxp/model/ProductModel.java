package com.mimi.sxp.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
@Table(name = "tbl_product")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ProductModel extends AbstractModel {

	private static final long serialVersionUID = 6255122145362319006L;

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@Column(name = "id", nullable = false)
	private String id;

	@Pattern(regexp = "[0-9a-zA-Z\u4E00-\u9FA5_-]{1,20}", message = "名格式有误")
	private String name;
	
	private String preViewUrl;

	private String introduction;
	
	@DateFormat(message = "创建日期有误")
	private Date createDate;

	@DateFormat(message = "更新日期有误")
	private Date updateDate;

	private String createrId;

	private String editorId;
	
	private String description;

	@Column(nullable=false,columnDefinition="INT default 0")
	private int priority;
	
	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "shopId", updatable = false)
	private ShopModel shop;

	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "products")
	@JsonIgnore
	private List<DesignPanoramaModel> designPanoramas;

	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "products")
	@JsonIgnore
	private List<DesignImageModel> designImages;

	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "products")
	@JsonIgnore
	private List<DesignRingModel> designRings;

	public List<DesignPanoramaModel> getDesignPanoramas() {
		return designPanoramas;
	}

	public void setDesignPanoramas(List<DesignPanoramaModel> designPanoramas) {
		this.designPanoramas = designPanoramas;
	}

	public List<DesignImageModel> getDesignImages() {
		return designImages;
	}

	public void setDesignImages(List<DesignImageModel> designImages) {
		this.designImages = designImages;
	}

	public List<DesignRingModel> getDesignRings() {
		return designRings;
	}

	public void setDesignRings(List<DesignRingModel> designRings) {
		this.designRings = designRings;
	}

	public String getPreViewUrl() {
		return preViewUrl;
	}

	public void setPreViewUrl(String preViewUrl) {
		this.preViewUrl = preViewUrl;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public ShopModel getShop() {
		return shop;
	}

	public void setShop(ShopModel shop) {
		this.shop = shop;
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
