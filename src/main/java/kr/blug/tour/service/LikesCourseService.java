package kr.blug.tour.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import kr.blug.tour.dto.LikesCourseDto;
import kr.blug.tour.entity.LikesCourseEntity;
import kr.blug.tour.repository.LikesCourseRepository;

@Service
public class LikesCourseService {

	@Autowired
	private LikesCourseRepository likesCourseRepository;

	public Optional<LikesCourseDto> findByUserAndCourseId(Long userId, Long courseId) {
		
		
		
		return likesCourseRepository.findByUser_UserIdAndCourse_CourseId(userId, courseId).map(myCourse->{
			LikesCourseDto dto = new LikesCourseDto();

			dto.setLikesCourseId(myCourse.getLikesCourseId());
			dto.setUserId(myCourse.getUser().getUserId());
			dto.setCourseId(myCourse.getCourse().getCourseId());
			dto.setAreaCode(myCourse.getAreaCode());
			dto.setSigunguCode(myCourse.getSigunguCode());
			dto.setGoodOrHate(myCourse.getGoodOrHate());
			
			return dto;
		});

		
//		if(myCourse) {
//				LikesCourseDto dto = new LikesCourseDto();
//				
//				dto.setLikesCourseId(myCourse.getLikesCourseId());
//				dto.setUserId(myCourse.getUser().getUserId());
//				dto.setCourseId(myCourse.getCourse().getCourseId());
//				dto.setAreaCode(myCourse.getAreaCode());
//				dto.setSigunguCode(myCourse.getSigunguCode());
//				dto.setGoodOrHate(myCourse.getGoodOrHate());
//				
//				return dto;
//			}
//			else {
//				return null;
//			}	

	}

	
	public Page<LikesCourseDto> listMyLikes(Long userId, Pageable pageable) {
		
		return likesCourseRepository.findAllByUser_UserId(userId, pageable).map(entity->{
			
			LikesCourseDto dto = new LikesCourseDto();
			
			dto.setLikesCourseId(entity.getLikesCourseId());
			dto.setUserId(entity.getUser().getUserId());
			dto.setCourseId(entity.getCourse().getCourseId());
			dto.setAreaCode(entity.getAreaCode());
			dto.setSigunguCode(entity.getSigunguCode());
			dto.setGoodOrHate(entity.getGoodOrHate());
			
			return dto;
		});
		
//		if(lists.isPresent()) {
//			for()
//		
//		}
//		else {
//		}
//		
//
//				LikesCourseDto dto = new LikesCourseDto();
//				
//				dto.setLikesCourseId(entity.getLikesCourseId());
//				dto.setUserId(entity.getUser().getUserId());
//				dto.setCourseId(entity.getCourse().getCourseId());
//				dto.setAreaCode(entity.getAreaCode());
//				dto.setSigunguCode(entity.getSigunguCode());
//				dto.setGoodOrHate(entity.getGoodOrHate());
//				
//				return dto;
//			
//			});
		

			
	}
		
	
}
