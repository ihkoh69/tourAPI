package kr.blug.tour.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.blug.tour.dto.LikesContentDto;
import kr.blug.tour.dto.RemarksCourseDto;
import kr.blug.tour.dto.SaveResponseDto;
import kr.blug.tour.entity.CourseEntity;
import kr.blug.tour.entity.RemarksCourseEntity;
import kr.blug.tour.entity.UserEntity;
import kr.blug.tour.repository.CourseRepository;
import kr.blug.tour.repository.ProjectionLikesCotentCount;
import kr.blug.tour.repository.ProjectionRemarksCourse;
import kr.blug.tour.repository.RemarksCourseRepository;
import kr.blug.tour.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RemarksCourseService {

	@Autowired
	private RemarksCourseRepository remarksCourseRepository;
	
	@Autowired
	private CourseRepository courseRepository;
	
	@Autowired
	private UserRepository userRepository;

	public Page<RemarksCourseDto> listRemarksCourse(Pageable pageable, Long userId, Long courseId) {
		

		Page<ProjectionRemarksCourse> page = remarksCourseRepository.listRemarksCourseOrderByCrdttmDesc(pageable, userId, courseId);
		
		page.getContent().forEach(item -> {
		    System.out.println(item.getUserNickname() + " : " + item.getComment());
		});	
		
		Page<RemarksCourseDto> dtoPage = page.map(item -> {
				RemarksCourseDto dto = new RemarksCourseDto();
				
				dto.setRemarks_course_id(item.getRemarksCourseId());
				dto.setCourse_id(item.getCourseId());
				dto.setUser_id(item.getUserId());
				dto.setUser_nickname(item.getUserNickname());
				dto.setComment(item.getComment());
				dto.setCrdttm(item.getCrdttm());
				
				return dto;
			}
		);
		
		return dtoPage;

		
	}

	public SaveResponseDto saveRemarksCourse(RemarksCourseDto dto) {

		//1. 유저가 존재하는지 검사한다.
		Optional<UserEntity>  user = userRepository.findById(dto.getUser_id());
		if(user.isEmpty()) {
			return new SaveResponseDto(false, "invalid user_id",null, null);	
		}
		
		//2. 여행코스가 유효한지 검사한다.
		Optional<CourseEntity> course = courseRepository.findById(dto.getCourse_id());
		if(course.isEmpty() ) {
			return new SaveResponseDto(false, "invalid course_id",null, null);				
		}
		
		RemarksCourseEntity remark = new RemarksCourseEntity();
		
		remark.setUser(user.get());
		remark.setCourse(course.get());
		remark.setComment(dto.getComment());
		remark.setCrdttm(LocalDateTime.now());
		
		RemarksCourseEntity savedRemark = remarksCourseRepository.save(remark);		
		
		return new SaveResponseDto(true, "saved", "remarks_course_id", savedRemark.getRemarksCourseId());	
	}

	public SaveResponseDto deleteRemarksCourse(Long remarksCourseId) {
		
		Optional<RemarksCourseEntity> remark = remarksCourseRepository.findById(remarksCourseId);
		if(remark.isEmpty()) return new SaveResponseDto(false, "not_found", null, null);
		
		remarksCourseRepository.delete(remark.get());
		return new SaveResponseDto(true, "deleted", "remarks_course_id", remark.get().getRemarksCourseId());
	}
	
}
