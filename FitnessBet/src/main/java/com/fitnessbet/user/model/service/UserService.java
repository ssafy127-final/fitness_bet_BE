package com.fitnessbet.user.model.service;

import com.fitnessbet.user.model.dto.User;

public interface UserService {
	
	public int registUser(User user);
	
	public boolean authenticate(String id, String pw);
}
