package com.slsd.dao;

import com.slsd.entiy.Book;
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
 * @description:
 * @author: Mr.Wang
 **/
@Repository
public class BookDao {

    @Resource
    JdbcTemplate jdbcTemplate;

    public List<Book> selectBook(Integer offset, Integer length, Book book) {
        String sql = "select * from Book where 1=1 ";
        List<Object> sqlParams = new ArrayList<Object>();
        if (book != null) {
            if (book.getBookName() != "") {
                sql += " and Bookname like ? ";
                sqlParams.add("%" + book.getBookName() + "%");
            }
            if (!StringUtil.isEmpty(book.getClassification())) {
                sql += " and classification like ? ";
                sqlParams.add("%" + book.getClassification() + "%");
            }
            if (book.getAuthor() != "") {
                sql += " and author like ? ";
                sqlParams.add("%" + book.getAuthor() + "%");
            }
            if (book.getBookName() != "") {
                sql += " and status = ? ";
                sqlParams.add("%" + book.getStatus() + "%");
            }
            sql += " order by bookid asc limit ?,?";
            sqlParams.add(offset);
            sqlParams.add(length);
        }
        try {
            RowMapper<Book> rowMapper = BeanPropertyRowMapper.newInstance(Book.class);
            return jdbcTemplate.query(sql, sqlParams.toArray(new Object[sqlParams.size()]), rowMapper);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    public Integer countbook(Book book) {
        String sql = "select count(*) from Book where 1=1 ";
        List<Object> sqlParams = new ArrayList<Object>();
        if (book != null) {
            if (book.getBookName() != "") {
                sql += " and book_name like ? ";
                sqlParams.add("%" + book.getBookName() + "%");
            }
            if (book.getClassification() != "") {
                sql += " and classification like ? ";
                sqlParams.add("%" + book.getClassification() + "%");
            }
            if (book.getAuthor() != "") {
                sql += " and author like ? ";
                sqlParams.add("%" + book.getAuthor() + "%");
            }
            if (book.getBookName() != "") {
                sql += " and status = ? ";
                sqlParams.add(book.getStatus());
            }
            if (book.getBookid() != null) {
                sql += " and bookid = ? ";
                sqlParams.add(book.getBookid());
            }
        }
        try {
            return this.jdbcTemplate.queryForObject(sql,
                    sqlParams.toArray(new Object[sqlParams.size()]), Integer.class);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    public boolean AddBook(Book book) {
        String sql = "insert into book (Bookid,book_name,Status,author,classification,content,createtime,bookurl)values (?,?,?,?,?,?,?,?)";
        try {
            return jdbcTemplate.update(sql,book.getBookid(), book.getBookName(), book.getStatus(), book.getAuthor(), book.getClassification(),
                    book.getContent(), book.getCreateTime(), book.getBookurl()) > 0 ? true : false;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean updateBook(Book book) {
        String sql = "update book set book_name=?,Status=?,author=?,classification=?,content=?,modifyTime=?,bookurl=? where Bookid=?";
        try {
            return jdbcTemplate.update(sql, book.getBookName(), book.getStatus(), book.getAuthor(), book.getClassification(),
                    book.getContent(), book.getModifyTime(), book.getBookurl(), book.getBookid()) > 0 ? true : false;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean del(Integer bookid) {
        String sql = "delete from book where bookid=? ";
        try {
            return jdbcTemplate.update(sql, bookid) > 0 ? true : false;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean updateBook(Integer status, Integer bookid) {
        String sql = "update book set status=? where bookid=?";
        try {
            return jdbcTemplate.update(sql, status, bookid) > 0 ? true : false;
        } catch (Exception e) {
            return false;
        }
    }

    public Book selectBookbyId(Integer bookid) {
        String sql = "select * from Book where bookid=?";
        try {
            RowMapper<Book> rowMapper = BeanPropertyRowMapper.newInstance(Book.class);
            Book bk=jdbcTemplate.queryForObject(sql,rowMapper,bookid);
            return bk;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}

