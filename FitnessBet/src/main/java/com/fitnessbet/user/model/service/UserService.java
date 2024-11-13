package com.fitnessbet.user.model.service;

import java.util.List;

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
	
	public int calculateReward(String id, int reward);
	
	public int minusBetPoint(String id, int betPoint);
}
