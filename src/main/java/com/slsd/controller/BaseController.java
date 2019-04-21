package com.slsd.controller;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.slsd.util.ContextUtils;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
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

	public void writeJson(HttpServletResponse response, String jsonStr) {
		response.setContentType("text/json;charset=utf-8");
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		PrintWriter pw = null;
		try {
			pw = response.getWriter();
			pw.write(jsonStr);
			pw.flush();
		} catch (Exception e) {
			logger.info("输出JSON数据异常", e);
		}finally{
			if(pw!=null){
				pw.close();
			}
		}
	}
	/**
	 *
	 * 向页面响应json字符数组串流.
	 *
	 * @param response
	 * @param jsonStr
	 * @throws IOException
	 * @return void
	 * @author 蒋勇
	 * @date 2015-1-14 下午4:18:33
	 */
	public void writeJsonStr(HttpServletResponse response, String jsonStr) throws IOException {

		OutputStream outStream = null;
		try {
			response.reset();
			response.setCharacterEncoding("UTF-8");
			outStream = response.getOutputStream();
			outStream.write(jsonStr.getBytes("UTF-8"));
			outStream.flush();
		} catch (IOException e) {
			logger.info("输出JSON数据异常(writeJsonStr)", e);
		} finally {
			if(outStream!=null){
				outStream.close();
			}
		}
	}

	public void writeJsonStr(HttpServletResponse response, InputStream in) throws IOException {

		if(null == in ){
			return ;
		}
		OutputStream outStream = null;
		try {
			response.reset();
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			outStream = response.getOutputStream();
			int len = 0;
			byte[] byt = new byte[1024];
			while ((len = in.read(byt)) != -1) {
				outStream.write(byt, 0, len);
			}
			outStream.flush();

		} catch (IOException e) {

			logger.info("输出JSON数据异常(writeJsonStr)", e);
		} finally {
			if(outStream!=null){
				outStream.close();
				in.close();
			}
		}
	}


	/**
	 * 输出JSON数据
	 *
	 * @param response
	 * @param
	 */
	public void writeJson(HttpServletResponse response, Object obj) {
		response.setContentType("text/json;charset=utf-8");
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		PrintWriter pw = null;
		Gson gson = new Gson();
		try {
			pw = response.getWriter();
			pw.write(gson.toJson(obj));

			pw.flush();
		} catch (Exception e) {
			logger.info("输出JSON数据异常", e);
		}finally{
			if(pw!=null){
				pw.close();
			}
		}
	}




	public void writeHtml(HttpServletResponse response, String html) {
		response.setContentType("text/html;;charset=utf-8");
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		PrintWriter pw = null;
		try {
			pw = response.getWriter();
			pw.write(html);
			pw.flush();
		} catch (Exception e) {
			logger.info("输出HTML数据异常", e);
		}finally{
			if(pw!=null){
				pw.close();
			}
		}
	}

}
