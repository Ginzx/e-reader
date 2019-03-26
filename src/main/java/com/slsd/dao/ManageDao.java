package com.slsd.dao;


import com.slsd.entiy.Manager;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

@Repository
public class ManageDao {

	@Resource
	private JdbcTemplate jdbcTemplate;

	public Manager login(String account,String pwd){
		String sql = "select * from manager where account=? and pwd=? and status<>2";
		try {
			RowMapper<Manager> rowMapper = BeanPropertyRowMapper.newInstance(Manager.class);
			return this.jdbcTemplate.query(sql, rowMapper, account, pwd).get(0);
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
	};

}
