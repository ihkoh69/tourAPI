package kr.blug.tour.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.blug.tour.entity.LikesCourseEntity;

@Repository
public interface LikesCourseRepository extends JpaRepository<LikesCourseEntity, Long>{

	Optional<LikesCourseEntity> findByUser_UserIdAndCourse_CourseId(Long userId, Long courseId);

	Page<LikesCourseEntity> findAllByUser_UserId(Long userId, Pageable pageable);

}
