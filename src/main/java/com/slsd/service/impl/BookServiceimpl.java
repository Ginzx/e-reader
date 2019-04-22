package com.slsd.service.impl;

import com.slsd.dao.BookDao;
import com.slsd.entiy.Book;
import com.slsd.service.BookService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @program: ereader
 * @description:
 * @author: Mr.Wang
 **/
@Service("BookService")
public class BookServiceimpl implements BookService {

	@Resource
	private BookDao bookDao;

	@Override
	public List<Book> selectBook(Integer offset, Integer length, Book book) {
		return bookDao.selectBook((offset - 1) * length, length, book);
	}

	@Override
	public Integer countbook(Book book) {
		return bookDao.countbook(book);
	}

	@Override
	public boolean AddBook(Book book) {
		return bookDao.AddBook(book);
	}

	@Override
	public boolean updateBook(Book book) {
		return bookDao.updateBook(book);
	}

	@Override
	public boolean del(Integer bookid) {
		return bookDao.del(bookid);
	}

	@Override
	public void updateBook(Integer status, Integer bookids) throws Exception {
		bookDao.updateBook(status,bookids);
	}

	@Override
	public Book selectBookbyId(Integer bookid) {
		return bookDao.selectBookbyId(bookid);
	}
}
