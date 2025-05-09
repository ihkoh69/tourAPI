package kr.blug.tour.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class MyController {

//	@RequestMapping("/")
//	public String root( ) {
//		return "Hi!!!";
//	}

	@RequestMapping("/")
	public String root() {
		return "root Hi";
	}
	
	
	
}
