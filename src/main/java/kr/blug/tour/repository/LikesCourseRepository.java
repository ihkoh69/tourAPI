	package kr.blug.tour.repository;



import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import kr.blug.tour.entity.LikesCourseEntity;

@Repository
public interface LikesCourseRepository extends JpaRepository<LikesCourseEntity, Long>{



	Page<LikesCourseEntity> findAllByUser_UserId(Long userId, Pageable pageable);
	
	@Query("SELECT count(*) FROM LikesCourseEntity t WHERE t.course.courseId = :courseId")
	long countByCourseId(@Param("courseId") Long courseId);
	
//	@Query(value = "SELECT count(*) FROM likes_course WHERE user_id = :userId AND course_id = :courseId", nativeQuery = true)
//	long  countNativeByuserIdAndCourseId(@Param("userId") Long userId, @Param("courseId") Long courseId);

	
	@Query("SELECT l FROM LikesCourseEntity l JOIN l.course c WHERE l.user.userId = :userId ORDER BY c.areaCode ASC")
	Page<LikesCourseEntity> findByUserIdOrderByCourseAreaCode(@Param("userId") Long userId, Pageable pageable);
	
	
	
	//countQuery는 반드시 group by 된 course_id의 수만 세도록 따로 작성해줘야 함.
	@Query(
		    value = """
				SELECT 
				    a.course_id AS courseId,
				    w.user_id AS writerUserId,
				    w.nickname AS writerNickname,
				    a.area_code AS areaCode,
				    a.sigungu_code AS sigunguCode,
				    a.course_name AS courseName,
				    a.description AS description,
				    a.crdttm AS crdttm,
				    a.updttm AS updttm,
				    stat.cnt AS likesCount
				FROM (
				    SELECT lc.course_id, COUNT(*) AS cnt
				    FROM likes_course lc
				    GROUP BY lc.course_id
				) stat
				JOIN course a ON a.course_id = stat.course_id
				JOIN user w ON a.user_id = w.user_id
				WHERE (:userId IS NULL OR EXISTS (
				    SELECT 1 FROM likes_course l2 
				    WHERE l2.course_id = a.course_id AND l2.user_id = :userId
				))
				AND (:areaCode IS NULL OR a.area_code = :areaCode)
				AND (:sigunguCode IS NULL OR a.sigungu_code = :sigunguCode)
				AND (:creatorUserId IS NULL OR a.user_id = :creatorUserId)
				AND (:courseId IS NULL OR a.course_id = :courseId)
				ORDER BY stat.cnt DESC
		        """,
		    countQuery = """
		        SELECT COUNT(*) FROM (
		            SELECT lc.course_id
		            FROM likes_course lc
		            JOIN course c on lc.course_id = c.course_id
		            WHERE (:userId IS NULL OR lc.user_id = :userId) AND        
		                  (:areaCode IS NULL OR c.area_code = :areaCode) AND 
		                  (:sigunguCode IS NULL OR c.sigungu_code = :sigunguCode) AND
		                  (:creatorUserId IS NULL OR c.user_id = :creatorUserId) AND
		                  (:courseId IS NULL OR c.course_id = :courseId) AND
		                  (:userId IS NULL OR lc.user_id = :userId)
		            GROUP BY lc.course_id
		        ) AS counted
		        """,
		    nativeQuery = true
		)
		Page<ProjectionLikesCourseCount> listCoursesOrderByLikesCountDesc(Pageable pageable, 
				@Param("areaCode")  String areaCode, 
				@Param("sigunguCode")  String sigunguCode, 
				@Param("userId")  Long userId, 
				@Param("creatorUserId") Long creatorUserId,
				@Param("courseId") Long courseId);

	boolean existsByUser_UserIdAndCourse_CourseId(Long userId, Long courseId);

	Optional<LikesCourseEntity> findByUser_UserIdAndCourse_CourseIdOrderByCrdttmDesc(Long userId, Long courseId);
	
	LikesCourseEntity findByUser_UserIdAndCourse_CourseId(Long userId, Long courseId);

	Long countByCourse_CourseId(Long courseId);

	@Query(
			value = """
				    SELECT COUNT(*) FROM likes_course rc
				    WHERE (:userId IS NULL OR rc.user_id = :userId)
				      AND (:courseId IS NULL OR rc.course_id = :courseId)
					""",
			nativeQuery = true
		
			
			)
		long countByOptionalUserAndContent(
		    @Param("userId") Long userId,
		    @Param("courseId") Long courseId
		);


	
}
