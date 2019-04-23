package com.slsd.dao;

import com.slsd.entiy.Group;
import com.slsd.entiy.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: ereader
 * @description:
 * @author: Mr.Wang
 **/
@Repository
public class UserDao {

	@Resource
	private JdbcTemplate jdbcTemplate;

	public List<User> selectUser(Integer offset, Integer length, User user) {
		String sql = "select * from User where 1=1 ";
		List<Object> sqlParams = new ArrayList<Object>();
		if (user != null) {
			if (user.getUserName() != "") {
				sql += "and username like ? ";
				sqlParams.add("%" + user.getUserName() + "%");
			}
			if (user.getName() != "") {
				sql += "and user.name like ? ";
				sqlParams.add("%" + user.getName() + "%");
			}
			if (user.getPhone() != "") {
				sql += "and phone like ? ";
				sqlParams.add("%" + user.getPhone() + "%");
			}
			if (user.getIdCard() != "") {
				sql += "and id_card like ? ";
				sqlParams.add("%" + user.getIdCard() + "%");
			}
			if (user.getUsergroup() != "" && user.getUsergroup() != null) {
				sql += "and  usergroup = ? ";
				sqlParams.add(user.getUsergroup());
			}
			sql += "order by u_id desc limit ?,? ";
			sqlParams.add(offset);
			sqlParams.add(length);
		}
		try {
			RowMapper<User> rowMapper = BeanPropertyRowMapper.newInstance(User.class);
			return jdbcTemplate.query(sql, sqlParams.toArray(new Object[sqlParams.size()]), rowMapper);
		} catch (Exception e) {
			return null;
		}
	}

	public Integer countUser(User user) {
		String sql = "select count(*) from User where 1=1 ";
		List<Object> sqlParams = new ArrayList<Object>();
		if (user != null) {
			if (user.getUserName() != "") {
				sql += "and username like ? ";
				sqlParams.add("%" + user.getUserName() + "%");
			}
			if (user.getName() != "") {
				sql += "and user.name like ? ";
				sqlParams.add("%" + user.getName() + "%");
			}
			if (user.getPhone() != "") {
				sql += "and phone like ? ";
				sqlParams.add("%" + user.getPhone() + "%");
			}
			if (user.getIdCard() != "") {
				sql += "and id_card like ? ";
				sqlParams.add("%" + user.getIdCard() + "%");
			}
			if (user.getUsergroup() != "" && user.getUsergroup() != null) {
				sql += "and  usergroup = ? ";
				sqlParams.add(user.getUsergroup());
			}
		}
		try {
			return this.jdbcTemplate.queryForObject(sql,
					sqlParams.toArray(new Object[sqlParams.size()]), Integer.class);
		} catch (Exception e) {
			return null;
		}
	}

	public boolean editUser(User user) {
		String sql = "update User set  usergroup=? where u_id=?";
		try {
			return this.jdbcTemplate.update(sql, user.getUsergroup(), user.getuId()) > 0 ? true : false;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean addUser(User user) {
		String sql = "insert into User (UserName, UserPwd, E_mail, Register_Time, " +
				"Register_IP, Id_card, Phone, Imge_url, user.Name, usergroup) values (?,?,?,?,?,?,?,?,?,?) ";
		try {
			return this.jdbcTemplate.update(sql, user.getUserName(), user.getUserPwd(), user.getEMail(), user.getRegisterTime(),
					user.getRegisterIp(), user.getIdCard(), user.getPhone(), user.getImgeUrl(), user.getName(), user.getUsergroup()) > 0 ? true : false;
		} catch (Exception e) {
			return false;
		}
	}

	public List<User> selectUserbyGroup(String groupname) {
		String sql = "select * from user where usergroup =?";
		try {
			RowMapper<User> rowMapper = BeanPropertyRowMapper.newInstance(User.class);
			return jdbcTemplate.query(sql, rowMapper, groupname);
		} catch (Exception e) {
			return null;
		}
	}

	public User SelectUserByid(Integer id) {
		String sql = "select * from user where u_id=? ";
		try {
			RowMapper<User> rowMapper = BeanPropertyRowMapper.newInstance(User.class);
			return jdbcTemplate.queryForObject(sql, rowMapper, id);
		} catch (Exception e) {
			return null;
		}
	}

	public List<Group> SelectGroup(Integer offset, Integer length, Group group) {
		String sql = "select * from usergroup where 1=1 ";
		List<Object> sqlParams = new ArrayList<Object>();
		if (group != null) {
			if (group.getGroupname() != "") {
				sql += "and groupname like ? ";
				sqlParams.add("%" + group.getGroupname() + "%");
			}
		}
		sql += "order by groupid desc limit ?,? ";
		sqlParams.add(offset);
		sqlParams.add(length);
		try {
			RowMapper<Group> rowMapper = BeanPropertyRowMapper.newInstance(Group.class);
			return jdbcTemplate.query(sql, sqlParams.toArray(new Object[sqlParams.size()]), rowMapper);
		} catch (Exception e) {
			return null;
		}
	}

	public Integer CountGroup(Group group) {
		String sql = "select count(*) from usergroup where 1=1 ";
		List<Object> sqlParams = new ArrayList<Object>();
		if (group != null) {
			if (group.getGroupname() != "") {
				sql += "and groupname like ? ";
				sqlParams.add("%" + group.getGroupname() + "%");
			}
		}
		try {
			return this.jdbcTemplate.queryForObject(sql,
					sqlParams.toArray(new Object[sqlParams.size()]), Integer.class);
		} catch (Exception e) {
			return null;
		}
	}

	public Group SelectGroupbyid(Integer id) {
		String sql = "select * from usergroup where groupid=?";
		try {
			RowMapper<Group> rowMapper = BeanPropertyRowMapper.newInstance(Group.class);
			return jdbcTemplate.queryForObject(sql, rowMapper, id);
		} catch (Exception e) {
			return null;
		}
	}

	public boolean addgroup(Group group) {
		String sql = "insert into usergroup(groupname) values (?)";
		try {
			return this.jdbcTemplate.update(sql, group.getGroupname()) > 0 ? true : false;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean editgroup(Group group) {
		String sql = "update usergroup set groupname=? where groupid=?";
		try {
			return this.jdbcTemplate.update(sql, group.getGroupname(), group.getGroupid()) > 0 ? true : false;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean delgroup(Group group) {
		String sql = "delete from usergroup where groupid=?";
		try {
			return this.jdbcTemplate.update(sql,group.getGroupid()) > 0 ? true : false;
		} catch (Exception e) {
			return false;
		}
	}
}
