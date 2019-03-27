package com.slsd.dao;


import com.slsd.entiy.Manager;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ManageDao {

	@Resource
	private JdbcTemplate jdbcTemplate;

	/**
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

	public List<Manager> selectAll() {
		String sql = "select * from manager";
		try {
			RowMapper<Manager> rowMapper = BeanPropertyRowMapper.newInstance(Manager.class);
			return this.jdbcTemplate.query(sql, rowMapper);
		} catch (IndexOutOfBoundsException e) {
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

	public boolean delmanager(Manager manager) {
		String sql = "delete from manager where M_id=?";
		try {
			return this.jdbcTemplate.update(sql, manager.getmId()) > 0 ? true : false;
		} catch (Exception e) {
			return false;
		}
	}

}
