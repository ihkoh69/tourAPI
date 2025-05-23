package kr.blug.tour.dto;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import kr.blug.tour.entity.CourseEntity;
import kr.blug.tour.entity.RemarksCourseEntity;
import kr.blug.tour.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class RemarksCourseDto { 
	
		private Long remarks_course_id;
		private Long course_id;
		private Long user_id;
		private String user_nickname;		
		private String comment;
		private LocalDateTime crdttm;

}
