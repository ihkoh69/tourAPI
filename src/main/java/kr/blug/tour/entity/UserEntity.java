package kr.blug.tour.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name="user",
			 uniqueConstraints = {
					 @UniqueConstraint(columnNames = "email"),
					 @UniqueConstraint(columnNames = "nickname")
			 }
		)
public class UserEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;
	
	private String email;
	private String password;
	private String nickname;
	private String profileImg;
	private String authGrade;
	private LocalDateTime crdttm;
	private LocalDateTime updttm;
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<CourseEntity> courses = new ArrayList<>();
	

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<RemarksContentEntity> remarksContent = new ArrayList<>();
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<RemarksCourseEntity> remarksCourse = new ArrayList<>();

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL,orphanRemoval = true)
	private List<FavoritesEntity> favorites = new ArrayList<>();

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL,orphanRemoval = true)
	private List<LikesCourseEntity> likesCourse = new ArrayList<>();
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<LikesContentEntity> likesContent = new ArrayList<>();
	
}
