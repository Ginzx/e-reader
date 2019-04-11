package com.slsd.util;

import java.awt.*;
import java.io.ByteArrayInputStream;

/**
 * ttf字体文件
 * 
 * @author dsna
 */
public class ImgFontByte {
	public Font getFont(int fontHeight) {
		try {
			Font baseFont = Font.createFont(Font.TRUETYPE_FONT, new ByteArrayInputStream(hex2byte(getFontByteStr())));
			return baseFont.deriveFont(Font.PLAIN, fontHeight);
		} catch (Exception e) {
			return new Font("Arial", Font.PLAIN, fontHeight);
		}
	}

	private byte[] hex2byte(String str) {
		if (str == null)
			return null;
		str = str.trim();
		int len = str.length();
		//Check for oddness that won't work for negative numbers
		//在len是负数时，len%2==1是不正确的，(-5) % 2 == -1)。感觉改为if(len&1==1) return null;更好
		if (len == 0 || len % 2 == 1)
			return null;

		byte[] b = new byte[len / 2];
		try {
			for (int i = 0; i < str.length(); i += 2) {
				b[i / 2] = (byte) Integer.decode("0x" + str.substring(i, i + 2)).intValue();
			}
			return b;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * ttf字体文件的十六进制字符串
	 * 
	 * @return
	 */
	private String getFontByteStr() {
		return "0001000000100040000400c04f532f327d8175d4000087740000005650434c5461e3d9fb00008";
	}
}