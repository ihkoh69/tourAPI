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
				    a.addr1 AS addr1,
				    a.addr2 AS addr2,
				    a.title AS title,
				    a.firstimage AS firstimage,
				    a.mapx AS mapx,
				    a.mapy AS mapy,
				    a.crdttm AS crdttm,
				    c.cnt AS likesCount
				FROM (
				    SELECT lc.content_id, COUNT(*) AS cnt
				    FROM likes_content lc
				    GROUP BY lc.content_id
				) c
				JOIN contents a ON a.content_id = c.content_id
				WHERE (:userId IS NULL OR EXISTS (
				    SELECT 1 FROM likes_content ulc 
				    WHERE ulc.content_id = a.content_id AND ulc.user_id = :userId
				))
				AND (:areaCode IS NULL OR a.area_code = :areaCode)
				AND (:sigunguCode IS NULL OR a.sigungu_code = :sigunguCode)
				AND (:contentTypeId IS NULL OR a.content_type_id = :contentTypeId)
				AND (:contentId IS NULL OR a.content_id = :contentId)
				ORDER BY c.cnt DESC
		        """,
		    countQuery = """
		        SELECT COUNT(*) FROM (
		            SELECT lc.content_id
		            FROM likes_content lc
		            JOIN contents c on lc.content_id = c.content_id
		            WHERE (:areaCode IS NULL OR c.area_code = :areaCode) AND 
		                  (:sigunguCode IS NULL OR c.sigungu_code = :sigunguCode) AND
		                  (:contentTypeId IS NULL OR c.content_type_id = :contentTypeId) AND
		                  (:contentId IS NULL OR c.content_id = :contentId) AND 
		                  (:userId IS NULL OR lc.user_id = :userId)
		            GROUP BY lc.content_id) AS counted
		        """,
		    nativeQuery = true
		)
		Page<ProjectionLikesCotentCount> listContentsOrderByLikesCountDesc(Pageable pageable, 
				@Param("areaCode")  String areaCode, 
				@Param("sigunguCode")  String sigunguCode, 
				@Param("contentTypeId") String contentTypeId,
				@Param("contentId") String contentId,
				@Param("userId") Long userId
			    );

	
	
	LikesContentEntity findByUser_UserIdAndContents_ContentId(Long userId, String contentId);

	boolean existsByUser_UserIdAndContents_ContentId(Long user_id, String contentid);

	Long countByContents_ContentId(String contentId);

	@Query(
			value = """
				    SELECT COUNT(*) FROM likes_content rc
				    WHERE (:userId IS NULL OR rc.user_id = :userId)
				      AND (:contentId IS NULL OR rc.content_id = :contentId)
					""",
			nativeQuery = true
		
			
			)
		long countByOptionalUserAndContent(
		    @Param("userId") Long userId,
		    @Param("contentId") String contentId
		);

}
