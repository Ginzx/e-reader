package com.slsd.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneUtil {
	/**
	 * 中国移动
	 * 134.135.136.137.138.139.150.151.152.157.158.159.182.183.184.187.188.147(数据卡).178
	 */
	public static String regex_mobile = "^1(3[4-9]|47|5[012789]|8[23478]|78)\\d{8}$";
	/**
	 * 中国联通 130.131.132.155.156.185.186.145(数据卡).176
	 */
	public static String regex_unicom = "^1(3[0-2]|5[56]|8[56]|45|76)\\d{8}$";
	/**
	 * 中国电信 133.153.180.189.181.177
	 */
	public static String regex_telecom = "^1([35]3|8[019]|77)\\d{8}$";

	/**
	 * 配置手机号码类型
	 * 
	 * @param mobile 手机号码
	 * @return 1:移动 2:联通 3:电信 0:未匹配
	 */
	public static int phoneMatching(String mobile) {
		if (matching(PhoneUtil.regex_mobile, mobile)) {
			// 移动
			return 1;
		} else if (matching(PhoneUtil.regex_unicom, mobile)) {
			// 联通
			return 2;
		} else if (matching(PhoneUtil.regex_telecom, mobile)) {
			// 电信
			return 3;
		} else {
			// 没有匹配
			return 0;
		}
	}

	/**
	 * 匹配手机号码
	 * 
	 * @param regex 手机号码正则表达式
	 * @param mobile 手机号码
	 * @return 是否匹配上，true是
	 */
	public static boolean matching(String regex, String mobile) {
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(mobile);
		return m.matches();
	}

	/**
	 * 匹配是否为手机号码
	 * 
	 * @param mobile 手机号码
	 * @return 是否手机号码，true是
	 */
	public static boolean isMobile(String mobile) {
		Pattern p = Pattern.compile("^(1(3|5|6|7|8|9)\\d{9})|(14[57]\\d{8})$");
		Matcher m = p.matcher(mobile);
		return m.matches();
	}
}
