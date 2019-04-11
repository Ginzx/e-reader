package com.slsd.dao;

import com.slsd.entiy.Managerlog;
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

	public List<Managerlog> Select(Managerlog managerlog) {
		String sql = "select * from manager_log where 1=1";
		List<Object> sqlParams = new ArrayList<Object>();
		if (managerlog != null) {
			if (!StringUtil.isEmpty(managerlog.getAccount())) {
				sql += "and account like ?";
				sqlParams.add("%" + managerlog.getAccount() + "%");
			}
			if (!StringUtil.isEmpty(managerlog.getRemark())) {
				sql += "and remark like ?";
				sqlParams.add("%" + managerlog.getRemark() + "%");
			}
			sql += "ORDER BY Create_time DESC";
		}
		try {
			RowMapper<Managerlog> rowMapper = BeanPropertyRowMapper.newInstance(Managerlog.class);
			return this.jdbcTemplate.query(sql, sqlParams.toArray(new Object[sqlParams.size()]), rowMapper);
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
	}

	public boolean AddManagelog(Managerlog managerlog) {
		String sql = "insert into manager_log (m_id,account,content,Ip,remark,create_time) valus(?,?,?,?,?,?)";
		try {
			return this.jdbcTemplate.update(sql, managerlog.getmId(), managerlog.getAccount(), managerlog.getContent(),
					managerlog.getIp(), managerlog.getRemark(), managerlog.getCreateTime()) > 0 ? true : false;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean editManagelog(Managerlog managerlog) {

		String sql = "update manager_log set m_id=?,account=?,content=?,Ip=?,remark=?,create_time=?";
		try {
			return this.jdbcTemplate.update(sql, managerlog.getmId(), managerlog.getAccount(), managerlog.getContent(),
					managerlog.getIp(), managerlog.getRemark(), managerlog.getCreateTime()) > 0 ? true : false;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean delManagerlog(Managerlog managerlog){
		String sql="delete from manager_log where id=?";
		try {
			return this.jdbcTemplate.update(sql, managerlog.getId()) > 0 ? true : false;
		} catch (Exception e) {
			return false;
		}

	}


}
