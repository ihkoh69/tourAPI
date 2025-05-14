package kr.blug.tour.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RemarksCourseDto {

	private Long remarks_course_id;
	private Long user_id;
	@Column(length=1000)
	private String remarks_course;
	
	private Long course_id;
	private String course_name;


}
