package com.fitnessbet.mission.model.dao;

import java.util.List;

import com.fitnessbet.mission.model.dto.Mission;

public interface MissionDao {
	// 1. 미션 생성
	public int insertMission(Mission mission);

	// 2. 미션 삭제
	public int deleteMission(int id);

	// 3. 미션 수정
	public int updateMission(Mission mission);

	// 4. 미션 목록 불러오기
	public List<Mission> selectAll();

	public Mission selectOne(int id);

}
