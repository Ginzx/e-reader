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

	public List<ClassificationService> SelectFather() {
		String sql = "select * from Classification where parent_id=0";
		try {
			RowMapper<ClassificationService> rowMapper = BeanPropertyRowMapper.newInstance(ClassificationService.class);
			return jdbcTemplate.query(sql, rowMapper);
		} catch (Exception e) {
			return null;
		}
	}

	public List<ClassificationService> SelectSon(Integer parent_id) {
		String sql = "select * from Classification where parent_id=?";
		try {
			RowMapper<ClassificationService> rowMapper = BeanPropertyRowMapper.newInstance(ClassificationService.class);
			return jdbcTemplate.query(sql, rowMapper, parent_id);
		} catch (Exception e) {
			return null;
		}
	}

	public boolean AddClass(Classification classification) {
		String sql = "insert into classification (classification_name,parent_id) values(?,?)";
		try {
			return jdbcTemplate.update(sql, classification.getClassificationName(), classification.getParentId()) > 0 ? true : false;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean editClass(Classification classification){
		String sql="update classification set classification_name=?,parent_id=? ";
		try {
			return jdbcTemplate.update(sql, classification.getClassificationName(), classification.getParentId()) > 0 ? true : false;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean delClass(Integer id){
		String sql="delete from classification where classification_id=?";
		try {
			return jdbcTemplate.update(sql,id) > 0 ? true : false;
		} catch (Exception e) {
			return false;
		}
	}
}
