package com.fitnessbet.betting.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fitnessbet.betting.model.dao.BettingDao;
import com.fitnessbet.betting.model.dto.Betting;
import com.fitnessbet.betting.model.dto.BettingHistory;
import com.fitnessbet.betting.model.dto.Review;
import com.fitnessbet.mission.model.dto.Mission;
import com.fitnessbet.mission.model.service.MissionService;
import com.fitnessbet.user.model.dto.User;
import com.fitnessbet.user.model.service.UserService;

@Service
public class BettingServiceImpl implements BettingService {

	private final BettingDao dao;

	@Autowired
	private MissionService missionService;

	@Autowired
	private UserService userService;

	public BettingServiceImpl(BettingDao dao) {
		this.dao = dao;
	}

	@Override
	public List<Betting> getAllListByUserInfo(String userId, String status) {
		Betting betting = new Betting();
		User user = userService.getUserById(userId);
		betting.setLoginUser(user);
		if (status.equals("now")) {
			betting.setResult(0);
		} else {
			betting.setResult(1);
		}
		return dao.selectAll(betting);
	}

	@Override
	@Transactional
	public Betting readyCreateBetting(User user) {
		Betting newBetting = new Betting();

		Mission mission = missionService.getMissionByIndex();
		

		User challenger = userService.selectChallenger(user);
		newBetting.setChallenger(challenger.getId());
		newBetting.setMissionId(mission.getId());

		// 성별 따라 범위에서 랜덤돌리기
		if (challenger.getGender() == 0) {
			newBetting.setMissionCnt((int) ((mission.getFemaleMax() - mission.getFemaleMin() + 1) * Math.random())
					+ mission.getFemaleMin());
		} else {
			newBetting.setMissionCnt(
					(int) ((mission.getMaleMax() - mission.getMaleMin() + 1) * Math.random()) + mission.getMaleMin());
		}
		// betting에 인서트
		return newBetting;
	}
	@Override
	@Transactional
	public boolean createBetting(Betting betting) {
		
		return dao.insertBetting(betting) > 0;
	}

	@Override
	@Transactional
	public boolean finishBetting(Betting betting) {
		if (dao.finishBetting(betting) > 0) {
			List<BettingHistory> winUsers = dao.selectWinner(betting);
			// 포인트 정산
			int totalPoint = betting.getFailPoint() + betting.getSuccessPoint();
			int successCnt = 0;
			for (BettingHistory info : winUsers) {
				if(betting.getResult()==1) {
					totalPoint = (int) Math.ceil(totalPoint / (betting.getSuccessPoint()/info.getPoint()));
				}else {
					totalPoint = (int) Math.ceil(totalPoint / (betting.getFailPoint()/info.getPoint()));
				}
				successCnt += userService.calculateReward(info.getPlayer(),totalPoint);
			}
			if (successCnt == winUsers.size())
				return true;
		}
		return false;
	}

	@Override
	@Transactional
	public boolean joinBetting(BettingHistory bettingInfo) {
		// 참여 유저 포인트 감소시키기
		if (userService.minusBetPoint(bettingInfo.getPlayer(), bettingInfo.getPoint()) > 0) {
			
			if (dao.addBetHistory(bettingInfo) > 0) {
				Betting betting = dao.selectOneBettingById(bettingInfo.getBettingId());
				if (bettingInfo.getChoice() == 1) { // 성공선택
					betting.setSuccessCnt(betting.getSuccessCnt() + 1);
					betting.setSuccessPoint(betting.getSuccessPoint() + bettingInfo.getPoint());
				} else {
					betting.setFailCnt(betting.getFailCnt() + 1);
					betting.setFailPoint(betting.getFailPoint() + bettingInfo.getPoint());
				}
				return dao.joinBetting(betting) > 0;
			}
		}
		return false;
	}

	@Override
	@Transactional
	public boolean stopBetting(int id) {
		return dao.changeBettingStatusDone(id) > 0;
	}

//	@Override
//	public Betting getBettingAndUSerInfo(int bettingId) {
////		Map<String, Object> info = new HashMap<>();
////		Betting bet = dao.selectOneBettingById(id);
////		User userInfo = userService.getUserById(userId);
////		info.put("bettingInfo", bet);
////		info.put("userInfo", userInfo);
//		return dao.selectOneBettingById(bettingId);
//	}

	@Override
	public List<BettingHistory> getBettingHistory(String id) {
		return dao.selectBettingHistoryByUserId(id);
	}

	@Override
	public List<Betting> getChallengerBettingHistory(String id) {
		return dao.selectChallengerBettingHistory(id);
	}

	@Override
	public boolean createReview(Review review) {
		return dao.createReview(review) > 0;
	}

	@Override
	public boolean removeReview(int reviewId) {
		return dao.deleteReview(reviewId) > 0;
	}

	@Override
	public boolean modifyReview(Review review) {
		return dao.updateReview(review) > 0;
	}

	@Override
	public List<Review> getReviewsByBetId(int bettingId) {
		return dao.selectReviewList(bettingId);
	};

}
