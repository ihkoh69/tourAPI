package kr.blug.tour.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.blug.tour.service.ItineraryService;

@RestController
public class ItineraryController {
	
	@Autowired
	private ItineraryService itineraryService;
	
//	@PostMapping("/itinerary/save")
//	public ResponseEntity<Map<String, Object>> saveTour() {
//		
//	}

}
