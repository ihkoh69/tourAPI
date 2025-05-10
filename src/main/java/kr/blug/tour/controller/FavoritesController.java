package kr.blug.tour.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.blug.tour.dto.FavoritesDto;
import kr.blug.tour.service.FavoritesService;

@RestController
public class FavoritesController {

	@Autowired
	private FavoritesService favoritesService;
	
	@GetMapping("/favorites/list")
	public ResponseEntity<Map<String, Object>> listFavoritesByUserId(
			@RequestParam(value="userId", required=true) Long userId){
		
		
		List<FavoritesDto> items = favoritesService.listByUserId(userId);
		
		return ResponseEntity.ok(Map.of("result", "success", "items", items));
	}
}
