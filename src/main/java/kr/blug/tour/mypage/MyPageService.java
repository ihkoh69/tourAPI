package kr.blug.tour.mypage;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.blug.tour.repository.UserRepository;


@Service
public class MyPageService {
	

	@Autowired
	private UserRepository userRepository;
	
	
    public UserStatsDto getUserStats(Long userId) {
        List<UserStatsView> stats = userRepository.getMyStatus(userId);
        
        UserStatsDto myStats = new UserStatsDto();
        
        for(UserStatsView item : stats ) {
        	switch(item.getStatName()) {
        	
        	case "myCourseCount" : 
        		myStats.setMyCourseCount(item.getCnt());
        		break;
        		
        	case "myFavoritesCount" :
        		myStats.setMyFavoritesCount(item.getCnt());
        		break;
        		
        	case "myLikesContentCount" :
        		myStats.setMyLikesContentCount(item.getCnt());
        		break;
        		
        	case "myLikesCourseCount" :
        		myStats.setMyLikesCourseCoun(item.getCnt());
        		break;
        		
        	case "myRemarksContentCount" :
        		myStats.setMyRemarksContentCount(item.getCnt());
        		break;
        		
        	case "myRemarksCourseCount" :
        		myStats.setMyRemarksCourseCount(item.getCnt());
        		break;
        	}
                
        }
        
        
        return myStats;
    }

}
