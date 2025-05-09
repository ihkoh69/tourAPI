package kr.blug.tour.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import kr.blug.tour.dto.UserDto;
import kr.blug.tour.service.UserService;

@RestController
public class UserController {
	@Autowired
	private UserService userService;
	
	@GetMapping("/user/hi")
	public String UserHi() {
		return "hi user";
	}
	

//	@CrossOrigin(origins = "http://127.0.0.1:5500")
	@GetMapping("/user/{id}")
	public ResponseEntity<Map<String, Object>> userViewById(
			@PathVariable("id") Long id
			) {
		
		UserDto userInfo = userService.userViewById(id);
		return ResponseEntity.ok(Map.of("result","success", "item", userInfo));
	}
	
}
