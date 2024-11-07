package com.fitnessbet.betting.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fitnessbet.betting.model.dto.Betting;
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
	public ResponseEntity<?> getAllList(@RequestParam("campus") String campus, @RequestParam("classNum") int classNum, @RequestParam(value = "status", defaultValue = "now") String status){
		List<Betting> list = service.getAllListByUserInfo(campus, classNum, status);
		if(list != null && list.size()!=0) {
			return new ResponseEntity<List<Betting>>(list, HttpStatus.OK);
		}
		return new ResponseEntity<String>("정보 없음", HttpStatus.NO_CONTENT);
	}

	
	@PostMapping("")
	public ResponseEntity<String> createBetting(@RequestBody User user){
		if(service.createBetting(user)) {
			return new ResponseEntity<>("생성 완료", HttpStatus.OK);
		}
		return new ResponseEntity<>("생성 실패", HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
}
