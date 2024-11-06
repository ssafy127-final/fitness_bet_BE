package com.fitnessbet.mission.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fitnessbet.mission.model.dto.Mission;
import com.fitnessbet.mission.model.service.MissionService;
import com.fitnessbet.mission.model.service.MissionServiceImpl;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/mission")
public class MissionController {

	MissionService ms;

	public MissionController(MissionService ms) {
		this.ms = ms;
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> modify(@PathVariable("id") int id, @RequestBody Mission mission) {
		Mission oldMission = ms.getMissionById(id);

		if (!mission.getContent().equals(null)) {
			if (!mission.getContent().equals(oldMission.getContent())) {
				oldMission.setContent(mission.getContent());
			}
		}

		if (mission.getFemaleMax() > 0 && mission.getFemaleMax() != oldMission.getFemaleMax()) { // 0보다 크고 기존과 다르면 수정 시작
			oldMission.setFemaleMax(mission.getFemaleMax());
		}
		
		if (mission.getFemaleMin() > 0 && mission.getFemaleMin() != oldMission.getFemaleMin()) { // 0보다 크고 기존과 다르면 수정 시작
			oldMission.setFemaleMin(mission.getFemaleMin());
		}
		
		if (mission.getMaleMax() > 0 && mission.getMaleMax() != oldMission.getMaleMax()) { // 0보다 크고 기존과 다르면 수정 시작
			oldMission.setMaleMax(mission.getMaleMax());
		}
		
		if (mission.getMaleMin() > 0 && mission.getMaleMin() != oldMission.getMaleMin()) { // 0보다 크고 기존과 다르면 수정 시작
			oldMission.setMaleMin(mission.getMaleMin());
		}
		
		
		ms.modifyMission(oldMission);
		return ResponseEntity.status(HttpStatus.OK).body("수정 성공");
	}
}
