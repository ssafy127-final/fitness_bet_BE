package com.fitnessbet.betting.model.service;

import java.util.List;
import java.util.Map;

import com.fitnessbet.betting.model.dto.Betting;
import com.fitnessbet.betting.model.dto.BettingHistory;
import com.fitnessbet.betting.model.dto.Review;
import com.fitnessbet.user.model.dto.User;

public interface BettingService {

	List<Betting> getAllListByUserInfo(String campus, int classNum, String status);

	boolean createBetting(User user);

	boolean finishBetting(Betting betting);

	boolean joinBetting(BettingHistory bettingInfo);

	boolean stopBetting(int id);

	Map<String, Object> getBettingAndUSerInfo(int id, String userId);

	List<BettingHistory> getBettingHistory(String id);

	List<Betting> getChallengerBettingHistory(String id);

	boolean createReview(Review review);

	boolean removeReview(int reviewId);

	boolean modifyReview(Review review);

}
