package kr.blug.tour.mypage;

import java.util.List;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyPageController {
	
	
	private final MyPageService myPageService;
	
	public MyPageController(MyPageService myPageService) {
		this.myPageService = myPageService;
	}
	
    @GetMapping("/mystats")
    public UserStatsDto getStats(@RequestParam("user_id") Long userId) {
        return myPageService.getUserStats(userId);
    }

}
