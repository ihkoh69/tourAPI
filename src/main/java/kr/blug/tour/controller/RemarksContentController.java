package kr.blug.tour.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.blug.tour.dto.RemarksContentDto;
import kr.blug.tour.service.RemarksContentService;
import kr.blug.tour.util.RemarksContentUtil;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/remarks")
@Log4j2
public class RemarksContentController {
	
//	@Autowired
//	private RemarksContentUtil remarksContentUtil;
	
	@Autowired
	private RemarksContentService remarksContentService;
	
	//create
	@PostMapping("/content")
	public String remarksContentCreate(
			RemarksContentDto remarksContentDto
			){
		
		
		System.out.println(remarksContentDto);
		
		remarksContentService.remarksContentInsert(remarksContentDto);
		
		return null;
		
		
	}
	  


}
