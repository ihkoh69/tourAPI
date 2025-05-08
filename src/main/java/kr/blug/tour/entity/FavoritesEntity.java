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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="favorites")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FavoritesEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long favoritesId;
	
	private String contentId;
	private String ContentTypeId;
	private String title;
	private String addr;
	private String areaCode;
	private String sigunguCode;
	private String firstimage;
	private LocalDateTime crdttm;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="user_id")
	private UserEntity user;
	//private Long userId;
	
	
	
}
