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
import kr.blug.tour.dto.SaveResponseDto;
import kr.blug.tour.entity.ContentsEntity;
import kr.blug.tour.entity.CourseEntity;
import kr.blug.tour.entity.LikesContentEntity;
import kr.blug.tour.entity.LikesCourseEntity;
import kr.blug.tour.entity.UserEntity;
import kr.blug.tour.repository.CourseRepository;
import kr.blug.tour.repository.LikesCourseRepository;
import kr.blug.tour.repository.ProjectionLikesCourseCount;
import kr.blug.tour.repository.UserRepository;

@Service
public class LikesCourseService {

	@Autowired
	private LikesCourseRepository likesCourseRepository;
	
	@Autowired
	private CourseRepository courseRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	public Optional<LikesCourseDto> findByUserAndCourseId(Long userId, Long courseId) {
		
		
		return likesCourseRepository.findByUser_UserIdAndCourse_CourseIdOrderByCrdttmDesc(userId, courseId).map(myCourse->{
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
			dto.setCreator_user_id(entity.getCourse().getUser().getUserId());     // course를 작성한 유저  
			dto.setCreator_nickname(entity.getCourse().getUser().getNickname());  // course를 작성한 유저  
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


	public Page<LikesCourseDto> listLikesCourseAll(Pageable pageable, String areaCode, String sigunguCode, Long userId, Long creatorUserId) {
		
		
		
		// userId : 로그인한 사용자 id
		// writeUserId : 여행코스를 만든 사용자id
		
		Page<ProjectionLikesCourseCount> page = likesCourseRepository.listCoursesOrderByLikesCountDesc(pageable, areaCode, sigunguCode, userId, creatorUserId);
		
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
				dto.setCreator_user_id(course.getWriterUserId());
				dto.setCreator_nickname(course.getWriterNickname());
				dto.setAreacode(course.getAreaCode());
				dto.setSigungucode(course.getSigunguCode());
				dto.setLikes_count(course.getLikesCount());
				
	
	//			private LocalDateTime crdttm;
				
				
				return dto;
			}
		);

		
		return dtoPage;
	}


	public SaveResponseDto saveLikesCourse(Long userId, Long courseId) {

		// 1. 이미 좋아요 표시한 내용이 있는지 검사 user_id, contentid
		boolean isExists = likesCourseRepository.existsByUser_UserIdAndCourse_CourseId(userId, courseId);
		
		if(isExists) {
			return new SaveResponseDto(false, "record already exists", null, null);
		}
		
		// 2. 유효한(존재하는) 사용자인지 확인 user_id
		
		Optional<UserEntity> optionalUser = userRepository.findById(userId);
		if(optionalUser.isEmpty()) {
			return new SaveResponseDto(false, "user not exists", null, null);
		}
		UserEntity user = optionalUser.get();
		
		// 3. 저장되어 있는 여행코스 존재 여부 확인, 미존재시 에러반환
		Optional<CourseEntity> optionalCourse = courseRepository.findById(courseId);
		if(optionalCourse.isEmpty()) {
			return new SaveResponseDto(false, "course not exists", null, null);
		}
		CourseEntity course = optionalCourse.get();
		
		// 4. likesContent에 좋아요 데이터 저장	

		
		LikesCourseEntity likesCourseEntity = new LikesCourseEntity();
		likesCourseEntity.setUser(user);;
		likesCourseEntity.setCourse(course);
		likesCourseEntity.setCrdttm(LocalDateTime.now());
		
		LikesCourseEntity saved = likesCourseRepository.save(likesCourseEntity);
		
		return new SaveResponseDto(true, "saved", "likes_course_id", saved.getLikesCourseId());

		
	}

	public SaveResponseDto deleteByUserIdAndCourseId(Long userId, Long courseId) {
		
		LikesCourseEntity entity = likesCourseRepository.findByUser_UserIdAndCourse_CourseId(userId, courseId);
		if(entity != null) {
			Long likesCourseId = entity.getLikesCourseId();
			
			likesCourseRepository.delete(entity);
			return new SaveResponseDto(true, "deleted", "likes_course_id", likesCourseId);
		}
		else
		{
			return new SaveResponseDto(false, "not_found", null, null);
		}

	}
		
	
}
