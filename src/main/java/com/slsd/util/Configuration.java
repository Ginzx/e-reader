package com.slsd.util;

import java.util.ResourceBundle;

public class Configuration {

	/**
	 * 读取配置文件信息
	 * 
	 * @param name 读取节点名
	 * @param fileName 文件名
	 * @return 读取的节点值
	 */
	public static String readConfigString(String name, String fileName) {
		String result = null;
		try {
			ResourceBundle rb = ResourceBundle.getBundle(fileName);
			result = rb.getString(name);
		} catch (Exception e) {
			System.out.println("从" + fileName + "读取" + name + "出错:" + e.getMessage());
		}
		return result;
	}
}
