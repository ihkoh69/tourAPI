package kr.blug.tour.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseDto {
	
	private Long course_id;
	private Long user_id;	
	private String user_nickname;
	private String course_name;
	private String description;
	private Long shared_count;
	private String areacode;
	private String sigungucode;
	private LocalDateTime crdttm;
	private LocalDateTime updttm;
	
	
}
