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
@Table(name="remarks_content")
public class RemarksContentEntity {

		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Long remarksContentId;
		
		private LocalDateTime crdttm;
		
		@Column(columnDefinition = "TEXT", nullable=false, length=1000)
		private String comment; //댓글내용

		//RemarksContent vs UserEntity = N:1
		@ManyToOne(fetch = FetchType.LAZY)
		@JoinColumn(name="user_id", nullable = false)
		private UserEntity user;

		//RemarksContent vs ApiContents = N:1
		@ManyToOne(fetch = FetchType.LAZY)
		@JoinColumn(name="content_id", referencedColumnName = "content_id", nullable = false)
		private ContentsEntity contents;

}


