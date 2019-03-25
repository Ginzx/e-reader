package com.slsd.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeUtil {

	/**
	 * 判断时间戳是否在有效范围内
	 * 
	 * @param inputTimes 输入的时间戳
	 * @param regionValue 时间戳范围，毫秒级
	 * @return 是否有效，有效=true
	 */
	public static boolean checkTimeStamp(long inputTimes, long regionValue) {
		return Math.abs(inputTimes - System.currentTimeMillis()) < regionValue;
	}

	/**
	 * 时间对象转为字符串,注意：如果返回null，则表示解析失败
	 * 
	 * @param date 时间
	 * @param pattern 日期字符串的格式， 如:yyyy-MM-dd等形式
	 * @return 结果字符串
	 */
	public static String DateToString(Date date, String pattern) {
		SimpleDateFormat formatter = new SimpleDateFormat(pattern);
		if (null == pattern || "".equals(pattern)) {
			pattern = "yyyy-MM-dd";
		}
		try {
			return formatter.format(date);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 将一个字符串用给定的格式转换为日期类型,注意：如果返回null，则表示解析失败
	 * 
	 * @param datestr 需要解析的日期字符串
	 * @param pattern 日期字符串的格式，默认为“yyyy-MM-dd”的形式
	 * @return 解析后的日期
	 * @throws ParseException
	 */
	public static Date stringToDate(String datestr, String pattern) throws ParseException {
		Date date = null;
		if (null == pattern || "".equals(pattern)) {
			pattern = "yyyy-MM-dd";
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
		date = dateFormat.parse(datestr);
		return date;
	}

	/**
	 * 判断是否闰年
	 * 
	 * @param year 年份
	 * @return true=是闰年
	 */
	public static boolean isLeapYear(int year) {
		return (((year % 4) == 0 && year % 100 != 0) || year % 400 == 0);
	}

	/**
	 * 返回当前年份
	 * 
	 * @return 年份
	 */
	public static int getYear() {
		return Calendar.getInstance().get(Calendar.YEAR);
	}

	/**
	 * 返回当前月份
	 * 
	 * @return 月份
	 */
	public static int getMonth() {
		// 用get得到的月份数比实际的小1，需要加上
		return Calendar.getInstance().get(Calendar.MONTH) + 1;
	}

	/**
	 * 返回当前日
	 * 
	 * @return 几号
	 */
	public static int getMonthDay() {
		return Calendar.getInstance().get(Calendar.DATE);
	}

	/**
	 * 返回当前小时
	 * 
	 * @return 几号
	 */
	public static int getHours() {
		return Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * 取得当前日期以后若干天的日期。如果要得到以前的日期，参数用负数。例如要得到上星期同一天的日期，参数则为-7
	 * 
	 * @param days 增加的日期数
	 * @return 增加以后的日期
	 */
	public static Date addDays(int days) {
		return add(Calendar.getInstance().getTime(), days, Calendar.DATE);
	}

	/**
	 * 取得当前日期以后若干月的日期
	 * 
	 * @param months 增加的月份数
	 * @return 结果日期
	 */
	public static Date addMonths(int months) {
		return add(Calendar.getInstance().getTime(), months, Calendar.MONTH);
	}

	/**
	 * 取得当前日期以后若干年的日期
	 * 
	 * @param years 增加的年份数
	 * @return 结果日期
	 */
	public static Date addYears(int years) {
		return add(Calendar.getInstance().getTime(), years, Calendar.MONTH);
	}

	/**
	 * 内部方法。为指定日期增加相应的天数或月数
	 * 
	 * @param date 基准日期
	 * @param amount 增加的数量
	 * @param field 增加的单位，年，月或者日
	 * @return 增加以后的日期
	 */
	public static Date add(Date date, int amount, int field) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(field, amount);
		return calendar.getTime();
	}

	/**
	 * 计算两个日期相差天数,用第一个日期减去第二个。如果前一个日期小于后一个日期，则返回负数
	 * 
	 * @param one 第一个日期数，作为基准
	 * @param two 第二个日期数，作为比较
	 * @return 两个日期相差天数
	 */
	public static long diffDays(Date one, Date two) {
		return (one.getTime() - two.getTime()) / (24 * 60 * 60 * 1000);
	}

	/**
	 * 计算两个日期相差月份数,如果前一个日期小于后一个日期，则返回负数
	 * 
	 * @param one 第一个日期数，作为基准
	 * @param two 第二个日期数，作为比较
	 * @return 两个日期相差月份数
	 */
	public static int diffMonths(Date one, Date two) {
		Calendar calendar = Calendar.getInstance();
		// 得到第一个日期的年分和月份数
		calendar.setTime(one);
		int yearOne = calendar.get(Calendar.YEAR);
		int monthOne = calendar.get(Calendar.MONDAY);
		// 得到第二个日期的年份和月份
		calendar.setTime(two);
		int yearTwo = calendar.get(Calendar.YEAR);
		int monthTwo = calendar.get(Calendar.MONDAY);
		return (yearOne - yearTwo) * 12 + (monthOne - monthTwo);
	}

	/**
	 * 返回当前月的最后一天
	 * 
	 * @return 本月最后一天的日期
	 */
	public static Date getMonthLastDay() {
		return getMonthLastDay(Calendar.getInstance().getTime());
	}

	/**
	 * 返回给定日期中的月份中的最后一天
	 * 
	 * @param date 基准日期
	 * @return 该月最后一天的日期
	 */
	public static Date getMonthLastDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		// 将日期设置为下一月第一天
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, 1);
		// 减去1天，得到的即本月的最后一天
		calendar.add(Calendar.DATE, -1);
		return calendar.getTime();
	}

	/**
	 * 返回给定日期中的月份中的第一天
	 * 
	 * @param date 基准日期
	 * @return 该月第一天的日期
	 */
	public static Date getMonthFirstDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		return calendar.getTime();
	}

	/**
	 * 获取当前日期为星期几，0=星期天 1=星期一 6=星期六
	 * 
	 * @param date
	 * @return
	 */
	public static int getWeekDay(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int week_index = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (week_index < 0) {
			week_index = 0;
		}
		return week_index;
	}

	/**
	 * 是否周6
	 * 
	 * @param date
	 * @return
	 */
	public static boolean isSatDay(Date date) {
		return getWeekDay(date) == 6;
	}

	/**
	 * 是否周日
	 * 
	 * @param date
	 * @return
	 */
	public static boolean isSunDay(Date date) {
		return getWeekDay(date) == 0;
	}

	/**
	 * 获取指定时间的小时数
	 * 
	 * @param date
	 * @return
	 */
	public static int getHours(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * 获取指定时间的分钟数
	 * 
	 * @param date
	 * @return
	 */
	public static int getMinutes(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.MINUTE);
	}

	/**
	 * 获取指定时间的秒数
	 * 
	 * @param date
	 * @return
	 */
	public static int getSeconds(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.SECOND);
	}

	/**
	 * 获取日期前一天
	 * @param date
	 * @return
	 */
	public static Date getYesterday(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int day = cal.get(Calendar.DATE);
		cal.set(Calendar.DATE, day - 1);
		return cal.getTime();
	}

	/**
	 * 获取日期前n天
	 * @param date
	 * @return
	 */
	public static Date getDaysBefore(Date date, int n) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int day = cal.get(Calendar.DATE);
		cal.set(Calendar.DATE, day - n);
		return cal.getTime();
	}

	/**
	 * 获取指定小时数前的日期
	 * @param date
	 * @param num
	 * @return
	 */
	public static Date getHoursBefore(Date date, int num) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int hour = cal.get(Calendar.HOUR);
		cal.set(Calendar.HOUR, hour - num);
		return cal.getTime();
	}

	/**
	 * 当前年的结束时间，如2012-12-31 23:59:59
	 * 
	 * @return
	 */
	public static Date getCurrentYearEndTime(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		try {
			cal.set(cal.get(Calendar.YEAR), 11,31);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cal.getTime();
	}

	/**
	 ** 当前季度的结束时间，如2012-03-31 23:59:59
	 ** 
	 ** @return
	 *    
	 */
	public static Date getCurrentQuarterEndTime(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int currentMonth = cal.get(Calendar.MONTH) + 1;
		try {
			if (currentMonth >= 1 && currentMonth <= 3) {
				cal.set(cal.get(Calendar.YEAR), 2,31);
			} else if (currentMonth >= 4 && currentMonth <= 6) {
				cal.set(cal.get(Calendar.YEAR), 5,30);
			} else if (currentMonth >= 7 && currentMonth <= 9) {
				cal.set(cal.get(Calendar.YEAR), 8,30);
			} else if (currentMonth >= 10 && currentMonth <= 12) {
				cal.set(cal.get(Calendar.YEAR), 11,31);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cal.getTime();
	}

	/**
	 * 获取当前日期的季度
	 * @return
	 */
	public static String getCurrentQuarter(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int currentMonth = cal.get(Calendar.MONTH) + 1;
		String current=null;
		try {
			if (currentMonth >= 1 && currentMonth <= 3) {
				current="一";
			} else if (currentMonth >= 4 && currentMonth <= 6) {
				current="二";
			} else if (currentMonth >= 7 && currentMonth <= 9) {
				current="三";
			} else if (currentMonth >= 10 && currentMonth <= 12) {
				current="四";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return current;
	}

	/**
	 * 返回给定日期的年份
	 *
	 * @return 年份
	 */
	public static int getTimeYear(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.YEAR);
	}

	/**
	 * 返回给定日期的月份
	 *
	 * @return 月份
	 */
	public static int getTimeMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.MONTH)+ 1;
	}

}
