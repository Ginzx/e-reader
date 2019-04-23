package com.slsd.controller;

import com.alibaba.fastjson.JSON;
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
import java.util.regex.Pattern;

/**
 * @program: ereader
 * @description:
 * @author: Mr.Wang
 **/
@Controller
@RequestMapping(value = "/readUser")
public class UserController extends BaseController {

	@Resource
	private ManagerlogService managerlogService;
	@Resource
	private UserService userService;

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public ModelAndView qryReadUser(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("/readUser/search");
		try {

			Integer count = userService.countUser(null);
			mv.addObject("total", count);
			List<Group> readGroups = userService.SelectGroup(1, 1000,null);
			mv.addObject("readGroups", readGroups);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}

	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public ModelAndView ReadUser(HttpServletRequest request, @RequestParam(required = false) String account,
								 @RequestParam(required = false) String cardNo,
								 @RequestParam(required = false) String name,
								 @RequestParam(required = false) String phone,
								 Integer groupId) {
		ModelAndView mv = new ModelAndView("/readUser/search");
		User readUser = new User();
		try {
			readUser.setUserName(account);
			readUser.setName(name);
			readUser.setPhone(phone);
			readUser.setIdCard(cardNo);
			if (groupId != null) {
				Group group = userService.SelectGroupbyid(groupId);
				readUser.setUsergroup(group.getGroupname());
			}
			mv.addObject("account", account);
			mv.addObject("name", name);
			mv.addObject("phone", phone);
			mv.addObject("cardNo", cardNo);
			mv.addObject("groupId", groupId);
			Integer count = userService.countUser(readUser);
			mv.addObject("total", count);
			List<Group> readGroups = userService.SelectGroup(1, 1000,null);
			mv.addObject("readGroups", readGroups);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public ModelAndView qryReadUserList(HttpServletRequest request, @RequestParam(required = false) String account,
										@RequestParam(required = false) String cardNo,
										@RequestParam(required = false) String name,
										@RequestParam(required = false) String phone,
										Integer groupId, Integer curr, Integer size) {
		ModelAndView mv = new ModelAndView("/readUser/list");
		User readUser = new User();
		try {
			readUser.setUserName(account);
			readUser.setName(name);
			readUser.setPhone(phone);
			readUser.setIdCard(cardNo);
			if (groupId != null) {
				Group group = userService.SelectGroupbyid(groupId);
				readUser.setUsergroup(group.getGroupname());
			}
			List<User> users = userService.selectUser(curr, size, readUser);
			mv.addObject("readUsers", users);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}

	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public ModelAndView update(HttpServletRequest request, HttpServletResponse response, Integer id) {
		ModelAndView mv = new ModelAndView("/readUser/update");
		try {
			User readUser = userService.SelectUserByid(id);
			mv.addObject("readUser", readUser);
			List<Group> readGroups = userService.SelectGroup(1, 1000,null);
			mv.addObject("readGroups", readGroups);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public void update(HttpServletRequest request, HttpServletResponse response, Integer id,
					   @RequestParam(required = false) Integer groupId) {
		Manager Loginmanager = (Manager) SessionUtils.getAttr(request, "ManagerInfo");
		Managerlog managerLog = new Managerlog();
		try {
			//操作日志
			managerLog.setmId(Loginmanager.getmId());
			managerLog.setCreateTime(new Date());
			managerLog.setContent("修改用户分组");
			managerLog.setAccount(Loginmanager.getAccount());
			managerLog.setIp(ContextUtils.getIpAddr(request));
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			User user = userService.SelectUserByid(id);
			if (groupId != null) {
				Group group = userService.SelectGroupbyid(groupId);
				user.setUsergroup(group.getGroupname());
			}else {
				user.setUsergroup(null);
			}
			if (userService.editUser(user)) {
				respSuccessMsg(response, null, "修改用户分组成功");
				managerLog.setRemark("修改用户分组成功");//操作结果 s=成功 f=失败（原因写备注）
			} else {
				respErrorMsg(response, "修改用户分组失败");
				managerLog.setRemark("修改用户分组失败");
			}
			managerlogService.AddManagelog(managerLog);
		} catch (Exception e) {
			respErrorMsg(response, "修改用户分组失败，系统有异常，请联系管理员");
			e.printStackTrace();
			managerLog.setRemark("修改用户分组");
			managerlogService.AddManagelog(managerLog);
		}
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public ModelAndView add(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("/readUser/add");
		try{
			List<Group> readGroups = userService.SelectGroup(1, 1000,null);
			mv.addObject("readGroups", readGroups);
			mv.addObject("readGroups", readGroups);
		}catch (Exception e) {
			respErrorMsg(response, "打开添加页面失败，系统有异常，请联系管理员");
		}
		return mv;
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public void addReadUser(HttpServletRequest request, HttpServletResponse response,
							@RequestParam(required = false) String account,
							@RequestParam(required = false) String enPwd,
							@RequestParam(required = false) String name,
							@RequestParam(required = false) String phone,
							@RequestParam(required = false) String cardNo,
							@RequestParam(required = false) String email,
							//@RequestParam(required = false) String registerIp,
							//(required = false) Integer loginCount,
							Integer groupId) {
		Manager Loginmanager = (Manager) SessionUtils.getAttr(request, "ManagerInfo");
		Managerlog managerLog = new Managerlog();
		try {
			//操作日志
			managerLog.setmId(Loginmanager.getmId());
			managerLog.setCreateTime(new Date());
			managerLog.setContent("添加用户分组");
			managerLog.setAccount(Loginmanager.getAccount());
			managerLog.setIp(ContextUtils.getIpAddr(request));
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			User user=new User();
			Pattern idNumPattern = Pattern.compile("(\\d{14}[0-9a-zA-Z])|(\\d{17}[0-9a-zA-Z])");
			if (!idNumPattern.matcher(cardNo).matches()){
				respErrorMsg(response, "身份证格式错误！");
				return;
			}

			if (groupId != null) {
				Group group = userService.SelectGroupbyid(groupId);
				user.setUsergroup(group.getGroupname());
			}else {
				user.setUsergroup(null);
			}
			user.setUserName(account);
			user.setUserPwd(enPwd);
			user.setIdCard(cardNo);
			user.setEMail(email);
			user.setPhone(phone);
			user.setRegisterIp(ContextUtils.getIpAddr(request));
			user.setRegisterTime(new Date());
			user.setName(name);
			if(userService.addUser(user)){
				respSuccessMsg(response, null, "添加成功");
				managerLog.setRemark("添加了一名用戶");//操作结果 s=成功 f=失败（原因写备注）
			} else {
				respErrorMsg(response, "失败");
				managerLog.setRemark("添加用戶失敗");
			}
			managerlogService.AddManagelog(managerLog);
		} catch (Exception e) {
			respErrorMsg(response, "添加用戶失敗，系统有异常，请联系管理员");
			e.printStackTrace();
			managerLog.setRemark("添加用戶失敗");
			managerlogService.AddManagelog(managerLog);
		}
	}
}
