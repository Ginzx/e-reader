package com.slsd.util;

import java.util.Random;

public class RandomUtil {

	/**
	 * 生成随机几位数字字符串
	 * 
	 * @param k 生成位数
	 * @return 数字字符串
	 */
	public static String generateRandomNumber(int k) {
		Random random = new Random();
		String sRand = "";
		for (int i = 0; i < k; i++) {
			String rand = String.valueOf(random.nextInt(10));
			sRand += rand;
		}
		return sRand;
	}

	/**
	 * 生成固定长度的随机字符,生成字母A-Z,a-z之间的随机字符
	 * 
	 * @param len 随机个数
	 * @return 随机字符
	 */
	public static String generateRandomChar(int len) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < len; i++) {
			int intRand = (int) (Math.random() * 52);
			char base = (intRand < 26) ? 'A' : 'a';
			char c = (char) (base + intRand % 26);
			sb.append(c);
		}
		return sb.toString();
	}

	/**
	 * 生成固定长度的随机字符和数字
	 * 
	 * @param len 位数
	 * @return 随机字符和数字
	 */
	public static String generateRandomCharAndNumber(int len) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < len; i++) {
			int intRand = (int) (Math.random() * 52);
			int numValue = (int) (Math.random() * 10);
			char base = (intRand < 26) ? 'A' : 'a';
			char c = (char) (base + intRand % 26);
			if (numValue % 2 == 0) {
				sb.append(c);
			} else {
				sb.append(numValue);
			}
		}
		return sb.toString();
	}

}
