package kr.blug.tour.mypage;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class UserStatsDto {
	
	Long myCourseCount;
	Long myFavoritesCount;
	Long myLikesContentCount;
	Long myLikesCourseCoun;
	Long myRemarksContentCount;
	Long myRemarksCourseCount;

}
