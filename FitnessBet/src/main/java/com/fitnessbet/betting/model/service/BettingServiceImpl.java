package com.fitnessbet.betting.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fitnessbet.betting.model.dao.BettingDao;
import com.fitnessbet.betting.model.dto.Betting;
import com.fitnessbet.betting.model.dto.BettingHistory;
import com.fitnessbet.mission.model.dao.MissionDao;
import com.fitnessbet.mission.model.dto.Mission;
import com.fitnessbet.user.model.dao.UserDao;
import com.fitnessbet.user.model.dto.User;

@Service
public class BettingServiceImpl implements BettingService {

	private final BettingDao betDao;

	@Autowired
	private MissionDao missionDao;

	@Autowired
	private UserDao userDao;

	public BettingServiceImpl(BettingDao dao) {
		this.betDao = dao;
	}

	@Override
	public List<Betting> getAllListByUserInfo(String campus, int classNum, String status) {
		Betting betting = new Betting();
		User user = new User();
		user.setCampus(campus);
		user.setClassNum(classNum);
		betting.setUser(user);
		if (status.equals("now")) {
			betting.setResult(0);
		} else {
			betting.setResult(1);
		}
		return betDao.selectAll(betting);
	}

	@Override
	@Transactional
	public boolean createBetting(User user) {
		Betting newBetting = new Betting();
		// 미션 몇개인지 가져오기
//		int cnt = missionDao.getMissionCnt();
//		// 랜덤 돌리기 => 나온 숫자로 리밋 걸어서 해당 번호꺼 종목이랑 범위 가져오기
//		int newBettingMissionNum = (int) (Math.random() * cnt);
//		Mission mission = missionDao.getMission(newBettingMissionNum);
		// service에서 가져오기
		
		
		// 사람 랜덤돌리기
//		user.setRandomNum((int) (Math.random() * userDao.getPeopleCnt(user)));
//		User challenger = userDao.selectChallenger(user);
		// service에서 가져오기
		newBetting.setChallenger(challenger.getId());

		// 성별 따라 범위에서 랜덤돌리기
		if (challenger.getGender() == 0) {
			newBetting.setMissionCnt((int) ((mission.getFemaleMax() - mission.getFemaleMin() + 1) * Math.random())
					+ mission.getFemaleMin());
		} else {
			newBetting.setMissionCnt(
					(int) ((mission.getMaleMax() - mission.getMaleMin() + 1) * Math.random()) + mission.getMaleMin());
		}
		// betting에 인서트
		return betDao.insertBetting(newBetting) > 0;
	}

	@Override
	@Transactional
	public boolean finishBetting(Betting betting) {
		return betDao.finishBetting(betting) > 0;
	}

	@Override
	@Transactional
	public boolean joinBetting(BettingHistory bettingInfo) {
		if (betDao.addBetHistory(bettingInfo) > 0) {
			Betting betting = betDao.selectOneBettingById(bettingInfo.getBettingId());
			if (bettingInfo.getChoice() == 1) { // 성공선택
				betting.setSuccessCnt(betting.getSuccessCnt() + 1);
				betting.setSuccessPoint(betting.getSuccessPoint() + bettingInfo.getPoint());
			} else {
				betting.setFailCnt(betting.getFailCnt() + 1);
				betting.setFailPoint(betting.getFailPoint() + bettingInfo.getPoint());
			}
			return betDao.joinBetting(betting) > 0;
		}
		return false;
	}

}
