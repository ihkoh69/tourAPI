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
import kr.blug.tour.dto.FavoritesSaveDto;
import kr.blug.tour.dto.SaveResponseDto;
import kr.blug.tour.service.FavoritesService;

@RestController
public class FavoritesController {

	@Autowired
	private FavoritesService favoritesService;
	
	
    @PostMapping("/favorites/save")
    public ResponseEntity<Map<String,Object>> addFavorite(@RequestBody FavoritesSaveDto dto) {
    	
        SaveResponseDto result = favoritesService.saveFavorite(dto);

        return ResponseEntity.ok(Map.of("result", result));
        
    }
    
    @DeleteMapping("/favorites/delete")
    public ResponseEntity<Map<String, Object>> deleteFavorite(
    		@RequestParam(value="user_id", required=true) Long userId,
    		@RequestParam(value="contentid", required=true) String contentId)
    {
    	
    	SaveResponseDto result = favoritesService.deleteByUserIdAndContentId(userId, contentId);
    	
    	return ResponseEntity.ok(Map.of("result", result));

    }
	
	@GetMapping("/favorites/list")
	public ResponseEntity<Map<String, Object>> listFavoritesByUserId(
			@RequestParam(value="user_id", required=true) Long userId,
			@RequestParam(value="contenttypeid", required=true) String contentTypeId,
			@PageableDefault(size=10, page=0, sort="crdttm", direction = Sort.Direction.ASC) Pageable pageable){ // sort에 대입되는 인자는 물리적DB의 컬럼명 user_id가 아니라 Entity의 필드명 userId여야 
		
		
		Page<FavoritesDto> items = favoritesService.listByUserIdAndContentTypeId(userId, contentTypeId, pageable);
		
		return ResponseEntity.ok(Map.of(
				"result", "success", 
				"items", items.getContent(),
				"totalPages", items.getTotalPages(),
				"totoalElements", items.getTotalElements(),				
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
