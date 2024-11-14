package com.fitnessbet.betting.model.dto;

public class Review {
	
	private int id;
	private String writer;
	private String content;
	private String regDate;
	private String modDate;
	private int bettingId;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	public String getModDate() {
		return modDate;
	}
	public void setModDate(String modDate) {
		this.modDate = modDate;
	}
	public int getBettingId() {
		return bettingId;
	}
	public void setBettingId(int bettingId) {
		this.bettingId = bettingId;
	}
	
	
	
}
