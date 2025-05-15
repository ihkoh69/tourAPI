package kr.blug.tour.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FavoritesSaveDto {

	private Long user_id;
	private String contentid;
	private String contenttypeid;
	private String title;
	private String addr;
	private String areacode;
	private String sigungucode;
	private String firstimage;
	
}
