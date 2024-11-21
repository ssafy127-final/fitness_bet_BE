package com.fitnessbet.user.model.dto;

import java.sql.Date;

public class PointHistory {
	private int id;
	private String userId;
	private Integer productId;
	private Integer bettingId;
	private int category;
	private Date recordDate;
	private int point;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	public Integer getBettingId() {
		return bettingId;
	}
	public void setBettingId(Integer bettingId) {
		this.bettingId = bettingId;
	}
	public int getCategory() {
		return category;
	}
	public void setCategory(int category) {
		this.category = category;
	}
	public Date getRecordDate() {
		return recordDate;
	}
	public void setRecordDate(Date recordDate) {
		this.recordDate = recordDate;
	}
	public int getPoint() {
		return point;
	}
	public void setPoint(int point) {
		this.point = point;
	}
	
	@Override
	public String toString() {
		return "PointHistory [id=" + id + ", userId=" + userId + ", productId=" + productId + ", bettingId=" + bettingId
				+ ", category=" + category + ", recordDate=" + recordDate + ", point=" + point + "]";
	}
	
	
	
}
