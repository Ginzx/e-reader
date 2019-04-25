package com.slsd.controller;

import com.slsd.entiy.Activity;
import com.slsd.entiy.Manager;
import com.slsd.entiy.Managerlog;
import com.slsd.service.ActivityService;
import com.slsd.service.ManagerlogService;
import com.slsd.util.ContextUtils;
import com.slsd.util.SessionUtils;
import com.slsd.util.StringUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
@RequestMapping("/activity")
public class ActivityController extends BaseController {
	@Resource
	private ManagerlogService managerlogService;
	@Resource
	private ActivityService activityService;

	@RequestMapping(value = "/qryReadActivityCount", method = RequestMethod.GET)
	public ModelAndView ActivityCount(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("/activity/search");
		try {
			Integer total = activityService.countActivity(null);
			mv.addObject("total", total);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}

	@RequestMapping(value = "/qryReadActivityCount", method = RequestMethod.POST)
	public ModelAndView ActivityCount1(HttpServletRequest request, HttpServletResponse response,
											 String name, Integer publishStatus) {
		ModelAndView mv = new ModelAndView("/activity/search");
		try {
			Activity activity = new Activity();
			activity.setName(name);
			activity.setPublishStatus(publishStatus);
			Integer total = activityService.countActivity(activity);
			mv.addObject("total", total);
			mv.addObject("name", name);
			mv.addObject("publishStatus", publishStatus);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}

	@RequestMapping(value = "/qryReadActivityList", method = RequestMethod.POST)
	public ModelAndView qryReadActivityList(HttpServletRequest request,HttpServletResponse response,String name, Integer publishStatus,Integer curr, Integer size) {
		ModelAndView mv = new ModelAndView("/activity/list");
		try {
		Activity readActivity=new Activity();
			readActivity.setName(name);
			readActivity.setPublishStatus(publishStatus);
			List<Activity> ReadActivityList=activityService.SelectActivity(curr,size,readActivity);
			Integer total = activityService.countActivity(readActivity);
			mv.addObject("total", total);
			mv.addObject("ReadActivityList", ReadActivityList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}

	@RequestMapping(value = "updateReadActivity", method = RequestMethod.GET)
	private ModelAndView updateActivity(HttpServletRequest request,HttpServletResponse response,Integer activityId) {
		ModelAndView mv = new ModelAndView("activity/update");
		try {
			Activity ReadActivity=activityService.SelectByid(activityId);
			mv.addObject("readActivity",ReadActivity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}

	@RequestMapping(value = "updateReadActivity", method = RequestMethod.POST)
	private void updateActivity(HttpServletRequest request,
									HttpServletResponse response,Integer activityId,String name,String content,String contentEnd,String title,String coverImg,String bannerImg) {
		if(StringUtil.isEmpty(bannerImg)){
			respErrorMsg(response, "banner图片为必填");
			return;
		}
		Manager Loginmanager = (Manager) SessionUtils.getAttr(request, "ManagerInfo");
		Managerlog managerLog = new Managerlog();
		try {
			//操作日志
			managerLog.setmId(Loginmanager.getmId());
			managerLog.setCreateTime(new Date());
			managerLog.setContent("修改活动");
			managerLog.setAccount(Loginmanager.getAccount());
			managerLog.setIp(ContextUtils.getIpAddr(request));
		} catch (Exception e) {
			e.printStackTrace();
		}
		Activity readActivity = activityService.SelectByid(activityId);
		readActivity.setName(name);
		readActivity.setContent(content);
		readActivity.setContentEnd(contentEnd);
		readActivity.setTitle(title);
		readActivity.setBannerImgs(bannerImg);
		readActivity.setCoverImg(coverImg);
		readActivity.setModifyTime(new Date());
		try {
			activityService.updateActivity(readActivity);
			respSuccessMsg(response, true,"修改成功");
			managerLog.setRemark("成功");
			managerlogService.AddManagelog(managerLog);
		} catch (Exception e) {
			respErrorMsg(response, "修改失败,请稍后再试");
			managerLog.setRemark("修改失败");
			managerlogService.AddManagelog(managerLog);
		}
	}


	@RequestMapping(value = "ActivityGrounding", method = RequestMethod.POST)
	private void ActivityGround(HttpServletRequest request,HttpServletResponse response,Integer activityId,Integer Stauts) {
		Manager Loginmanager = (Manager) SessionUtils.getAttr(request, "ManagerInfo");
		Managerlog managerLog = new Managerlog();
		try {
			//操作日志
			managerLog.setmId(Loginmanager.getmId());
			managerLog.setCreateTime(new Date());
			managerLog.setContent("活动发布状态修改");
			managerLog.setAccount(Loginmanager.getAccount());
			managerLog.setIp(ContextUtils.getIpAddr(request));
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			Activity activity=activityService.SelectByid(activityId);
			activity.setPublishStatus(Stauts);
			activity.setModifyTime(new Date());
			activityService.updateActivity(activity);
			respSuccessMsg(response, true,"成功");
			managerLog.setRemark("成功");
			managerlogService.AddManagelog(managerLog);
		} catch (Exception e) {
			respErrorMsg(response, "上架失败,请稍后再试");
			managerLog.setRemark("失败");
			managerlogService.AddManagelog(managerLog);
		}
	}

	@RequestMapping(value = "delReadActivity", method = RequestMethod.POST)
	private void delReadActivity(HttpServletRequest request,HttpServletResponse response,Integer id) {
		Manager Loginmanager = (Manager) SessionUtils.getAttr(request, "ManagerInfo");
		Managerlog managerLog = new Managerlog();
		try {
			managerLog.setmId(Loginmanager.getmId());
			managerLog.setCreateTime(new Date());
			managerLog.setContent("删除活动");
			managerLog.setAccount(Loginmanager.getAccount());
			managerLog.setIp(ContextUtils.getIpAddr(request));
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			Activity activity=activityService.SelectByid(id);
			if (activity.getPublishStatus()==1){
				respErrorMsg(response,"删除失败");
				managerLog.setRemark("删除失败，活动上架中");
			}else {
				activityService.delActivity(id);
				managerLog.setRemark("删除成功");
				respSuccessMsg(response, true,"删除成功");
			}
			managerlogService.AddManagelog(managerLog);
		} catch (Exception e) {
			respErrorMsg(response,"删除失败");
			managerLog.setRemark("删除失败，系统有异常");
			managerlogService.AddManagelog(managerLog);
		}
	}

	@RequestMapping(value = "addReadActivity", method = RequestMethod.GET)
	private ModelAndView addReadActivity(HttpServletRequest request,HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("activity/add");
		return mv;
	}

	@RequestMapping(value = "addReadActivity", method = RequestMethod.POST)
	private void addReadActivity(HttpServletRequest request,HttpServletResponse response,String name,String content,String contentEnd,String overTime,String title,String coverImg,String bannerImg) {
		if(StringUtil.isEmpty(bannerImg)){
			respErrorMsg(response, "banner图片为必填");
			return;
		}
		Manager Loginmanager = (Manager) SessionUtils.getAttr(request, "ManagerInfo");
		Managerlog managerLog = new Managerlog();
		try {
			//操作日志
			managerLog.setmId(Loginmanager.getmId());
			managerLog.setCreateTime(new Date());
			managerLog.setContent("创建活动");
			managerLog.setAccount(Loginmanager.getAccount());
			managerLog.setIp(ContextUtils.getIpAddr(request));
		} catch (Exception e) {
			e.printStackTrace();
		}
		Activity ReadActivity = new Activity();
		ReadActivity.setName(name);
		ReadActivity.setTitle(title);
		ReadActivity.setBannerImgs(bannerImg);
		ReadActivity.setCoverImg(coverImg);
		ReadActivity.setContent(content);
		ReadActivity.setContentEnd(contentEnd);
		ReadActivity.setPublishStatus(0);
		ReadActivity.setCreateTime(new Date());
		ReadActivity.setPublishUser(Loginmanager.getAccount());
		try {
			activityService.addActivity(ReadActivity);
			respSuccessMsg(response, true, "添加成功");
			managerlogService.AddManagelog(managerLog);
		} catch (Exception e) {
			respErrorMsg(response, "添加失败,请稍后再试");
			managerLog.setRemark("添加失败，系统有异常");
			managerlogService.AddManagelog(managerLog);
		}
	}
}
