package kr.blug.tour.naver;

import java.net.HttpURLConnection;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MetaImageExtractorService {
	
	private static final String DEFAULT_IMAGE_URL = "/images/no-image.jpg";
	
	
	public String extractMainImage(String url) {
		try {
			
	        if (!url.startsWith("http://") && !url.startsWith("https://")) {
	            url = "http://" + url;
	        }
	        
	        
	        // MIME 타입을 검사해서 text/html 인 경우에만 image를 얻을 가능성???
	        URL targetUrl = new URL(url);
	        HttpURLConnection conn = (HttpURLConnection) targetUrl.openConnection();
	        conn.setRequestMethod("HEAD");
	        conn.connect();
	        
	        String contentType = conn.getContentType();
	        if(contentType == null || !contentType.startsWith("text/html")) {
	        	return DEFAULT_IMAGE_URL;
	        }
	        
	        // Jsoup으로 HTML 파
			Document doc = Jsoup.connect(url)
					.userAgent("Mozilla/5.0")
					.get();
			
			String ogImage = doc.select("meta[property=og:image]").attr("content");
			
			return (ogImage == null || ogImage.isBlank()) ? DEFAULT_IMAGE_URL : ogImage;
		}
		catch (Exception e) {
			return DEFAULT_IMAGE_URL;
		}
	}
}
