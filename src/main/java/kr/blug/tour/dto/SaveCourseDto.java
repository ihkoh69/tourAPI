package kr.blug.tour.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaveCourseDto {

	private Long course_id;
	private Long creator_user_id;	
	private String creator_nickname;
	private String course_name;
	private String description;
	private Long shared_count;
	private String areacode;
	private String sigungucode;
	private List<CourseSpotDto> contents;
	private LocalDateTime updttm;
	

}
