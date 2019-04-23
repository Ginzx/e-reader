package com.slsd.service;

import com.slsd.entiy.Group;
import com.slsd.entiy.User;

import java.util.List;

public interface UserService {

	public List<User> selectUser(Integer offset, Integer length, User user);

	public Integer countUser(User user);

	public boolean editUser(User user);

	public boolean addUser(User user);

	public List<User> selectUserbyGroup(String groupname);

	public User SelectUserByid(Integer id);

	public List<Group> SelectGroup(Integer offset, Integer length,Group group);

	public Group SelectGroupbyid(Integer id);

	public boolean addgroup(Group group);

	public boolean editgroup(Group group);

	public boolean delgroup(Group group);

	public Integer CountGroup(Group group);
}
