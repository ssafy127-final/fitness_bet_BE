package com.fitnessbet.betting.model.service;

import java.util.List;

import com.fitnessbet.betting.model.dto.Betting;
import com.fitnessbet.betting.model.dto.BettingHistory;
import com.fitnessbet.betting.model.dto.Review;
import com.fitnessbet.user.model.dto.User;

public interface BettingService {

	List<Betting> getAllListByUserInfo(String userId, String status);

	boolean createBetting(Betting betting);

	boolean finishBetting(Betting betting);

	boolean joinBetting(BettingHistory bettingInfo);

	boolean stopBetting(int id);

//	Betting getBettingAndUSerInfo(int bettingId);

	List<BettingHistory> getBettingHistory(String id);

	List<Betting> getChallengerBettingHistory(String id);

	boolean createReview(Review review);

	boolean removeReview(int reviewId);

	boolean modifyReview(Review review);

	List<Review> getReviewsByBetId(int bettingId);

	Betting readyCreateBetting(User user);

}
