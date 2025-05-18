package kr.blug.tour.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.blug.tour.entity.FavoritesEntity;

@Repository
public interface FavoritesRepository extends JpaRepository<FavoritesEntity, Long>{

//	List<FavoritesEntity> findAllByUser_UserId(Long userId);
	Page<FavoritesEntity> findAllByUser_UserId(Long userId, Pageable pageable);

	Page<FavoritesEntity> findByUser_UserIdAndContents_ContentTypeIdOrderByCrdttmDesc(Long userId, String contentTypeId, Pageable pageable);
	
	boolean existsByUser_UserIdAndContents_ContentId(Long userId, String contentId);

	FavoritesEntity findByUser_UserIdAndContents_ContentId(Long userId, String contentId);
	
}
