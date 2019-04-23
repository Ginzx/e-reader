package com.slsd.controller;

import com.slsd.entiy.Group;
import com.slsd.entiy.Manager;
import com.slsd.entiy.Managerlog;
import com.slsd.entiy.User;
import com.slsd.service.ManagerlogService;
import com.slsd.service.UserService;
import com.slsd.util.ContextUtils;
import com.slsd.util.SessionUtils;
import com.slsd.util.StringUtil;
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
@RequestMapping(value = "/readGroup")
public class UserGroupController extends BaseController {
	@Resource
	private ManagerlogService managerlogService;
	@Resource
	private UserService userService;

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public ModelAndView qryReadGroupCount(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("/readGroup/search");
		try {
			Integer count = userService.CountGroup(null);
			mv.addObject("total", count);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}

	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public ModelAndView searchReadGroup(HttpServletRequest request, String groupName) {
		ModelAndView mv = new ModelAndView("/readGroup/search");
		try {
			Group group = new Group();
			group.setGroupname(groupName);
			Integer count = userService.CountGroup(group);
			mv.addObject("total", count);
			mv.addObject("groupName", groupName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public ModelAndView qryReadGroupList(
			@RequestParam() Integer curr,
			@RequestParam() Integer size, String groupName,
			HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("/readGroup/list");
		try {
			Group group = new Group();
			group.setGroupname(groupName);
			List<Group> groups = userService.SelectGroup(curr, size, group);
			mv.addObject("readGroups", groups);
		} catch (Exception e) {
		}
		return mv;
	}

	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public ModelAndView update(HttpServletRequest request, HttpServletResponse response, Integer id) {
		ModelAndView mv = new ModelAndView("/readGroup/update");
		try {
			Group readGroup = userService.SelectGroupbyid(id);
			mv.addObject("readGroup", readGroup);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public void update(HttpServletRequest request, HttpServletResponse response, Integer id,
					   @RequestParam(required = false) String groupName) {
		Manager Loginmanager = (Manager) SessionUtils.getAttr(request, "ManagerInfo");
		Managerlog managerLog = new Managerlog();
		try {
			//操作日志
			managerLog.setmId(Loginmanager.getmId());
			managerLog.setCreateTime(new Date());
			managerLog.setContent("修改用户分组名称");
			managerLog.setAccount(Loginmanager.getAccount());
			managerLog.setIp(ContextUtils.getIpAddr(request));
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			Group readGroup = userService.SelectGroupbyid(id);
			readGroup.setGroupname(groupName);
			if (userService.editgroup(readGroup)) {
				respSuccessMsg(response, null, "修改成功");
				managerLog.setRemark("修改用户分组名称成功");
			} else {
				respErrorMsg(response, "修改失败，可能存在相同阅读等级的组！");
				managerLog.setRemark("修改用户分组名称失败");
			}
			managerlogService.AddManagelog(managerLog);
		} catch (Exception e) {
			respErrorMsg(response, "修改失败，系统有异常，请联系管理员");
		}
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public ModelAndView add() {
		ModelAndView mv = new ModelAndView("/readGroup/add");
		return mv;
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public void addMenu(HttpServletRequest request, HttpServletResponse response,
						@RequestParam(required = false) String groupName) {
		Manager Loginmanager = (Manager) SessionUtils.getAttr(request, "ManagerInfo");
		Managerlog managerLog = new Managerlog();
		try {
			//操作日志
			managerLog.setmId(Loginmanager.getmId());
			managerLog.setCreateTime(new Date());
			managerLog.setContent("添加用户分组信息");
			managerLog.setAccount(Loginmanager.getAccount());
			managerLog.setIp(ContextUtils.getIpAddr(request));
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			Group readGroup = new Group();
			readGroup.setGroupname(groupName);
			if (userService.addgroup(readGroup)) {
				respSuccessMsg(response, null, "添加成功");
				managerLog.setRemark("添加用户分组成功");
			} else {
				respErrorMsg(response, "添加失败");
				managerLog.setRemark("添加用户分组失败");
			}
			managerlogService.AddManagelog(managerLog);
		} catch (Exception e) {
			respErrorMsg(response, "添加失败，系统有异常，请联系管理员");
		}
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public void delUserGroup(HttpServletRequest request, HttpServletResponse response,
							 Integer id) {
		Manager Loginmanager = (Manager) SessionUtils.getAttr(request, "ManagerInfo");
		Managerlog managerLog = new Managerlog();
		try {
			//操作日志
			managerLog.setmId(Loginmanager.getmId());
			managerLog.setCreateTime(new Date());
			managerLog.setContent("删除用户分组");
			managerLog.setAccount(Loginmanager.getAccount());
			managerLog.setIp(ContextUtils.getIpAddr(request));
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			Group group = userService.SelectGroupbyid(id);
			if(userService.delgroup(group)){
			List<User> users = userService.selectUserbyGroup(group.getGroupname());
			for (User user : users) {
				user.setUsergroup(null);
				userService.editUser(user);
			}
				respSuccessMsg(response, null, "删除成功");
			managerLog.setRemark("删除用户分组成功");
			}else {
				respErrorMsg(response, "删除失败");
				managerLog.setRemark("删除用户分组失败");
			}
			managerlogService.AddManagelog(managerLog);
		}catch (Exception e) {
			respErrorMsg(response, "删除失败，系统有异常，请联系管理员");
		}
	}


}
