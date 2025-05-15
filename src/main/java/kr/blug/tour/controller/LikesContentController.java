package kr.blug.tour.controller;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.blug.tour.dto.LikesContentDto;
import kr.blug.tour.dto.LikesCourseDto;
import kr.blug.tour.service.LikesContentService;

@RestController
public class LikesContentController {

	@Autowired
	private LikesContentService likesContentService;
	
	@GetMapping("/likes/content/check")
	public ResponseEntity<Map<String, Object>> findByUserAndContent(
				@RequestParam("user_id") Long userId,
				@RequestParam("contentid") String contentId){
		
		Optional<LikesContentDto> dto = likesContentService.findByUserAndContent(userId, contentId); 
		
		if(dto.isPresent()) {
			return ResponseEntity.ok(Map.of("resulte", "success", "item", dto));
		}
		else {
			return ResponseEntity.ok(Map.of("result", "not_found"));
		}
	}
	
	@GetMapping("/likes/content/mylist")
	public ResponseEntity<Map<String, Object>> listByUser(
				@RequestParam("userId") Long userId,
				@PageableDefault(size=10, page=0) Pageable pageable
			) {
		
		Page<LikesContentDto> items = likesContentService.listByUser(userId, pageable);
		
		if(!items.isEmpty()) {
			return ResponseEntity.ok(Map.of("result", "success", 
					
					"items", items,
					"totalPages", items.getTotalPages(),
					"totoalElements", items.getTotalElements(),				
					"currentPage", items.getNumber()		
					));
		}
		else {
			return ResponseEntity.ok(Map.of("result", "not_found"));
		}
		
	}
	

	@GetMapping("/likes/content/list")
	public ResponseEntity<Map<String, Object>> listLikesContentAll(
				@RequestParam(name="areacode", required = false) String areaCode,
				@RequestParam(name="sigungucode", required = false) String sigunguCode,
				@RequestParam(name="user_id", required = false) Long userId,				
				@PageableDefault(size=10, page=0) Pageable pageable
			) {
		
//		if(areaCode == null) areaCode = "%";
//		if(sigunguCode == null) sigunguCode  = "%";
//		if(userId == null) userId = "%";
		
		Page<LikesContentDto> items = likesContentService.listLikesContentAll(pageable, areaCode, sigunguCode, userId);
		if(!items.isEmpty()) {
		
			return ResponseEntity.ok(Map.of(
					"result", "success", "items", items,
					"totalPages", items.getTotalPages(),
					"totoalElements", items.getTotalElements(),				
					"currentPage", items.getNumber()		
					));
		}
		else {
			return ResponseEntity.ok(Map.of("result","not_found"));
		}
	}
	
	
	
	
	
	
}
