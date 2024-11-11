package com.fitnessbet.user.model.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fitnessbet.user.model.dao.UserDao;
import com.fitnessbet.user.model.dto.User;

@Service
public class UserServiceImpl implements UserService{
	
	private final UserDao userDao;
	
	public UserServiceImpl(UserDao userDao) {
		this.userDao = userDao;
	}
	
	@Override
	@Transactional
	public boolean registUser(User user) {
		int result = userDao.insertUser(user);
		return result == 1;
		
	}

	@Override
	@Transactional
	public User authenticate(String id, String pw) {
		User user = userDao.findById(id);
		if(user != null) {
			if(pw.equals(user.getPw())) {
				return user;
			}
		}
		return null;
	}

	@Override
	@Transactional
	public boolean rejectUser(String id) {
		int result = userDao.deleteUser(id);
		return result == 1;
	}

	@Override
	@Transactional
	public List<User> getList(User user) { // 유저 객체를 모두 담아서, classNum과 campus를 꺼내씀
		List<User> userList = userDao.selectAll(user);
		return userList;
	}

	@Override
	@Transactional
	public boolean approve(String id) {
		int result = userDao.updateAccepted(id);
		return result == 1;
	}

	@Override
	@Transactional
	public int countUser(User user) {
		return userDao.countAll(user);
	}

	@Override
	@Transactional
    public User selectChallenger(User user) {
       user.setRandomNum((int) (Math.random() * userDao.countAll(user)));
        User challenger = userDao.selectChallenger(user);
        return challenger;
    }


}
