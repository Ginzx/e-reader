package com.slsd.service.impl;

import com.slsd.dao.ManageDao;
import com.slsd.entiy.Manager;
import com.slsd.service.ManagerService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("ManageService")
public class ManagerServiceImpl implements ManagerService {

	@Resource
	private ManageDao manageDao;

	public Manager login(String account, String pwd) throws Exception {
		return manageDao.login(account, pwd);
	}

	public List<Manager> selectAll(Integer offset, Integer length) throws Exception {
		return manageDao.selectAll((offset-1)*length, length);
	}

	public Manager selectbyaccount(String account) throws Exception {
		return manageDao.selectbyaccount(account);
	}

	@Override
	public List<Manager> selectmanager(Integer offset, Integer length, Manager manager) throws Exception {
		return manageDao.selectmanager((offset-1)*length, length, manager);
	}

	public boolean addmanager(Manager manager) throws Exception {
		return manageDao.addmanager(manager);
	}

	public boolean editmanager(Manager manager) throws Exception {
		return manageDao.editmanager(manager);
	}

	public boolean delmanager(Integer mid) throws Exception {
		return manageDao.delmanager(mid);
	}

	@Override
	public Manager SelectById(Integer mid) {
		return manageDao.SelectById(mid);
	}

	@Override
	public Integer CountManager(Manager manager) throws Exception {
		return manageDao.CountManager(manager);
	}
}
