package kr.blug.tour.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import kr.blug.tour.entity.UserEntity;
import kr.blug.tour.mypage.UserStatsView;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long>{

	Optional<UserEntity> findByUserId(Long userId);

	boolean existsByUserId(Long creator_user_id);

	
	@Query(value = """
				SELECT myStat.statName, myStat.cnt
				FROM (
				    SELECT 'myCourseCount' AS statName, COUNT(*) AS cnt
				    FROM course
				    WHERE user_id = :userId
				
				    UNION ALL
				
				    SELECT 'myFavoritesCount' AS statName, COUNT(*) AS cnt
				    FROM favorites
				    WHERE user_id = :userId
				
				    UNION ALL
				
				    SELECT 'myLikesContentCount' AS statName, COUNT(*) AS cnt
				    FROM likes_content
				    WHERE user_id = :userId
				
				    UNION ALL
				
				    SELECT 'myLikesCourseCount' AS statName, COUNT(*) AS cnt
				    FROM likes_course
				    WHERE user_id = :userId
				
				    UNION ALL
				
				    SELECT 'myRemarksContentCount' AS statName, COUNT(*) AS cnt
				    FROM remarks_content
				    WHERE user_id = :userId
				
				    UNION ALL
				
				    SELECT 'myRemarksCourseCount' AS statName, COUNT(*) AS cnt
				    FROM remarks_course
				    WHERE user_id = :userId
				) AS myStat	"""		
			, nativeQuery = true) 
		List<UserStatsView> getMyStatus(@Param("userId") Long userId);

}
