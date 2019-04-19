package com.slsd.controller;

import com.slsd.entiy.Book;
import com.slsd.entiy.BookContent;
import com.slsd.entiy.Classification;
import com.slsd.entiy.Manager;
import com.slsd.service.BookContentService;
import com.slsd.service.BookService;
import com.slsd.service.ClassificationService;
import com.slsd.util.SessionUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: ereader
 * @description:
 * @author: Mr.Wang
 **/
@Controller
@RequestMapping(value = "/resource")
public class ResourceController extends BaseController {

	@Resource
	private BookService bookService;
	@Resource
	private BookContentService bookContentService;
	@Resource
	private ClassificationService classificationService;

	/**
	 * 跳转页面
	 *
	 * @return
	 */
	@RequestMapping(value = "/resSearch", method = RequestMethod.GET)
	public ModelAndView getResSearch(HttpServletRequest request,
									 HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("resource/resSearch");
		try {
			List<Classification> classifications = classificationService.Select();
			mv.addObject("classifications", classifications);
			Integer Count = bookService.countbook(null);
			mv.addObject("total", Count);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}

	@RequestMapping(value = "/resSearch", method = RequestMethod.POST)
	public ModelAndView postRseSearch(HttpServletRequest request,
									  HttpServletResponse response, String name, String category,
									  String author, Integer Status) {
		ModelAndView mv = new ModelAndView("resource/resSearch");
		Book book = new Book();
		book.setAuthor(author);
		book.setBookName(name);
		book.setStatus(Status);
		book.setClassification(category);
		try {
			List<Classification> classifications = classificationService.Select();
			mv.addObject("classifications", classifications);
			Integer Count = bookService.countbook(book);
			mv.addObject("classification", category);
			mv.addObject("author", author);
			mv.addObject("name", name);
			mv.addObject("Status", Status);
			mv.addObject("total", Count);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}


	@RequestMapping(value = "/resList", method = RequestMethod.POST)
	public ModelAndView psotResList(HttpServletRequest request,
									HttpServletResponse response, String name, String category,
									String author, Integer Status, Integer curr, Integer size) {
		ModelAndView mv = new ModelAndView("resource/resList");
		Manager manager = (Manager) SessionUtils.getAttr(request, "ManagerInfo");
		Book book = new Book();
		book.setAuthor(author);
		book.setBookName(name);
		book.setStatus(Status);
		book.setClassification(category);
		try {
			List<Book> BookList = bookService.selectBook(curr, size, book);
			mv.addObject("BookList", BookList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}
}
