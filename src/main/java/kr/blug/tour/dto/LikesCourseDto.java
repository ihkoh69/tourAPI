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
public class LikesCourseDto {

	private Long likes_course_id;
	private Long course_id;
	private String course_name;
	private String schedule;          // 일정 
	private String transportation;    // 교통
	private String budget;            // 예산 
	private String lodging;           // 숙박 
	private String description;       // 메모 
	private Long creator_user_id;
	private String creator_nickname;
	private Long user_id;
	private String user_nickname;
	private String areacode;
	private String sigungucode;
	private Long likes_count;
	private LocalDateTime crdttm;
	
}
