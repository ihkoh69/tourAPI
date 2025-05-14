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

}
