package com.slsd.dao;

import com.slsd.entiy.Activity;
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
public class ActivityDao {
	@Resource
	private JdbcTemplate jdbcTemplate;

	public List<Activity> SelectActivity(Integer offset, Integer length, Activity activity) {
		String sql = "select * from activity where 1=1 ";
		List<Object> sqlParams = new ArrayList<Object>();
		if (activity != null) {
			if (activity.getName() != "") {
				sql += "and activity.name like ?";
				sqlParams.add("%" + activity.getName() + "%");
			}
			if (activity.getPublishStatus() != null) {
				sql += "and publishStatus = ? ";
				sqlParams.add(activity.getPublishStatus());
			}
			sql += " order by id asc limit ?,?";
			sqlParams.add(offset);
			sqlParams.add(length);
		}
		try {
			RowMapper<Activity> rowMapper = BeanPropertyRowMapper.newInstance(Activity.class);
			return jdbcTemplate.query(sql, sqlParams.toArray(new Object[sqlParams.size()]), rowMapper);
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
	}

	public Activity SelectByid(Integer id){
		String sql="select * from activity where id=?";
		try {
			RowMapper<Activity> rowMapper = BeanPropertyRowMapper.newInstance(Activity.class);
			return jdbcTemplate.queryForObject(sql,rowMapper,id);
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
	}

	public Integer countActivity(Activity activity) {
		String sql = "select count(*) from activity where 1=1 ";
		List<Object> sqlParams = new ArrayList<Object>();
		if (activity != null) {
			if (activity.getName() != "") {
				sql += "and activity.name like ?";
				sqlParams.add("%" + activity.getName() + "%");
			}
			if (activity.getPublishStatus() != null) {
				sql += "and publishStatus = ? ";
				sqlParams.add(activity.getPublishStatus());
			}
		}
		try {
			return this.jdbcTemplate.queryForObject(sql,
					sqlParams.toArray(new Object[sqlParams.size()]), Integer.class);
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
	}

	public boolean addActivity(Activity activity) {
		String sql = "insert into activity(activity.name, title, coverImg, content, bannerImg, contentEnd, " +
				"publishStatus, createTime, publishUser) VALUES (?,?,?,?,?,?,?,?,?)";
		try {
			return jdbcTemplate.update(sql, activity.getName(), activity.getTitle(), activity.getCoverImg(),
					activity.getContent(), activity.getBannerImgs(), activity.getContentEnd(),
					activity.getPublishStatus(), activity.getCreateTime(), activity.getPublishUser()) > 0 ? true : false;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean updateActivity(Activity activity) {
		String sql = "update activity set activity.name=?,title=?,coverImg=?,content=?," +
				"bannerImg=?,contentEnd=?,publishStatus=?,createTime=?,publishUser=? where  id=?";
		try {
			return jdbcTemplate.update(sql, activity.getName(), activity.getTitle(), activity.getCoverImg(),
					activity.getContent(), activity.getBannerImgs(), activity.getContentEnd(), activity.getPublishStatus(),
					activity.getModifyTime(), activity.getPublishUser(), activity.getId()) > 0 ? true : false;
		} catch (Exception e) {
			return false;
		}
	}

		public boolean delActivity(Integer id){
		String sql="delete from activity where id=? ";
		try {
			return jdbcTemplate.update(sql,id) > 0 ? true : false;
		} catch (Exception e) {
			return false;
		}
	}
}
