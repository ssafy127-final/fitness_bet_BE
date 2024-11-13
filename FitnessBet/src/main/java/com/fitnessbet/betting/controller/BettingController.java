package com.fitnessbet.betting.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fitnessbet.betting.model.dto.Betting;
import com.fitnessbet.betting.model.dto.BettingHistory;
import com.fitnessbet.betting.model.service.BettingService;
import com.fitnessbet.user.model.dto.User;

@RestController
@RequestMapping("/betting")
public class BettingController {

	private final BettingService service;

	public BettingController(BettingService service) {
		this.service = service;
	}

	@GetMapping("") // 프론트에서 현재 로그인한사람 정보 넘겨줘야함(캠퍼스, 반)
	public ResponseEntity<?> getAllList(@RequestParam("campus") String campus, @RequestParam("classNum") int classNum,
			@RequestParam(value = "status", defaultValue = "now") String status) {
		List<Betting> list = service.getAllListByUserInfo(campus, classNum, status);
		if (list != null && list.size() != 0) {
			return new ResponseEntity<List<Betting>>(list, HttpStatus.OK);
		}
		return new ResponseEntity<String>("정보 없음", HttpStatus.NO_CONTENT);
	}

	@PostMapping("")
	public ResponseEntity<String> createBetting(@RequestBody User user) {
		if (user.getAdmin() == 0)
			return new ResponseEntity<>("권한 없음", HttpStatus.NOT_ACCEPTABLE);
		if (service.createBetting(user)) {
			return new ResponseEntity<>("생성 완료", HttpStatus.OK);
		}
		return new ResponseEntity<>("생성 실패", HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	// 베팅 종료(관리자 가능)
	@PutMapping("/{id}/stop")
	public ResponseEntity<String> stopBetting(@PathVariable("id") int id){
		if(service.stopBetting(id)) {
			return new ResponseEntity<>("종료 완료", HttpStatus.OK);
		}
		return new ResponseEntity<>("종료 실패", HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	// 베팅 결과 입력
	@PutMapping("/{id}/finish")
	public ResponseEntity<String> finishBetting(@RequestBody Betting betting){
		if(service.finishBetting(betting)) {
			return new ResponseEntity<>("결과 입력 완료", HttpStatus.OK);
		}
		return new ResponseEntity<>("결과 입력 실패", HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	// 참여자(joinPerson), 배팅 얼마했는지, 어느쪽에 했는지를 Betting 객체에 담아 전달하기(result, fail/success point)
	@PutMapping("/{id}")
	public ResponseEntity<String> joinBetting(@PathVariable("id") int id, @RequestBody BettingHistory bettingInfo){
		bettingInfo.setBettingId(id);
		if(service.joinBetting(bettingInfo)) {
			return new ResponseEntity<>("참여 완료", HttpStatus.OK);
		}
		return new ResponseEntity<>("참여 실패", HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getBettingAndUserInfo(@PathVariable int id, @RequestBody User user){
		// bettingInfo, userInfo에 각각 배팅정보, 유저정보 담겨있음
		Map<String, Object> info = service.getBettingAndUSerInfo(id, user);
		return new ResponseEntity<Map<String, Object>>(info, HttpStatus.OK);
	}

}
