package com.slsd.sql;

import com.slsd.entiy.BookContent;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HelloTest {
	public static void main(String[] args) {
		String fileNamedirs = "F:\\《无疆》（校对版全本）作者：小刀锋利.txt";
		try {
			// 编码格式
			String encoding = "GBK";
			// 文件路径
			File file = new File(fileNamedirs);
			if (file.isFile() && file.exists()) { // 判断文件是否存在
				// 输入流
				InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);// 考虑到编码格
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				Long count = (long) 0;
				int n = 0;
				String newStr = null;
				String titleName = null;
				String newChapterName = null;//新章节名称
				String substring = null;
				int indexOf = 0;
				int indexOf1 = 0;
				int line = 0;
				//小说内容类
				BookContent content = new BookContent();
				while ((lineTxt = bufferedReader.readLine()) != null) {
					content = new BookContent();
					count++;
					// 正则表达式
					Pattern p = Pattern.compile("(^\\s*第)(.{1,9})[章节卷集部篇回](\\s{1})(.*)($\\s*)");
					Matcher matcher = p.matcher(lineTxt);
					Matcher matcher1 = p.matcher(lineTxt);
					newStr = newStr + "<p  class=\"content-text\">" + lineTxt + "</p>";

					while (matcher.find()) {
						content.setChapter(newChapterName);
						titleName = matcher.group();
						newChapterName = titleName.trim();
						indexOf1 = indexOf;
						indexOf = newStr.indexOf(newChapterName);
						if (n == 0) {
							indexOf1 = newStr.indexOf(newChapterName);
						}
						n = 1;
					}
					while (matcher1.find()) {
						if (indexOf != indexOf1) {
							//根据章节数量生成
							content.setNumber(++line);
							content.setId(line);
							substring = newStr.substring(indexOf1, indexOf);
							content.setContent(substring);
							System.out.println(content.toString());
						}
					}
				}
				indexOf1 = indexOf;
				content.setChapter(newChapterName);
				content.setNumber(++line);
				content.setId(line);
				content.setContent(newStr.substring(indexOf1));
				System.out.println(content.toString());
				bufferedReader.close();
			} else {
				System.out.println("找不到指定的文件");
			}
		} catch (Exception e) {
			System.out.println("读取文件内容出错");
			e.printStackTrace();
		}
	}
}
