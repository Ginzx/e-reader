package com.slsd.controller;

import com.slsd.entiy.Manager;
import com.slsd.service.ManageService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

/**
 * @program: ereader
 *
 * @description: 管理员控制模块
 *
 * @author: Mr.Wang
 *
 **/
@Controller
@RequestMapping(value = "/manager")
public class ManagerController extends BaseController{

	@Resource
	private ManageService manageService;

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView getLogin() {
		return new ModelAndView("/login");
	}

	@RequestMapping(value = "/login",method = RequestMethod.POST)
	public void postLogin(){

	}


}
