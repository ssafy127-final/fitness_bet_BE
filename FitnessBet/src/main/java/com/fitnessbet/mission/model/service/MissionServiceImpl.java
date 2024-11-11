package com.fitnessbet.mission.model.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fitnessbet.mission.model.dao.MissionDao;
import com.fitnessbet.mission.model.dto.Mission;

@Service
public class MissionServiceImpl implements MissionService {

	private final MissionDao missionDao;

	public MissionServiceImpl(MissionDao missionDao) {
		this.missionDao = missionDao;
	}

	@Override
	@Transactional
	public boolean modifyMission(Mission mission) {
		int result = missionDao.updateMission(mission);
		return result == 1;
	}

	@Override
	@Transactional
	public Mission getMissionById(int id) {
		Mission oldMission = missionDao.selectOne(id);
		return oldMission;
	}

	@Override
	@Transactional
	public boolean registMission(Mission mission) {
	    return missionDao.insertMission(mission) == 1;
	}

	@Override
	@Transactional
	public boolean removeMission(int id) {
		int result = missionDao.deleteMission(id);
		return result == 1;
	}

	@Override
	@Transactional
	public List<Mission> getAllMissionList() {
		List<Mission> list = missionDao.selectAll();
		return list;
	}


}
