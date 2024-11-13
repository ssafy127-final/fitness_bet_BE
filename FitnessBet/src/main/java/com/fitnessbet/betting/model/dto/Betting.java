package com.fitnessbet.betting.model.dto;

import com.fitnessbet.user.model.dto.User;

public class Betting {
	private int id;
	private String challenger;
	private int missionId;
	private int missionCnt;
	private int successCnt;
	private int failCnt;
	private int successPoint;
	private int failPoint;
	private int result;
	private String regDate;
	
	private User user; // challenger에 해당하는 정보 조인해서 넣을 것(반, 기수 등등,,)
	


	public int getMissionCnt() {
		return missionCnt;
	}
	
	public void setMissionCnt(int missionCnt) {
		this.missionCnt = missionCnt;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getChallenger() {
		return challenger;
	}

	public void setChallenger(String challenger) {
		this.challenger = challenger;
	}

	public int getMissionId() {
		return missionId;
	}

	public void setMissionId(int missionId) {
		this.missionId = missionId;
	}

	public int getSuccessCnt() {
		return successCnt;
	}

	public void setSuccessCnt(int successCnt) {
		this.successCnt = successCnt;
	}

	public int getFailCnt() {
		return failCnt;
	}

	public void setFailCnt(int failCnt) {
		this.failCnt = failCnt;
	}

	public int getSuccessPoint() {
		return successPoint;
	}

	public void setSuccessPoint(int successPoint) {
		this.successPoint = successPoint;
	}

	public int getFailPoint() {
		return failPoint;
	}

	public void setFailPoint(int failPoint) {
		this.failPoint = failPoint;
	}

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public String getRegDate() {
		return regDate;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
	
	

}
