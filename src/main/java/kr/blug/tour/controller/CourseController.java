           package kr.blug.tour.controller;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.blug.tour.dto.CourseDto;
import kr.blug.tour.entity.CourseEntity;
import kr.blug.tour.service.CourseService;

@RestController
public class CourseController {
	
	@Autowired
	private CourseService courseService;
	
	@GetMapping("/course/list")
	public ResponseEntity<Map<String, Object>> listCourses(
			@RequestParam(name="user_id", required = false) Long user_id,
			@PageableDefault(size=10, page=0, sort = "courseName",  direction = Sort.Direction.ASC) Pageable pageable ){  // 소트될 속성명은 엔티티필드명임, db컬럼명이 아님 
		
		Page<CourseDto> items = courseService.listCourses(user_id, pageable);
		
		if(!items.getContent().isEmpty()) {
			return ResponseEntity.ok(Map.of("result","success",  
					"items", items.getContent(),
					"totalPages", items.getTotalPages(),
					"totoalElements", items.getTotalElements(),				
					"currentPage", items.getNumber()		));
		}
		else {
			return ResponseEntity.ok(Map.of("result","not_found"));
			
		}
		
		}

}
