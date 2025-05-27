package kr.blug.tour.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LikesCourseCheckDto {
	
	private Long course_id;
	private Long likes_count;
	
	private Long user_id;
	private boolean my_check;


}
