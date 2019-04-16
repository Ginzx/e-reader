package com.slsd.controller;

import com.slsd.entiy.Manager;
import com.slsd.entiy.Managerlog;
import com.slsd.service.ManagerService;
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

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public void UpdatePost(HttpServletRequest request, HttpServletResponse response, Integer mid, String account,
						   @RequestParam() String name,
						   @RequestParam(required = false) Integer status,
						   @RequestParam(required = false) String enPwd,
						   Integer flag) {
		Manager Loginmanager = (Manager) SessionUtils.getAttr(request, "ManagerInfo");
		Manager manager = new Manager();
		Managerlog managerLog = new Managerlog();
		if(status==null){
			status=1;
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
			if (handleflag){
				manager.setAccount(account);
				manager.setModifyTime(new Date());
				manager.setmId(mid);
				manager.setName(name);
				if(enPwd==""){
					Manager m1=managerService.SelectById(mid);
					String pwd=m1.getPwd();
					manager.setPwd(pwd);
				}else{
					manager.setPwd(enPwd);
				}
				manager.setStatus(status);
				if(managerService.editmanager(manager)){
					managerLog.setRemark("成功");
				}else{
					managerLog.setRemark("失败");
				}
			}
			managerlogService.AddManagelog(managerLog);
			 respSuccessMsg(response, true, "修改成功");
		}catch (Exception e){
			e.printStackTrace();
		}
	}
}
