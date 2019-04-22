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
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @program: ereader
 * @description:
 * @author: Mr.Wang
 **/
@Controller
@RequestMapping(value = "/chapter")
public class ChapterController extends BaseController {

	@Resource
	private BookContentService bookContentService;
	@Resource
	private ManagerlogService managerlogService;
	@Resource
	private BookService bookService;
	@Resource
	private ClassificationService classificationService;

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	private ModelAndView InfoCount(HttpServletRequest request,
								   HttpServletResponse response, Integer bookid) {
		ModelAndView mv = new ModelAndView();
		try {
			int count = bookContentService.CountBookContent(bookid);
			mv.addObject("total", count);
			mv.addObject("bookid", bookid);
			mv.setViewName("chapter/search");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}

	@RequestMapping(value = "/search", method = RequestMethod.POST)
	private ModelAndView chapter(HttpServletRequest request, HttpServletResponse response, Integer bookid) {
		ModelAndView mv = new ModelAndView();
		try {
			mv.addObject("bookid", bookid);
			int count = bookContentService.CountBookContent(bookid);
			mv.addObject("total", count);
			mv.setViewName("chapter/search");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	private ModelAndView InfoList(HttpServletRequest request,
								  HttpServletResponse response, Integer bookid, int curr, int size) {
		ModelAndView mv = new ModelAndView();
		try {
			List<BookContent> bk = bookContentService.Selecttitle(curr, size, bookid);
			int count = bookContentService.CountBookContent(bookid);
			mv.addObject("total", count);
			mv.addObject("chapterInfos", bk);
			mv.setViewName("chapter/list");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}

	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public ModelAndView update(HttpServletRequest request,
							   HttpServletResponse response, Integer id) {
		ModelAndView mv = new ModelAndView();
		try {
			BookContent b1 = bookContentService.SelectContent(id);
			mv.addObject("chapterInfo", b1);
			mv.setViewName("chapter/update");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public void updateChapter(HttpServletRequest request,
							  HttpServletResponse response, Integer id, String name, String content) {

		Manager Loginmanager = (Manager) SessionUtils.getAttr(request, "ManagerInfo");
		Managerlog managerLog = new Managerlog();
		try {
			//操作日志
			managerLog.setmId(Loginmanager.getmId());
			managerLog.setCreateTime(new Date());
			managerLog.setContent("修改书籍内容");
			managerLog.setAccount(Loginmanager.getAccount());
			managerLog.setIp(ContextUtils.getIpAddr(request));
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			BookContent bookContent = bookContentService.SelectContent(id);
			Book book = bookService.selectBookbyId(bookContent.getBid());
			//将所修改的书籍下架
			book.setStatus(2);
			book.setModifyTime(new Date());
			bookService.updateBook(book);
			bookContent.setChapter(name);
			bookContent.setContent(content);
			bookContentService.updateBookContent(bookContent);
			respSuccessMsg(response, null, "修改成功");
			managerLog.setRemark("修改成功");
			managerlogService.AddManagelog(managerLog);
		} catch (Exception e) {
			respErrorMsg(response, "删除失败，系统有异常，请联系管理员");
			e.printStackTrace();
			managerLog.setRemark("系统有异常");
			managerlogService.AddManagelog(managerLog);
		}
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public ModelAndView openAdd(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView();
		try {
			List<Classification> classification = classificationService.Select();
			mv.addObject("categoryList", classification);
			mv.setViewName("/chapter/add");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public void add(HttpServletRequest request, HttpServletResponse response, String name,
					String author, String longIntro, String categoryId, @RequestParam() MultipartFile file1, String coverUrl, String bookid) {
		Manager Loginmanager = (Manager) SessionUtils.getAttr(request, "ManagerInfo");
		Managerlog managerLog = new Managerlog();
		try {
			//操作日志
			managerLog.setmId(Loginmanager.getmId());
			managerLog.setCreateTime(new Date());
			managerLog.setContent("添加新的书籍");
			managerLog.setAccount(Loginmanager.getAccount());
			managerLog.setIp(ContextUtils.getIpAddr(request));
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (bookService.selectBookbyId(Integer.valueOf(bookid)) == null) {
			File targetFile = null;
			String url = "";//返回存储路径
			String fileName = file1.getOriginalFilename();//获取文件名加后缀
			if (fileName != null && fileName != "") {
				String path = request.getSession().getServletContext().getRealPath("txt/"); //文件存储位置\
				String fileF = fileName.substring(fileName.lastIndexOf("."), fileName.length());//文件后缀
				fileName = new Date().getTime() + "_" + new Random().nextInt(1000) + fileF;//新的文件名
				//先判断文件是否存在
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
				String fileAdd = sdf.format(new Date());
				//获取文件夹路径
				File file2 = new File(path + "/" + fileAdd);
				//如果文件夹不存在则创建
				if (!file2.exists() && !file2.isDirectory()) {
					file2.mkdirs();
				}
				//存入文件夹
				targetFile = new File(file2, fileName);
				try {
					file1.transferTo(targetFile);
				} catch (Exception e) {
					e.printStackTrace();
				}

				try {
					Book book = new Book();
					book.setStatus(0);
					Classification c = classificationService.Selectbyid(Integer.valueOf(categoryId));
					book.setBookName(name);
					book.setCreateTime(new Date());
					book.setContent(longIntro);
					book.setBookurl(coverUrl);
					book.setClassification(c.getClassificationName());
					book.setAuthor(author);
					List<BookContent> bk = addcontent(targetFile);
					book.setBookid(Integer.valueOf(bookid));
					bookService.AddBook(book);
					bookContentService.addBookcontent(bk,Integer.valueOf(bookid));
					respSuccessMsg(response, null, "导入成功");
					managerLog.setRemark("导入书籍成功");
					managerlogService.AddManagelog(managerLog);
				} catch (Exception e) {
					respErrorMsg(response, "导入失败，系统有异常，请联系管理员");
					e.printStackTrace();
					managerLog.setRemark("系统有异常");
					managerlogService.AddManagelog(managerLog);
				}

			}
		}else{
			respErrorMsg(response,"已存在BOOkid");
		}
	}

	public List<BookContent> addcontent(File file) {
		List<BookContent> bookContentList = new ArrayList<>();
		try {
			// 编码格式
			String encoding = "GBK";
			// 文件路径
			if (file.isFile() && file.exists()) { // 判断文件是否存在
				InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);// 考虑到编码格
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				Long count = (long) 0;
				int n = 0;
				String newStr = null;
				String titleName = null;
				String newChapterName = null;//新章节名称
				String substring = null;
				int indexOf = 0;
				int indexOf1 = 0;
				int line = 0;
				//小说内容类
				BookContent content = new BookContent();
				while ((lineTxt = bufferedReader.readLine()) != null) {
					content = new BookContent();
					count++;
					// 正则表达式
					Pattern p = Pattern.compile("(^\\s*第)(.{1,9})[章节卷集部篇回](\\s{1})(.*)($\\s*)");
					Matcher matcher = p.matcher(lineTxt);
					Matcher matcher1 = p.matcher(lineTxt);
					newStr = newStr + "<p  class=\"content-text\">" + lineTxt + "</p>";
					while (matcher.find()) {
						content.setChapter(newChapterName);
						titleName = matcher.group();
						newChapterName = titleName.trim();
						indexOf1 = indexOf;
						indexOf = newStr.indexOf(newChapterName);
						if (n == 0) {
							indexOf1 = newStr.indexOf(newChapterName);
						}
						n = 1;
					}
					while (matcher1.find()) {
						if (indexOf != indexOf1) {
							//根据章节数量生成
							content.setNumber(++line);
							content.setId(line);
							substring = newStr.substring(indexOf1, indexOf);
							content.setContent(substring);
							bookContentList.add(content);
						}
					}
				}
				indexOf1 = indexOf;
				content.setChapter(newChapterName);
				content.setNumber(++line);
				content.setId(line);
				content.setContent(newStr.substring(indexOf1));
				bookContentList.add(content);
				bufferedReader.close();
			} else {
				System.out.println("找不到指定的文件");
			}
		} catch (Exception e) {
			System.out.println("读取文件内容出错");
			e.printStackTrace();
		}
		return bookContentList;
	}
}
