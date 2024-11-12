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
import com.fitnessbet.mission.model.service.MissionService;
import com.fitnessbet.user.model.dao.UserDao;
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
		return dao.selectAll(betting);
	}

	@Override
	@Transactional
	public boolean createBetting(User user) {
		Betting newBetting = new Betting();

		Mission mission = missionService.getMissionByIndex();

		User challenger = userService.selectChallenger(user);
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
		return dao.insertBetting(newBetting) > 0;
	}

	@Override
	@Transactional
	public boolean finishBetting(Betting betting) {
		if (dao.finishBetting(betting) > 0) {
			List<BettingHistory> winUsers = dao.selectWinner(betting);
			// 포인트 정산
			int totalPoint = betting.getFailPoint() + betting.getSuccessPoint();
			for(BettingHistory info:winUsers) {
				userService.addPoint(info.getPlayer(), Math.ceil(totalPoint/info.getPoint()));
			}
		}
		return false;
	}

	@Override
	@Transactional
	public boolean joinBetting(BettingHistory bettingInfo) {
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
		return false;
	}

	@Override
	@Transactional
	public boolean stopBetting(int id) {
		return dao.changeBettingStatusDone(id) > 0;
	}

}
