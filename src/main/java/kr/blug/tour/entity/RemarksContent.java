package kr.blug.tour.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
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
public class RemarksContent {

		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Long remarksId;
		
		private String contentId;
		private String content;
		private LocalDateTime crdttm;

		@ManyToOne(fetch = FetchType.LAZY)
		@JoinColumn(name="user_id")
		private UserEntity user;

 }
