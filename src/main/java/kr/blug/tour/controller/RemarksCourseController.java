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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.blug.tour.dto.RemarksCommentUpdateDto;
import kr.blug.tour.dto.RemarksCourseDto;
import kr.blug.tour.dto.SaveResponseDto;
import kr.blug.tour.entity.RemarksCourseEntity;
import kr.blug.tour.service.RemarksCourseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;


@RestController
@Log4j2
@RequestMapping("/remarks/course")
public class RemarksCourseController {
	
	@Autowired
	private RemarksCourseService remarksCourseService;
	
	//read
	@GetMapping("/list")
	public ResponseEntity<Map<String, Object>> listRemarksCourse(
			@RequestParam(name="user_id", required = false) Long userId,
			@RequestParam(name="course_id", required = false) Long courseId,
			@PageableDefault(size=10, page=0) Pageable pageable
			){
		Page<RemarksCourseDto> items = remarksCourseService.listRemarksCourse(pageable, userId, courseId);
		
		if(!items.isEmpty()) {
			return ResponseEntity.ok(Map.of(
					"result", "success", "items", items,
					"totalPages", items.getTotalPages(),
					"totoalElements", items.getTotalElements(),				
					"currentPage", items.getNumber()
					));
		}else {
			return ResponseEntity.ok(Map.of("result","not_found"));
		}				
	}
	
	@PostMapping("/save")
	public ResponseEntity<Map<String, Object>> saveRemarksCourse(
				@RequestBody RemarksCourseDto dto
			){
		
		SaveResponseDto result = remarksCourseService.saveRemarksCourse(dto);
		
		return ResponseEntity.ok(Map.of("result", result));		
	}
	
	@DeleteMapping("/delete")
	public ResponseEntity<Map<String, Object>> deleteRemarksCourse(
				@RequestParam(name="remarks_course_id", required = false) Long remarksCourseId
		) {
		
		if(remarksCourseId == null) return ResponseEntity.ok(Map.of("result", "remarks_course_id is required"));		
		
		SaveResponseDto result = remarksCourseService.deleteRemarksCourse(remarksCourseId);
	
		return ResponseEntity.ok(Map.of("result", result));		
	}
	
	@GetMapping("/count")
	public ResponseEntity<Map<String, Object>> countRemarksCourse(
			@RequestParam(name="user_id", required = false) Long userId,
			@RequestParam(name="course_id", required = false) Long courseId
		){
		
		Long cnt = remarksCourseService.countRemarksCourse(userId, courseId);
		return ResponseEntity.ok(Map.of("result", "success", "count", cnt));
	}

	@PatchMapping("/update/{remarks_course_id}")
	public ResponseEntity<Map<String, Object>> updateRemarksCourse(
				@PathVariable("remarks_course_id") Long remarksCourseId,
				@RequestBody RemarksCommentUpdateDto dto
			) {
		
			if(remarksCourseId == null) ResponseEntity.ok(Map.of("result", "remarks_coourse_id is required"));
			SaveResponseDto result = remarksCourseService.updateCommentById(remarksCourseId, dto); 
		
		return ResponseEntity.ok(Map.of("result", result));
		
	}
	
}
