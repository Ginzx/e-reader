package com.slsd.entiy;


import java.io.Serializable;
import java.util.Date;

public class Manager implements Serializable {

	private static final long serialVersionUID = -8878337478034276751L;

	private Integer mId; //管理员编号
	private String account;//管理员账号
	private String name; //管理员姓名
	private String pwd;  //管理员密码
	private Integer status; //管理员账号状态
	private Date createTime; //创建时间
	private Date modifyTime;//修改时间

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
