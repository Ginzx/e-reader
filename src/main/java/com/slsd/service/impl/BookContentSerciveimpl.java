package com.slsd.service.impl;

import com.slsd.dao.BookContentDao;
import com.slsd.entiy.BookContent;
import com.slsd.service.BookContentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @program: ereader
 * @description:
 * @author: Mr.Wang
 **/
@Service("BookContentService")
public class BookContentSerciveimpl implements BookContentService {

	@Resource
	private BookContentDao bookContentDao;

	@Override
	public List<BookContent> Selecttitle(Integer offset, Integer length, Integer id) {
		return bookContentDao.Selecttitle((offset - 1) * length, length, id);
	}

	@Override
	public Integer CountBookContent(Integer id) {
		return bookContentDao.CountBookContent(id);
	}

	@Override
	public BookContent SelectContent(Integer id) {
		return bookContentDao.SelectContent(id);
	}

	@Override
	public void addBookcontent(List<BookContent> bookContents, Integer bid) {
		for (BookContent bookcontent : bookContents
		) {
			bookContentDao.addBookcontent(bookcontent, bid);
		}
	}

	@Override
	public boolean delBookContent(Integer bid) {
		return delBookContent(bid);
	}

	@Override
	public boolean updateBookContent(BookContent bookContent) {
		return updateBookContent(bookContent);
	}
}
