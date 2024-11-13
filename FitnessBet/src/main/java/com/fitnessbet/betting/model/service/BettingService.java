package com.fitnessbet.betting.model.service;

import java.util.List;
import java.util.Map;

import com.fitnessbet.betting.model.dto.Betting;
import com.fitnessbet.betting.model.dto.BettingHistory;
import com.fitnessbet.user.model.dto.User;

public interface BettingService {

	List<Betting> getAllListByUserInfo(String campus, int classNum, String status);

	boolean createBetting(User user);

	boolean finishBetting(Betting betting);

	boolean joinBetting(BettingHistory bettingInfo);

	boolean stopBetting(int id);

	Map<String, Object> getBettingAndUSerInfo(int id, User user);

	List<BettingHistory> getBettingHistory(String id);

	List<BettingHistory> getChallengerBettingHistory(String id);

}
