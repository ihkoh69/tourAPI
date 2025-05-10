package kr.blug.tour.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.blug.tour.dto.CourseDto;
import kr.blug.tour.entity.CourseEntity;
import kr.blug.tour.repository.CourseRepository;

@Service
public class CourseService {
	
	@Autowired
	private CourseRepository courseRepository;

	public List<CourseDto> listCourses() {
		
		List<CourseEntity> courseList = courseRepository.findAll();

		List<CourseDto> items = new ArrayList<>();
		
		for(CourseEntity course : courseList) {
			CourseDto dto = new CourseDto();
			dto.setCourseId(course.getCourseId());
			dto.setUserId(course.getUser().getUserId());
			dto.setUserNickname(course.getUser().getNickname());
			dto.setCourseName(course.getCourseName());
			dto.setDescription(course.getDescription());
			dto.setAreaCode(course.getAreaCode());
			dto.setSigunguCode(course.getSigunguCode());
			
			items.add(dto);
		}
		
		return items;
	}

}
