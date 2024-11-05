package com.fitnessbet.user.model.dao;

import com.fitnessbet.user.model.dto.User;

public interface UserDao {
	// 회원 가입
	public int insertUser(User user);
	
	public User findById(String id);
}
