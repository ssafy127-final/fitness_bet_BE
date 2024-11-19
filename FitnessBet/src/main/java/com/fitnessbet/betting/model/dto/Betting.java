package com.fitnessbet.betting.model.dto;

import java.util.List;

import com.fitnessbet.mission.model.dto.Mission;
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
	
	private User challengeUser; // challenger에 해당하는 정보 조인해서 넣을 것(반, 기수 등등,,)
	private User loginUser;
	
	private BettingHistory history; // 로그인 한 유저가 해당 배팅 참여했는지 여부 확인 
	
	private Mission mission; // 미션 저장할필드
	
	private List<Review> reviews; 
		

	public User getChallengeUser() {
		return challengeUser;
	}

	public void setChallengeUser(User challengeUser) {
		this.challengeUser = challengeUser;
	}

	public User getLoginUser() {
		return loginUser;
	}

	public void setLoginUser(User loginUser) {
		this.loginUser = loginUser;
	}
	
	public Mission getMission() {
		return mission;
	}

	public void setMission(Mission mission) {
		this.mission = mission;
	}

	public BettingHistory getHistory() {
		return history;
	}

	public void setHistory(BettingHistory history) {
		this.history = history;
	}


	public List<Review> getReviews() {
		return reviews;
	}

	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}

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

	
	
	
	
	

}
