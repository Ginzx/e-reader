package com.slsd.controller;

import com.slsd.entiy.Feature;
import com.slsd.entiy.Manager;
import com.slsd.entiy.Managerlog;
import com.slsd.entiy.User;
import com.slsd.service.FeatureService;
import com.slsd.service.ManagerService;
import com.slsd.service.ManagerlogService;
import com.slsd.util.ContextUtils;
import com.slsd.util.SessionUtils;
import com.slsd.util.StringUtil;
import com.slsd.util.ValidateCode;
import jdk.nashorn.internal.ir.RuntimeNode;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @program: ereader
 * @description: 管理员控制模块
 * @author: Mr.Wang
 **/
@Controller
@RequestMapping(value = "/")
public class ManagerController extends BaseController {

	@Resource
	private ManagerService managerService;

	@Resource
	private FeatureService featureService;
	@Resource
	private ManagerlogService managerlogService;

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView getLogin() {
		return new ModelAndView("/login");
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public void postLogin(HttpServletRequest request, HttpServletResponse response,
						  @RequestParam() String account,
						  @RequestParam() String pwd,
						  @RequestParam() String smscode) {
		Managerlog managerlog = new Managerlog();
		try {
			String randCode = (String) request.getSession().getAttribute("randcode");
			if (!StringUtil.isEmpty(randCode)) {
				// 验证码正确
				if (!randCode.equalsIgnoreCase(smscode)) {
					respErrorMsg(response, "登录失败，验证码不正确");
					return;
				}
			}
			Manager manager = managerService.login(account, pwd);
			if (manager == null) {
				respErrorMsg(response, "登录失败，请检查帐号和密码");
				return;
			} else {
				List<Feature> list = featureService.SelectMangerfeatureF(manager.getmId());
				if (list.size() == 0 && manager.getmId() != 0) {
					respErrorMsg(response, "请联系管理员配置帐号的角色权限");
					return;
				}
				if (manager.getStatus() == 3) {
					respErrorMsg(response, "帐号已被禁用，请联系管理员");
					return;
				} else {
					Integer mid = manager.getmId();
					SessionUtils.setAttr(request, "ManagerInfo", manager);
					String ip = ContextUtils.getIpAddr(request);
					managerlog.setIp(ip);
					managerlog.setAccount(account);
					managerlog.setContent("登录");
					managerlog.setCreateTime(new Date());
					managerlog.setmId(manager.getmId());
					managerlog.setRemark("成功");
					managerlogService.AddManagelog(managerlog);
					respSuccessMsg(response, true, "登录成功");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 右侧欢迎页
	 *
	 * @return
	 */
	@RequestMapping(value = "/welcome", method = RequestMethod.GET)
	public ModelAndView getIndexRight() {
		return new ModelAndView("/welcome");
	}

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public ModelAndView getIndex(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("/index");
		Manager manager = (Manager) SessionUtils.getAttr(request, "ManagerInfo");
		List<Feature> featureList = new ArrayList<>();
		if (manager.getmId() == 0) {
			featureList = featureService.SelectFather();
			for (int i = 0; i < featureList.size(); i++) {
				featureList.get(i).setFeatures(featureService.SelectSon(featureList.get(i).getfId()));
			}
		} else {
			featureList = featureService.SelectMangerfeatureF(manager.getmId());
			for (int i = 0; i < featureList.size(); i++) {
				featureList.get(i).setFeatures(featureService.SelectManagerFeatureS(manager.getmId(), featureList.get(i).getfId()));
			}
		}
		mv.addObject("menus", featureList);
		mv.addObject("manager", manager);
		return mv;
	}

	/**
	 * 退出
	 *
	 * @return
	 */
	@RequestMapping(value = "/exit", method = RequestMethod.POST)
	public void exit(HttpServletRequest request, HttpServletResponse response) {
		SessionUtils.removeAttr(request, "ManagerInfo");
		respSuccessMsg(response, null, "退出成功");
	}

	/**
	 * 验证码
	 *
	 * @return
	 */
	@RequestMapping(value = "/getRand")
	public void getRand(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 设置响应的类型格式为图片格式
		response.setContentType("image/jpeg");
		// 禁止图像缓存。
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		HttpSession session = request.getSession();
		ValidateCode vCode = new ValidateCode(102, 45, 4, 80);
		session.setAttribute("randcode", vCode.getCode());
		vCode.write(response.getOutputStream());
	}

	@RequestMapping(value = "/validateCode")
	public void validateCode(HttpServletRequest request, HttpServletResponse response,
							 @RequestParam() String smscode) throws ServletException, IOException {
		String randCode = (String) request.getSession().getAttribute("randcode");
		if (!StringUtil.isEmpty(randCode)) {
			// 验证码正确
			if (!randCode.equalsIgnoreCase(smscode)) {
				respErrorMsg(response, "验证码不正确");
			} else {
				respSuccessMsg(response, true, "成功");
			}
		}
	}


}
