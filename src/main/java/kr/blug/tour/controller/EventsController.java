package kr.blug.tour.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.blug.tour.dto.EventsDto;
import kr.blug.tour.service.EventsService;

@RestController
public class EventsController {

	@Autowired
	private EventsService eventsService;
	
	@GetMapping("/events/list")
	public ResponseEntity<Map<String, Object>> listEvents(
            @RequestParam(name="keyword", required = false) String keyword,
            @RequestParam(name="p_start_date", required = false) String pStartDate,
            @RequestParam(name="p_end_date", required = false) String pEndDate,
            @RequestParam(name="areacode", required = false) String areaCode,
            @RequestParam(name="sigungucode", required = false) String sigunguCode,
            @RequestParam(name="cat3", required = false) String cat3,
            @RequestParam(name="contenttypeid", required = false) String contentTypeId,
            @RequestParam(name="gps_x", required = false) String pointX,
            @RequestParam(name="gps_y", required = false) String pointY,
            @RequestParam(name = "radius_km", required = false, defaultValue = "10.0") double radiusKm,
            @PageableDefault(size = 10, page = 0) Pageable pageable
			) {
		
		
		System.out.println("==================================================== pStartDate = " + pStartDate);
		System.out.println("==================================================== pEndDate = " + pEndDate);
		System.out.println("==================================================== sigungucode = " + sigunguCode);
		System.out.println("==================================================== areaCode = " + areaCode);
		System.out.println("==================================================== radiusKm = " + radiusKm);
		
		if(pStartDate == null || pEndDate == null) {
			return ResponseEntity.ok(Map.of("result", "error", "err_msg", "요청파라메터 p_start_date와 p_end_date는 필수값입니다. "));
		}
		
		if((pointX != null && pointY==null) || (pointY != null && pointX==null)) {
			return ResponseEntity.ok(Map.of("result", "error", "err_msg", "요청파라메터 gps_x와 gps_y는 둘 다 있거나 둘다 없어야 합니다. "));
		}
			
		
		Page<EventsDto> items = eventsService.listEvents(
				keyword,
	            pStartDate,
	            pEndDate,
	            areaCode,
	            sigunguCode,
	            cat3,
	            contentTypeId,
	            pointX,
	            pointY,
	            radiusKm,
	            pageable				
				);
		
		return ResponseEntity.ok(Map.of("result", "success", "items", items));
	}
}
