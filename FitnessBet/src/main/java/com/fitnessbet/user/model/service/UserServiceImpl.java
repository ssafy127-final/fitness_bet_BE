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
	public int registUser(User user) {
		int result = userDao.insertUser(user);
		return result;
		
	}

	@Override
	public boolean authenticate(String id, String pw) {
		User user = userDao.findById(id);
		System.out.println("id" + id);
		System.out.println("pw" + pw);
		System.out.println(user.toString());
		if(user != null) {
			if(pw.equals(user.getPw())) {
				return true;
			}
		}
		return false;
	}


}
