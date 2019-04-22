package com.slsd.controller;

import com.slsd.entiy.*;
import com.slsd.service.BookContentService;
import com.slsd.service.BookService;
import com.slsd.service.ClassificationService;
import com.slsd.service.ManagerlogService;
import com.slsd.util.ContextUtils;
import com.slsd.util.SessionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

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
	@Resource
	private ManagerlogService managerlogService;

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

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public void delBook(HttpServletRequest request, HttpServletResponse response, @RequestParam() Integer bookid) {
		Manager Loginmanager = (Manager) SessionUtils.getAttr(request, "ManagerInfo");
		Managerlog managerLog = new Managerlog();
		try {
			//操作日志
			managerLog.setmId(Loginmanager.getmId());
			managerLog.setCreateTime(new Date());
			managerLog.setContent("删除书本");
			managerLog.setAccount(Loginmanager.getAccount());
			managerLog.setIp(ContextUtils.getIpAddr(request));
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			if (bookService.del(bookid)) {
				bookContentService.delBookContentall(bookid);
				respSuccessMsg(response, null, "删除成功");
				managerLog.setRemark("成功");//操作结果 s=成功 f=失败（原因写备注）
			} else {
				respErrorMsg(response, "删除失败");
				managerLog.setRemark("删除失败");
			}
			managerlogService.AddManagelog(managerLog);
		} catch (Exception e) {
			respErrorMsg(response, "删除失败，系统有异常，请联系管理员");
			e.printStackTrace();
			managerLog.setRemark("系统有异常");
			managerlogService.AddManagelog(managerLog);
		}
	}

	@RequestMapping(value = "/updateStatus", method = RequestMethod.POST)
	public void updateBookStatus(HttpServletRequest request, HttpServletResponse response,
								 @RequestParam() Integer bookid, String status) {
		Manager Loginmanager = (Manager) SessionUtils.getAttr(request, "ManagerInfo");
		Managerlog managerLog = new Managerlog();

		try {
			//操作日志
			managerLog.setmId(Loginmanager.getmId());
			managerLog.setCreateTime(new Date());
			managerLog.setContent("更新书籍状态");
			managerLog.setAccount(Loginmanager.getAccount());
			managerLog.setIp(ContextUtils.getIpAddr(request));
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			bookService.updateBook(Integer.valueOf(status), bookid);
			respSuccessMsg(response, null, "修改书籍状态成功");
			managerLog.setRemark("成功");//操作结果 s=成功 f=失败（原因写备注）
			managerlogService.AddManagelog(managerLog);
		} catch (Exception e) {
			respErrorMsg(response, "上架失败，系统有异常，请联系管理员");
			e.printStackTrace();
			managerLog.setRemark("系统有异常");
			managerlogService.AddManagelog(managerLog);
		}
	}

	@RequestMapping(value = "/resDetails", method = RequestMethod.GET)
	private ModelAndView resDetails(Integer bookid) {
		ModelAndView mv = new ModelAndView();
		try {
			Book book = bookService.selectBookbyId(bookid);
			mv.addObject("resInfo", book);
			List<Classification> classifications = classificationService.Select();
			mv.addObject("classifications", classifications);
			List<BookContent> bookContents = bookContentService.Selecttitle(1, 10, bookid);
			mv.addObject("chapterInfos", bookContents);
			mv.setViewName("/resource/resDetail");
		} catch (Exception e) {
			e.printStackTrace();
			mv.setViewName("/resource/blank");
		}
		return mv;
	}

	@RequestMapping(value = "/uploadImg", method = RequestMethod.POST)
	public void imgUpload(HttpServletRequest request, HttpServletResponse response,
						  @RequestParam("myfiles") MultipartFile file) {
		File targetFile = null;
		String url = "";//返回存储路径
		String fileName = file.getOriginalFilename();//获取文件名加后缀
		if (fileName != null && fileName != "") {
			String path = request.getSession().getServletContext().getRealPath("images/upload/"); //文件存储位置\
			String fileF = fileName.substring(fileName.lastIndexOf("."), fileName.length());//文件后缀
			fileName = new Date().getTime() + "_" + new Random().nextInt(1000) + fileF;//新的文件名
			String url1 = "/images/upload/";
			//先判断文件是否存在
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			String fileAdd = sdf.format(new Date());
			//获取文件夹路径
			File file1 = new File(path + "/" + fileAdd);
			//如果文件夹不存在则创建
			if (!file1.exists() && !file1.isDirectory()) {
				file1.mkdirs();
			}
			//将图片存入文件夹
			targetFile = new File(file1, fileName);
			try {
				//将上传的文件写到服务器上指定的文件。
				file.transferTo(targetFile);
				url = url1 + fileAdd + "/" + fileName;
			} catch (Exception e) {
				e.printStackTrace();
				respErrorMsg(response, null);
			}
		}
		respSuccessMsg(response, url, "成功");
	}

	@RequestMapping(value = "/updateResource", method = RequestMethod.POST)
	private void resourceSave(HttpServletRequest request, HttpServletResponse response, Integer id, String name,
							  String author, String categoryId, String coverUrl,String longIntro) {
		Manager Loginmanager = (Manager) SessionUtils.getAttr(request, "ManagerInfo");
		Managerlog managerLog = new Managerlog();
		try {
			//操作日志
			managerLog.setmId(Loginmanager.getmId());
			managerLog.setCreateTime(new Date());
			managerLog.setContent("修改书籍");
			managerLog.setAccount(Loginmanager.getAccount());
			managerLog.setIp(ContextUtils.getIpAddr(request));
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			Book book = bookService.selectBookbyId(id);
			book.setAuthor(author);
			book.setBookName(name);
			book.setModifyTime(new Date());
			book.setBookurl(coverUrl);
			book.setStatus(2);
			book.setContent(longIntro);
			book.setClassification(categoryId);
			bookService.updateBook(book);
			respSuccessMsg(response, null, "修改书籍成功");
			managerLog.setRemark("成功");//操作结果 s=成功 f=失败（原因写备注）
			managerlogService.AddManagelog(managerLog);
		} catch (Exception e) {
			respErrorMsg(response, "失败，系统有异常，请联系管理员");
			e.printStackTrace();
			managerLog.setRemark("系统有异常");
			managerlogService.AddManagelog(managerLog);
		}
	}
}
