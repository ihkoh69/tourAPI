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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.blug.tour.dto.LikesContentCheckDto;
import kr.blug.tour.dto.LikesContentDto;
import kr.blug.tour.dto.LikesCourseDto;
import kr.blug.tour.dto.SaveContentDto;
import kr.blug.tour.dto.SaveResponseDto;
import kr.blug.tour.service.LikesContentService;

@RestController
public class LikesContentController {

	@Autowired
	private LikesContentService likesContentService;
	
	
	
	@PostMapping("/likes/content/save")
	public ResponseEntity<Map<String, Object>> saveLikesContent(
    		@RequestBody SaveContentDto dto) {

		        SaveResponseDto result = likesContentService.saveLikesContent(dto);

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
	
	@DeleteMapping("/likes/content/delete")
	public ResponseEntity<Map<String, Object>> deleteLikesContent(
				@RequestParam(name="user_id", required = false) Long userId,
				@RequestParam(name="contentid", required = false)  String contentId
			) {
		
		if(userId == null) {
			return ResponseEntity.ok(Map.of("result", "error - parameter user_id is required"));				
		}
		
		if(contentId == null) {
			return ResponseEntity.ok(Map.of("result", "error - parameter contentId is required"));	
		}
		
		SaveResponseDto result = likesContentService.deleteByUserIdAndContentId(userId, contentId);
		
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
	
	
	@GetMapping("/likes/content/check")
	public ResponseEntity<Map<String, Object>> findByUserAndContent(
				@RequestParam(name = "user_id", required = false) Long userId,
				@RequestParam(name= "contentid", required = false) String contentId){
		
		if(userId == null || contentId == null ) {
			return ResponseEntity.ok(Map.of("result", "error", "msg", "유저id와 컨텐츠id는 반드시 입력해야 합니다."));				
		}
		
		
		LikesContentCheckDto dto = likesContentService.findByUserAndContent(userId, contentId); 
		

			return ResponseEntity.ok(Map.of("result", "success", "items", dto));

	}
	
	
	


	@GetMapping("/likes/content/list")
	public ResponseEntity<Map<String, Object>> listLikesContentAll(
				@RequestParam(name="areacode", required = false) String areaCode,
				@RequestParam(name="sigungucode", required = false) String sigunguCode,
				@RequestParam(name="contenttypeid", required = false) String contentTypeId,
				@RequestParam(name="contentid", required = false) String contentId,
				@RequestParam(name="user_id", required = false) Long userId,
				@PageableDefault(size=10, page=0) Pageable pageable
			) {

		
		System.out.println("contentTypeId = " + contentTypeId);
		
		
		
		Page<LikesContentDto> items = likesContentService.listLikesContentAll(pageable, areaCode, sigunguCode, contentTypeId, contentId, userId);
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
	
	
	
	
	
	
}
