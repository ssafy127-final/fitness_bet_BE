package com.fitnessbet.mission.model.service;

import java.util.List;

import com.fitnessbet.mission.model.dto.Mission;

public interface MissionService {
									// 수정할 내용이 담긴 뉴 미션이잖아
	public boolean modifyMission(Mission mission);
	
	public Mission getMissionById(int id);
	
	public boolean registMission(Mission mission);

	public boolean removeMission(int id);

	public List<Mission> getAllMissionList();
	
	public int countMisson();
	
	public Mission getMissionByIndex();
}
