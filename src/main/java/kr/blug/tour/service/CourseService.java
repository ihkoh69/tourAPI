package kr.blug.tour.service;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import kr.blug.tour.dto.CourseDto;
import kr.blug.tour.dto.CourseSpotDto;
import kr.blug.tour.entity.CourseEntity;
import kr.blug.tour.entity.CourseSpotEntity;
import kr.blug.tour.repository.CourseRepository;

@Service
public class CourseService {
	
	@Autowired
	private CourseRepository courseRepository;

	public Page<CourseDto> listCourses(Long user_id, Pageable pageable) {
		
		Page<CourseEntity> courseList ;
		
		if(user_id == null) {		
			courseList = courseRepository.findAll(pageable);
		} 
		else {
			courseList = courseRepository.findAllByUser_UserIdOrderByUpdttmDesc(user_id, pageable);
		}

		return courseList.map(course->{
			
			CourseDto dto = new CourseDto();
			dto.setCourse_id(course.getCourseId());
			dto.setWriter_user_id(course.getUser().getUserId());
			dto.setWriter_nickname(course.getUser().getNickname());
			dto.setCourse_name(course.getCourseName());
			dto.setDescription(course.getDescription());
			dto.setAreacode(course.getAreaCode());
			dto.setSigungucode(course.getSigunguCode());
			dto.setShared_count(course.getSharedCount());
			
			
//			dto.setSpots(
//				    course.getCourseSpots().stream()
//				        .map(spot -> new CourseSpotDto(
//				        		
//				            spot.getCourseSpotId(),
//				            spot.getCourse().getCourseId(),
//				            spot.getContents().getContentId(),
//				            spot.getContents().getContentTypeId(),
//				            spot.getContents().getTitle(),
//				            spot.getContents().getAddr(),
//				            spot.getContents().getFirstimage(),
//				            spot.getContents().getAreaCode(),
//				            spot.getContents().getSigunguCode()
//				           	)	            
//				            
//				         )
//				        .collect(Collectors.toList())
//				);
//			dto.setSpots(
//				    course.getCourseSpots().stream()
//				        .map(spot -> new CourseSpotDto(
//				        		
//				            spot.getCourseSpotId(),
//				            spot.getCourse().getCourseId(),
//				            spot.getContents().getContentId(),
//				            spot.getContents().getContentTypeId(),
//				            spot.getContents().getTitle(),
//				            spot.getContents().getAddr(),
//				            spot.getContents().getFirstimage(),
//				            spot.getContents().getAreaCode(),
//				            spot.getContents().getSigunguCode()
//				           	)	            
//				            
//				         )
//				        .collect(Collectors.toList())
//				);
			
			

			
			
			
			return dto;
		});
		

	}

}
