package kr.blug.tour.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.blug.tour.dto.LikesContentDto;
import kr.blug.tour.entity.LikesContentEntity;

@Repository
public interface LikesContentRepository extends JpaRepository<LikesContentEntity, Long> {

	Optional<LikesContentEntity> findByUser_UserIdAndContentId(Long userId, String contentId);

	Page<LikesContentEntity> findByUser_UserId(Long userId, Pageable pageable);


}
