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
			dto.setCourse_id(course.getCourseId());
			dto.setUser_id(course.getUser().getUserId());
			dto.setUser_nickname(course.getUser().getNickname());
			dto.setCourse_name(course.getCourseName());
			dto.setDescription(course.getDescription());
			dto.setAreacode(course.getAreaCode());
			dto.setSigungucode(course.getSigunguCode());
			
			return dto;
		});
		

	}

}
