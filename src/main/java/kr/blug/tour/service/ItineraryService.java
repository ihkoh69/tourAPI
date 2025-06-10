package kr.blug.tour.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import kr.blug.tour.controller.EventsController;
import kr.blug.tour.dto.ItineraryDto;
import kr.blug.tour.dto.ItineraryUpdateDto;
import kr.blug.tour.dto.ItineraryViewDto;
import kr.blug.tour.dto.SaveContentDto;
import kr.blug.tour.dto.SaveResponseDto;
import kr.blug.tour.entity.ContentsEntity;
import kr.blug.tour.entity.CourseEntity;
import kr.blug.tour.entity.CourseSpotEntity;
import kr.blug.tour.entity.ItineraryEntity;
import kr.blug.tour.entity.UserEntity;
import kr.blug.tour.repository.CourseRepository;
import kr.blug.tour.repository.ItineraryRepository;
import kr.blug.tour.repository.UserRepository;
import kr.blug.tour.util.ParamCheckUtils;

@Service
public class ItineraryService {

    private final EventsController eventsController;

    private final UserService userService;
	
	@Autowired
	private ItineraryRepository itineraryRepository;
	
	@Autowired
	private CourseRepository courseRepository;
	
	@Autowired
	private UserRepository userRepository;
	

    ItineraryService(UserService userService, EventsController eventsController) {
        this.userService = userService;
        this.eventsController = eventsController;
    }
	

	public SaveResponseDto saveItinerary(ItineraryDto dto) {
		
		if(!ParamCheckUtils.isValidDate(dto.getStart_date()) || !ParamCheckUtils.isValidDate(dto.getEnd_date())) {
			return new SaveResponseDto(false, "날짜 파라미터 형식을 확인하세요 yyyyMMdd");
		}
		
		if(dto.getStart_date().compareTo(dto.getEnd_date()) > 0 ) {
			return new SaveResponseDto(false, "여행종료 날짜가 여행시작일 보다 작습니다.");
		}
		
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
		LocalDate startDate = LocalDate.parse(dto.getStart_date(), formatter);
		LocalDate today = LocalDate.now();
		
		if(startDate.isBefore(today)) {
			return new SaveResponseDto(false, "여행일정이 과거 날짜입니다.");
		}
				
		if(itineraryRepository.existsByUser_UserIdAndCourse_CourseIdAndStartDate(
				dto.getUser_id(), dto.getCourse_id(), dto.getStart_date()
				
				)) {
			
			return new SaveResponseDto(false, "데이터가 이미 있습니다.");
			
		}
		
		
		Optional<UserEntity> user = userRepository.findById(dto.getUser_id());
		if(user.isEmpty()) {
			return new SaveResponseDto(false, "존재하지 않는 user_id입니다.");
		}
		
		Optional<CourseEntity> course = courseRepository.findById(dto.getCourse_id());
		if(course.isEmpty()) {
			return new SaveResponseDto(false, "존재하지 않는 course_id입니다.");
		}
		
		
		ItineraryEntity entity = ItineraryEntity.builder()
		.user(user.get())
		.course(course.get())
		.startDate(dto.getStart_date())
		.endDate(dto.getEnd_date())
		.isVisited(false)
		.review("")
		.crdttm(LocalDateTime.now())
		.build();
		
		ItineraryEntity newEntity = itineraryRepository.save(entity);
		
		return new SaveResponseDto(true, "saved", 
				"itinerary_id", newEntity.getItineraryId(),null);

	}


	public Page<ItineraryDto> listTour(Long userId, String pStartDate, String pEndDate, Pageable pageable) {

		
	    Page<ItineraryEntity> entityPage = itineraryRepository.findByUserIdAndDateRange(userId, pStartDate, pEndDate, pageable);

	    return entityPage.map(entity -> {
	        ItineraryDto dto = new ItineraryDto();
	        		
	        dto.setItinerary_id(entity.getItineraryId());
	        dto.setUser_id(entity.getUser().getUserId());
	        dto.setCourse_id(entity.getCourse().getCourseId());
	        dto.setTitle(entity.getCourse().getCourseName());
	        dto.setStart_date(entity.getStartDate());
	        dto.setEnd_date(entity.getEndDate());
	        dto.setIs_visited(entity.getIsVisited());
	        dto.setReview(entity.getReview());
	        dto.setCrdttm(entity.getCrdttm());
	        
	        return dto;
	    });

	}


	public SaveResponseDto updateTour(Long itineraryId, ItineraryUpdateDto dto) {
		
		
		Optional<ItineraryEntity> target = itineraryRepository.findById(itineraryId);
		if(target.isEmpty()) {
			return new SaveResponseDto(false, "DB에 일치하는 itinerary_id 가 없습니다.");			
		}
		ItineraryEntity entity = target.get();
		
			
		if(dto.getStart_date() != null && ParamCheckUtils.isValidDate(dto.getStart_date())== false) {		
			return new SaveResponseDto(false, "start_date 의 값과 형식(yyyyMMdd)을 확인하세요");			
		}
		if(dto.getEnd_date() != null && !ParamCheckUtils.isValidDate(dto.getEnd_date())) {		
				return new SaveResponseDto(false, "end_date 의 값과 형식(yyyyMMdd)을 확인하세요");						
		}
		
		if(dto.getStart_date() != null && dto.getEnd_date() != null) {
			if(dto.getStart_date().compareTo(dto.getEnd_date()) > 0) {
				return new SaveResponseDto(false, "start_date 값은 end_date 값보다 작거나 같아야 합니다.");
			}
			entity.setStartDate(dto.getStart_date());
			entity.setEndDate(dto.getEnd_date());			
		}
		else if(dto.getStart_date() !=null && dto.getEnd_date() == null) {
			if(dto.getStart_date().compareTo(entity.getEndDate()) > 0) {
				return new SaveResponseDto(false, "start_date 값만 변경할 경우 기존에 저장된 end_date값 보다 작거나 같아야 합니다.");				
			}
			entity.setStartDate(dto.getStart_date());
		}
		else if(dto.getStart_date() == null && dto.getEnd_date() != null) {
			if(dto.getEnd_date().compareTo(entity.getStartDate())<0) {
				return new SaveResponseDto(false, "end_date 값만 변경할 경우 기존에 저장된 start_date값 보다 크거나 같아야 합니다.");				
			}
			entity.setEndDate(dto.getEnd_date());
		}
		else {}// 날짜 파라미터 입력 없음. 처리내용도 없음 
	

		
		if(dto.getCourse_id() != null) {
			Optional<CourseEntity> course = courseRepository.findById(dto.getCourse_id()); 
			if(course.isEmpty()) {
				return new SaveResponseDto(false, "DB에 일치하는 course_id 가 없습니다.");			
			}
			entity.setCourse(course.get());
		}
		
		if(dto.getIs_visited() != null) {
			entity.setIsVisited(dto.getIs_visited());
		}
		
		if(dto.getGeneral_review() != null) {
			entity.setReview(dto.getGeneral_review());
		}
		
		ItineraryEntity saved = itineraryRepository.save(entity);
		
		return new SaveResponseDto(true, "updated", "itinerary_id", saved.getItineraryId());
	}


	public SaveResponseDto deleteTour(Long itineraryId) {
		
		if(!itineraryRepository.existsById(itineraryId)) return new SaveResponseDto(false, "DB에 해당 itinerary_id가 없습니다.");
		
		itineraryRepository.deleteById(itineraryId);
		return new SaveResponseDto(true, "delted", "itinerary_id", itineraryId);
	}


	public ItineraryViewDto viewTourDetail(Long itineraryId) {
		
		ItineraryViewDto dto = new ItineraryViewDto();
	
		Optional<ItineraryEntity> res = itineraryRepository.findByItineraryId(itineraryId);
	
		if(res.isEmpty()) {
			return null;
		}
			
		ItineraryEntity item = res.get();	
	

		dto.setItinerary_id(item.getItineraryId());
		dto.setUser_id(item.getUser().getUserId());
		dto.setCourse_id(item.getCourse().getCourseId());
		
		dto.setTitle(dto.getTitle());
		dto.setDiscription(dto.getDiscription());
		dto.setStart_date(item.getStartDate());
		dto.setEnd_date(item.getEndDate());
		dto.setIs_visited(item.getIsVisited());
		dto.setReview(item.getReview());
		
		List<SaveContentDto> contents = new ArrayList<>();
		
		for(CourseSpotEntity entity : item.getCourse().getCourseSpots()) {
			SaveContentDto content = new SaveContentDto();
			
			content.setContentid(entity.getContents().getContentId());
			content.setContenttypeid(entity.getContents().getContentTypeId());
			content.setTitle(entity.getContents().getTitle());
			content.setAddr1(entity.getContents().getAddr1());
			content.setAddr2(entity.getContents().getAddr2());
			content.setAreacode(entity.getContents().getAreaCode());
			content.setSigungucode(entity.getContents().getSigunguCode());
			content.setFirstimage(entity.getContents().getFirstimage());
			content.setMapX(entity.getContents().getMapX());
			content.setMapY(entity.getContents().getMapY());
			
			contents.add(content);
		}
	
		dto.setContents(contents);
		
		return dto;
	}
	
}
