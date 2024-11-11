package com.fitnessbet.betting.model.dao;

import java.util.List;

import com.fitnessbet.betting.model.dto.Betting;

public interface BettingDao {

	List<Betting> selectAll(Betting betting);

	int insertBetting(Betting newBetting);

}
