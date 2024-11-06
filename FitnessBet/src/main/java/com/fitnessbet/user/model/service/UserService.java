package com.fitnessbet.user.model.service;

import java.util.List;

import com.fitnessbet.user.model.dto.User;

public interface UserService {
	
	public boolean registUser(User user);
	
	public User authenticate(String id, String pw);
	
	public boolean rejectUser(String id);
	
	public List<User> getList(int classNum);
	
	public boolean approve(String id);
}
