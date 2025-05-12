package kr.blug.tour.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.blug.tour.dto.RemarksContentDto;
import kr.blug.tour.dto.RemarksCourseDto;
import kr.blug.tour.service.RemarksContentService;
import kr.blug.tour.service.RemarksCourseService;
import kr.blug.tour.util.RemarksContentUtil;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/api/remarks")
@Log4j2
public class RemarksCourseController {
	
//	@Autowired
//	private RemarksContentUtil remarksContentUtil;
	
	@Autowired
	private RemarksCourseService remarksCourseService;
	
	//create
	@PostMapping("/course")
	public Map<String, String> remarksContentCreate(
			@ModelAttribute RemarksCourseDto remarksCourseDto
			){
		
//		remarksCourseService.remarksCourseInsert(remarksCourseDto);
		
		return Map.of("result","success");
		
		
	}
	  


}
