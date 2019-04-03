package com.slsd.entiy;

import java.io.Serializable;
import java.util.Date;

/**
 * @program: ereader
 *
 * @description:管理员日志
 *
 * @author: Mr.Wang
 *
 **/
public class Managelog implements Serializable {


	private static final long serialVersionUID = -6440143328027516205L;
	private Integer id;  //操作编号
	private Integer mId; //操作管理员编号
	private String account;//操作管理员账号
	private String content;//操作内容
	private String ip;//操作IP地址
	private String remark;//操作结果
	private Date createTime;//操作时间

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remrak) {
		this.remark = remrak;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "Managelog{" +
				"id=" + id +
				", mId=" + mId +
				", account='" + account + '\'' +
				", content='" + content + '\'' +
				", ip='" + ip + '\'' +
				", remrak='" + remark + '\'' +
				", createTime=" + createTime +
				'}';
	}
}
