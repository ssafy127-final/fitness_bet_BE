package com.fitnessbet.mission.model.service;

import org.springframework.stereotype.Service;

import com.fitnessbet.mission.model.dao.MissionDao;
import com.fitnessbet.mission.model.dto.Mission;

@Service
public class MissionServiceImpl implements MissionService {

	MissionDao missionDao;

	public MissionServiceImpl(MissionDao missionDao) {
		this.missionDao = missionDao;
	}

	@Override
	public void modifyMission(Mission mission) {
		missionDao.updateMission(mission);
	}

	@Override
	public Mission getMissionById(int id) {
		Mission oldMission = missionDao.selectOne(id);
		return oldMission;
	}

}
