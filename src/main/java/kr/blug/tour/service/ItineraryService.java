package kr.blug.tour.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.blug.tour.repository.ItineraryRepository;

@Service
public class ItineraryService {
	
	@Autowired
	private ItineraryRepository itineraryRepository;

}
