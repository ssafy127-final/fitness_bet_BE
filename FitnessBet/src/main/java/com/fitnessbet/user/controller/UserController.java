package com.fitnessbet.user.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fitnessbet.user.model.dto.LoginRequestInfo;
import com.fitnessbet.user.model.dto.PointHistory;
import com.fitnessbet.user.model.dto.User;
import com.fitnessbet.user.model.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")

public class UserController {

	private static final int APPROVED = 1;
	private static final int SESSION_TIMEOUT = 3600;
	private static final int TRUE = 1;

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping("/regist")
	public ResponseEntity<User> regist(@RequestBody User user) {
		boolean isRegisted = userService.registUser(user);
		if (isRegisted) {
			return ResponseEntity.status(HttpStatus.CREATED).build();
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequestInfo loginRequestInfo, HttpServletRequest request) {
		// 세션 고정 공격 방지를 위한 세션 폐기 및 재생성
		// 기존 세션 폐기
		HttpSession oldSession = request.getSession(false);
		if (oldSession != null) {
			oldSession.invalidate(); // 기존 세션을 무효화.
		}

		// 새로운 세션 생성
		HttpSession newSession = request.getSession(true);
		User user = userService.authenticate(loginRequestInfo.getId(), loginRequestInfo.getPw());
		if (user != null) {
			if (user.getAccept() == APPROVED) { // 0 은 가입 대기중 / 1은 가입 승인 완료
				newSession.setAttribute("userId", user.getId());
				newSession.setAttribute("userName", user.getName());
				newSession.setAttribute("isAdmin", user.getAdmin()); // 관리자인지 아닌지 세션에 저장
				newSession.setAttribute("campus", user.getCampus());
				newSession.setAttribute("classNum", user.getClassNum());
				newSession.setMaxInactiveInterval(SESSION_TIMEOUT); // 1시간 동안 아무 상호작용 없으면 자동 세션 만료
				return ResponseEntity.status(HttpStatus.OK).body(user);
			}
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("아직 가입 승인이 허가되지 않았습니다.");

		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 정보가 올바르지 않습니다.");
	}

	@PostMapping("/logout")
	public ResponseEntity<String> logout(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.invalidate();
		}
		return ResponseEntity.ok("로그아웃 되었습니다.");
	}

	@DeleteMapping("/delete/{id}") // 승인 거절 & 유저 삭제
	public ResponseEntity<?> delete(@PathVariable("id") String id, HttpServletRequest request) {
		HttpSession session = request.getSession();
		int isAdmin = (int) session.getAttribute("isAdmin"); // 관리자는 1 / 일반 학생은 0

		if (isAdmin == TRUE) { // 관리자 일 경우에만 작동해야함.
			boolean isDeleted = userService.rejectUser(id);
			if (isDeleted) { // 삭제가 됐으면
				return ResponseEntity.status(HttpStatus.OK).body("삭제 성공");
			} else {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("삭제 실패");
			}
		}
		// 0일 경우
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("삭제는 관리자만 가능합니다.");
	}

	@PatchMapping("/approve/{id}")
	public ResponseEntity<?> approve(@PathVariable("id") String id, HttpServletRequest request) {
		HttpSession session = request.getSession();
		int isAdmin = (int) session.getAttribute("isAdmin"); // 관리자는 1 / 일반 학생은 0

		if (isAdmin == TRUE) { // 관리자 일 경우에만 작동해야함.
			boolean isUpdated = userService.approve(id);
			if (isUpdated) { // 수정 됐으면
				return ResponseEntity.status(HttpStatus.OK).body("수정 성공");
			} else {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("수정 실패");
			}
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("승인은 관리자만 가능합니다.");
	}

	@GetMapping("/list")
	public ResponseEntity<?> getList(HttpServletRequest request) {
		if (!verifyAdmin(request)) { // 관리자 권한 확인
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("관리자 권한이 필요합니다.");
		}
		HttpSession session = request.getSession(false);

		if (session.getAttribute("classNum") == null || session.getAttribute("campus") == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("필수 세션 속성이 누락되었습니다.");
		}

		int classNum = (int) session.getAttribute("classNum");
		String campus = (String) session.getAttribute("campus");
		User user = new User();
		user.setClassNum(classNum);
		user.setCampus(campus);

		List<User> list = userService.getList(user);
		return ResponseEntity.status(HttpStatus.OK).body(list);
	}

	@GetMapping("/check-id")
	public ResponseEntity<?> checkUniqueId(@RequestParam("id") String id) {
		User user = userService.getUserById(id);
		if (user != null) {
			// 결과 값이 있다면 중복이므로
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("이미 가입되어 있는 학번입니다.");
		}
		// 결과 값이 없다면 중복이 아니다.
		return ResponseEntity.status(HttpStatus.OK).body("확인되었습니다.");
	}
	
	@GetMapping("/autoLogin")
	public ResponseEntity<?> autoLoginBySessionInfo(HttpServletRequest request, @RequestParam String id) {
		boolean isLogin = verifyLogin(request);
		HttpSession session = request.getSession(false);
		String userId = (String) session.getAttribute("userId");
		if (isLogin && userId.equals(id)) {
			return ResponseEntity.status(HttpStatus.OK).body(userService.getUserById(id));
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("자동 로그인 실패");
	}
	
	@PostMapping("/daily")
	public ResponseEntity<?> addDailyReward(@RequestParam String id, HttpServletRequest request){
	    if (id == null || id.isEmpty()) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("유효하지 않은 사용자 ID입니다.");
	    }

	    boolean isLogin = verifyLogin(request);
	    if (!isLogin) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
	    }

	    try {
	        boolean result = userService.addDailyPoint(id, 50); // 50포인트 추가
	        if (result) { // 성공 
	            return ResponseEntity.ok("출석 포인트를 받았습니다.");
	        } else {
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("출석 포인트 적용 실패");
	        }
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류: " + e.getMessage());
	    }
	}
	
	@GetMapping("/point/history")
	public ResponseEntity<?> getPointHistoryList(@RequestParam("userId") String userId){
		List<PointHistory> list = userService.getPointHistoryList(userId);
		if(list!=null && list.size()>0) {
			return new ResponseEntity<List<PointHistory>>(list, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@GetMapping("/ranking")
	public ResponseEntity<?> getRankingList(@RequestParam("userId") String id){
		List<User> rankingList = userService.getWinningPercentList(id);
		if(rankingList!=null && rankingList.size()>0) {
			return new ResponseEntity<List<User>>(rankingList, HttpStatus.OK);
		}
		return new ResponseEntity<>("집계가 없습니다.",HttpStatus.NO_CONTENT);
	}
	
	
	
	
	private boolean verifyLogin(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			return true;
			
		}return false;
		
	}
	
	private boolean verifyAdmin(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session == null) {
			return false;
		}
		int isAdmin = (int) session.getAttribute("isAdmin");
		if (isAdmin == TRUE) {
			return true;
		}
		return false;
	}
}
