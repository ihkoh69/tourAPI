package kr.blug.tour.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

	@Entity
	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	@Table(name="likes_course",
						uniqueConstraints = {
								@UniqueConstraint(
										columnNames = {"course_id", "user_id"}
								)
						}
			    )
	public class LikesCourseEntity {
		
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Long likesCourseId;
		
		private String areaCode;
		private String sigunguCode;
		private String goodOrHate;
		private LocalDateTime crdttm;
		
		@ManyToOne(fetch = FetchType.LAZY)
		@JoinColumn(name="user_id", nullable = false)
		private UserEntity user;
//		private Long userId;
		
		
		@ManyToOne(fetch = FetchType.LAZY)
		@JoinColumn(name="course_id", nullable = false)
		private CourseEntity course;
//		private Long courseId;
		
		
		
}
