package com.slsd.service.impl;

import com.slsd.dao.ManageDao;
import com.slsd.entiy.Manager;
import com.slsd.service.ManageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("ManageService")
public class ManageServiceImpl implements ManageService {

	@Resource
	private ManageDao manageDao;

	public Manager login(String account, String pwd) throws Exception{
		return manageDao.login(account, pwd);
	}

	public List<Manager> selectAll() throws Exception{
		return manageDao.selectAll();
	}

	public Manager selectbyaccount(String account)throws Exception {
		return manageDao.selectbyaccount(account);
	}

	public boolean addmanager(Manager manager) throws Exception{
		return manageDao.addmanager(manager);
	}

	public boolean editmanager(Manager manager) throws Exception{
		return manageDao.editmanager(manager);
	}

	public boolean delmanager(Manager manager) throws Exception{
		return manageDao.delmanager(manager);
	}
}
