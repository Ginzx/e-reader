package com.slsd.service;

import com.slsd.entiy.BookContent;

import java.util.List;

public interface BookContentService {

	public List<BookContent> Selecttitle(Integer offset, Integer length, Integer id);

	public Integer CountBookContent(Integer id);

	public BookContent SelectContent(Integer id);

	public void addBookcontent(List<BookContent> bookContents, Integer bid);

	public boolean delBookContent(Integer id);

	public boolean delBookContentall(Integer bid);

	public boolean updateBookContent(BookContent bookContent);
}
