package kr.blug.tour.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SaveContentDto {

	private Long user_id;   // 관광지 좋아요,찜 목록, 관광지 댓글 등록 등에 사
	private Long course_id;  // 여행코스의 방문지점으로 등록 시 사
	
	private String contentid;
	private String contenttypeid;
	private String title;
	private String addr1;
	private String addr2;
	private String areacode;
	private String sigungucode;
	private String firstimage;
	private String mapX;
	private String mapY;
	
	private String comment;  // remarks/content 저장시에만 사용
	
}
