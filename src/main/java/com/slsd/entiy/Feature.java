package com.slsd.entiy;

import java.io.Serializable;
import java.util.List;

/**
 * @program: ereader
 * @description:功能模块
 * @author: Mr.Wang
 **/
public class Feature implements Serializable {

	private static final long serialVersionUID = 3582647077520838441L;
	private Integer fId;//功能模块id
	private String fName;//功能模块名称
	private Integer parent_id;//父类功能模块id
	private List<Feature> features;//子类功能列表

	@Override
	public String toString() {
		return "Feature{" +
				"fId=" + fId +
				", fName='" + fName + '\'' +
				", parent_id=" + parent_id +
				", features=" + features +
				'}';
	}

	public Integer getParent_id() {
		return parent_id;
	}

	public void setParent_id(Integer parent_id) {
		this.parent_id = parent_id;
	}

	public Integer getfId() {
		return fId;
	}

	public void setfId(Integer fId) {
		this.fId = fId;
	}

	public String getfName() {
		return fName;
	}

	public void setfName(String fName) {
		this.fName = fName;
	}

	public List<Feature> getFeatures() {
		return features;
	}

	public void setFeatures(List<Feature> features) {
		this.features = features;
	}
}
