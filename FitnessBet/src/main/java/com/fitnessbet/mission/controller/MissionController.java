package com.fitnessbet.mission.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fitnessbet.mission.model.dto.Mission;
import com.fitnessbet.mission.model.service.MissionService;
import com.fitnessbet.mission.model.service.MissionServiceImpl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/mission")
public class MissionController {

	MissionService ms;

	private static final int TRUE = 1;

	public MissionController(MissionService ms) {
		this.ms = ms;
	}

	@PostMapping("")
	public ResponseEntity<?> regist(@RequestBody Mission mission, HttpServletRequest request) {

		if (!verifyAdmin(request)) { // 관리자 권한 확인
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("등록을 위한 관리자 권한이 없습니다.");
		}

		if (!isValidMission(mission)) { // 유효한 데이터로 미션을 추가했는지 확인
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("잘못된 요청입니다. 입력값을 다시 확인하세요");
		}

		boolean isRegisted = ms.registMission(mission);

		if (isRegisted) {
			return ResponseEntity.status(HttpStatus.CREATED).body("성공적으로 추가되었습니다.");
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("실패");
	}

	// 유효 데이터 미션인지 확인
	private boolean isValidMission(Mission mission) {
		if (mission.getContent() != null && mission.getFemaleMin() != 0 && mission.getMaleMin() != 0
				&& mission.getFemaleMax() >= mission.getFemaleMin() && mission.getMaleMax() >= mission.getMaleMin()) {
			return true;
		}
		return false;
	}

	// 관리자 권한 확인
	private boolean verifyAdmin(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		int isAdmin = (int) session.getAttribute("isAdmin");
		if (isAdmin == TRUE) {
			return true;
		}
		return false;
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> modify(@PathVariable("id") int id, @RequestBody Mission mission) {
		if (!isValidMission(mission)) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("잘못된 요청입니다. 입력값을 확인하세요.");
		}
		Mission oldMission = ms.getMissionById(id);
		if (oldMission == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 미션을 찾을 수 없습니다.");
		}

		if (mission.getContent() != null) {
			if (!mission.getContent().equals(oldMission.getContent())) {
				oldMission.setContent(mission.getContent());
			}
		}

		// 여성 최대 개수
		if (mission.getFemaleMax() > 0 && mission.getFemaleMax() != oldMission.getFemaleMax()) { // 0보다 크고 기존과 다르면 수정 시작
			oldMission.setFemaleMax(mission.getFemaleMax());
		}
		// 여성 최소 개수
		if (mission.getFemaleMin() > 0 && mission.getFemaleMin() != oldMission.getFemaleMin()) { // 0보다 크고 기존과 다르면 수정 시작
			oldMission.setFemaleMin(mission.getFemaleMin());
		}
		// 남성 최대 개수
		if (mission.getMaleMax() > 0 && mission.getMaleMax() != oldMission.getMaleMax()) { // 0보다 크고 기존과 다르면 수정 시작
			oldMission.setMaleMax(mission.getMaleMax());
		}
		// 남성 최소 개수
		if (mission.getMaleMin() > 0 && mission.getMaleMin() != oldMission.getMaleMin()) { // 0보다 크고 기존과 다르면 수정 시작
			oldMission.setMaleMin(mission.getMaleMin());
		}
		boolean isUpdated = ms.modifyMission(oldMission);

		if (isUpdated) {
			return ResponseEntity.status(HttpStatus.OK).body("수정 성공");
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("수정 실패");
	}
}
