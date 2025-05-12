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
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="remarks_content")
public class RemarksContentEntity {

		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Long remarksContentId;
		
		private String contentId;
		private String contentTypeId;
		private String title;
		private String addr;
		private String areaCode;
		private String sigunguCode;
		private String firstimage;
		
		@Column(length=1000)
		private String remarksContent;;
		private LocalDateTime crdttm;

		//RemarksContent vs UserEntity = N:1
		@ManyToOne(fetch = FetchType.LAZY)
		@JoinColumn(name="user_id")
		private UserEntity user;

 }

