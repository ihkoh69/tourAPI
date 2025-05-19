package kr.blug.tour.dto;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import kr.blug.tour.entity.CourseEntity;
import kr.blug.tour.entity.RemarksCourseEntity;
import kr.blug.tour.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

public class RemarksCourseDto { 
	
	//댓글 서비스 Request을 위한 DTO
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="remarks_course",
uniqueConstraints = {
		  @UniqueConstraint(columnNames = {"course_id", "user_id"})
		}
)
	public static class Request {

		private Long remarks_course_id;
		private String remarks_course;
		private LocalDateTime crdttm;
		private UserEntity user;
		private CourseEntity course;
		
//		private Long user_id;
//		private String nickname;
//		
//		private Long course_id;
//		private String course_name;
//		private String areacode;
//		private String sigungucode;	
//		private Long writer_user_id;	
//		private String writer_nickname;

		//Dto to toEntity
		public RemarksCourseEntity toEntity() {
			RemarksCourseEntity comments = RemarksCourseEntity.builder()
					.remarksCourseId(remarks_course_id)
					.remarksCourse(remarks_course)
					.user(user)
					.course(course)
					.build();
			
			return comments;
		}
	}

	//댓글 정보를 리턴할 Response
	//Entity 클래스를 생성자 파라미터로 받아 데이터를 Dto로 변환하여 응답
	//별도 전달객체를 활용해 연관관계를 맺은 엔티티간의 무한참조 방지
	@RequiredArgsConstructor
	@Getter
	public static class Response {

		private Long remarks_course_id;
		private String remarks_course;
		private LocalDateTime crdttm;
		private String nickname;
		private Long user_id;
		private Long course_id;
		private String course_name;
		private String areacode;
		private String sigungucode;	
		private Long writer_user_id;	
		private String writer_nickname;
		
		public Response (RemarksCourseEntity comment) {
			this.remarks_course_id = comment.getRemarksCourseId();
			this.remarks_course= comment.getRemarksCourse();
			this.nickname = comment.getUser().getNickname();
			this.user_id = comment.getUser().getUserId();
			this.course_id = comment.getCourse().getCourseId();
			this.course_name =comment.getCourse().getCourseName();
			this.areacode = comment.getCourse().getAreaCode();
			this.sigungucode = comment.getCourse().getSigunguCode();
//			this.writer_nickname = comment.getCourse()
			
		}
	}
}
