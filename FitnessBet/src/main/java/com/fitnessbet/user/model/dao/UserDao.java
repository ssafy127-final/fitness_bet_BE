package com.fitnessbet.user.model.dao;

import java.util.List;

import com.fitnessbet.user.model.dto.User;

public interface UserDao {
	// 회원 가입
	public int insertUser(User user);
	
	public User findById(String id);
	
	public int deleteUser(String id);
	
	public List<User> selectAll(int classNum);
	
	public int updateAccepted(String id);
}
