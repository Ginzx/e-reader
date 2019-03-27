package com.slsd.util;


import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class StringUtil {
	/**
	 * 判断字符串(过滤前后空格)是否为空或null，为空返回true
	 * 
	 * @param str 待判定字符串
	 * @return 是否为空或null
	 */
	public static boolean isEmpty(String str) {
		return (str == null || str.trim().length() == 0);
	}

	/**
	 * 是否包含空字符串
	 * 
	 * @param strs
	 * @return
	 */
	public static boolean hasEmptyStr(String... strs) {
		for (String str : strs) {
			if (isEmpty(str)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 对象转为string格式，为空时，转为默认带入字符串
	 * 
	 * @param obj 待转对象
	 * @param defaultStr 默认字符串
	 * @return 字符串
	 */
	public static String objToString(Object obj, String defaultStr) {
		if (obj == null) {
			return defaultStr;
		} else {
			return obj.toString();
		}
	}

	/**
	 * 判断字符串长度是否合法
	 * 
	 * @param instr 输入字符串
	 * @param minLength 最小长度
	 * @param maxLength 最大长度
	 * @return true=合法
	 */
	public static boolean checkStringLength(String instr, int minLength, int maxLength) {
		return !(instr.length() < minLength || instr.length() > maxLength);
	}

	/**
	 * 判断字符串是否全是数字
	 * 
	 * @param str 输入字符串
	 * @return true=全是数字
	 */
	public static boolean isNum(String str) {
		if (null == str) {
			return false;
		}
		return str.matches("\\d+");
	}

	/**
	 * 判断是否为整数
	 * @param str 输入字符串
	 * @return 是整数返回true,否则返回false
	 */
	public static boolean isInteger(String str) {
		return str.matches("^[-\\+]?[\\d]+$");
	}

	/**
	 * 判断是否为浮点数，包括double和float
	 * @param str 传入的字符串
	 * @return 是浮点数返回true,否则返回false
	 */
	public static boolean isDouble(String str) {
		return str.matches("^[-\\+]?\\d+\\.\\d+$");
	}

	/**
	 * 判断是否URL
	 * @param url 路径
	 * @return true=是url
	 */
	public static boolean isURL(String url) {
		if (isEmpty(url)) {
			return false;
		}
		return url.matches("http://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?");
	}

	/**
	 * 判断是否Email
	 * @param email 邮箱地址
	 * @return true=是邮箱
	 */
	public static boolean isEmail(String email) {
		if (isEmpty(email)) {
			return false;
		}
//		return email.toLowerCase().matches("[_a-z0-9-]+(\\.[_a-z0-9-]+)*@[a-z0-9-]+(\\.[a-z0-9-]+)*");
		return email.toLowerCase().matches("^([a-z0-9A-Z_]+[-|\\.]?)+[a-z0-9A-Z_]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$");
	}

	/**
	 * 是否只含数字和字母
	 * @param str
	 * @return
	 */
	public static boolean isLetterOrDigit(String str) {
		String regex = "^[a-z0-9A-Z]+$";
		return str.matches(regex);
	}

	/**
	 * 获取url路径的page地址(如http://a.com/1.html返回1.html)
	 * @param strURL
	 * @return
	 */
	public static String getPageFromUrl(String strURL) {
		String temp = null;
		int len = strURL.indexOf("?");
		if (len > -1) {
			temp = strURL.substring(0, strURL.indexOf("?"));
			return temp.substring(temp.lastIndexOf("/") + 1);
		} else {
			return strURL.substring(strURL.lastIndexOf("/") + 1);
		}
	}

	/**
	 * 去掉url中的路径，留下请求参数部分
	 * @param strURL url地址
	 * @return url请求参数部分
	 */
	private static String TruncateUrlPage(String strURL) {
		String temp = null;
		int len = strURL.indexOf("?");
		if (len > -1) {
			return strURL.substring(len + 1);
		} else {
			return temp;
		}
	}

	/**
	 * 获取url参数键值对
	 * @param strURL
	 * @return
	 */
	public static Map<String, String> getUrlParamsMap(String strURL) {
		// 截取?之后的所有参数
		String StrParams = TruncateUrlPage(strURL);
		if (StrParams != null) {
			Map<String, String> paramMap = new HashMap<String, String>();
			String paramaters[] = StrParams.split("&");
			for (String param : paramaters) {
				String values[] = param.split("=");
				paramMap.put(values[0], values[1]);
			}
			return paramMap;
		} else {
			return null;
		}
	}
	private static boolean isEmojiCharacter(char codePoint) {
		return (codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA)
				|| (codePoint == 0xD)
				|| ((codePoint >= 0x20) && (codePoint <= 0xD7FF))
				|| ((codePoint >= 0xE000) && (codePoint <= 0xFFFD))
				|| ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF));
	}

	/**
	 * 过滤emoji 或者 其他非文字类型的字符
	 *
	 * @param source
	 * @return
	 */
	public static String filterEmoji(String source) {
		if (StringUtils.isBlank(source)) {
			return source;
		}
		StringBuilder buf = null;
		int len = source.length();
		for (int i = 0; i < len; i++) {
			char codePoint = source.charAt(i);
			if (isEmojiCharacter(codePoint)) {
				if (buf == null) {
					buf = new StringBuilder(source.length());
				}
				buf.append(codePoint);
			}
		}
		if (buf == null) {
			return source;
		} else {
			if (buf.length() == len) {
				buf = null;
				return source;
			} else {
				return buf.toString();
			}
		}
	}
	
	public static void main(String[] args) {
		System.out.println(isNum(""));
	}

}
