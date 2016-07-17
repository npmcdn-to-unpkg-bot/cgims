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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mimi.sxp.web.validator.DateFormat;

@Entity
@Table(name = "tbl_house_type")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class HouseTypeModel extends AbstractModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4269566832013521987L;

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@Column(name = "id", nullable = false)
	private String id;

	@Pattern(regexp = "[0-9a-zA-Z\u4E00-\u9FA5_-]{1,80}", message = "名格式有误")
	private String name;
	
	private String housePlanUrl;

    private Float insideArea;

    private Float grossFloorArea;

    private Integer roomNum;

    private Integer hallNum;

    private Integer kitchenNum;

    private Integer toiletNum;

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
	@JoinColumn(name = "realEstateProjectId", referencedColumnName="id", updatable = false)
	private RealEstateProjectModel realEstateProject;

	@OneToMany(mappedBy = "houseType",cascade = { CascadeType.PERSIST, CascadeType.MERGE,
			CascadeType.REMOVE })
	@JsonIgnore
	private List<DesignImageModel> designImages;

	@OneToMany(mappedBy = "houseType",cascade = { CascadeType.PERSIST, CascadeType.MERGE,
			CascadeType.REMOVE })
	@JsonIgnore
	private List<DesignRingModel> designRings;

	@OneToMany(mappedBy = "houseType",cascade = { CascadeType.PERSIST, CascadeType.MERGE,
			CascadeType.REMOVE })
	@JsonIgnore
	private List<DesignPanoramaModel> designPanoramas;

	public Float getInsideArea() {
		return insideArea;
	}

	public void setInsideArea(Float insideArea) {
		this.insideArea = insideArea;
	}

	public Float getGrossFloorArea() {
		return grossFloorArea;
	}

	public void setGrossFloorArea(Float grossFloorArea) {
		this.grossFloorArea = grossFloorArea;
	}

	public Integer getRoomNum() {
		return roomNum;
	}

	public void setRoomNum(Integer roomNum) {
		this.roomNum = roomNum;
	}

	public Integer getHallNum() {
		return hallNum;
	}

	public void setHallNum(Integer hallNum) {
		this.hallNum = hallNum;
	}

	public Integer getKitchenNum() {
		return kitchenNum;
	}

	public void setKitchenNum(Integer kitchenNum) {
		this.kitchenNum = kitchenNum;
	}

	public Integer getToiletNum() {
		return toiletNum;
	}

	public void setToiletNum(Integer toiletNum) {
		this.toiletNum = toiletNum;
	}

	public String getHousePlanUrl() {
		return housePlanUrl;
	}

	public void setHousePlanUrl(String housePlanUrl) {
		this.housePlanUrl = housePlanUrl;
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

	public List<DesignPanoramaModel> getDesignPanoramas() {
		return designPanoramas;
	}

	public void setDesignPanoramas(List<DesignPanoramaModel> designPanoramas) {
		this.designPanoramas = designPanoramas;
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

	public RealEstateProjectModel getRealEstateProject() {
		return realEstateProject;
	}

	public void setRealEstateProject(RealEstateProjectModel realEstateProject) {
		this.realEstateProject = realEstateProject;
	}

}
