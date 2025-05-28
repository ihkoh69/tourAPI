package kr.blug.tour.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import jakarta.transaction.Transactional;
import kr.blug.tour.entity.CourseSpotEntity;

public interface CourseSpotRepository extends JpaRepository<CourseSpotEntity, Long>{

	List<CourseSpotEntity> findALLByCourse_CourseId(Long courseId);

	void deleteAllByCourse_CourseId(Long courseId);

}
