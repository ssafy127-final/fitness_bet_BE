package com.fitnessbet.user.model.dto;

import java.sql.Date;

public class User {
	private String id; // 학번
	private String pw; // 비밀번호
	private String name; // 이름
	private String campus; // 소속 캠퍼스
	private int classNum; // 소속 반
	private int currentPoint; // 현재 보유 포인트
	private int totalPoint; // 누적 포인트
	private Date visited; // 마지막 방문 날짜
	private int gender; // 0 : 여성 / 1 : 남성
	private int admin; // 0 : 일반 / 1 : 관리자
	
	private int accept; // 0 : 가입 대기중  / 1: 가입 승인 완
	private String phone; // 전화번호
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPw() {
		return pw;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCampus() {
		return campus;
	}
	public void setCampus(String campus) {
		this.campus = campus;
	}
	public int getClassNum() {
		return classNum;
	}
	public void setClassNum(int classNum) {
		this.classNum = classNum;
	}
	public int getCurrentPoint() {
		return currentPoint;
	}
	public void setCurrentPoint(int currentPoint) {
		this.currentPoint = currentPoint;
	}
	public int getTotalPoint() {
		return totalPoint;
	}
	public void setTotalPoint(int totalPoint) {
		this.totalPoint = totalPoint;
	}
	public Date getVisited() {
		return visited;
	}
	public void setVisited(Date visited) {
		this.visited = visited;
	}
	
	public int getGender() {
		return gender;
	}
	public void setGender(int gender) {
		this.gender = gender;
	}
	public int getAdmin() {
		return admin;
	}
	public void setAdmin(int admin) {
		this.admin = admin;
	}
	public int getAccept() {
		return accept;
	}
	public void setAccept(int accept) {
		this.accept = accept;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", pw=" + pw + ", name=" + name + ", campus=" + campus + ", classNum=" + classNum
				+ ", currentPoint=" + currentPoint + ", totalPoint=" + totalPoint + ", visited=" + visited + ", gender="
				+ gender + ", admin=" + admin + ", accept=" + accept + ", phone=" + phone + "]";
	}
	
	
	
}
