package kr.blug.tour.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FavoritesDto {

	private Long favorites_id;
	private Long user_id;
	private String user_nickname;
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
	private LocalDateTime crdttm;
	
}
