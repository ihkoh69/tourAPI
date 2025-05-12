package kr.blug.tour.entity;

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
	
	private String contentId;
	private String contentTypeId;
	private String title;
	
	@Column(length=300)
	private String addr;
	
	@Column(length=300)
	private String titleImage;
	
	@Column(length=2000)
	private String subImages;
	private String cat3;
	private String areaCode;
	private String sigunguCode;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="course_id")
	private CourseEntity course;
//	private Long courseId;
	
	

}
