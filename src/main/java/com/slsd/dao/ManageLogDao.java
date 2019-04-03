package com.slsd.dao;

import com.slsd.entiy.Managelog;
import com.slsd.util.StringUtil;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: ereader
 * @description:管理员操作日志记录
 * @author: Mr.Wang
 **/

@Repository
public class ManageLogDao {

	@Resource
	private JdbcTemplate jdbcTemplate;

	public List<Managelog> Select(Managelog managelog) {
		String sql = "select * from manager_log where account=? and pwd=? and status<>2";
		List<Object> sqlParams = new ArrayList<Object>();
		if (managelog != null) {
			if (!StringUtil.isEmpty(managelog.getAccount())) {
				sql += "and account like ?";
				sqlParams.add("%" + managelog.getAccount() + "%");
			}
			if (!StringUtil.isEmpty(managelog.getRemark())) {
				sql += "and remark like ?";
				sqlParams.add("%" + managelog.getRemark() + "%");
			}
			sql += "ORDER BY Create_time DESC";
		}
		try {
			RowMapper<Managelog> rowMapper = BeanPropertyRowMapper.newInstance(Managelog.class);
			return this.jdbcTemplate.query(sql, sqlParams.toArray(new Object[sqlParams.size()]), rowMapper);
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
	}

	public boolean AddManagelog(Managelog managelog) {
		String sql = "insert into manager_log (m_id,account,content,Ip,remark,create_time) valus(?,?,?,?,?,?)";
		try {
			return this.jdbcTemplate.update(sql, managelog.getmId(), managelog.getAccount(), managelog.getContent(),
					managelog.getIp(), managelog.getRemark(), managelog.getCreateTime()) > 0 ? true : false;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean editManagelog(Managelog managelog) {

		String sql = "update manager_log set m_id=?,account=?,content=?,Ip=?,remark=?,create_time=?";
		try {
			return this.jdbcTemplate.update(sql, managelog.getmId(), managelog.getAccount(), managelog.getContent(),
					managelog.getIp(), managelog.getRemark(), managelog.getCreateTime()) > 0 ? true : false;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean delManagerlog(Managelog managelog){
		String sql="delete from manager_log where id=?";
		try {
			return this.jdbcTemplate.update(sql, managelog.getId()) > 0 ? true : false;
		} catch (Exception e) {
			return false;
		}

	}


}
