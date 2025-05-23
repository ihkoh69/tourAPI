package kr.blug.tour.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CourseSpotDto {
	
	private Long course_spot_id;	
	private Long course_id;
	
	private String contentid;
	private String contenttypeid;
	private String title;
	private String addr1;
	private String titleImage;
	private String areacode;
	private String sigungucode;
	private String firstimage;
}
