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

	Optional<LikesCourseEntity> findByUser_UserIdAndCourse_CourseId(Long userId, Long courseId);

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
		            a.user_id AS writerUserId,
		            u.nickname AS writerNickname,
		            a.area_code AS areaCode,
		            a.sigungu_code AS sigunguCode,
		            a.course_name AS courseName,
		            a.description AS description,
		            a.crdttm AS crdttm,
		            a.updttm AS updttm,
		            c.cnt AS likesCount
		        FROM (
		            SELECT course_id, COUNT(*) AS cnt
		            FROM likes_course
		            GROUP BY course_id
		        ) c
		        JOIN course a ON a.course_id = c.course_id
		        JOIN user u ON a.user_id = u.user_id
		        ORDER BY c.cnt DESC
		        """,
		    countQuery = """
		        SELECT COUNT(*) FROM (
		            SELECT course_id
		            FROM likes_course
		            GROUP BY course_id
		        ) AS counted
		        """,
		    nativeQuery = true
		)
		Page<ProjectionLikesCourseCount> listCoursesOrderByLikesCountDesc(Pageable pageable);
	
	
	
}
