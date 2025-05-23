package kr.blug.tour.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FavoritesDto {

	private Long favorites_id;
	private Long user_id;
	private String user_nickname;
	private String contentid;
	private String contenttypeid;
	private String title;
	private String addr1;
	private String areacode;
	private String sigungucode;
	private String firstimage;
	private LocalDateTime crdttm;
	
}
