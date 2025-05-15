package kr.blug.tour.controller;

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.blug.tour.dto.RemarksContentDto;
import kr.blug.tour.dto.RemarksContentResDto;
import kr.blug.tour.repository.RemarksContentRepository;
import kr.blug.tour.service.RemarksContentService;
import kr.blug.tour.util.RemarksContentUtil;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/remarks/content")
@Log4j2
public class RemarksContentController {
	
//	@Autowired
//	private RemarksContentUtil remarksContentUtil;
	
	@Autowired
	private RemarksContentService remarksContentService;
	
	//create
//	@PostMapping("/")
//	public String remarksContentCreate(
//			@RequestBody RemarksContentDto remarksContentDto
//			){		
//		System.out.println(remarksContentDto);		
//		remarksContentService.remarksContentInsert(remarksContentDto);		
//		return null;	
//	}
	  
	//조회
	@GetMapping("/check")
	public ResponseEntity<Map<String, Object>> findByUserAndContent(
			@RequestParam("user_id") Long user_id,
			@RequestParam("content_id") String contentid){
			
		Optional<RemarksContentDto> dto = remarksContentService
				.findByUserAndContentid(user_id, contentid);
		if(dto.isPresent()) {
			return ResponseEntity.ok(Map.of("result", "success","data",dto));
		}
		else {
			return ResponseEntity.ok(Map.of("result", "not_found"));
		}
	}
	
	//total list 현재 작동 안함
//	@GetMapping("/list")
//	public ResponseEntity<Map<String, Object>> remarksContentListView(){
//		List<RemarksContentResDto> remarksContentList = remarksContentService.getList();
//		return ResponseEntity.ok(Map.of("result", "success","data",remarksContentList));
//	}
	
	//pagination
//	@GetMapping("/list")
//	public ResponseEntity<Map<String, Object>> listByUser(
//			@RequestParam("user_id") Long user_id,
//			@PageableDefault(size=10, page=1, sort = "title", direction = Sort.Direction.ASC) Pageable pageable
//		) {
//		Page<RemarksContentDto> items = remarksContentService.listByUser(user_id, pageable);
//		
//		if(!items.isEmpty()) {
//			return ResponseEntity.ok(Map.of("result", "success",
//					"items",items,
//					"totalPages", items.getTotalPages(),
//					"totoalElements", items.getTotalElements(),				
//					"currentPage", items.getNumber()		
//					));
//		}
//		else {
//			return ResponseEntity.ok(Map.of("result", "not_found"));
//		}
//	}
}
