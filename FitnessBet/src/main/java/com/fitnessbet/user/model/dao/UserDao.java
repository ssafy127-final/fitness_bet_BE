package com.fitnessbet.user.model.dao;

import java.util.List;

import com.fitnessbet.user.model.dto.PointHistory;
import com.fitnessbet.user.model.dto.User;

public interface UserDao {
	// 회원 가입
	public int insertUser(User user);
	
	// id로 유저 찾기
	public User findById(String id);
	
	// 유저 삭제 (db에서 삭제)
	public int deleteUser(String id);
	
	// 반 별 모든 학생들 리스트 뽑기
	public List<User> selectAll(User user);
	
	// 해당 id 유저 가입 승인
	public int updateAccepted(String id);
	
	// 반 별 학생 수 반환
	public int countAll(User user);
	
	// 미션 도전자 랜덤 반환
	public User selectChallenger(User user);
	
	// 배팅에 이겼을 때, 얻은 배팅 포인트 합산하기
	public int updateReward(User user);
	
	// 배팅 포인트 걸 때, 현재 보유 포인트에서 배팅 포인트 차감하기
	public int minusBettingPoint(User user);
	
	public int visitedCheck(User user);
	
	public int insertPointHistory(PointHistory ph);

	public List<PointHistory> selectAllPointHistory(String userId);
}
