package com.fitnessbet.betting.model.service;

import java.util.List;

import com.fitnessbet.betting.model.dto.Betting;
import com.fitnessbet.user.model.dto.User;

public interface BettingService {

	List<Betting> getAllListByUserInfo(String campus, int classNum, String status);

	boolean createBetting(User user);

}
