package kr.blug.tour.service;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import kr.blug.tour.dto.CourseDto;
import kr.blug.tour.entity.CourseEntity;
import kr.blug.tour.repository.CourseRepository;

@Service
public class CourseService {
	
	@Autowired
	private CourseRepository courseRepository;

	public Page<CourseDto> listCourses(Pageable pageable) {
		
		Page<CourseEntity> courseList = courseRepository.findAll(pageable);

		return courseList.map(course->{
			
			CourseDto dto = new CourseDto();
			dto.setCourseId(course.getCourseId());
			dto.setUserId(course.getUser().getUserId());
			dto.setUserNickname(course.getUser().getNickname());
			dto.setCourseName(course.getCourseName());
			dto.setDescription(course.getDescription());
			dto.setAreaCode(course.getAreaCode());
			dto.setSigunguCode(course.getSigunguCode());
			
			return dto;
		});
		

	}

}
