package com.fitnessbet.user.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fitnessbet.user.model.dto.LoginRequestInfo;
import com.fitnessbet.user.model.dto.User;
import com.fitnessbet.user.model.service.UserService;
import com.fitnessbet.user.model.service.UserServiceImpl;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/user")
public class UserController {
	
	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@PostMapping("/regist")
	public ResponseEntity<User> regist(@RequestBody User user) {
		int result = userService.registUser(user);
		if(result == 1) {
			return ResponseEntity.status(HttpStatus.CREATED).build();
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequestInfo loginRequestInfo) {
		boolean isAuthenticated = userService.authenticate(loginRequestInfo.getId(), loginRequestInfo.getPw());
		if(isAuthenticated) {
			return ResponseEntity.ok("로그인 성공");
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 정보가 올바르지 않습니다.");
	}
	
	
	
	
}
