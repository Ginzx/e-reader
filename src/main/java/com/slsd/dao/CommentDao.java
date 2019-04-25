package com.slsd.dao;

import com.slsd.entiy.Comment;
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
public class CommentDao {
	@Resource
	private JdbcTemplate jdbcTemplate;

	public List<Comment> SelectComment(Integer offset, Integer length,Comment comment){
		String sql="select * from comment where 1=1 ";
		List<Object> sqlParams = new ArrayList<Object>();
		if(comment!=null){
			if(comment.getAccount()!=""&&comment.getAccount()!=null){
				sql+="and account like ?";
				sqlParams.add("%" + comment.getAccount() + "%");
			}
			if(comment.getStatus()!=null){
				sql+="and status = ? ";
				sqlParams.add( comment.getAccount());
			}
			sql += " order by id asc limit ?,?";
			sqlParams.add(offset);
			sqlParams.add(length);
		}
		try {
			RowMapper<Comment> rowMapper = BeanPropertyRowMapper.newInstance(Comment.class);
			return jdbcTemplate.query(sql, sqlParams.toArray(new Object[sqlParams.size()]), rowMapper);
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
	}

	public Integer CountComment(Comment comment){
		String sql="select count(*) from comment where 1=1 ";
		List<Object> sqlParams = new ArrayList<Object>();
		if(comment!=null){
			if(comment.getAccount()!=""&&comment.getAccount()!=null){
				sql+="and account like ?";
				sqlParams.add("%" + comment.getAccount() + "%");
			}
			if(comment.getStatus()!=null){
				sql+="and status = ? ";
				sqlParams.add("%" + comment.getAccount() + "%");
			}
		}
		try {
			return this.jdbcTemplate.queryForObject(sql,
					sqlParams.toArray(new Object[sqlParams.size()]), Integer.class);
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
	}

	public boolean updateComment(Integer id,Integer status){
		String sql="update comment set status=? where id=?";
		try {
			return jdbcTemplate.update(sql,status,id) > 0 ? true : false;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean delComment(Integer id){
		String sql="delete from comment where id=?";
		try {
			return jdbcTemplate.update(sql,id) > 0 ? true : false;
		} catch (Exception e) {
			return false;
		}
	}
}
