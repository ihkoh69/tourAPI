package kr.blug.tour.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import kr.blug.tour.dto.RemarksCourseDto;
import kr.blug.tour.entity.RemarksCourseEntity;

public interface RemarksCourseRepository extends JpaRepository<RemarksCourseEntity, Long> {

	Optional<RemarksCourseDto> findByUser_UserIdAndCourse_CourseId(Long userId, Long courseId);

	List<RemarksCourseEntity> findByUserUserIdAndCourseCourseId(Long userId, Long courseId);

	

}
