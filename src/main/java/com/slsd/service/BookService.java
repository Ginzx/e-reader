package com.slsd.service;

import com.slsd.entiy.Book;

import java.util.List;

/**
 * @program: ereader
 * @description:
 * @author: Mr.Wang
 **/
public interface BookService {

	public List<Book> selectBook(Integer offset, Integer length, Book book);

	public Integer countbook(Book book);

	public boolean AddBook(Book book);

	public boolean updateBook(Book book);

	public boolean del(Integer bookid);

	public void updateBook(Integer status,Integer bookids) throws Exception ;

	public Book selectBookbyId(Integer bookid);

}
