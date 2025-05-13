package kr.blug.tour.entity;

import java.time.LocalDateTime;
import java.util.List;

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
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="likes_content",
			  uniqueConstraints = {
					  @UniqueConstraint(columnNames = {"content_id", "user_id"})
			  }			  
		)
public class LikesContentEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long likesContentId;
		
	private LocalDateTime crdttm;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name= "content_id",  referencedColumnName = "content_id", nullable = false)
	private ContentsEntity contents;
	//private String contentId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="user_id", nullable = false)
	private UserEntity user;
//	private Long userId;
	
}




//contentsEntity(contents테이블로 분할)

//	@Column(name="content_type_id", nullable = false)
//	private String contentTypeId;
//	
//	@Column(name="title", nullable = false)
//	private String title;
//	
//	@Column(name="addr", nullable = false)
//	private String addr;
//	
//	@Column(name="area_code", nullable = false)
//	private String areaCode;
//	private String sigunguCode;
//	private String firstimage;
