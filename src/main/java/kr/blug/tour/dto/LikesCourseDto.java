package kr.blug.tour.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LikesCourseDto {

	private Long likesCourseId;
	private Long userId;
	private Long courseId;
	private String areaCode;
	private String sigunguCode;
	private String goodOrHate;
	private LocalDateTime crdttm;
	
}
