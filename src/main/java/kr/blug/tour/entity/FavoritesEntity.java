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
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="favorites",
			  uniqueConstraints = {
					  @UniqueConstraint(columnNames = {"user_id", "content_id"})
			  }

		)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FavoritesEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long favoritesId;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name= "content_id",  referencedColumnName = "content_id", nullable = false)
	private ContentsEntity contents;
	//private String contentId;
	
//	@Column(name="content_id", nullable = false)
//	private String contentId;
//	
//	private String contentTypeId;
//	private String title;
//	private String addr;
//	private String areaCode;
//	private String sigunguCode;
//	private String firstimage;
	
	
	private LocalDateTime crdttm;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="user_id")
	private UserEntity user;
	//private Long userId;
	
	
	
}
