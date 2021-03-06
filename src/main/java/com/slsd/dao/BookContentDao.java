package com.slsd.dao;

import com.slsd.entiy.Book;
import com.slsd.entiy.BookContent;
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
public class BookContentDao {
	@Resource
	JdbcTemplate jdbcTemplate;

	public List<BookContent> Selecttitle(Integer offset, Integer length, Integer id) {
		String sql = "select id,Bookcontent.number,chapter from Bookcontent where bid = ? order by Bookcontent.number asc limit ?,?";
		try {
			RowMapper<BookContent> rowMapper = BeanPropertyRowMapper.newInstance(BookContent.class);
			return jdbcTemplate.query(sql, rowMapper, id, offset, length);
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
	}

	public Integer CountBookContent(Integer id) {
		String sql = "select count(*) from bookcontent where bid=?";
		List<Object> sqlParams = new ArrayList<Object>();
		sqlParams.add(id);
		try {
			return this.jdbcTemplate.queryForObject(sql,
					sqlParams.toArray(new Object[sqlParams.size()]), Integer.class);
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
	}

	public BookContent SelectContent(Integer id) {
		String sql = "select * from bookcontent where id=?";
		try {
			RowMapper<BookContent> rowMapper = BeanPropertyRowMapper.newInstance(BookContent.class);
			return jdbcTemplate.queryForObject(sql, rowMapper, id);
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
	}

	public boolean addBookcontent(BookContent bookContent, Integer bid) {
		String sql = "insert into bookcontent (bid,chapter,content,number)values (?,?,?,?)";
		try {
			return jdbcTemplate.update(sql, bid, bookContent.getChapter(),
					bookContent.getContent(), bookContent.getNumber()) > 0 ? true : false;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean delBookContentall(Integer bid) {
		String sql = "delete from bookcontent where bid=?";
		try {
			return jdbcTemplate.update(sql, bid) > 0 ? true : false;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean delBookContent(Integer id) {
		String sql = "delete from bookcontent where id=?";
		try {
			return jdbcTemplate.update(sql, id) > 0 ? true : false;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean updateBookContent(BookContent bookContent) {
		String sql = "update bookcontent set chapter=?,content=?,number=? where id=?";
		try {
			return jdbcTemplate.update(sql, bookContent.getChapter(),
					bookContent.getContent(), bookContent.getNumber(), bookContent.getBid()) > 0 ? true : false;
		} catch (Exception e) {
			return false;
		}
	}


}
