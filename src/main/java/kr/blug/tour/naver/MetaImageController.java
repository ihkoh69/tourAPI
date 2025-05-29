package kr.blug.tour.naver;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/image")
@RequiredArgsConstructor
public class MetaImageController {
	
	public final MetaImageExtractorService metaImageExtractorService;
	
	@GetMapping("/extract")
	public Map<String, String> extract( @RequestParam(name = "url") String url ) {
		String imageUrl = metaImageExtractorService.extractMainImage(url);
		return Map.of("imageUrl", imageUrl);
	}
	
}
