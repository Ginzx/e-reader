package com.slsd.entiy;

import java.io.Serializable;

/**
 * @program: ereader
 * @description: 管理员菜单关联
 * @author: Mr.Wang
 **/
public class ManagerFeature implements Serializable {

	private static final long serialVersionUID = -7636967312604297517L;
	private Integer mId;
	private  Integer pId;

	public Integer getmId() {
		return mId;
	}

	public void setmId(Integer mId) {
		this.mId = mId;
	}

	public Integer getpId() {
		return pId;
	}

	public void setpId(Integer pId) {
		this.pId = pId;
	}
}
