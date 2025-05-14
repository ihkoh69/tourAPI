package kr.blug.tour.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LikesCourseDto {

	private Long likes_course_id;
	private Long user_id;
	private Long course_id;
	private String course_name;
	private String course_description;
	private String areacode;
	private String sigungucode;
	private Long likes_count;
	private LocalDateTime crdttm;
	
}
