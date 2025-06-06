package kr.blug.tour.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="remarks_course")
public class RemarksCourseEntity {

		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Long remarksCourseId;
		
		private LocalDateTime crdttm; //작성시 또는 수정시
		
		@Column(columnDefinition = "TEXT", nullable=false, length=1000)
		private String comment; //댓글내용

		//RemarksCourse vs UserEntity = N:1
		@ManyToOne(fetch = FetchType.LAZY)   
		@JoinColumn(name="user_id", nullable = false)
		private UserEntity user;

		//RemarksCourse vs MyCourse = N:1
		@ManyToOne(fetch = FetchType.LAZY)
		@JoinColumn(name="course_id", nullable = false)
		private CourseEntity course;
		
	 }
