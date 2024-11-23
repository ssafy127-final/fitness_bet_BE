package com.fitnessbet.product.model.dto;

import com.fitnessbet.user.model.dto.User;

public class DateFilter {
	
	private String from;
	private String to;
	
	private User userInfo;
	
	
	public User getUserInfo() {
		return userInfo;
	}
	public void setUserInfo(User userInfo) {
		this.userInfo = userInfo;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	
	

}
