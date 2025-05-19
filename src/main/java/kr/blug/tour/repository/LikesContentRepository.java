package kr.blug.tour.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import kr.blug.tour.dto.LikesContentDto;
import kr.blug.tour.entity.LikesContentEntity;

@Repository
public interface LikesContentRepository extends JpaRepository<LikesContentEntity, Long> {

	Optional<LikesContentEntity> findByUser_UserIdAndContents_ContentIdOrderByCrdttm(Long userId, String contentId);

	Page<LikesContentEntity> findByUser_UserId(Long userId, Pageable pageable);

	

	//countQuery는 반드시 group by 된 course_id의 수만 세도록 따로 작성해줘야 함.
	@Query(
		    value = """
		        SELECT 
		            a.contents_row_id  AS contentsRowId,
		            a.content_id AS contentId,
		            a.content_type_id AS contentTypeId,
		            a.area_code AS areaCode,
		            a.sigungu_code AS sigunguCode,
		            a.addr AS addr,
		            a.title AS title,
		            a.firstimage AS firstimage,
		            a.crdttm AS crdttm,
		            c.cnt AS likesCount
		        FROM (
		            SELECT lc.content_id, COUNT(*) AS cnt
					FROM likes_content lc
					JOIN user u1 ON lc.user_id = u1.user_id
					WHERE (:userId IS NULL OR lc.user_id = :userId)
					GROUP BY lc.content_id		            
		        ) c
		        JOIN contents a ON a.content_id = c.content_id
		         WHERE (:areaCode IS NULL OR a.area_code = :areaCode) AND (:sigunguCode IS NULL OR a.sigungu_code = :sigunguCode)
		        ORDER BY c.cnt DESC
		        """,
		    countQuery = """
		        SELECT COUNT(*) FROM (
		            SELECT lc.content_id
		            FROM likes_content lc
		            JOIN contents c on lc.content_id = c.content_id
		            WHERE (:userId IS NULL OR lc.user_id = :userId) AND 
		                  (:areaCode IS NULL OR c.area_code = :areaCode) AND 
		                  (:sigunguCode IS NULL OR c.sigungu_code = :sigunguCode)
		            GROUP BY lc.content_id
		        ) AS counted
		        """,
		    nativeQuery = true
		)
		Page<ProjectionLikesCotentCount> listContentsOrderByLikesCountDesc(Pageable pageable, 
				@Param("areaCode")  String areaCode, 
				@Param("sigunguCode")  String sigunguCode, 
				@Param("userId")  Long userId);

	LikesContentEntity findByUser_UserIdAndContents_ContentId(Long userId, String contentId);

	boolean existsByUser_UserIdAndContents_ContentId(Long user_id, String contentid);

}
