package com.fitnessbet.mission.model.service;

import java.util.List;

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
	public boolean modifyMission(Mission mission) {
		int result = missionDao.updateMission(mission);
		return result == 1;
	}

	@Override
	public Mission getMissionById(int id) {
		Mission oldMission = missionDao.selectOne(id);
		return oldMission;
	}

	@Override
	public boolean registMission(Mission mission) {
	    return missionDao.insertMission(mission) == 1;
	}

	@Override
	public boolean removeMission(int id) {
		int result = missionDao.deleteMission(id);
		return result == 1;
	}

	@Override
	public List<Mission> getAllMissionList() {
		List<Mission> list = missionDao.selectAll();
		return list;
	}


}
