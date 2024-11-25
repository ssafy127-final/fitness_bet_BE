package com.fitnessbet.user.model.service;

import java.util.List;

import com.fitnessbet.user.model.dto.PointHistory;
import com.fitnessbet.user.model.dto.User;

public interface UserService {
	
	public boolean registUser(User user);
	
	public User authenticate(String id, String pw);
	
	public User getUserById(String id);
	
	public boolean rejectUser(String id);
	
	public List<User> getList(User user);
	
	public boolean approve(String id);
	
	public int countUser(User user);
	
	public User selectChallenger(User user);
	
	public int calculateReward(String id, int reward, int bettingId);
	
	public int minusBetPoint(String id, int betPoint);
	
	public boolean updateVisited(User user);
	
	public boolean recordPointHistory(PointHistory ph);

	public boolean addDailyPoint(String id, int dailyPoint);

	public List<PointHistory> getPointHistoryList(String userId);

	public List<User> getWinningPercentList(String id);
	
	public boolean exchangeProduct(PointHistory ph);
	
	public boolean addChallengePoint(String id, int challengePoint);
}
