package com.slsd.entiy;

/**
 * @program: ereader
 * @description:
 * @author: Mr.Wang
 **/
public class BookContent {
	private long id;
	private String chapter;//章节名
	private String content;//内容
	private long number; //排序号
	private long bid; //书本id

	public long getBid() {
		return bid;
	}

	public void setBid(long bid) {
		this.bid = bid;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getChapter() {
		return chapter;
	}

	public void setChapter(String chapter) {
		this.chapter = chapter;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public long getNumber() {
		return number;
	}

	public void setNumber(long number) {
		this.number = number;
	}

	@Override
	public String toString() {
		return "Content{" +
				"id=" + id +
				", chapter='" + chapter + '\'' +
				", content='" + content + '\'' +
				", number=" + number +
				'}';
	}
}
