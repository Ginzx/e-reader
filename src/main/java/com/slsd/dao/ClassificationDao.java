package com.slsd.dao;

import com.slsd.entiy.Classification;
import com.slsd.service.ClassificationService;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * @program: ereader
 * @description:书籍分类模块
 * @author: Mr.Wang
 **/
@Repository
public class ClassificationDao {

	@Resource
	private JdbcTemplate jdbcTemplate;

	public List<Classification> Select() {
		String sql = "select * from Classification  ";
		try {
			RowMapper<Classification> rowMapper = BeanPropertyRowMapper.newInstance(Classification.class);
			return jdbcTemplate.query(sql, rowMapper);
		} catch (Exception e) {
			return null;
		}
	}

	public boolean AddClass(Classification classification) {
		String sql = "insert into classification (classification_name) values(?)";
		try {
			return jdbcTemplate.update(sql, classification.getClassificationName()) > 0 ? true : false;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean editClass(Classification classification) {
		String sql = "update classification set classification_name=? where classification_id=?";
		try {
			return jdbcTemplate.update(sql, classification.getClassificationName(), classification.getClassificationId()) > 0 ? true : false;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean delClass(Integer id) {
		String sql = "delete from classification where classification_id=?";
		try {
			return jdbcTemplate.update(sql, id) > 0 ? true : false;
		} catch (Exception e) {
			return false;
		}
	}
}
