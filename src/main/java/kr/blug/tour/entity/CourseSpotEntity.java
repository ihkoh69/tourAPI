package kr.blug.tour.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="course_spot")
public class CourseSpotEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long courseSpotId;
	
	@ManyToOne(fetch=FetchType.LAZY)	
	@JoinColumn(name="content_id", referencedColumnName = "content_id")
	private ContentsEntity contents;	
	//private String contentId;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="course_id")
	private CourseEntity course;
	//	private Long courseId;
	
	
	private LocalDateTime crdttm;

}
