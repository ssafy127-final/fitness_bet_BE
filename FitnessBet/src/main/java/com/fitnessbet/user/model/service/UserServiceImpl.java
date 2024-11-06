package com.fitnessbet.user.model.service;

import java.util.List;

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
	public boolean registUser(User user) {
		int result = userDao.insertUser(user);
		return result == 1;
		
	}

	@Override
	public User authenticate(String id, String pw) {
		User user = userDao.findById(id);
//		System.out.println("id" + id);
//		System.out.println("pw" + pw);
//		System.out.println(user.toString());
		if(user != null) {
			if(pw.equals(user.getPw())) {
				return user;
			}
		}
		return null;
	}

	@Override
	public boolean rejectUser(String id) {
		int result = userDao.deleteUser(id);
		return result == 1;
	}

	@Override
	public List<User> getList(int classNum) {
		List<User> userList = userDao.selectAll(classNum);
		return userList;
	}

	@Override
	public boolean approve(String id) {
		int result = userDao.updateAccepted(id);
		return result == 1;
	}


}
