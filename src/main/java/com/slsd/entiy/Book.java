package com.slsd.entiy;

import java.io.Serializable;
import java.util.Date;

/**
 * @program: ereader
 * @description:书籍
 * @author: Mr.Wang
 **/
public class Book implements Serializable {

	private static final long serialVersionUID = 7380107790072404223L;
	private Integer bookid;//书籍编号
	private String bookName;//书籍名称
	private String Status;//书籍状态
	private String author;//书籍作者
	private String classification;
	private String bookurl;
	private String content;
	private Date createTime;
	private Date modifyTime;

	public String getClassification() {
		return classification;
	}

	public void setClassification(String classification) {
		this.classification = classification;
	}

	public String getBookurl() {
		return bookurl;
	}

	public void setBookurl(String bookurl) {
		this.bookurl = bookurl;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public Integer getBookid() {
		return bookid;
	}

	public void setBookid(Integer bookid) {
		this.bookid = bookid;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		Status = status;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	@Override
	public String toString() {
		return "book{" +
				"bookid=" + bookid +
				", bookName='" + bookName + '\'' +
				", Status='" + Status + '\'' +
				", author='" + author + '\'' +
				'}';
	}
}
