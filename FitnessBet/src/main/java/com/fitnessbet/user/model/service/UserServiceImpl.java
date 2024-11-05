package com.fitnessbet.user.model.service;

import org.springframework.stereotype.Service;

import com.fitnessbet.user.model.dao.UserDao;
import com.fitnessbet.user.model.dto.User;

@Service
public class UserServiceImpl implements UserService{
	
	private final UserDao userDao;
	
	public UserServiceImpl(UserDao userDao) {
		this.userDao = userDao;
	}
	
	@Override
	public void registUser(User user) {
		userDao.insertUser();
	}

}
