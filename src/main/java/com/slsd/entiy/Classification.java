package com.slsd.entiy;

import java.io.Serializable;
import java.util.List;

/**
 * @program: ereader
 * @description:书籍分类
 * @author: Mr.Wang
 **/
public class Classification implements Serializable {

	private static final long serialVersionUID = 5514688280240189687L;
	private Integer classificationId; //书籍类别编号
	private String classificationName;//书籍类别名称
	private Integer parentId; //父类id
	private List<Classification> classifications; //子类书籍类别

	@Override
	public String toString() {
		return "classification{" +
				"classificationId=" + classificationId +
				", classificationName='" + classificationName + '\'' +
				", classifications=" + classifications +
				'}';
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public Integer getClassificationId() {
		return classificationId;
	}

	public void setClassificationId(Integer classificationId) {
		this.classificationId = classificationId;
	}

	public String getClassificationName() {
		return classificationName;
	}

	public void setClassificationName(String classificationName) {
		this.classificationName = classificationName;
	}

	public List<Classification> getClassifications() {
		return classifications;
	}

	public void setClassifications(List<Classification> classifications) {
		this.classifications = classifications;
	}
}
