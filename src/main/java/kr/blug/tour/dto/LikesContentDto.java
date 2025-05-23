package kr.blug.tour.dto;

import java.time.LocalDateTime;

import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import kr.blug.tour.entity.ContentsEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LikesContentDto {

	private Long likes_content_id;
	private Long user_id;
    private String user_nickname;
	private String contentid;	
	private String contenttypeid;
	private String title;
	private String addr1;
	private String areacode;
	private String sigungucode;
	private String firstimage;
	private Long likes_count;
	
	private LocalDateTime crdttm;
	


}
