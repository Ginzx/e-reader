package com.slsd.entiy;

import java.io.Serializable;
import java.util.Date;

/**
 * @program: ereader
 * @description:用户
 * @author: Mr.Wang
 **/
public class User implements Serializable {

	private static final long serialVersionUID = -8834508903158989683L;
	private Integer uId; //用户编号
	private String userName;//用户账号
	private String userPwd;//用户密码
	private String EMail; //用户邮箱地址
	private Date RegisterTime;//注册时间
	private String RegisterIp;//注册IP
	private String IdCard;//身份证号码
	private String phone;//电话号码
	private String imgeUrl;//头像地址
	private String name;//用户姓名
	private String usergroup;

	public String getUsergroup() {
		return usergroup;
	}

	public void setUsergroup(String usergroup) {
		this.usergroup = usergroup;
	}

	@Override
	public String toString() {
		return "User{" +
				"uId=" + uId +
				", userName='" + userName + '\'' +
				", userPwd='" + userPwd + '\'' +
				", EMail='" + EMail + '\'' +
				", RegisterTime=" + RegisterTime +
				", RegisterIp='" + RegisterIp + '\'' +
				", IdCard='" + IdCard + '\'' +
				", phone='" + phone + '\'' +
				", imgeUrl='" + imgeUrl + '\'' +
				", name='" + name + '\'' +
				'}';
	}

	public Integer getuId() {
		return uId;
	}

	public void setuId(Integer uId) {
		this.uId = uId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPwd() {
		return userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}

	public String getEMail() {
		return EMail;
	}

	public void setEMail(String EMail) {
		this.EMail = EMail;
	}

	public Date getRegisterTime() {
		return RegisterTime;
	}

	public void setRegisterTime(Date registerTime) {
		RegisterTime = registerTime;
	}

	public String getRegisterIp() {
		return RegisterIp;
	}

	public void setRegisterIp(String registerIp) {
		RegisterIp = registerIp;
	}

	public String getIdCard() {
		return IdCard;
	}

	public void setIdCard(String idCard) {
		IdCard = idCard;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getImgeUrl() {
		return imgeUrl;
	}

	public void setImgeUrl(String imgeUrl) {
		this.imgeUrl = imgeUrl;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
