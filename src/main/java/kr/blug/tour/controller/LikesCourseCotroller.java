package kr.blug.tour.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.blug.tour.dto.LikesCourseDto;
import kr.blug.tour.entity.LikesCourseEntity;
import kr.blug.tour.service.LikesCourseService;
import lombok.extern.log4j.Log4j2;


@RestController
@Log4j2
public class LikesCourseCotroller {

	
	@Autowired
	private LikesCourseService likesCourseService;
	
	
	@GetMapping("/likes/course/list")
	public ResponseEntity<Map<String, Object>> listMyLikes(
			@RequestParam("userId") Long userId,
			@PageableDefault(size=10, page=1, sort="areaCode", direction = Sort.Direction.ASC ) Pageable pageable
			){
		
		Page<LikesCourseDto> items =  likesCourseService.listMyLikes(userId, pageable);
		
		System.out.println(items.isEmpty());
		if(!items.isEmpty()) { 
			return ResponseEntity.ok(Map.of("result","success", 
					"items", items,
					"totalPages", items.getTotalPages(),
					"totoalElements", items.getTotalElements(),				
					"currentPage", items.getNumber()		));
		}
		else {
			return ResponseEntity.ok(Map.of("result","not_found"));
			
		}
			
		
	}
	
	
	@GetMapping("/likes/course/check")
	public ResponseEntity<Map<String, Object>> findByUserIdAndCourseId(
			@RequestParam("userId") Long  userId,
			@RequestParam("courseId") Long courseId
			) {
		

		
		Optional<LikesCourseDto> dto =  likesCourseService.findByUserAndCourseId(userId, courseId);
		
		log.info("Checking like: userId={}, courseId={}, found={}", userId, courseId, dto.isPresent());
		
		if(dto.isPresent()) {
			
			return ResponseEntity.ok(Map.of("result", "success", "item", dto));
		}
		else
		{
			return ResponseEntity.ok(Map.of("result", "not_found"));				
		}
	
    }
	
}
