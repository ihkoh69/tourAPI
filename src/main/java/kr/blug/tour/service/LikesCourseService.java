package kr.blug.tour.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import kr.blug.tour.dto.LikesCourseCheckDto;
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
	
	public LikesCourseCheckDto findByUserAndCourseId(Long userId, Long courseId) {
		
		
		LikesCourseCheckDto dto = new LikesCourseCheckDto();
		
		Long likesCount = likesCourseRepository.countByCourse_CourseId(courseId);
		boolean my_check = likesCourseRepository.existsByUser_UserIdAndCourse_CourseId(userId, courseId);
		
		dto.setCourse_id(courseId);
		dto.setLikes_count(likesCount);
		
		dto.setUser_id(userId);
		dto.setMy_check(my_check);
		
			
		return dto;

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
			dto.setDescription(entity.getCourse().getDescription());
		    
		    dto.setSchedule(entity.getCourse().getSchedule());
		    dto.setTransportation(entity.getCourse().getTransportation());
		    dto.setBudget(entity.getCourse().getBudget());
		    dto.setLodging(entity.getCourse().getLodging());
		    dto.setDescription(entity.getCourse().getDescription());
			
			dto.setAreacode(entity.getCourse().getAreaCode());
			dto.setSigungucode(entity.getCourse().getSigunguCode());

			
			return dto;
		});
		
			
	}


	public Page<LikesCourseDto> listLikesCourseAll(Pageable pageable, String areaCode, String sigunguCode, Long userId, Long creatorUserId, Long courseId) {
		
		
		
		// userId : 로그인한 사용자 id
		// writeUserId : 여행코스를 만든 사용자id
		
		Page<ProjectionLikesCourseCount> page = likesCourseRepository.listCoursesOrderByLikesCountDesc(pageable, areaCode, sigunguCode, userId, creatorUserId, courseId);
		
		page.getContent().forEach(course -> {
		    System.out.println(course.getCourseName() + " / 좋아요 수: " + course.getLikesCount());
		});	
		

//		Page<LikesCourseDto> dtoPage = page.map(course -> new LikesCourseDto()
//		);
		
		Page<LikesCourseDto> dtoPage = page.map(course -> {
				LikesCourseDto dto = new LikesCourseDto();
				
				dto.setCourse_id(course.getCourseId());
				dto.setCourse_name(course.getCourseName());
				
				dto.setSchedule(course.getSchedule());
				dto.setTransportation(course.getTransportation());
				dto.setBudget(course.getBudget());
				dto.setLodging(course.getLodging());
				dto.setDescription(course.getDescription());
				
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

		
		
		if(userId == null) {
			return new SaveResponseDto(false, "유저id는 필수 파라미터입니다.");				
		}
		
		if(courseId == null) {
			return new SaveResponseDto(false, "코스id는 필수 파라미터입니다.");		
		}
				
		
		// 1. 이미 좋아요 표시한 내용이 있는지 검사 user_id, contentid
		boolean isExists = likesCourseRepository.existsByUser_UserIdAndCourse_CourseId(userId, courseId);
		
		if(isExists) {
			Long likesCount = likesCourseRepository.countByCourse_CourseId(courseId);
			return new SaveResponseDto(false, "record already exists", likesCount);
		}
		
		// 2. 유효한(존재하는) 사용자인지 확인 user_id
		
		Optional<UserEntity> optionalUser = userRepository.findById(userId);
		if(optionalUser.isEmpty()) {
			Long likesCount = likesCourseRepository.countByCourse_CourseId(courseId);
			return new SaveResponseDto(false, "user not exists", likesCount);
		}
		UserEntity user = optionalUser.get();
		
		// 3. 저장되어 있는 여행코스 존재 여부 확인, 미존재시 에러반환
		Optional<CourseEntity> optionalCourse = courseRepository.findById(courseId);
		if(optionalCourse.isEmpty()) {
			return new SaveResponseDto(false, "course not exists",null);
		}
		CourseEntity course = optionalCourse.get();
		
		// 4. likesContent에 좋아요 데이터 저장	

		
		LikesCourseEntity likesCourseEntity = new LikesCourseEntity();
		likesCourseEntity.setUser(user);;
		likesCourseEntity.setCourse(course);
		likesCourseEntity.setCrdttm(LocalDateTime.now());
		
		LikesCourseEntity saved = likesCourseRepository.save(likesCourseEntity);
		
		Long likesCount = likesCourseRepository.countByCourse_CourseId(courseId);		
		return new SaveResponseDto(true, "saved", "likes_course_id", saved.getLikesCourseId(), likesCount);

		
	}

	public SaveResponseDto deleteByUserIdAndCourseId(Long userId, Long courseId) {
		
		
		if(userId == null) {
			return new SaveResponseDto(false, "유저id는 필수 파라미터입니다.");				
		}
		
		if(courseId == null) {
			return new SaveResponseDto(false, "코스id는 필수 파라미터입니다.");				
		}
		
		
		LikesCourseEntity entity = likesCourseRepository.findByUser_UserIdAndCourse_CourseId(userId, courseId);
		if(entity != null) {
			Long likesCourseId = entity.getLikesCourseId();
			
			likesCourseRepository.delete(entity);
			
			Long likesCount = likesCourseRepository.countByCourse_CourseId(courseId);	
			return new SaveResponseDto(true, "deleted", "likes_course_id", likesCourseId, courseId);
		}
		else
		{
			Long likesCount = likesCourseRepository.countByCourse_CourseId(courseId);	
			return new SaveResponseDto(false, "not_found", courseId);
		}

	}


	public Long countlikesCourse(Long userId, Long courseId) {
		return likesCourseRepository.countByOptionalUserAndContent(userId, courseId);
	}
		
	
}
