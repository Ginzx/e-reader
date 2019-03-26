package com.slsd.service.impl;

import com.slsd.dao.ManageDao;
import com.slsd.entiy.Manager;
import com.slsd.service.ManageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("ManageService")
public class ManageServiceImpl implements ManageService {

	@Resource
	private ManageDao manageDao;

	public Manager login(String account, String pwd) {
		return manageDao.login(account,pwd);
	}
}
