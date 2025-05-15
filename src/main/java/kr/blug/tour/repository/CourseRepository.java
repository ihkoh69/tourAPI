package kr.blug.tour.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.blug.tour.entity.CourseEntity;

@Repository
public interface CourseRepository extends JpaRepository<CourseEntity, Long>{

	Page<CourseEntity> findAllByUser_UserIdOrderByUpdttmDesc(Long user_id, Pageable pageable);	

}
