package com.slsd.util;

public class UserAgentUtil {
	/**
	 * 根据ua获取操作系统
	 * @param ua
	 * @return android/ios/pc/other
	 */
	public static String getOs(String ua) {
		if (ua.contains("Windows")) {
			return "pc";
		} else if (ua.contains("Mac OS X")||ua.contains("iOS")) {
			return "ios";
		} else if (ua.contains("Android")||ua.contains("okhttp")) {
			return "android";
		} else {
			return "other";
		}
	}

	/**
	 * 根据ua获取浏览器类型
	 * @param ua
	 * @return QQ/UC/Sougou/Baidu/Safari/360/Chrome/IE/other
	 */
	public static String getBrowserType(String ua) {
		if (ua.contains("MQQBrowser")) {
			return "QQ";
		} else if (ua.contains("UCWEB") || ua.contains("UCBrowser")) {
			return "UC";
		} else if (ua.contains("SogouMobileBrowser")) {
			return "Sougou";
		} else if (ua.contains("baidubrowser")) {
			return "Baidu";
		} else if (ua.contains("Safari") && ua.contains("Mac OS X")) {
			return "Safari";
		} else if (ua.contains("360SE") || ua.contains("360browser")) {
			return "360";
		} else if (ua.contains("Chrome")) {
			return "Chrome";
		}else if (ua.contains("MSIE")) {
			return "IE";
		} else {
			return "other";
		}
	}
}
