package com.fitnessbet.user.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fitnessbet.user.model.dto.User;
import com.fitnessbet.user.model.service.UserService;
import com.fitnessbet.user.model.service.UserServiceImpl;

import org.springframework.http.HttpStatus;
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
	public ResponseEntity<User> regist(@ModelAttribute User user) {
		userService.registUser(user);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	
	
}
