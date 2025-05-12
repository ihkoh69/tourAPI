package kr.blug.tour.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import kr.blug.tour.entity.RemarksCourseEntity;

public interface RemarksCourseRepository extends JpaRepository<RemarksCourseEntity, Long> {
	
//	@Query("select t from RemarksCourseEntity t left join fetch t.content_id where t.id = :id")
//	Optional<RemarksCourseEntity> findById(@Param("id") Long id);

	List<RemarksCourseEntity> findAllByUser_UserId(Long userId);
}