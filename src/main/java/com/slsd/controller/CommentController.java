package com.slsd.controller;

import com.slsd.entiy.Comment;
import com.slsd.entiy.Manager;
import com.slsd.entiy.Managerlog;
import com.slsd.service.CommentService;
import com.slsd.service.ManagerlogService;
import com.slsd.util.ContextUtils;
import com.slsd.util.SessionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * @program: ereader
 * @description:
 * @author: Mr.Wang
 **/
@Controller
@RequestMapping("/comment")
public class CommentController extends BaseController {

	@Resource
	private CommentService commentService;
	@Resource
	private ManagerlogService managerlogService;

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public ModelAndView qryCommentCount(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("/comment/search");
		try {
			Integer commentsCount = commentService.CountComment(null);
			mv.addObject("total", commentsCount);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}

	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public ModelAndView qryCommentCount(HttpServletRequest request, @RequestParam(required = false) String account,
										@RequestParam(required = false) Integer checkFlag) {
		ModelAndView mv = new ModelAndView("/comment/search");
		try {
			Comment comment = new Comment();
			comment.setAccount(account);
			comment.setStatus(checkFlag);
			Integer commentsCount = commentService.CountComment(comment);
			mv.addObject("account", account);
			mv.addObject("checkFlag", checkFlag);
			mv.addObject("total", commentsCount);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public ModelAndView qryCommentList(
			@RequestParam(required = false) String account,
			@RequestParam(required = false) Integer checkFlag,
			@RequestParam(required = true) Integer curr,
			@RequestParam(required = true) Integer size,
			HttpServletRequest request) {
		Comment comment = new Comment();
		comment.setStatus(checkFlag);
		comment.setAccount(account);
		ModelAndView mv = new ModelAndView("/comment/list");
		try {
			List<Comment> comments = commentService.SelectComment(curr, size, comment);
			mv.addObject("comments", comments);
			Integer commentsCount = commentService.CountComment(comment);
			mv.addObject("total", commentsCount);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}

	@RequestMapping(value = "/batchdelete", method = RequestMethod.POST)
	public void batchDelete(HttpServletRequest request, HttpServletResponse response,
							@RequestParam(required = true) String ids) {
		Manager Loginmanager = (Manager) SessionUtils.getAttr(request, "ManagerInfo");
		Managerlog managerLog = new Managerlog();
		try {
			//操作日志
			managerLog.setmId(Loginmanager.getmId());
			managerLog.setCreateTime(new Date());
			managerLog.setContent("批量删除评论");
			managerLog.setAccount(Loginmanager.getAccount());
			managerLog.setIp(ContextUtils.getIpAddr(request));
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			commentService.delComment(ids);
			respSuccessMsg(response, null, "批量删除成功");
		    managerLog.setRemark("批量删除成功");
			managerlogService.AddManagelog(managerLog);
		} catch (Exception e) {
			managerLog.setRemark("批量删除失败");
			respErrorMsg(response,"失败");
			managerlogService.AddManagelog(managerLog);
		}
	}

	@RequestMapping(value = "/batchCheck", method = RequestMethod.POST)
	public void batchCheck(HttpServletRequest request, HttpServletResponse response,
						   @RequestParam(required = true) String ids,Integer checkFlag) {
			Manager Loginmanager = (Manager) SessionUtils.getAttr(request, "ManagerInfo");
			Managerlog managerLog = new Managerlog();
			try {
				//操作日志
				managerLog.setmId(Loginmanager.getmId());
				managerLog.setCreateTime(new Date());
				managerLog.setContent("批量审核评论");
				managerLog.setAccount(Loginmanager.getAccount());
				managerLog.setIp(ContextUtils.getIpAddr(request));
			} catch (Exception e) {
				e.printStackTrace();
			}
		try {
			commentService.updateComment(ids, checkFlag);
			respSuccessMsg(response, null, "批量审核成功");
			managerlogService.AddManagelog(managerLog);
		} catch (Exception e) {
			managerLog.setRemark("批量审核失败，系统有异常");
			respErrorMsg(response,"失败");
			managerlogService.AddManagelog(managerLog);
		}
	}

}
