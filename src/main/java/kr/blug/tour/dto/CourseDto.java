package kr.blug.tour.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseDto {
	
	private Long courseId;
	private Long userId;	
	private String userNickname;
	private String courseName;
	private String description;
	private String areaCode;
	private String sigunguCode;
	private LocalDateTime crdttm;
	private LocalDateTime updttm;
	
	
}
