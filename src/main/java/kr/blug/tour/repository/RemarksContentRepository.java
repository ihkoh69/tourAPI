package kr.blug.tour.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import kr.blug.tour.dto.RemarksContentDto;
import kr.blug.tour.entity.RemarksContentEntity;

@Repository
public interface RemarksContentRepository extends JpaRepository<RemarksContentEntity, Long> {


	
	@Query("select t from RemarksContentEntity t left join fetch t.content_id where t.id = :id")
	Optional<RemarksContentEntity> findById(@Param("id") Long id);

//	Optional<RemarksContentEntity> findByUser_User_idAndContents_Contentid(Long user_id, String contentid);
//	Optional<RemarksContentEntity> findByUser_UserIdAndContents_ContentId(Long userid, String contentid);
//	Page<RemarksContentEntity> findByUser_UserId(Long userid, Pageable pageable);

	

}
