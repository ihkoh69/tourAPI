package kr.blug.tour.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import kr.blug.tour.entity.RemarksContentEntity;

public interface RemarksContentRepository extends JpaRepository<RemarksContentEntity, Long>{

	

	//countQuery는 반드시 group by 된 Content_id의 수만 세도록 따로 작성해줘야 함.
		@Query(
			    value = """
					SELECT
					    rc.remarks_content_id  AS remarksContentId,
					    rc.content_id AS contentId,
					    rc.user_id AS userId,
					    u.nickname AS userNickname,
					    rc.comment AS comment,
					    rc.crdttm AS crdttm
					FROM remarks_content rc
					JOIN user u ON rc.user_id = u.user_Id
					WHERE (:userId IS NULL OR rc.user_id = :userId)
					  AND (:contentId IS NULL OR rc.Content_id = :contentId)
					ORDER BY rc.crdttm DESC
			        """,
			    nativeQuery = true
			)
			Page<ProjectionRemarksContent> listRemarksContentOrderByCrdttmDesc(Pageable pageable, 
					@Param("userId")  Long userId, 
					@Param("contentId")  String contentId
				    );

}
