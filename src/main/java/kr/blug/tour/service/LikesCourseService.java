package kr.blug.tour.service;

import java.time.LocalDateTime;
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
import kr.blug.tour.repository.ProjectionLikesCourseCount;

@Service
public class LikesCourseService {

	@Autowired
	private LikesCourseRepository likesCourseRepository;

	public Optional<LikesCourseDto> findByUserAndCourseId(Long userId, Long courseId) {
		
		
		return likesCourseRepository.findByUser_UserIdAndCourse_CourseId(userId, courseId).map(myCourse->{
			LikesCourseDto dto = new LikesCourseDto();

//			dto.setLikes_count(likesCourseRepository.countNativeByuserIdAndCourseId(userId,courseId));
			dto.setLikes_count(likesCourseRepository.countByCourseId(courseId));
				
			dto.setLikes_course_id(myCourse.getLikesCourseId());
			dto.setUser_id(myCourse.getUser().getUserId());
			dto.setCourse_id(myCourse.getCourse().getCourseId());
			dto.setCourse_name(myCourse.getCourse().getCourseName());
			dto.setCourse_description(myCourse.getCourse().getDescription());
			dto.setAreacode(myCourse.getCourse().getAreaCode());
			dto.setSigungucode(myCourse.getCourse().getSigunguCode());

			
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

		// like 누적횟수로 정렬하고 싶었으나 Page 객체는 이미 정렬이 완료된 형태라 다시 정렬을 수행할 수 없음(비권장)
		// 따라서 정렬을 하려면 JPA가 쿼리를 하는 상황 또는 Pageable객체가 만들어지기 전에 해야함. 
		// 다만 JPA Repository Interface를 마음대로 조작하기 힘들어서(잘 몰라서) 
		// JPQL을 사용하여 필요한 정렬을 수행하는 것이 방법이 될 것으로 생각함 2025.05.14
		
		
		return likesCourseRepository.findAllByUser_UserId(userId, pageable).map(entity->{
			
			LikesCourseDto dto = new LikesCourseDto();
			
			dto.setLikes_course_id(entity.getLikesCourseId());
			dto.setUser_id(entity.getUser().getUserId());           //좋아요를 선택한 유저  
			dto.setUser_nickname(entity.getUser().getNickname()); //좋아요를 선택한 유저   
			dto.setWriter_userid(entity.getCourse().getUser().getUserId());     // course를 작성한 유저  
			dto.setWriter_nickname(entity.getCourse().getUser().getNickname());  // course를 작성한 유저  
			dto.setCourse_id(entity.getCourse().getCourseId());
			dto.setLikes_count(likesCourseRepository.countByCourseId(dto.getCourse_id()));
			
			dto.setCourse_name(entity.getCourse().getCourseName());
			dto.setCourse_description(entity.getCourse().getDescription());
			dto.setAreacode(entity.getCourse().getAreaCode());
			dto.setSigungucode(entity.getCourse().getSigunguCode());

			
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


	public Page<LikesCourseDto> listLikesCourseAll(Pageable pageable) {
		
		
		Page<ProjectionLikesCourseCount> page = likesCourseRepository.listCoursesOrderByLikesCountDesc(pageable);
		
		page.getContent().forEach(course -> {
		    System.out.println(course.getCourseName() + " / 좋아요 수: " + course.getLikesCount());
		});	
		

//		Page<LikesCourseDto> dtoPage = page.map(course -> new LikesCourseDto()
//		);
		
		Page<LikesCourseDto> dtoPage = page.map(course -> {
				LikesCourseDto dto = new LikesCourseDto();
				
				dto.setCourse_id(course.getCourseId());
				dto.setCourse_name(course.getCourseName());
				dto.setCourse_description(course.getDescription());
				dto.setWriter_userid(course.getWriterUserId());
				dto.setWriter_nickname(course.getWriterNickname());
				dto.setAreacode(course.getAreaCode());
				dto.setSigungucode(course.getSigunguCode());
				dto.setLikes_count(course.getLikesCount());
				
	
	//			private LocalDateTime crdttm;
				
				
				return dto;
			}
		);

		
		return dtoPage;
	}


		
	
}
