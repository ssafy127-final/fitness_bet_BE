package com.fitnessbet.betting.model.dao;

import java.util.List;

import com.fitnessbet.betting.model.dto.Betting;
import com.fitnessbet.betting.model.dto.BettingHistory;
import com.fitnessbet.betting.model.dto.Review;

public interface BettingDao {

	List<Betting> selectAll(Betting betting);

	int insertBetting(Betting newBetting);

	int finishBetting(Betting betting);

	int joinBetting(Betting betting);

	int addBetHistory(BettingHistory bettingInfo);

	Betting selectOneBettingById(int bettingId);

	int changeBettingStatusDone(int id);

	List<BettingHistory> selectWinner(Betting betting);

	List<BettingHistory> selectBettingHistoryByUserId(String id);

	List<Betting> selectChallengerBettingHistory(String id);

	int createReview(Review review);

	int deleteReview(int reviewId);

	int updateReview(Review review);

}
