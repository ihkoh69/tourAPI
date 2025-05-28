package kr.blug.tour.repository;


import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import kr.blug.tour.dto.ItineraryViewDto;
import kr.blug.tour.entity.ItineraryEntity;

@Repository
public interface ItineraryRepository extends JpaRepository<ItineraryEntity, Long> {

	boolean existsByUser_UserIdAndCourse_CourseIdAndStartDate(Long user_id, Long course_id, String start_date);

	
	@Query("""
		    SELECT i
		    FROM ItineraryEntity i
		    WHERE i.user.userId = :userId
		      AND (i.endDate BETWEEN :startDate AND :endDate
		           OR i.startDate BETWEEN :startDate AND :endDate)
		""")
		Page<ItineraryEntity> findByUserIdAndDateRange(
		    @Param("userId") Long userId,
		    @Param("startDate") String startDate,
		    @Param("endDate") String endDate,
		    Pageable pageable
		);


	Optional<ItineraryEntity> findByItineraryId(Long itineraryId);
}
