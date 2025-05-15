package kr.blug.tour.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="contents", 
		     uniqueConstraints = {
		    		 @UniqueConstraint(columnNames = {"content_id"})
		     }
		)
@Data
@AllArgsConstructor
@NoArgsConstructor

public class ContentsEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long contentsRowId;

	
	@Column(name="content_id", nullable = false)
	private String contentId;
	private String contentTypeId;
	private String title;
	private String addr;
	private String areaCode;
	private String sigunguCode;
	
	@Column(length=1000)
	private String firstimage;
	private LocalDateTime crdttm;
	
		
	@OneToMany(mappedBy = "contents", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<LikesContentEntity> likesContents = new ArrayList<>();	
			
	@OneToMany(mappedBy = "contents", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<FavoritesEntity> favorites = new ArrayList<>();	
	
	@OneToMany(mappedBy = "contents", cascade = CascadeType.ALL,orphanRemoval = true)
	private List<CourseSpotEntity> courseSpot = new ArrayList<>();
}
