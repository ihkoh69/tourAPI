package kr.blug.tour.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.blug.tour.dto.RemarksCourseDto;
import kr.blug.tour.entity.RemarksCourseEntity;
import kr.blug.tour.repository.CourseRepository;
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
	
//	@Transactional(readOnly = true)
//	public List<RemarksCourseDto.Response> getRemarksByUserAndCourse(Long userId, Long courseId) {
//        List<RemarksCourseEntity> entities = remarksCourseRepository.findByUserUserIdAndCourseCourseId(userId, courseId);
//        return entities.stream()
//                .map(RemarksCourseDto.Response::new)
//                .collect(Collectors.toList());
//    }

	
	
//	public Optional<RemarksCourseDto> findByUserAndCourseId(Long userId, Long courseId){
//		
//		return remarksCourseRepository.findByUser_UserIdAndCourse_CourseId(userId, courseId)
//				.map(myRemarksCourse ->{
//					RemarksCourseDto dto = new RemarksCourseDto();
//					
////					dto.
//					
//					return dto;			
//				});		
//	}

	public Page<RemarksCourseDto> listRemarksCourseAll(Pageable pageable, Long userId, String nickname) {
		// TODO Auto-generated method stub
		return null;
	}


}
