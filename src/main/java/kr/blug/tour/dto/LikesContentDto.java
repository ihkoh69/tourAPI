package kr.blug.tour.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LikesContentDto {

	private Long likesContentId;
	private Long userId;
	private Long contentId;
	private String contentTypeId;
	private String title;
	private String addr;
	private String areaCode;
	private String sigunguCode;
	private String firstimage;
	private String goodOrHate;
	private LocalDateTime crdttm;
}
