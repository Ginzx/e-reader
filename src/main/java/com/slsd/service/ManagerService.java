package com.slsd.service;


import com.slsd.entiy.Manager;

import java.util.List;

public interface ManagerService {

	public Manager login(String account, String pwd) throws Exception;

	public List<Manager> selectAll(Integer offset, Integer length) throws Exception;

	public Manager selectbyaccount(String account) throws Exception;

	public List<Manager> selectmanager(Integer offset, Integer length,Manager manager) throws Exception;

	public boolean addmanager(Manager manager) throws Exception;

	public boolean editmanager(Manager manager) throws Exception;

	public boolean delmanager(Manager manager) throws Exception;
}
