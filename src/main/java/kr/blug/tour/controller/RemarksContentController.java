package kr.blug.tour.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.blug.tour.dto.RemarksContentDto;
import kr.blug.tour.dto.RemarksCourseDto;
import kr.blug.tour.dto.SaveContentDto;
import kr.blug.tour.dto.SaveResponseDto;
import kr.blug.tour.service.RemarksContentService;
import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
@RequestMapping("/remarks/content")
public class RemarksContentController {
	
	
	@Autowired
	private RemarksContentService remarksContentService;
	
	//read
	@GetMapping("/list")
	public ResponseEntity<Map<String, Object>> listRemarksCourse(
			@RequestParam(name="user_id", required = false) Long userId,
			@RequestParam(name="contentid", required = false) String contentId,
			@PageableDefault(size=10, page=0) Pageable pageable
			){
		Page<RemarksContentDto> items = remarksContentService.listRemarksContent(pageable, userId, contentId);
		
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
	public ResponseEntity<Map<String,Object>> saveRemarksContent(
					@RequestBody SaveContentDto dto
			){
		
		SaveResponseDto result = remarksContentService.saveRemarksContent(dto);
		
		return ResponseEntity.ok(Map.of("result", result));
	}
	
	@DeleteMapping("/delete")
	public ResponseEntity<Map<String, Object>> deleteRemarksContent(
				@RequestParam(name="remarks_content_id", required = false) Long remarksContentId
			) {
		
		if(remarksContentId == null) return ResponseEntity.ok(Map.of("result", "remarks_content_id is required"));
		
		SaveResponseDto result = remarksContentService.deleteRemarksContent(remarksContentId);
		
		return ResponseEntity.ok(Map.of("result",result));
	}

}
