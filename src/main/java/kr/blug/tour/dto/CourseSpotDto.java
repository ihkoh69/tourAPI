package kr.blug.tour.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseSpotDto {
	private Long courseSpotId;
	
	private Long courseId;
	
	private String contentId;
	private String contentTypeId;
	private String title;
	private String addr;
	private String titleImage;
	private String subImages;
	private String cat3;
	private String areaCode;
	private String sigunguCode;
}
