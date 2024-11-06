package com.fitnessbet.mission.model.service;

import com.fitnessbet.mission.model.dto.Mission;

public interface MissionService {
									// 수정할 내용이 담긴 뉴 미션이잖아
	public void modifyMission(Mission mission);
	
	public Mission getMissionById(int id);
	
	
}
