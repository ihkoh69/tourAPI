package kr.blug.tour.service;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import kr.blug.tour.config.WebConfig;
import kr.blug.tour.dto.CourseDto;
import kr.blug.tour.dto.CourseSpotDto;
import kr.blug.tour.dto.SaveCourseDto;
import kr.blug.tour.dto.SaveResponseDto;
import kr.blug.tour.entity.ContentsEntity;
import kr.blug.tour.entity.CourseEntity;
import kr.blug.tour.entity.CourseSpotEntity;
import kr.blug.tour.entity.UserEntity;
import kr.blug.tour.repository.ContentsRepository;
import kr.blug.tour.repository.CourseRepository;
import kr.blug.tour.repository.CourseSpotRepository;
import kr.blug.tour.repository.UserRepository;

@Service
public class CourseService {

    private final WebConfig webConfig;
	
	@Autowired
	private CourseRepository courseRepository;
	
	@Autowired
	private CourseSpotRepository courseSpotRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ContentsRepository contentsRepository;

    CourseService(WebConfig webConfig) {
        this.webConfig = webConfig;
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

				spot.setCourse_id(spotEntity.getCourse().getCourseId());
				spot.setCourse_spot_id(spotEntity.getCourseSpotId());
				
				spot.setContentid(spotEntity.getContents().getContentId());
				spot.setContenttypeid(spotEntity.getContents().getContentTypeId());
				spot.setTitle(spotEntity.getContents().getTitle());
				spot.setAddr(spotEntity.getContents().getAddr());
				spot.setAreacode(spotEntity.getContents().getAreaCode());
				spot.setSigungucode(spotEntity.getContents().getSigunguCode());
				spot.setFirstimage(spotEntity.getContents().getFirstimage());
				
				spotlists.add(spot);
				System.out.println(spotEntity.getContents().getTitle());

			}
				
			
			dto.setContents(spotlists);
			
			
			dto.setShared_count(course.getSharedCount());
			
			return dto;
		});
		

	}

	public SaveResponseDto saveCourse(SaveCourseDto dto) {
		// TODO Auto-generated method stub
		// 1. 유효한  user_id 인지 여
		boolean isExists = userRepository.existsByUserId(dto.getCreator_user_id());
		
		if(!isExists) {
			return new SaveResponseDto(false, "invalid user_id", null, null);
		}
		
		//2. Contents 들이 존재하는지 검사, 없다면 새로 저장
		
		List<CourseSpotDto> contents = dto.getContents();
		
		
		String areaCode = null;
		String sigunguCode = null;
		
		for(CourseSpotDto spot : contents) {
			//2.1 컨텐츠 존재여부 검사
			isExists = contentsRepository.existsByContentId(spot.getContentid());
			
			// 전달 받은 제일 마지막 컨텐츠의 지역코드를 대표지역으로 세팅한다.
			
			areaCode = spot.getAreacode();
			sigunguCode = spot.getSigungucode();
			
			if(!isExists) {
				
				ContentsEntity contentEntity = new ContentsEntity();
				
				contentEntity.setContentId(spot.getContentid());
				contentEntity.setContentTypeId(spot.getContenttypeid());
				contentEntity.setTitle(spot.getTitle());
				contentEntity.setAddr(spot.getAddr());
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
		
//		UserEntity user = new UserEntity();
//		user.setUserId(dto.getCreator_user_id());  // user객체의 다른 컬럼이 null 로 세팅되지만 참조 객체일뿐이므로 user테이블 데이터에 영향은 없다.
		
		UserEntity user = userRepository.findByUserId(dto.getCreator_user_id());
		
		course.setUser(user);
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
		
		for(CourseSpotDto spot : contents) {
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
		
		return new SaveResponseDto(true, "saved", "course_id", savedCourse.getCourseId());
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
			return new SaveResponseDto(true, "deleted", "course_id", courseId);
			
		}
		else {
			return new SaveResponseDto(false, "not_found", null, null);
		}
		
	}

}
