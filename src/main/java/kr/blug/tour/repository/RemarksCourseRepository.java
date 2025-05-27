package kr.blug.tour.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import kr.blug.tour.dto.RemarksCourseDto;
import kr.blug.tour.entity.RemarksCourseEntity;

public interface RemarksCourseRepository extends JpaRepository<RemarksCourseEntity, Long> {

	Optional<RemarksCourseDto> findByUser_UserIdAndCourse_CourseId(Long userId, Long courseId);

	List<RemarksCourseEntity> findByUserUserIdAndCourseCourseId(Long userId, Long courseId);

//	Page<RemarksCourseEntity> findByUser_UserIdAndCourse_CourseIdOrderByCrdttmDesc(Pageable pageable, Long userId,
//			String courseId);

	
	
	//countQuery는 반드시 group by 된 course_id의 수만 세도록 따로 작성해줘야 함.
		@Query(
			    value = """
					SELECT
					    rc.remarks_course_id  AS remarksCourseId,
					    rc.course_id AS courseId,
					    rc.user_id AS userId,
					    u.nickname AS userNickname,
					    rc.comment AS comment,
					    rc.crdttm AS crdttm
					FROM remarks_course rc
					JOIN user u ON rc.user_id = u.user_Id
					WHERE (:userId IS NULL OR rc.user_id = :userId)
					  AND (:courseId IS NULL OR rc.course_id = :courseId)
					ORDER BY rc.crdttm DESC
			        """,
			    nativeQuery = true
			)
			Page<ProjectionRemarksCourse> listRemarksCourseOrderByCrdttmDesc(Pageable pageable, 
					@Param("userId")  Long userId, 
					@Param("courseId")  Long courseId
				    );

		@Query(
				value = """
					    SELECT COUNT(*) FROM remarks_course rc
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
