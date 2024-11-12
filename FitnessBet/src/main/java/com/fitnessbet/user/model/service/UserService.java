package com.fitnessbet.user.model.service;

import java.util.List;

import com.fitnessbet.user.model.dto.User;

public interface UserService {
	
	public boolean registUser(User user);
	
	public User authenticate(String id, String pw);
	
	public boolean rejectUser(String id);
	
	public List<User> getList(User user);
	
	public boolean approve(String id);
	
	public int countUser(User user);
	
	public User selectChallenger(User user);
	//이겼을 때 
	public int addPoint();
	// 졌을 때
	public int minusPoint();
}
