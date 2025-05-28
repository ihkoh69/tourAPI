package kr.blug.tour.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.blug.tour.dto.ItineraryUpdateDto;
import kr.blug.tour.dto.LikesCourseCheckDto;
import kr.blug.tour.dto.LikesCourseDto;
import kr.blug.tour.dto.SaveResponseDto;
import kr.blug.tour.service.LikesCourseService;

import lombok.extern.log4j.Log4j2;


@RestController
@Log4j2
public class LikesCourseCotroller {

	
	@Autowired
	private LikesCourseService likesCourseService;
	
	@PostMapping("/likes/course/save")
	public ResponseEntity<Map<String, Object>> addLikesCourse(
				@RequestParam(name="user_id", required = false) Long userId,
				@RequestParam(name="course_id", required = false) Long courseId
			) {
		

		SaveResponseDto result = likesCourseService.saveLikesCourse(userId, courseId);
		
        return ResponseEntity.ok(Map.of("result", result));

				
	}
	
	@DeleteMapping("/likes/course/delete")
	public ResponseEntity<Map<String, Object>> deleteLikesCourse(
				@RequestParam(name="user_id", required = false) Long userId,
				@RequestParam(name="course_id", required = false)  Long courseId
			) {
		
		
		SaveResponseDto result = likesCourseService.deleteByUserIdAndCourseId(userId, courseId);
		
        return ResponseEntity.ok(Map.of("result", result));

	}
	
	@GetMapping("/likes/course/list")
	public ResponseEntity<Map<String, Object>> listLikesCourseAll(
				@RequestParam(name="areacode", required = false) String areaCode,
				@RequestParam(name="sigungucode", required = false) String sigunguCode,
				@RequestParam(name="user_id", required = false) Long userId,
				@RequestParam(name="creator_user_id", required = false) Long creatorUserId,
				@RequestParam(name="course_id", required = false) Long courseId,
				@PageableDefault(size=10, page=0) Pageable pageable
			) {

		// writeUserId = 여행코스를 만든 유저
		// userId = 로그인한 유저(좋아요를 선택한 유저)
		Page<LikesCourseDto> items = likesCourseService.listLikesCourseAll(pageable, areaCode, sigunguCode, userId, creatorUserId, courseId);
		if(!items.isEmpty()) {
		
			return ResponseEntity.ok(Map.of(
					"result", "success", 
					"items", items,
					"totalPages", items.getTotalPages(),
					"totalElements", items.getTotalElements(),				
					"currentPage", items.getNumber()		
					));
		}
		else {
			return ResponseEntity.ok(Map.of("result","not_found"));
		}
	}
	
	
	
	@GetMapping("/likes/course/check")
	public ResponseEntity<Map<String, Object>> findByUserIdAndCourseId(
			@RequestParam(name = "user_id", required = false) Long  userId,
			@RequestParam(name = "course_id", required = false) Long courseId
			) {

		
		if(userId == null || courseId == null) {
			return ResponseEntity.ok(Map.of("result", "error", "err_msg", "유저id와 여행코스id는 반드시 입력해야 합니다."));				
		}
		
		LikesCourseCheckDto dto =  likesCourseService.findByUserAndCourseId(userId, courseId);
		

		return ResponseEntity.ok(Map.of("result", "success", "items", dto));				
	
    }
	

	
}
