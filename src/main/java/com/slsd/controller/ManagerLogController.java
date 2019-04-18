package com.slsd.controller;

import com.slsd.entiy.Managerlog;
import com.slsd.service.ManagerlogService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @program: ereader
 * @description:
 * @author: Mr.Wang
 **/
@Controller
@RequestMapping(value = "/sysLog")
public class ManagerLogController extends BaseController {
	@Resource
	private ManagerlogService managerlogService;

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public ModelAndView searchLog() {
		ModelAndView mv = new ModelAndView("/sysLog/search");
		try {
			Integer total = managerlogService.CountManagelog(null);
			mv.addObject("total", total);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}

	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public ModelAndView searchLog(HttpServletRequest request, String account, String remark) {
		ModelAndView mv = new ModelAndView("/sysLog/search");
		try {
			Managerlog managerlog = new Managerlog();
			managerlog.setRemark(remark);
			managerlog.setAccount(account);
			int total = managerlogService.CountManagelog(managerlog);
			mv.addObject("total", total);
			mv.addObject("account", account);
			mv.addObject("remark", remark);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public ModelAndView logList(HttpServletRequest request, String account, String remark, Integer curr, Integer size) {
		ModelAndView mv = new ModelAndView("/sysLog/list");
		try {
			Managerlog managerlog = new Managerlog();
			managerlog.setAccount(account);
			managerlog.setRemark(remark);
			List<Managerlog> sysLogs = managerlogService.Select(curr, size, managerlog);
			mv.addObject("sysLogs", sysLogs);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}
}
