package com.slsd.controller;

import com.alibaba.fastjson.JSON;
import com.slsd.util.ContextUtils;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class BaseController {

	private static Logger logger = Logger.getLogger(BaseController.class);

	/** 返回状态键名 **/
	private static final String KEY_CODE = "code";
	/** 返回数据键名 **/
	private static final String KEY_DATA = "data";
	/** 返回信息键名 **/
	private static final String KEY_MSG = "msg";

	/** 代表成功的值 **/
	private static final String VALUE_SUCCESS = "200";
	/** 代表错误的值 **/
	private static final String VALUE_ERROR = "300";

	/**
	 * 封装并以json返回成功执行的信息
	 * @param response
	 * @param data
	 * @param msg
	 */
	protected void respSuccessMsg(HttpServletResponse response, Object data, String msg) {
		Map<String, Object> map = new HashMap<>();
		try {
			map.put(KEY_DATA, data);
			map.put(KEY_CODE, VALUE_SUCCESS);
			map.put(KEY_MSG, msg);
			response.setContentType("application/json");
			ContextUtils.respString(response, JSON.toJSONString(map));
		} catch (Exception e) {
			logger.error(e.toString());
		}
	}

	/**
	 * 封装并以json返回错误执行的信息
	 * @param response
	 * @param msg
	 */
	protected void respErrorMsg(HttpServletResponse response, String msg) {
		Map<String, Object> map = new HashMap<>();
		try {
			map.put(KEY_CODE, VALUE_ERROR);
			map.put(KEY_MSG, msg);
			response.setContentType("application/json");
			ContextUtils.respString(response, JSON.toJSONString(map));
		} catch (Exception e) {
			logger.error(e.toString());
		}
	}
}
