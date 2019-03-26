package com.slsd.entiy;


import java.io.Serializable;
import java.util.Date;

public class Manager implements Serializable {

	private static final long serialVersionUID = -8878337478034276751L;

	private Integer mId;
	private String account;
	private String name;
	private String pwd;
	private Integer status;
	private Date createTime;
	private Date modifyTime;

	public Integer getmId() {
		return mId;
	}

	public void setmId(Integer mId) {
		this.mId = mId;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
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

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	@Override
	public String toString() {
		return "Manager{" +
				"mid=" + mId +
				", account='" + account + '\'' +
				", name='" + name + '\'' +
				", pwd='" + pwd + '\'' +
				", status=" + status +
				", createTime=" + createTime +
				", modifyTime=" + modifyTime +
				'}';
	}
}
