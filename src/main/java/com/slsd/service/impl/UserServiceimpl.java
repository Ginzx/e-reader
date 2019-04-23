package com.slsd.service.impl;

import com.slsd.dao.UserDao;
import com.slsd.entiy.Group;
import com.slsd.entiy.User;
import com.slsd.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @program: ereader
 * @description:
 * @author: Mr.Wang
 **/
@Service("UserService")
public class UserServiceimpl implements UserService {

	@Resource
	private UserDao userDao;

	@Override
	public List<User> selectUser(Integer offset, Integer length, User user) {
		return userDao.selectUser((offset-1)*length,length,user);
	}

	@Override
	public Integer countUser(User user) {
		return userDao.countUser(user);
	}

	@Override
	public boolean editUser(User user) {
		return userDao.editUser(user);
	}

	@Override
	public boolean addUser(User user) {
		return userDao.addUser(user);
	}

	@Override
	public List<User> selectUserbyGroup(String groupname) {
		return userDao.selectUserbyGroup(groupname);
	}

	@Override
	public User SelectUserByid(Integer id) {
		return userDao.SelectUserByid(id);
	}

	@Override
	public List<Group> SelectGroup(Integer offset, Integer length,Group group) {
		return userDao.SelectGroup((offset-1)*length,length,group);
	}

	@Override
	public Group SelectGroupbyid(Integer id) {
		return userDao.SelectGroupbyid(id);
	}

	@Override
	public boolean addgroup(Group group) {
		return userDao.addgroup(group);
	}

	@Override
	public boolean editgroup(Group group) {
		return userDao.editgroup(group);
	}

	@Override
	public boolean delgroup(Group group) {
		return userDao.delgroup(group);
	}

	@Override
	public Integer CountGroup(Group group) {
		return userDao.CountGroup(group);
	}
}
