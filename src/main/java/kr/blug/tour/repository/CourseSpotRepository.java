package kr.blug.tour.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.blug.tour.entity.CourseSpotEntity;

public interface CourseSpotRepository extends JpaRepository<CourseSpotEntity, Long>{

	List<CourseSpotEntity> findALLByCourse_CourseId(Long courseId);

}
