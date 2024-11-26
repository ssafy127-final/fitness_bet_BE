package com.fitnessbet.betting.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
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
import com.fitnessbet.betting.model.dto.Review;
import com.fitnessbet.betting.model.service.BettingService;
import com.fitnessbet.mission.model.dto.Mission;
import com.fitnessbet.user.model.dto.User;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/betting")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class BettingController {

	private static final int TRUE = 1;

	private final BettingService service;

	public BettingController(BettingService service) {
		this.service = service;
	}

	@GetMapping("") // 프론트에서 현재 로그인한사람 정보 넘겨줘야함(캠퍼스, 반)
	public ResponseEntity<?> getAllList(@RequestParam("userId") String userId,
			@RequestParam(value = "status", defaultValue = "now") String status) {
		List<Betting> list = service.getAllListByUserInfo(userId, status);
		if (list != null && list.size() != 0) {
			return new ResponseEntity<List<Betting>>(list, HttpStatus.OK);
		}
		return new ResponseEntity<String>("정보 없음", HttpStatus.NO_CONTENT);
	}


	
	@GetMapping("/{bettingId}")
	public ResponseEntity<List<Review>> getReviewList(@PathVariable int bettingId,
			@RequestParam("userId") String userId) {
		List<Review> reviewList = service.getReviewsByBetId(bettingId);
		return new ResponseEntity<>(reviewList, HttpStatus.OK);
	}
	
	// 참여자(joinPerson), 배팅 얼마했는지, 어느쪽에 했는지를 Betting 객체에 담아 전달하기(result, fail/success
	// 배팅 참여 
	@PutMapping("/{id}")
	public ResponseEntity<String> joinBetting(@PathVariable("id") int id, @RequestBody BettingHistory bettingInfo) {
		bettingInfo.setBettingId(id);
		if(!service.checkBettingStatus(id)) {
			return new ResponseEntity<>("참여 불가", HttpStatus.BAD_REQUEST);
		}
		if (service.joinBetting(bettingInfo)) {
			return new ResponseEntity<>("참여 완료", HttpStatus.OK);
		}
		return new ResponseEntity<>("참여 실패", HttpStatus.INTERNAL_SERVER_ERROR);
	}

	// 베팅 종료(관리자 가능)
	@PutMapping("/{id}/stop")
	public ResponseEntity<String> stopBetting(@PathVariable("id") int id) {
		if (service.stopBetting(id)) {
			return new ResponseEntity<>("종료 완료", HttpStatus.OK);
		}
		return new ResponseEntity<>("종료 실패", HttpStatus.INTERNAL_SERVER_ERROR);
	}

	// 베팅 결과 입력
	@PutMapping("/{id}/finish")
	public ResponseEntity<String> finishBetting(@PathVariable("id") int id,@RequestBody Betting betting) {
		betting.setId(id);
		if (service.finishBetting(betting)) {
			return new ResponseEntity<>("결과 입력 완료", HttpStatus.OK);
		}
		return new ResponseEntity<>("결과 입력 실패", HttpStatus.INTERNAL_SERVER_ERROR);
	}
		
	@GetMapping("/detail/{bettingId}")
	public ResponseEntity<?> getBettingDetail(@PathVariable("bettingId") int bettingId, @RequestParam("id") String id){
		Betting betting = service.getBettingDetail(bettingId, id);
		if (betting.getId() !=0 ) {
			return new ResponseEntity<Betting>(betting, HttpStatus.OK);
		}else {
			return new ResponseEntity<>("해당 배팅 결과 가져오지 못함", HttpStatus.NO_CONTENT);
		}
	}

	@GetMapping("/create")
	public ResponseEntity<?> readyCreateBetting(HttpServletRequest request, @RequestParam String id) {
		if (!verifyAdmin(request)) { // 관리자 권한 확인
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("관리자 권한이 필요합니다.");
		}
		Betting betting = service.readyCreateBetting(id);
		if (betting != null) {
			return new ResponseEntity<Betting>(betting, HttpStatus.OK);
		}
		
		return new ResponseEntity<>("생성 실패", HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@PostMapping("/create")
	public ResponseEntity<String> createBetting(HttpServletRequest request, @RequestBody Betting betting) {
		if (!verifyAdmin(request)) { // 관리자 권한 확인
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("관리자 권한이 필요합니다.");
		}
		if (service.createBetting(betting)) {
			return new ResponseEntity<>("생성 완료", HttpStatus.OK);
		}
		return new ResponseEntity<>("생성 실패", HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	// 배팅 코멘트 작성
	@PostMapping("/review")
	public ResponseEntity<String> writeReview(@RequestBody Review review) {
		if (service.createReview(review)) {
			return new ResponseEntity<>("작성 성공", HttpStatus.OK);
		}
		return new ResponseEntity<>("작성 실패", HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@PutMapping("/review")
	public ResponseEntity<String> modifyReview(@RequestBody Review review) {
		if (service.modifyReview(review)) {
			return new ResponseEntity<>("수정 성공", HttpStatus.OK);
		}
		return new ResponseEntity<>("수정 실패", HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@DeleteMapping("/review")
	public ResponseEntity<String> removeReview(@RequestParam int reviewId) {
		if (service.removeReview(reviewId)) {
			return new ResponseEntity<>("삭제 성공", HttpStatus.OK);
		}
		return new ResponseEntity<>("삭제 실패", HttpStatus.INTERNAL_SERVER_ERROR);
	}

	// 참여 배팅 목록 조회
	@GetMapping("/history/join")
	public ResponseEntity<?> getBettingListByUserId(@RequestParam String id) {
		List<BettingHistory> list = service.getBettingHistory(id);

		if (list != null && list.size() != 0) {
			return new ResponseEntity<List<BettingHistory>>(list, HttpStatus.OK);
		}
		return new ResponseEntity<String>("정보 없음", HttpStatus.NO_CONTENT);
	}

	// 챌린저로 참여 목록 조회
	@GetMapping("/history/challenger")
	public ResponseEntity<?> getBettingListByChallengerId(@RequestParam String id) {
		List<Betting> list = service.getChallengerBettingHistory(id);

		if (list != null && list.size() != 0) {
			return new ResponseEntity<List<Betting>>(list, HttpStatus.OK);
		}
		return new ResponseEntity<String>("정보 없음", HttpStatus.NO_CONTENT);
	}

	@GetMapping("/slot")
	public ResponseEntity<?> getSlotData(HttpServletRequest request) {
		if (!verifyAdmin(request)) { // 관리자 권한 확인
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("관리자 권한이 필요합니다.");
		}
		
		HttpSession session = request.getSession(false);

		String campus = (String) session.getAttribute("campus");
		Integer classNumObj = (Integer) session.getAttribute("classNum"); // nullException 방지
		if (campus == null || classNumObj == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("필수 세션 정보가 누락되었습니다.");
		}
		
		int classNum = classNumObj.intValue(); // Integer ==> int로 
		List<User> userList = service.getUserList(campus, classNum);
		List<Mission> missionList = service.getMissionList();
		if (userList.isEmpty() && missionList.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("데이터가 없습니다.");
		}
		Map<String, Object> response = new HashMap<>();
		response.put("users", userList);
		response.put("missions", missionList);

		return ResponseEntity.ok(response); // Map을 JSON 형태로 변환하여 반환
	}

	private boolean verifyAdmin(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session == null) {
			System.out.println("세션 만료 혹은 로그인 해야함");
			return false;
		}
		int isAdmin = (int) session.getAttribute("isAdmin");
		System.out.println(isAdmin);
		if (isAdmin == TRUE) {
			return true;
		}
		return false;
	}

}