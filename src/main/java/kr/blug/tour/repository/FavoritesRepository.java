package kr.blug.tour.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import kr.blug.tour.entity.FavoritesEntity;

@Repository
public interface FavoritesRepository extends JpaRepository<FavoritesEntity, Long>{

//	List<FavoritesEntity> findAllByUser_UserId(Long userId);
	Page<FavoritesEntity> findAllByUser_UserId(Long userId, Pageable pageable);

	Page<FavoritesEntity> findByUser_UserIdAndContents_ContentTypeIdOrderByCrdttmDesc(Long userId, String contentTypeId, Pageable pageable);
	
	boolean existsByUser_UserIdAndContents_ContentId(Long userId, String contentId);

	FavoritesEntity findByUser_UserIdAndContents_ContentId(Long userId, String contentId);

	Page<FavoritesEntity> findByUser_UserIdAndContents_ContentIdAndContents_ContentTypeIdOrderByCrdttmDesc(Long userId,
			String contentId, String contentTypeId, Pageable pageable);

	
    @Query(value = """
            SELECT 
            f.favorites_id, u.user_id, u.nickname, 
            c.content_id, c.title, c.addr, c.area_code, c.sigungu_code, c.firstimage, f.crdttm
            FROM favorites f
            JOIN contents c ON f.content_id = c.content_id
            JOIN user u ON f.user_id = u.user_id
            WHERE (:userId IS NULL OR f.user_id = :userId)
              AND (:contentId IS NULL OR c.content_id = :contentId)
              AND (:contentTypeId IS NULL OR c.content_type_id = :contentTypeId)
              AND (:areaCode IS NULL OR c.area_code = :areaCode)
              AND (:sigunguCode IS NULL OR c.sigungu_code = :sigunguCode)
            ORDER BY f.crdttm DESC
            """,
            countQuery = """
            SELECT COUNT(*)
            FROM favorites f
            JOIN contents c ON f.content_id = c.content_id
            JOIN user u ON f.user_id = u.user_id
            WHERE (:userId IS NULL OR f.user_id = :userId)
              AND (:contentId IS NULL OR c.content_id = :contentId)
              AND (:contentTypeId IS NULL OR c.content_type_id = :contentTypeId)
              AND (:areaCode IS NULL OR c.area_code = :areaCode)
              AND (:sigunguCode IS NULL OR c.sigungu_code = :sigunguCode)
            """,
            nativeQuery = true)
        Page<FavoritesEntity> listMyFavorites(
            @Param("userId") Long userId,
            @Param("contentId") String contentId,
            @Param("contentTypeId") String contentTypeId,
            @Param("areaCode") String areaCode,
            @Param("sigunguCode") String sigunguCode,
            Pageable pageable
        );

	
}
