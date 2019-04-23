package com.slsd.dao;


import com.slsd.entiy.Manager;
import com.slsd.util.SQLUtil;
import com.slsd.util.StringUtil;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 管理员模块
 */
@Repository
public class ManageDao {

	@Resource
	private JdbcTemplate jdbcTemplate;

	/**
	 * 登录
	 *
	 * @param account
	 * @param pwd
	 * @return
	 */
	public Manager login(String account, String pwd) {
		String sql = "select * from manager where account=? and pwd=? and status<>2";
		try {
			RowMapper<Manager> rowMapper = BeanPropertyRowMapper.newInstance(Manager.class);
			return this.jdbcTemplate.queryForObject(sql, rowMapper, account, pwd);
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
	}

	/**
	 * 查找所有后台用户
	 *
	 * @return
	 */
	public List<Manager> selectAll(Integer offset, Integer length) {
		String sql = "select * from manager order by M_id desc ";
		try {
			sql = SQLUtil.getPageSQL(sql, offset, length, SQLUtil.DBMS_MYSQL_TYPE);
			RowMapper<Manager> rowMapper = BeanPropertyRowMapper.newInstance(Manager.class);
			return this.jdbcTemplate.query(sql, rowMapper);
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
	}

	public Integer CountManager(Manager manager) {
		String sql = "select count(*) from manager where 1=1 ";
		List<Object> sqlParams = new ArrayList<Object>();
		if (manager != null) {
			if (manager.getAccount()!="") {
				sql += "and account like ?";
				sqlParams.add("%" + manager.getAccount() + "%");
			}
			if (manager.getName()!="") {
				sql += "and manager.name like ?";
				sqlParams.add("%" + manager.getName() + "%");
			}
			if (manager.getStatus() != null && manager.getStatus() > 0) {
				sql += "and status=?";
				sqlParams.add(manager.getStatus());
			}
		}
		try {
			return this.jdbcTemplate.queryForObject(sql,
					sqlParams.toArray(new Object[sqlParams.size()]), Integer.class);
		} catch (Exception e) {
			return null;
		}
	}


	public List<Manager> selectmanager(Integer offset, Integer length, Manager manager) {
		String sql = "select * from manager where 1=1 ";
		List<Object> sqlParams = new ArrayList<Object>();
		if (manager != null) {
			if (manager.getAccount()!="") {
				sql += "and account like ? ";
				sqlParams.add("%" + manager.getAccount() + "%");
			}
			if (manager.getName()!="") {
				sql += "and manager.name like ? ";
				sqlParams.add("%" + manager.getName() + "%");
			}
			if (manager.getStatus() != null && manager.getStatus() > 0) {
				sql += "and status=? ";
				sqlParams.add(manager.getStatus());
			}
			sql += "order by m_id desc limit ?,?";
			sqlParams.add(offset);
			sqlParams.add(length);
		}
		try {
			RowMapper<Manager> rowMapper = BeanPropertyRowMapper.newInstance(Manager.class);
			return jdbcTemplate.query(sql, sqlParams.toArray(new Object[sqlParams.size()]), rowMapper);
		} catch (Exception e) {
			return null;
		}
	}

	public Manager selectbyaccount(String account) {
		String sql = "select * from manager where account=? ";
		try {
			RowMapper<Manager> rowMapper = BeanPropertyRowMapper.newInstance(Manager.class);
			return this.jdbcTemplate.queryForObject(sql, rowMapper, account);
		} catch (Exception e) {
			return null;

		}
	}

	public Manager SelectById(Integer mid) {
		String sql = "select * from manager where M_id=?";
		try {
			RowMapper<Manager> rowMapper = BeanPropertyRowMapper.newInstance(Manager.class);
			return this.jdbcTemplate.queryForObject(sql, rowMapper, mid);
		} catch (Exception e) {
			return null;
		}
	}

	public boolean addmanager(Manager manager) {
		String sql = "insert INTO manager(account,name,pwd,Status,Create_Time)VALUES(?,?,?,1,?);";
		try {
			return this.jdbcTemplate.update(sql, manager.getAccount(), manager.getName(), manager.getPwd(), manager.getCreateTime()) > 0 ? true : false;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean editmanager(Manager manager) {
		String sql = "update manager SET " +
				"account=?,Name=?,Pwd=?,Status=?,Modify_Time=?" +
				"WHERE M_id=?";
		try {
			return this.jdbcTemplate.update(sql, manager.getAccount(),
					manager.getName(), manager.getPwd(), manager.getStatus(), manager.getModifyTime(), manager.getmId()) > 0 ? true : false;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean delmanager(Integer mid) {
		String sql = "delete from manager where M_id=?";
		try {
			return this.jdbcTemplate.update(sql, mid) > 0 ? true : false;
		} catch (Exception e) {
			return false;
		}
	}

}
