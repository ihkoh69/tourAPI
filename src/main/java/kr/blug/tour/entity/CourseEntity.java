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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="course")
public class CourseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long courseId;
	
	private String courseName;
	private String description;
	
	@Column(name="shared_count")
	private Long sharedCount = 0L;  // 공유된 횟수, Column 어노테이션에서는 default 값을 쓸 수 없다.
	private String areaCode;
	private String sigunguCode;
	private LocalDateTime updttm;


	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="user_id")
	private UserEntity user;
//	private Long userId;	
	
	@OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<CourseSpotEntity> courseSpots = new ArrayList<>();
	
	@OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<LikesCourseEntity> likesCourse = new ArrayList<>();

	@OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<RemarksCourseEntity> remarksCourse = new ArrayList<>();
	
}
