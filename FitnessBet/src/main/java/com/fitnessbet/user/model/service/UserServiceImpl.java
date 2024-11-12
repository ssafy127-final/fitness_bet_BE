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

	@Override
	@Transactional
	public int calculateReward(String id, int reward) {
		User user = userDao.findById(id); // 해당 id 유저를 찾아와서
		int beforeTotalPoint = user.getTotalPoint(); // 현재 토탈 포인트와
		int beforePoint = user.getCurrentPoint(); // 현재 가지고 있는 포인트를 꺼냄
		int afterPoint = beforePoint + reward; // 정산 포인트를 현재 포인트에 합산
		int afterTotalPoint = beforeTotalPoint + reward; // 정산 포인트 토탈 포인트 합산
		user.setCurrentPoint(afterPoint); // 유저 객체에 정산된 소유 포인트 저장
		user.setTotalPoint(afterTotalPoint); // 유저 객체에 정산된 토탈 포인트 저장
		return userDao.updateReward(user); // 해당 유저 반환
	}

	@Override
	@Transactional
	public int minusBetPoint(String id, int betPoint) {
		User user = userDao.findById(id); // 해당 유저 불러와서
		int currPoint = user.getCurrentPoint(); // 현재 소유 포인트 꺼내고
		int minusBetPoint = currPoint - betPoint; // 배팅에 건 포인트 차감 시키고
		user.setCurrentPoint(minusBetPoint); // 유저 객체에 저장
		return userDao.minusBettingPoint(user); // dao 호출... 차감된 배팅 포인트를 담은 유저 객체를 파라미터로 줌
	}


}
