package kr.blug.tour.service;

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
import kr.blug.tour.entity.RemarksCourseEntity;
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
	
}
