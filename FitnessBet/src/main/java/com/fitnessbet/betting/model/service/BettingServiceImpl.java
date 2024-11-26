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
	public Betting readyCreateBetting(String id) {
		Betting newBetting = new Betting();

		Mission mission = missionService.getMissionByIndex();

		User user = userService.getUserById(id);

		User challenger = userService.selectChallenger(user);
		newBetting.setChallengeUser(challenger);
		newBetting.setMission(mission);

		// 성별 따라 범위에서 랜덤돌리기
		if (challenger.getGender() == 0) {
			newBetting.setMissionCnt((int) ((mission.getFemaleMax() - mission.getFemaleMin() + 1) * Math.random())
					+ mission.getFemaleMin());
		} else {
			newBetting.setMissionCnt(
					(int) ((mission.getMaleMax() - mission.getMaleMin() + 1) * Math.random()) + mission.getMaleMin());
		}
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
			Betting betInfo = dao.selectOneBetting(betting.getId());
			int challengeReward = 0;
			String challengerId = betInfo.getChallenger();
			if (betInfo.getResult() == 1) { // 성공이라면 미션참여보상 70포인트
				challengeReward = 70;
			} else if (betInfo.getResult() == -1) { // 실패는 미션참여보상 50포인트
				challengeReward = 50;
			}
			userService.addChallengePoint(challengerId, challengeReward);
			List<BettingHistory> winUsers = dao.selectWinner(betInfo);
			// 포인트 정산
			int totalPoint = betInfo.getFailPoint() + betInfo.getSuccessPoint();
			int successCnt = 0;
			for (BettingHistory info : winUsers) {
				if (betInfo.getResult() == 1) {
					totalPoint = (int) Math.ceil(totalPoint / (betInfo.getSuccessPoint() / info.getPoint()));
					// 총 성공 포인트 / 유저가 배팅한 포인트
					System.out.println("미션 성공 ! " + "유저 : " + info.getPlayer() + "배팅한 포인트" + info.getPoint()
							+ " 얻은 포인트 " + totalPoint + "유저의 선택 " + info.getChoice());
				} else {
					totalPoint = (int) Math.ceil(totalPoint / (betInfo.getFailPoint() / info.getPoint()));
					System.out.println("미션 실패 ! " + "유저 : " + info.getPlayer() + "배팅한 포인트" + info.getPoint()
							+ " 얻은 포인트 " + totalPoint + "유저의 선택 " + info.getChoice());
				}
				info.setPrize(totalPoint);
				if (dao.updateBettingHistoryPrize(info)) {
					successCnt += userService.calculateReward(info.getPlayer(), totalPoint, betting.getId());

				}
			}
			if (successCnt == winUsers.size())
				return true;
		}
		return false;
	}

	@Override
	@Transactional
	public boolean joinBetting(BettingHistory bettingInfo) {
		System.out.println(bettingInfo.getBettingId() + "!!!!! id");
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
	}

	@Override
	public List<User> getUserList(String campus, int classNum) {
		User user = new User();
		user.setCampus(campus);
		user.setClassNum(classNum);
		return userService.getList(user);
	}

	@Override
	public List<Mission> getMissionList() {
		return missionService.getAllMissionList();
	}

	@Override
	public Betting getBettingDetail(int bettingId, String id) {
		Betting betting = dao.selectOneBetting(bettingId);
		User user = new User();
		user.setId(id);
		betting.setLoginUser(user);
		return dao.selectOneBettingDetail(betting);
	}

	@Override
	public int getNetProfit(String id, int bettingId) {
		// bh의 player와 bh의 배팅아이디를 담은 객체를
		BettingHistory tmpBh = new BettingHistory();
		tmpBh.setPlayer(id);
		tmpBh.setBettingId(bettingId);
		BettingHistory bh = dao.selectPrizeAndPointById(tmpBh);
		System.out.println(bh.getPrize());
		System.out.println(bh.getPoint());
		int netProfit = bh.getPrize() - bh.getPoint();
		return netProfit;
	}

	@Override
	public boolean checkBettingStatus(int id) {
		return dao.selectOneBetting(id).getResult()==0;
	};

}
