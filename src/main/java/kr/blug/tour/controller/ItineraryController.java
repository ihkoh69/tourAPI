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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.blug.tour.dto.ItineraryDto;
import kr.blug.tour.dto.ItineraryUpdateDto;
import kr.blug.tour.dto.ItineraryViewDto;
import kr.blug.tour.dto.SaveResponseDto;
import kr.blug.tour.service.ItineraryService;
import kr.blug.tour.util.ParamCheckUtils;

@RestController
public class ItineraryController {
	
	@Autowired
	private ItineraryService itineraryService;
	
	@PostMapping("/itinerary/save")
	public ResponseEntity<Map<String, Object>> saveTour(
				@RequestBody ItineraryDto dto
			) {
		
		SaveResponseDto res = itineraryService.saveItinerary(dto);
		System.out.println(dto.toString());
		
		return ResponseEntity.ok(Map.of("result", res));
	}
	
	@GetMapping("/itinerary/list")
	public ResponseEntity<Map<String, Object>> listTour(
				@RequestParam(name="user_id", required = false) Long userId,
				@RequestParam(name="p_start_date", required = false) String pStartDate,
				@RequestParam(name="p_end_date", required = false) String pEndDate,
				@PageableDefault(size = 10, page = 0) Pageable pageable
			){
	
		
		
		if(!ParamCheckUtils.isValidDate(pStartDate) || !ParamCheckUtils.isValidDate(pEndDate)) {
				return ResponseEntity.ok(Map.of("result", "error", "msg", "날짜 파라미터 형식을 확인하세요 yyyyMMdd"));
			}
		
		Page<ItineraryDto> items = itineraryService.listTour(userId, pStartDate, pEndDate, pageable); 
		
		return ResponseEntity.ok(Map.of("result", items));
	}
	
	
	@PatchMapping("/itinerary/update/{id}")
	public ResponseEntity<Map<String, Object>> updateTour(
			@PathVariable("id") Long itineraryId,
			@RequestBody ItineraryUpdateDto dto
			) {
		
		SaveResponseDto result = itineraryService.updateTour(itineraryId, dto);
		
		
		return ResponseEntity.ok(Map.of("result", result));
	}
	
	@DeleteMapping("/itinerary/delete/{id}")
	public ResponseEntity<Map<String, Object>> delteTour(
			@PathVariable(name="id") Long itineraryId) {
		
		
		SaveResponseDto result = itineraryService.deleteTour(itineraryId);
		
		return ResponseEntity.ok(Map.of("result", result));
		
	}
	
	@GetMapping("/itinerary/view/{id}")
	public ResponseEntity<Map<String, Object>> viewTourDetail(
			@PathVariable(name="id") Long itineraryId,
			@PageableDefault(size=10, page=0) Pageable pageable
		) {
		
		ItineraryViewDto items = itineraryService.viewTourDetail(itineraryId);
		
		if(items == null) {
			return ResponseEntity.ok(Map.of("result", "no_data"));
		}
		else {
			return ResponseEntity.ok(Map.of("result", "success", "items", items));
		}
		

	}
	
	
}
