package kr.blug.tour.entity;

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
	private Long course_spot_id;
	

	
	private String contentId;
	private String contentTypeId;
	private String title;
	private String addr;
	private String title_image;
	private String sub_images;
	private String cat3;
	private String areacode;
	private String sigungucode;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="course_id")
	private CourseEntity course;
//	private Long course_id;
	
	

}
