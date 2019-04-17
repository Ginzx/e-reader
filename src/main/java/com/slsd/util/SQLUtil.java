package com.slsd.util;

public class SQLUtil {
	// 定义数据库类型
	public static String DBMS_ORACLE_TYPE = "oracle";

	public static String DBMS_MYSQL_TYPE = "mysql";

	public SQLUtil() throws Exception {
	}

	/**
	 * 
	 * @param sql 标准的SQL语句
	 * @param pageStart 页开始
	 * @param pageSize 页大小
	 * @param db_type 数据库类型
	 * @return
	 */
	public static String getPageSQL(String sql, long pageStart, int pageSize, String db_type) {
		long pageEnd = pageStart + pageSize;
		StringBuffer retStr = new StringBuffer(sql);
		if (db_type.equals(DBMS_ORACLE_TYPE)) {
			boolean hasOffset = true;
			sql = sql.trim();
			StringBuffer pagingSelect = new StringBuffer(sql.length() + 100);
			if (hasOffset) {
				pagingSelect.append("select * from ( select row_.*, rownum rownum_ from ( ");
			} else {
				pagingSelect.append("select * from ( ");
			}
			pagingSelect.append(sql);
			if (hasOffset) {
				pagingSelect.append(" ) row_ where rownum <= " + pageEnd + ") where rownum_ > "
						+ pageStart + "");
			} else {
				pagingSelect.append(" ) where rownum <= " + pageSize + "");
			}
//			System.out.println(pagingSelect.toString());
			return pagingSelect.toString();

		} else if (db_type.equals(DBMS_MYSQL_TYPE)) {
			sql = sql.trim();
			StringBuffer pagingSelect = new StringBuffer(sql.length() + 100);
			pagingSelect.append(" limit "+pageStart+" "+pageSize+" ");
			return pagingSelect.toString();
		}

		return retStr.toString();
	}

	/**
	 *
	 * @param sql 标准的SQL语句
	 * @param currPage 当前页
	 * @param pageSize 页大小
	 * @return sql
	 */
	public static String getOraclePageSQL(String sql, long currPage, int pageSize) {
		long pageStart = pageSize * (currPage - 1);
		long pageEnd = pageStart + pageSize;
		sql = sql.trim();
		return "select * from ( select row_.*, rownum rownum_ from ( " +
				sql +
				" ) row_ where rownum <= " + pageEnd + ") where rownum_ > " + pageStart;
	}

}
