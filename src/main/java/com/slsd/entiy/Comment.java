package com.slsd.entiy;

import java.io.Serializable;
import java.util.Date;

/**
 * @program: ereader
 * @description:
 * @author: Mr.Wang
 **/
public class Comment implements Serializable {

	private static final long serialVersionUID = -3083196152077407057L;
	private Integer id;
	private String account;
	private String content;
	private String resource;
	private Integer status;
	private Date createTime;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
