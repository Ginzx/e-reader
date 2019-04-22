package com.slsd.entiy;

/**
 * @program: ereader
 * @description:
 * @author: Mr.Wang
 **/
public class BookContent {
	private Integer id;
	private String chapter;//章节名
	private String content;//内容
	private Integer number; //排序号
	private Integer bid; //书本id

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public Integer getBid() {
		return bid;
	}

	public void setBid(Integer bid) {
		this.bid = bid;
	}

	@Override
	public String toString() {
		return "BookContent{" +
				"id=" + id +
				", chapter='" + chapter + '\'' +
				", content='" + content + '\'' +
				", number=" + number +
				", bid=" + bid +
				'}';
	}
}
