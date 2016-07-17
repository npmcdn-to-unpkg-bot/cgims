package com.mimi.sxp.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Analyzer;
import org.hibernate.search.annotations.Facet;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Fields;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;

import com.chenlb.mmseg4j.analysis.ComplexAnalyzer;
import com.mimi.sxp.web.validator.DateFormat;

@Entity
@Table(name = "tbl_information")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Indexed
@Analyzer(impl=ComplexAnalyzer.class)//分词器  
public class InformationModel extends AbstractModel {
	private static final long serialVersionUID = -7667149470334675951L;

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@Column(name = "id", nullable = false)
	private String id;

	@Pattern(regexp = "[0-9a-zA-Z\u4E00-\u9FA5_-]{1,80}", message = "名格式有误")
	@Field(index=Index.YES, analyze=Analyze.YES, store=Store.NO)
//	@Field(index=Index.NO, analyze=Analyze.NO, store=Store.NO)
//	@Fields({
//	    @Field,
//	    @Field(name="sorting_name", analyze=Analyze.NO)
//	})
	private String name;
	
	private String preViewUrl;
	
	private String author;
	
	private String summary;

	@Type(type="text")
    private String content;

	@Field(index=Index.YES, analyze=Analyze.YES, store=Store.NO)
	@Type(type="text")
    private String contentText;

	@Column(updatable = false)
	@DateFormat(message = "创建日期有误")
	private Date createDate;

	@DateFormat(message = "更新日期有误")
	@Field(index=Index.NO, analyze=Analyze.NO, store=Store.NO)
//	@Facet
	private Date updateDate;

	@Column(updatable = false)
	private String createrId;

	private String editorId;

	private String description;

	@Column(nullable=false,columnDefinition="INT default 0")
	private int priority;

	public String getContentText() {
		return contentText;
	}

	public void setContentText(String contentText) {
		this.contentText = contentText;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
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

	public String getPreViewUrl() {
		return preViewUrl;
	}

	public void setPreViewUrl(String preViewUrl) {
		this.preViewUrl = preViewUrl;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}


	


}
