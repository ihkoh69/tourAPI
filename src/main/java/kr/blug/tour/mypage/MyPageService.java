package kr.blug.tour.mypage;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.blug.tour.repository.UserRepository;


@Service
public class MyPageService {
	

	@Autowired
	private UserRepository userRepository;
	
	
    public List<UserStatsView> getUserStats(Long userId) {
        return userRepository.getMyStatus(userId);
    }

}
