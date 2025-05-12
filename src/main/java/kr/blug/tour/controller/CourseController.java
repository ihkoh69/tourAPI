           package kr.blug.tour.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import kr.blug.tour.dto.CourseDto;
import kr.blug.tour.entity.CourseEntity;
import kr.blug.tour.service.CourseService;

@Controller
public class CourseController {
	
	@Autowired
	private CourseService courseService;
	
	public ResponseEntity<Map<String, Object>> listCourses(){
		
		List<CourseDto> items = courseService.listCourses();
		
		return ResponseEntity.ok(Map.of("result","success", "items", items));
	}

}
