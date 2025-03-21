package com.fitnessbet.user.model.service;

import java.util.List;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fitnessbet.betting.model.dao.BettingDao;
import com.fitnessbet.betting.model.service.BettingService;
import com.fitnessbet.user.model.dao.UserDao;
import com.fitnessbet.user.model.dto.PointHistory;
import com.fitnessbet.user.model.dto.User;

@Service
public class UserServiceImpl implements UserService{
	
	private final UserDao userDao;

	private final BettingService bettingService;
	
	public UserServiceImpl(UserDao userDao, @Lazy BettingService bettingService) {
		this.userDao = userDao;
		this.bettingService = bettingService;
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
	public User getUserById(String id) {
		return userDao.findById(id);
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
	public int calculateReward(String id, int reward, int bettingId) {
		User user = userDao.findById(id); // 해당 id 유저를 찾아와서
		int beforeTotalPoint = user.getTotalPoint(); // 현재 토탈 포인트와
		int beforePoint = user.getCurrentPoint(); // 현재 가지고 있는 포인트를 꺼냄
		int netProfit = bettingService.getNetProfit(id, bettingId);
		int afterPoint = beforePoint + reward; // 정산 포인트를 현재 포인트에 합산
		int afterTotalPoint = beforeTotalPoint + netProfit; // 배팅 포인트를 제외한 순수익 포인트를 토탈 포인트에 저장
		user.setCurrentPoint(afterPoint); // 유저 객체에 정산된 소유 포인트 저장
		user.setTotalPoint(afterTotalPoint); // 유저 객체에 정산된 토탈 포인트 저장		
		int result = userDao.updateReward(user); // 해당 유저 반환
		if(result == 1) {
			PointHistory ph = new PointHistory();
			ph.setCategory(1); // 1 : 배팅 성공 카테고리
			ph.setUserId(id);
			ph.setPoint(reward);
			return userDao.insertPointHistory(ph);
		}
		return 0;
	}

	@Override
	@Transactional
	public int minusBetPoint(String id, int betPoint) {
		User user = userDao.findById(id); // 해당 유저 불러와서
		int currPoint = user.getCurrentPoint(); // 현재 소유 포인트 꺼내고
		int minusBetPoint = currPoint - betPoint; // 배팅에 건 포인트 차감 시키고
		user.setCurrentPoint(minusBetPoint); // 유저 객체에 저장
		int result = userDao.minusBettingPoint(user); // dao 호출... 차감된 배팅 포인트를 담은 유저 객체를 파라미터로 줌
		if(result == 1) {
			PointHistory ph = new PointHistory();
			ph.setCategory(2); // 2 : 배팅 차감 카테고리 번호
			ph.setUserId(id);
			ph.setPoint(-betPoint);
			return userDao.insertPointHistory(ph);
		}
		return 0;
	}

	@Override
	@Transactional
	public boolean recordPointHistory(PointHistory ph) {
		int result = userDao.insertPointHistory(ph);
		return result == 1;
	}

	@Override
	@Transactional
	public boolean updateVisited(User user) {
		int result = userDao.visitedCheck(user);
		return result == 1;
	}

	@Override
	@Transactional
	public boolean addDailyPoint(String id, int dailyPoint) {
		User user = userDao.findById(id);
		int currPoint = user.getCurrentPoint(); // 현재 소유 포인트 꺼내고
		int afterPoint = currPoint + dailyPoint;
		user.setCurrentPoint(afterPoint);// 포인트 수정
		user.setTotalPoint(user.getTotalPoint() + dailyPoint);
		if (userDao.updateReward(user) > 0 && userDao.visitedCheck(user) > 0) {
			PointHistory ph = new PointHistory();
			ph.setCategory(0); // 출석 : 0
			ph.setUserId(id);
			ph.setPoint(dailyPoint);
			return userDao.insertPointHistory(ph) > 0;
		}
		return false;
	}

	@Override
	public List<PointHistory> getPointHistoryList(String userId) {
		return userDao.selectAllPointHistory(userId);
	}

	@Override
	public List<User> getWinningPercentList(String id) {
		User user = userDao.findById(id);
		List<User> list = userDao.selectWinCnt(user);
		return list;
	}
	
	@Override
	@Transactional
	public boolean exchangeProduct(PointHistory ph) {
		String userId = ph.getUserId();
		int point = ph.getPoint();
		ph.setCategory(3); // 카테고리 3 : 상품 구매  
		
		User user = userDao.findById(userId);
		int currentPoint = user.getCurrentPoint();
		int afterPoint = currentPoint - point;
		user.setCurrentPoint(afterPoint); 
		int result = userDao.minusBettingPoint(user); // 포인트 삭감 진행
		ph.setPoint(-point);
		if(result == 1) {
			if(userDao.insertPointHistory(ph) > 0) {
				return true;
			}
		}
		return false;
	}

	@Override
	@Transactional
	public boolean addChallengePoint(String id, int challengePoint) {
		User user = userDao.findById(id);
		int currentPoint = user.getCurrentPoint();
		int totalPoint = user.getTotalPoint();
		
		user.setCurrentPoint(currentPoint + challengePoint);
		user.setTotalPoint(totalPoint + challengePoint);
		if(userDao.updateReward(user) == 1) {
			PointHistory ph = new PointHistory();
			ph.setCategory(4); // 카테고리 4 : 미션 챌린지 포인트
			ph.setUserId(id);
			ph.setPoint(challengePoint);
			return userDao.insertPointHistory(ph) == 1;
		}
		return false;
	}
	
	

}
