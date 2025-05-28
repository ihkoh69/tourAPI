package kr.blug.tour.service;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import kr.blug.tour.config.WebConfig;
import kr.blug.tour.controller.UserController;
import kr.blug.tour.dto.CourseDto;
import kr.blug.tour.dto.CourseSpotDto;
import kr.blug.tour.dto.CourseUpdateDto;
import kr.blug.tour.dto.SaveContentDto;
import kr.blug.tour.dto.SaveCourseDto;
import kr.blug.tour.dto.SaveResponseDto;
import kr.blug.tour.entity.ContentsEntity;
import kr.blug.tour.entity.CourseEntity;
import kr.blug.tour.entity.CourseSpotEntity;
import kr.blug.tour.entity.UserEntity;
import kr.blug.tour.repository.ContentsRepository;
import kr.blug.tour.repository.CourseRepository;
import kr.blug.tour.repository.CourseSpotRepository;
import kr.blug.tour.repository.LikesCourseRepository;
import kr.blug.tour.repository.UserRepository;

@Service
public class CourseService {

    private final UserController userController;

    private final WebConfig webConfig;
	
	@Autowired
	private CourseRepository courseRepository;
	
	@Autowired
	private CourseSpotRepository courseSpotRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ContentsRepository contentsRepository;
	
	@Autowired
	private LikesCourseRepository likesCourseRepository;

    CourseService(WebConfig webConfig, UserController userController) {
        this.webConfig = webConfig;
        this.userController = userController;
    }

    
    
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
			dto.setCreator_user_id(course.getUser().getUserId());
			dto.setCreator_nickname(course.getUser().getNickname());
			dto.setCourse_name(course.getCourseName());
			dto.setDescription(course.getDescription());
			dto.setAreacode(course.getAreaCode());
			dto.setSigungucode(course.getSigunguCode());			


			// 여행코스에서 계획한 방문 지점들의 contents 정보를 불러온다 
			
			List<CourseSpotDto> spotlists = new ArrayList<>();
			
			for(CourseSpotEntity spotEntity : course.getCourseSpots()) {
				CourseSpotDto spot = new CourseSpotDto();

				spot.setCourse_spot_id(spotEntity.getCourseSpotId());
				
				spot.setContentid(spotEntity.getContents().getContentId());
				spot.setContenttypeid(spotEntity.getContents().getContentTypeId());
				spot.setTitle(spotEntity.getContents().getTitle());
				spot.setAddr1(spotEntity.getContents().getAddr());
				spot.setAreacode(spotEntity.getContents().getAreaCode());
				spot.setSigungucode(spotEntity.getContents().getSigunguCode());
				spot.setFirstimage(spotEntity.getContents().getFirstimage());

				spotlists.add(spot);
				System.out.println(spotEntity.getContents().getTitle());

			}
				
			System.out.println(spotlists.toString());
			dto.setContents(spotlists);
			
			
			dto.setShared_count(course.getSharedCount());
			
			return dto;
		});
		

	}

	public SaveResponseDto saveCourse(SaveCourseDto dto) {
		// TODO Auto-generated method stub
		// 1. 유효한  user_id 인지 여
		boolean isExists = userRepository.existsByUserId(dto.getCreator_user_id());
		
		
		Optional<UserEntity> user = userRepository.findByUserId(dto.getCreator_user_id());
		
		if(user.isEmpty()) {
			Long likesCount = likesCourseRepository.countByCourse_CourseId(dto.getCourse_id());	
			return new SaveResponseDto(false, "invalid user_id", likesCount);
		}
		
		//2. Contents 들이 존재하는지 검사, 없다면 새로 저장
		
		List<SaveContentDto> contents = dto.getContents();
		
		
		String areaCode = null;
		String sigunguCode = null;
		
		for(SaveContentDto spot : contents) {
			//2.1 컨텐츠 존재여부 검사
			isExists = contentsRepository.existsByContentId(spot.getContentid());
			
			// 전달 받은 제일 마지막 컨텐츠의 지역코드를 대표지역으로 세팅한다.			
			areaCode = spot.getAreacode();
			sigunguCode = spot.getSigungucode();
			
			
			// 컨텐츠가 DB에 저장된 적이 없다면 저장을 진행 
			if(!isExists) {
				
				ContentsEntity contentEntity = new ContentsEntity();
				
				contentEntity.setContentId(spot.getContentid());
				contentEntity.setContentTypeId(spot.getContenttypeid());
				contentEntity.setTitle(spot.getTitle());
				contentEntity.setAddr(spot.getAddr1());
				contentEntity.setAreaCode(spot.getAreacode());
				contentEntity.setSigunguCode(spot.getSigungucode());
				contentEntity.setFirstimage(spot.getFirstimage());
				contentEntity.setCrdttm(LocalDateTime.now());
				
				// 컨텐츠 일반 정보 저장 
				contentsRepository.save(contentEntity);
				System.out.println("여행지 정보 " + spot.getContentid() + "를 저장했습니다.");
			}
			else {
			// 존재하는 컨텐츠는 skip
				System.out.println("여행지 정보 " + spot.getContentid() + "는 이미 존재합니다.");
			}
		}
		
		// 3. Course, CourseSpot 정보 저장
		

		
		CourseEntity course = new CourseEntity();

		
		course.setUser(user.get());
		course.setCourseName(dto.getCourse_name());
		course.setDescription(dto.getDescription());
		course.setSharedCount(0L);
		
		course.setAreaCode(areaCode);
		course.setSigunguCode(sigunguCode);
		course.setUpdttm(LocalDateTime.now());

		// 3-1 Course 마스터 정보를 저장 
		CourseEntity savedCourse = courseRepository.save(course);
		System.out.println("여행코스 정보 마스터  " + savedCourse.getCourseId() + "를 생성했습니다.");
		
		// 2단계에서 만들어둔 contents 객체 재사용
		
		for(SaveContentDto spot : contents) {
			CourseSpotEntity entity = new CourseSpotEntity();
			
//			// 영속성 판단 로직의 차이로 에러 발생함.
//			ContentsEntity content  = new ContentsEntity();
//			content.setContentId(spot.getContentid());
			
			ContentsEntity content = contentsRepository.findByContentId(spot.getContentid()).orElseGet(()->{
				return null;
			});
			
					
			entity.setCourse(savedCourse);
			// 미사용  priorityNo;
			entity.setContents(content);
			entity.setCrdttm(LocalDateTime.now());
			
			CourseSpotEntity savedSpot = courseSpotRepository.save(entity);
			
			System.out.println(" 여행코스 세부방문지점 정보 " + savedSpot.getCourseSpotId()+ "를 저장했습니다.");
			
		}
		
		Long likesCount = likesCourseRepository.countByCourse_CourseId(dto.getCourse_id());	
		return new SaveResponseDto(true, "saved", "course_id", savedCourse.getCourseId(), likesCount);
	}

	public SaveResponseDto deleteCourse(Long courseId) {
		
		Optional<CourseEntity> course = courseRepository.findById(courseId);
		
		
		
		if(course.isPresent()) {
			
			// 1. 먼저 자식 레코드 부터 삭제한다. 
			List<CourseSpotEntity> spotLists = courseSpotRepository.findALLByCourse_CourseId(course.get().getCourseId());
			
			if(spotLists != null) {
				for(CourseSpotEntity spot : spotLists) {
					courseSpotRepository.deleteById(spot.getCourseSpotId());
					System.out.println("여행코스 상세방문지정보 " + spot.getCourseSpotId() + "를 삭제합니다. ");
					
				}
			}
			
			courseRepository.deleteById(courseId);
			System.out.println("여행코스 정보 마스터 " + courseId + "를 삭제합니다. ");
			Long likesCount = likesCourseRepository.countByCourse_CourseId(courseId);	
			return new SaveResponseDto(true, "deleted", "course_id", courseId, likesCount);
			
		}
		else {
			Long likesCount = likesCourseRepository.countByCourse_CourseId(courseId);	
			return new SaveResponseDto(false, "not_found", likesCount);
		}
		
	}


	public SaveResponseDto updateCourse(Long courseId, CourseUpdateDto dto) {
		
		//1. course_id가 유효한지 검사
		Optional<CourseEntity> rec = courseRepository.findById(courseId);
		if(rec.isEmpty()) {
			return new SaveResponseDto(false, "DB에 course_id에 해당하는 자료가 없습니다.");
		}
		
		CourseEntity target = rec.get();
		
		//2. 마스터 레코드의 수정 부분이 있다면 반영
		if(dto.getCourse_name() != null) {
			target.setCourseName(dto.getCourse_name());			
		}
		if(dto.getDescription() != null) {
			target.setDescription(dto.getDescription());
		}
			
		CourseEntity updatedCourse = courseRepository.save(target);
		
		
		//3. no_spot_update값이 false이면 자식 레코드(course_spot)들을 모두 삭제 후 재등록한다.		
		if(dto.isContents_update() == true) {
			System.out.println("*************************************** XXXXXXXXXXX");

			courseSpotRepository.deleteAllByCourse_CourseId(courseId);
			
			for(SaveContentDto spot : dto.getContents()) {
				
				CourseSpotEntity courseSpot = new CourseSpotEntity();
				courseSpot.setCourse(updatedCourse);
				
				
				//3-A DB에 해당 컨텐츠가 없다면 저장해 준다.				
				Optional<ContentsEntity> content = contentsRepository.findByContentId(spot.getContentid());
				if(content.isEmpty()) {
					
					ContentsEntity newContent = new ContentsEntity();
					
					newContent.setContentId(spot.getContentid());
					newContent.setContentTypeId(spot.getContenttypeid());
					newContent.setTitle(spot.getTitle());
					newContent.setAddr(spot.getAddr1());
					newContent.setAreaCode(spot.getAreacode());
					newContent.setSigunguCode(spot.getSigungucode());
					newContent.setFirstimage(spot.getFirstimage());
					
					ContentsEntity newOne = contentsRepository.save(newContent);
					
					System.out.println("새로운 content 등록" + newOne.getContentsRowId() + " " + newOne.getContentId());
					
					courseSpot.setContents(newOne);
				}
				else { //3-B 기존에 저장되어 있던 컨텐츠 
					courseSpot.setContents(content.get());
				}
				
				//새로운 CourseSpot 레코드생성시간을 포함하여 DB에 저장한다.
				courseSpot.setCrdttm(LocalDateTime.now());
				CourseSpotEntity newSpot = courseSpotRepository.save(courseSpot);
				
				System.out.println("새로운 courseSpot 자료 저장" + newSpot.getCourseSpotId());
			}

		}
		
		return new SaveResponseDto(true, "updated", "course_id", updatedCourse.getCourseId());
		
	}

}
