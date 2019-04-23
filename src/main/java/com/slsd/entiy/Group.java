package com.slsd.entiy;

import java.io.Serializable;

/**
 * @program: ereader
 * @description:
 * @author: Mr.Wang
 **/
public class Group implements Serializable {
	private static final long serialVersionUID = 1087043734106816278L;
	private Integer groupid;
	private String groupname;

	public Integer getGroupid() {
		return groupid;
	}

	public void setGroupid(Integer groupid) {
		this.groupid = groupid;
	}

	public String getGroupname() {
		return groupname;
	}

	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}
}
