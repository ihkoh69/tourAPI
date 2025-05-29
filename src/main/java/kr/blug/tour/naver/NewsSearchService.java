package kr.blug.tour.naver;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NewsSearchService {
	
	@Value("${naver.client-id}")
	private String clientId;
	
	@Value("${naver.client-secret}")
	private String clientSecret;
	
	private final WebClient webClient = WebClient.builder().baseUrl("https://openapi.naver.com").build();
	
	public NewsResponseDto searchNews(String keyword, int startPage, int rowCount) {
				
		return webClient.get()
				.uri(uriBuilder -> uriBuilder
						.path("/v1/search/news.json")
						.queryParam("query", keyword)
						.queryParam("display", rowCount)
						.queryParam("start", startPage)
						.build())
				.header("X-Naver-Client-Id", clientId)
				.header("X-Naver-Client-Secret", clientSecret)
				.retrieve()
				.bodyToMono(NewsResponseDto.class)
				.block();
	}

}
