package kr.blug.tour.naver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/news")
@RequiredArgsConstructor
public class NewsSearchController {
	
	@Autowired
	private final NewsSearchService newsSearchService;
	
	
	@GetMapping
	public NewsResponseDto getNews(
		@RequestParam(name = "keyword", required = true) String keyword,
		@RequestParam(name = "page", defaultValue = "1") int startPage, 
		@RequestParam(name = "size", defaultValue = "10") int rowCount
			) {
		
		System.out.println("########################### /api/news ################" + keyword);
		return newsSearchService.searchNews(keyword, startPage, rowCount);
		
	}
	
}
