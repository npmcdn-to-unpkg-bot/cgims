package com.mimi.sxp.model;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

public abstract class AbstractModel implements Serializable {
	private static final long serialVersionUID = -6539982236830027288L;
	public abstract String getId() ;

	public abstract void setId(String id);

	public abstract Date getCreateDate();

	public abstract void setCreateDate(Date createDate);

	public abstract Date getUpdateDate();

	public abstract void setUpdateDate(Date updateDate) ;

	public abstract String getCreaterId();

	public abstract void setCreaterId(String createrId);

	public abstract String getEditorId();

	public abstract void setEditorId(String editorId);
	
	public abstract String getDescription();
	
	public abstract void setDescription(String description);

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
