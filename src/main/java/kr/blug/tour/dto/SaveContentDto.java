package kr.blug.tour.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaveContentDto {

	private Long user_id;
	private String contentid;
	private String contenttypeid;
	private String title;
	private String addr1;
	private String areacode;
	private String sigungucode;
	private String firstimage;
	
	private String comment;  // remarks/content 저장시에만 사용
	
}
