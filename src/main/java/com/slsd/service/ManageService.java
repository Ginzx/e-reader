package com.slsd.service;


import com.slsd.entiy.Manager;

import java.util.List;

public interface ManageService {

	public Manager login(String account, String pwd) throws Exception;

	public List<Manager> selectAll() throws Exception;

	public Manager selectbyaccount(String account) throws Exception;

	public boolean addmanager(Manager manager) throws Exception;

	public boolean editmanager(Manager manager) throws Exception;

	public boolean delmanager(Manager manager) throws Exception;
}
