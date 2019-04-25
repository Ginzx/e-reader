package com.slsd.entiy;

import java.io.Serializable;
import java.util.Date;

/**
 * @program: ereader
 * @description:
 * @author: Mr.Wang
 **/
public class Activity implements Serializable {

	private static final long serialVersionUID = 1548687205355148457L;
	private Integer id; //活动编号
	private String name; //活动名
	private String title; //标题
	private String coverImg; //封面图各导航页面显示
	private String bannerImgs; //banner图逗号分隔，最多5张
	private String content; //导语html源码格式，不含css和js
	private String contentEnd; //结束语html源码格式，不含css和js
	private Integer publishStatus; //发布状态0=未上架1=已上架2=已下架
	private String publishUser; //发布者帐号\
	private Date createTime; //创建时间
	private Date modifyTime; //修改时间

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCoverImg() {
		return coverImg;
	}

	public void setCoverImg(String coverImg) {
		this.coverImg = coverImg;
	}

	public String getBannerImgs() {
		return bannerImgs;
	}

	public void setBannerImgs(String bannerImgs) {
		this.bannerImgs = bannerImgs;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getContentEnd() {
		return contentEnd;
	}

	public void setContentEnd(String contentEnd) {
		this.contentEnd = contentEnd;
	}

	public Integer getPublishStatus() {
		return publishStatus;
	}

	public void setPublishStatus(Integer publishStatus) {
		this.publishStatus = publishStatus;
	}

	public String getPublishUser() {
		return publishUser;
	}

	public void setPublishUser(String publishUser) {
		this.publishUser = publishUser;
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
}
