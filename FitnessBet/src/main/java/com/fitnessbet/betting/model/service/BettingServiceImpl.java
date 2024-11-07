package com.fitnessbet.betting.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fitnessbet.betting.model.dao.BettingDao;
import com.fitnessbet.betting.model.dto.Betting;
import com.fitnessbet.user.model.dto.User;

@Service
public class BettingServiceImpl implements BettingService{
	
	private final BettingDao dao;
	
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
		if(status.equals("now")) {
			betting.setResult(0);
		}else {
			betting.setResult(1);
		}
		return dao.selectAll(betting);
	}

	@Override
	public boolean createBetting(User user) {
		// 미션 몇개인지 가져오기
		
		// 랜덤 돌리기 => 나온 숫자로 리밋 걸어서 해당 번호꺼 종목이랑 범위 가져오기
		
		// 사람 랜덤돌리기 
		
		// 성별 따라 범위에서 랜덤돌리기
		
		// betting에 인서트
		return false;
	}

}
