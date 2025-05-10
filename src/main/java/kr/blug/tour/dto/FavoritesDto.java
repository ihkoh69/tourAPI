package kr.blug.tour.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FavoritesDto {

	private Long favoritesId;
	private Long userId;
	private String userNickName;
	private String contentId;
	private String ContentTypeId;
	private String title;
	private String addr;
	private String areaCode;
	private String sigunguCode;
	private String firstimage;
	private LocalDateTime crdttm;
	
}
