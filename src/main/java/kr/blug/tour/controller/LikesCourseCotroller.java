package kr.blug.tour.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import kr.blug.tour.dto.LikesCourseDto;
import kr.blug.tour.dto.SaveResponseDto;
import kr.blug.tour.entity.CourseEntity;
import kr.blug.tour.entity.LikesCourseEntity;
import kr.blug.tour.entity.LikesCourseEntity;
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
		
		
		if(userId == null) {
			return ResponseEntity.ok(Map.of("result", "error - parameter user_id is required"));				
		}
		
		if(courseId == null) {
			return ResponseEntity.ok(Map.of("result", "error - parameter course_id is required"));	
		}
		
		SaveResponseDto result = likesCourseService.saveLikesCourse(userId, courseId);
		
        if(result.isSuccess()) {
        	return ResponseEntity.ok(Map.of(
        			"result", "success", 
        			"message", result.getMessage(), 
        			"id_name", result.getId_name(), 
        			"value", result.getId() ));
        } 
        else {
        	return ResponseEntity.ok(Map.of(
        			"result", result.getMessage()));
        }
				
	}
	
	@DeleteMapping("/likes/course/delete")
	public ResponseEntity<Map<String, Object>> deleteLikesCourse(
				@RequestParam(name="user_id", required = false) Long userId,
				@RequestParam(name="course_id", required = false)  Long courseId
			) {
		
		
		if(userId == null) {
			return ResponseEntity.ok(Map.of("result", "error - parameter user_id is required"));				
		}
		
		if(courseId == null) {
			return ResponseEntity.ok(Map.of("result", "error - parameter course_id is required"));	
		}
		
		
		SaveResponseDto result = likesCourseService.deleteByUserIdAndCourseId(userId, courseId);
		
        if(result.isSuccess()) {
        	return ResponseEntity.ok(Map.of(
        			"result", "success", 
        			"message", result.getMessage(), 
        			"id_name", result.getId_name(), 
        			"value", result.getId() ));
        } 
        else {
        	return ResponseEntity.ok(Map.of(
        			"result", result.getMessage()));
        }
	}
	
	@GetMapping("/likes/course/list")
	public ResponseEntity<Map<String, Object>> listLikesCourseAll(
				@RequestParam(name="areacode", required = false) String areaCode,
				@RequestParam(name="sigungucode", required = false) String sigunguCode,
				@RequestParam(name="user_id", required = false) Long userId,
				@RequestParam(name="creator_user_id", required = false) Long creatorUserId,
				@PageableDefault(size=10, page=0) Pageable pageable
			) {

		// writeUserId = 여행코스를 만든 유저
		// userId = 로그인한 유저(좋아요를 선택한 유저)
		Page<LikesCourseDto> items = likesCourseService.listLikesCourseAll(pageable, areaCode, sigunguCode, userId, creatorUserId);
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
	
	
//	// 사용하지않음. 구버전 
//	@GetMapping("/likes/course/mylist")
//	public ResponseEntity<Map<String, Object>> listMyLikes(
//			@RequestParam("user_id") Long userId,
//			@PageableDefault(size=10, page=0) Pageable pageable
////			@PageableDefault(size=10, page=0, sort="areaCode", direction = Sort.Direction.ASC ) Pageable pageable   /* @PageableDefault(sort = "중첩 필드")에서 중첩 필드(course.areaCode 등)는 사용할 수 없음 */
//			){
//		
//						// LikesCourseEntity에 포함된 아래내용에 따라 course객체를 사용하여 course.areaCode의 형태로 참조
////						@ManyToOne(fetch = FetchType.LAZY)
////						@JoinColumn(name="course_id", nullable = false)
////						private CourseEntity course;
//		
//		Sort sort = Sort.by(Sort.Direction.ASC,"course.areaCode");
//		Pageable sortedPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
//		
//		
////		Page<LikesCourseDto> items =  likesCourseService.listMyLikes(userId, pageable);
//		Page<LikesCourseDto> items =  likesCourseService.listMyLikes(userId, sortedPageable);
//		
//		System.out.println(items.isEmpty());
//		if(!items.isEmpty()) { 
//			return ResponseEntity.ok(Map.of("result","success", 
//					"items", items.getCourse(),
//					"totalPages", items.getTotalPages(),
//					"totalElements", items.getTotalElements(),				
//					"currentPage", items.getNumber()		));
//		}
//		else {
//			return ResponseEntity.ok(Map.of("result","not_found"));
//			
//		}	
//		
//	}
//	
	
	@GetMapping("/likes/course/check")
	public ResponseEntity<Map<String, Object>> findByUserIdAndCourseId(
			@RequestParam(name = "user_id", required = false) Long  userId,
			@RequestParam(name = "course_id", required = false) Long courseId
			) {

		
		Optional<LikesCourseDto> dto =  likesCourseService.findByUserAndCourseId(userId, courseId);
		
		log.info("Checking like: userId={}, courseId={}, found={}", userId, courseId, dto.isPresent());
		
		if(dto.isPresent()) {
			
			return ResponseEntity.ok(Map.of(
					"result", "success", 
					"item", dto));
		}
		else
		{
			return ResponseEntity.ok(Map.of("result", "not_found"));				
		}
	
    }
	
}
