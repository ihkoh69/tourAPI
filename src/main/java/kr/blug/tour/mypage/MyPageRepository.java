//package kr.blug.tour.mypage;
//
//import java.util.List;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//import org.springframework.stereotype.Repository;
//
//@Repository
//public interface MyPageRepository extends JpaRepository<UserStatsView, Long> {
//	
//	@Query(value = """
//				SELECT myStat.statName, myStat.cnt
//				FROM (
//				    SELECT 'myCourseCount' AS statName, COUNT(*) AS cnt
//				    FROM course
//				    WHERE user_id = :userId
//				
//				    UNION ALL
//				
//				    SELECT 'myFavoritesCount' AS statName, COUNT(*) AS cnt
//				    FROM favorites
//				    WHERE user_id = :userId
//				
//				    UNION ALL
//				
//				    SELECT 'myLikesContentCount' AS statName, COUNT(*) AS cnt
//				    FROM likes_content
//				    WHERE user_id = :userId
//				
//				    UNION ALL
//				
//				    SELECT 'myLikesCourseCount' AS statName, COUNT(*) AS cnt
//				    FROM likes_course
//				    WHERE user_id = :userId
//				
//				    UNION ALL
//				
//				    SELECT 'myRemarksContentCount' AS statName, COUNT(*) AS cnt
//				    FROM remarks_content
//				    WHERE user_id = :userId
//				
//				    UNION ALL
//				
//				    SELECT 'myRemarksCourseCount' AS statName, COUNT(*) AS cnt
//				    FROM remarks_course
//				    WHERE user_id = :userId
//				) AS myStat	"""		
//			, nativeQuery = true) 
//		List<UserStatsView> getMyStatus(@Param("userId") Long userId);
//}
