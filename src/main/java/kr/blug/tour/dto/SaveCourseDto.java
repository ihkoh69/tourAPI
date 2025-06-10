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
	private String schedule;          // 일정 
	private String transportation;    // 교통
	private String budget;            // 예산 
	private String lodging;           // 숙박 
	private String description;       // 메모 

	private Long shared_count;
	private String areacode;
	private String sigungucode;
	private List<SaveContentDto> contents;
	private LocalDateTime updttm;
	

}
