package kr.blug.tour.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="remarks_course",
uniqueConstraints = {
		  @UniqueConstraint(columnNames = {"course_id", "user_id"})
		}
)
public class RemarksCourseDto {

	private Long remarks_course_id;
	private Long user_id;

	private String remarks_course;
	
	private Long course_id;
	private String course_name;


}
