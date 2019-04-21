package com.slsd.controller;

import com.slsd.entiy.Feature;
import com.slsd.entiy.Manager;
import com.slsd.entiy.Managerlog;
import com.slsd.entiy.User;
import com.slsd.service.FeatureService;
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
import java.util.Date;
import java.util.List;

/**
 * @program: ereader
 * @description: 菜单管理
 * @author: Mr.Wang
 **/
@Controller
@RequestMapping(value = "/menu")
public class MenuController extends BaseController {
	@Resource
	private FeatureService featureService;
	@Resource
	private ManagerlogService managerlogService;

	/**
	 * 打开菜单页面——查询
	 *
	 * @return
	 */
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public ModelAndView searchMenu() {
		ModelAndView mv = new ModelAndView("/menu/search");
		try {
			Integer menusCount = featureService.MenuCount(null);
			mv.addObject("total", menusCount);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}

	/**
	 * 查询菜单列表——点查询按钮
	 *
	 * @param menuName
	 * @return
	 */
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public ModelAndView searchMenu(String menuName) {
		ModelAndView mv = new ModelAndView("/menu/search");
		try {
			Feature menu = new Feature();
			menu.setfName(StringUtil.objToString(menuName, "").trim());
			Integer menusCount = featureService.MenuCount(menu);
			mv.addObject("total", menusCount);
			mv.addObject("menuName", menuName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}

	/**
	 * 查询菜单列表——列表
	 *
	 * @param menuName 菜单名
	 * @param curr     当前页
	 * @param size     一页条数
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public ModelAndView menuList(String menuName, Integer curr, Integer size) {
		Feature menu = new Feature();
		menu.setfName(StringUtil.objToString(menuName, "").trim());
		ModelAndView mv = new ModelAndView("/menu/list");
		try {
			List<Feature> menus = featureService.Selectall(curr, size, menu);
			mv.addObject("menus", menus);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}

	/**
	 * 修改菜单——到修改页面
	 *
	 * @param request
	 * @param response
	 * @param fid
	 * @return
	 */
	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public ModelAndView update(HttpServletRequest request, HttpServletResponse response, String fid) {
		ModelAndView mv = new ModelAndView("/menu/update");
		try {
			List<Feature> firstMenus = featureService.SelectFather();
			mv.addObject("firstMenus", firstMenus);
			Feature menu = featureService.selfeaturebyid(Integer.valueOf(fid));
			mv.addObject("menu", menu);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}

	/**
	 * 修改菜单——提交
	 *
	 * @param request
	 * @param response
	 * @param id
	 * @param menuName
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public void update(HttpServletRequest request, HttpServletResponse response, Integer id,
					   @RequestParam() String menuName,
					   @RequestParam() Integer parentId,
					   @RequestParam(required = false) String menuUrl
	) {
		Manager Loginmanager = (Manager) SessionUtils.getAttr(request, "ManagerInfo");
		Feature menu = new Feature();
		Managerlog managerLog = new Managerlog();
		try {
			//操作日志
			managerLog.setmId(Loginmanager.getmId());
			managerLog.setCreateTime(new Date());
			managerLog.setContent("修改菜单");
			managerLog.setAccount(Loginmanager.getAccount());
			managerLog.setIp(ContextUtils.getIpAddr(request));
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			menu.setfId(id);
			menu.setfName(menuName);
			menu.setParent_id(parentId);
			menu.setParentAdress(menuUrl);
			if (featureService.EditFeature(menu)) {
				respSuccessMsg(response, null, "修改成功");
				managerLog.setRemark("成功");//操作结果 s=成功 f=失败（原因写备注）
			} else {
				respErrorMsg(response, "修改失败");
				managerLog.setRemark("修改失败");
			}
			managerlogService.AddManagelog(managerLog);
		} catch (Exception e) {
			respErrorMsg(response, "修改失败，系统有异常，请联系管理员");
			managerLog.setRemark("系统异常");
			managerlogService.AddManagelog(managerLog);

		}
	}

	/**
	 * 删除单个菜单
	 * @param request
	 * @param response
	 * @param id
	 */

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public void delete(HttpServletRequest request, HttpServletResponse response,
					   @RequestParam() Integer id) {
		Manager Loginmanager = (Manager) SessionUtils.getAttr(request, "ManagerInfo");
		Managerlog managerLog = new Managerlog();
		try {
			//操作日志
			managerLog.setmId(Loginmanager.getmId());
			managerLog.setCreateTime(new Date());
			managerLog.setContent("删除书籍");
			managerLog.setAccount(Loginmanager.getAccount());
			managerLog.setIp(ContextUtils.getIpAddr(request));
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			if (featureService.Delfeature(id)) {
				respSuccessMsg(response, null, "删除成功");
				managerLog.setRemark("成功");//操作结果 s=成功 f=失败（原因写备注）
			} else {
				respErrorMsg(response, "删除失败");
				managerLog.setRemark("删除失败");
			}
			managerlogService.AddManagelog(managerLog);
		} catch (Exception e) {
			respErrorMsg(response, "修改失败，系统有异常，请联系管理员");
			managerLog.setRemark("系统异常");
			managerlogService.AddManagelog(managerLog);

		}
	}

	/**
	 * 添加菜单——打开页面
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public ModelAndView add() {
		ModelAndView mv = new ModelAndView("/menu/add");
		try {
			List<Feature> firstMenus =featureService.SelectFather();
			mv.addObject("firstMenus", firstMenus);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}

	/**
	 * 添加菜单——提交
	 * @param request
	 * @param response
	 * @param menuName
	 * @param parentId
	 * @param menuUrl
	 */

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public void addMenu(HttpServletRequest request, HttpServletResponse response,
						@RequestParam() String menuName,
						@RequestParam() Integer parentId,
						@RequestParam(required = false) String menuUrl
						) {
		Manager Loginmanager = (Manager) SessionUtils.getAttr(request, "ManagerInfo");
		Managerlog managerLog = new Managerlog();
		Feature feature=new Feature();
		try {
			//操作日志
			managerLog.setmId(Loginmanager.getmId());
			managerLog.setCreateTime(new Date());
			managerLog.setContent("添加菜单");
			managerLog.setAccount(Loginmanager.getAccount());
			managerLog.setIp(ContextUtils.getIpAddr(request));
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			feature.setfName(menuName);
			feature.setParent_id(parentId);
			feature.setParentAdress(menuUrl);
			if (featureService.AddFeature(feature)){
				respSuccessMsg(response, null, "添加成功");
				managerLog.setRemark("添加成功");//操作结果 s=成功 f=失败（原因写备注）
			} else {
				respErrorMsg(response, "添加失败");
				managerLog.setRemark("添加失败");
			}
			managerlogService.AddManagelog(managerLog);
		} catch (Exception e) {
			respErrorMsg(response, "添加失败，系统有异常，请联系管理员");
			managerLog.setRemark("系统异常");
			managerlogService.AddManagelog(managerLog);

		}
	}

}
