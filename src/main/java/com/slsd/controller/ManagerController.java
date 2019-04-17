package com.slsd.controller;

import com.slsd.entiy.*;
import com.slsd.service.FeatureService;
import com.slsd.service.ManagerService;
import com.slsd.service.ManagerlogService;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @program: ereader
 * @description:
 * @author: Mr.Wang
 **/
@Controller
@RequestMapping(value = "/manage")
public class ManagerController extends BaseController {

	@Resource
	private ManagerService managerService;
	@Resource
	private ManagerlogService managerlogService;
	@Resource
	private FeatureService featureService;

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public ModelAndView searchUser(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("/manage/search");
		try {
			Manager manager = (Manager) SessionUtils.getAttr(request, "ManagerInfo");
			Integer ManagerCount = managerService.CountManager(null);
			mv.addObject("total", ManagerCount);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}

	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public ModelAndView searchUser(HttpServletRequest request, String account, String name,
								   Integer status) {
		ModelAndView mv = new ModelAndView("/manage/search");
		try {
			Manager manager = (Manager) SessionUtils.getAttr(request, "ManagerInfo");
			Manager manager1 = new Manager();
			manager1.setAccount(account);
			manager1.setName(name);
			manager1.setStatus(status);
			Integer ManagerCount = managerService.CountManager(manager1);
			mv.addObject("total", ManagerCount);
			mv.addObject("account", account);
			mv.addObject("name", name);
			mv.addObject("status", status);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public ModelAndView userList(HttpServletRequest request, String account, String name,
								 Integer status, Integer curr, Integer size) {
		Manager manager1 = (Manager) SessionUtils.getAttr(request, "ManagerInfo");
		Manager manager = new Manager();
		manager.setAccount(account);
		manager.setName(name);
		manager.setStatus(status);
		ModelAndView mv = new ModelAndView("/manage/list");
		try {
			List<Manager> managers = managerService.selectmanager(curr - 1, size, manager);
			mv.addObject("managers", managers);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}

	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public ModelAndView update(HttpServletRequest request, HttpServletResponse response, Integer mid, Integer flag) {
		ModelAndView mv = new ModelAndView("/manage/update");
		try {
			Manager manager = managerService.SelectById(mid);
			mv.addObject("manager", manager);
		} catch (Exception e) {
			return null;
		}
		if (null != flag && flag == 1) {
			mv.addObject("flag", flag);//個人中心修改密碼用
		}
		return mv;
	}

	/**
	 * 修改
	 *
	 * @param request
	 * @param response
	 * @param mid
	 * @param account
	 * @param name
	 * @param status
	 * @param enPwd
	 * @param flag
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public void UpdatePost(HttpServletRequest request, HttpServletResponse response, Integer mid, String account,
						   @RequestParam() String name,
						   @RequestParam(required = false) Integer status,
						   @RequestParam(required = false) String enPwd,
						   Integer flag) {
		Manager Loginmanager = (Manager) SessionUtils.getAttr(request, "ManagerInfo");
		Manager manager = new Manager();
		Managerlog managerLog = new Managerlog();
		if (status == null) {
			status = 1;
		}
		try {
			//操作日志
			managerLog.setmId(Loginmanager.getmId());
			managerLog.setCreateTime(new Date());
			managerLog.setContent("修改后台用户");
			managerLog.setAccount(Loginmanager.getAccount());
			managerLog.setIp(ContextUtils.getIpAddr(request));
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			boolean handleflag = true;
			if (Loginmanager.getmId() != 0) {
				if (Loginmanager.getmId() != mid) {
					managerLog.setRemark("没有相应权限");
					handleflag = false;
					respErrorMsg(response, "修改用户——提交失败，并没有相应权限");
				}
			}
			if (handleflag) {
				manager.setAccount(account);
				manager.setModifyTime(new Date());
				manager.setmId(mid);
				manager.setName(name);
				if (enPwd == "") {
					Manager m1 = managerService.SelectById(mid);
					String pwd = m1.getPwd();
					manager.setPwd(pwd);
				} else {
					manager.setPwd(enPwd);
				}
				manager.setStatus(status);
				if (managerService.editmanager(manager)) {
					managerLog.setRemark("成功");
				} else {
					managerLog.setRemark("失败");
				}
			}
			managerlogService.AddManagelog(managerLog);
			respSuccessMsg(response, true, "修改成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 删除单个用户
	 *
	 * @param request
	 * @param response
	 * @param mid
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public void delete(HttpServletRequest request, HttpServletResponse response,
					   @RequestParam() String mid) {
		Manager Loginmanager = (Manager) SessionUtils.getAttr(request, "ManagerInfo");
		Managerlog managerLog = new Managerlog();
		try {
			//操作日志
			managerLog.setmId(Loginmanager.getmId());
			managerLog.setCreateTime(new Date());
			managerLog.setContent("删除后台用户");
			managerLog.setAccount(Loginmanager.getAccount());
			managerLog.setIp(ContextUtils.getIpAddr(request));
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			boolean handleflag = true;
			// 超级管理员
			if (Loginmanager.getmId() != 0) {
				managerLog.setRemark("没有相应权限");
				handleflag = false;
				respErrorMsg(response, "修改用户——提交失败，并没有相应权限");
			}
			if (handleflag) {
				if (Loginmanager.getmId().equals(mid)) {
					respErrorMsg(response, "不能自己删除自己哦！");
					managerLog.setRemark("不能删除本身");
				} else if (mid.equals("1")) {
					respErrorMsg(response, "不能删除超级系统管理员哦！");
					managerLog.setRemark("不能删除超级管理员");
				} else {
					if (managerService.delmanager(Integer.valueOf(mid))) {
						featureService.delManagerFeature(Integer.valueOf(mid));
						respSuccessMsg(response, null, "删除成功");
					} else {
						respErrorMsg(response, "删除失败");
						managerLog.setRemark("删除失败");
					}
				}
			}
			managerlogService.AddManagelog(managerLog);
		} catch (Exception e) {
			respErrorMsg(response, "删除失败，系统有异常，请联系管理员");
			e.printStackTrace();
			managerLog.setRemark("系统有异常");
			managerlogService.AddManagelog(managerLog);
		}
	}

	/**
	 * 菜单设置页面——打开
	 *
	 * @param request
	 * @param response
	 * @param mId
	 * @return
	 */

	@RequestMapping(value = "/menusearch", method = RequestMethod.GET)
	public ModelAndView getMenuSearch(HttpServletRequest request, HttpServletResponse response,
									  @RequestParam(value = "mid") Integer mid) {
		ModelAndView mv = new ModelAndView("/manage/menusearch");
		mv.addObject("mid", mid);
		return mv;
	}

	/**
	 * 获取某级菜单并关联是否有权限的菜单
	 *
	 * @param request
	 * @param response
	 * @param mid
	 */
	@RequestMapping(value = "/menuchilds", method = RequestMethod.POST)
	public void postMenuChilds(HttpServletRequest request, HttpServletResponse response,
							   @RequestParam(value = "mid") Integer mid) {
		List<TreeNode> nodes;
		try {
			Manager Loginmanager = (Manager) SessionUtils.getAttr(request, "ManagerInfo");
			nodes = featureService.getMenuTreeNodes(mid);
		} catch (Exception e) {
			nodes = new ArrayList<>();
		}
		respSuccessMsg(response, nodes, "ok");
	}


	/**
	 * 设置角色的菜单
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/setmenus", method = RequestMethod.POST)
	public void setMenus(HttpServletRequest request, HttpServletResponse response,
						 @RequestParam(value = "ids") String fid,
						 @RequestParam(value = "mid") Integer mid) {
		Manager Loginmanager = (Manager) SessionUtils.getAttr(request, "ManagerInfo");
		Managerlog managerLog = new Managerlog();
		try {
			//操作日志
			managerLog.setmId(Loginmanager.getmId());
			managerLog.setCreateTime(new Date());
			managerLog.setContent("修改管理员菜单");
			managerLog.setAccount(Loginmanager.getAccount());
			managerLog.setIp(ContextUtils.getIpAddr(request));
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			if (StringUtil.isEmpty(fid)) {
				respSuccessMsg(response, null, "设置成功");
				managerLog.setRemark("成功");
			} else {
				featureService.setMenus(mid, fid);
				respSuccessMsg(response, null, "菜单设置成功");
				managerLog.setRemark("成功");
			}
			managerlogService.AddManagelog(managerLog);
		} catch (Exception e) {
			respErrorMsg(response, "操作失败");
			managerLog.setRemark("系统异常");
			managerlogService.AddManagelog(managerLog);
			e.printStackTrace();
		}

	}
}
