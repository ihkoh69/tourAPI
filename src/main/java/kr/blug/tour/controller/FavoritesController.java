package kr.blug.tour.controller;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

import kr.blug.tour.dto.FavoritesDto;
import kr.blug.tour.dto.SaveContentDto;
import kr.blug.tour.dto.SaveResponseDto;
import kr.blug.tour.service.FavoritesService;

@RestController
public class FavoritesController {

	@Autowired
	private FavoritesService favoritesService;
	
	
    @PostMapping("/favorites/save")
    public ResponseEntity<Map<String,Object>> addFavorite(
    		@RequestBody SaveContentDto dto) {
    	
        SaveResponseDto result = favoritesService.saveFavorite(dto);
        
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
    
    @DeleteMapping("/favorites/delete")
    public ResponseEntity<Map<String, Object>> deleteFavorite(
    		@RequestParam(value="user_id", required=true) Long userId,
    		@RequestParam(value="contentid", required=true) String contentId)
    {
    	
    	SaveResponseDto result = favoritesService.deleteByUserIdAndContentId(userId, contentId);
    	
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
	
	@GetMapping("/favorites/list")
	public ResponseEntity<Map<String, Object>> listFavoritesByUserId(
			@RequestParam(value="user_id", required = false) Long userId,
			@RequestParam(value="contenttypeid", required = false) String contentTypeId,
			@RequestParam(value="contentid", required = false) String contentId,
			@RequestParam(value="areacode", required = false) String areaCode,
			@RequestParam(value="sigungucode", required = false) String sigunguCode,			
			@PageableDefault(size=10, page=0, sort="crdttm", direction = Sort.Direction.ASC) Pageable pageable){ // sort에 대입되는 인자는 물리적DB의 컬럼명 user_id가 아니라 Entity의 필드명 userId여야 
		
		
		if(userId == null) {
			return ResponseEntity.ok(Map.of("result", "error", "msg", "유저id는 반드시 사용해야 합니다."));
		}
		
		if(sigunguCode != null && areaCode == null) {
			return ResponseEntity.ok(Map.of("result", "error", "msg", "시군구코드는 반드시 시도코드와 함께 사용해야합니다."));
		}
		
		Page<FavoritesDto> items = favoritesService.listMyFavorites(userId, contentTypeId, contentId, areaCode, sigunguCode, pageable);
		
		return ResponseEntity.ok(Map.of(
				"result", "success", 
				"items", items.getContent(),
				"totalPages", items.getTotalPages(),
				"totalElements", items.getTotalElements(),				
				"currentPage", items.getNumber()				
				));
	}
	
//	@GetMapping("/favorites/list")
//	public ResponseEntity<Map<String, Object>> listFavoritesByUserId(
//			@RequestParam(value="userId", required=true) Long userId){
//		
//		
//		List<FavoritesDto> items = favoritesService.listByUserId(userId);
//		
//		return ResponseEntity.ok(Map.of("result", "success", "items", items));
//	}
	
	
}
