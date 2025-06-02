package kr.blug.tour.service;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import kr.blug.tour.dto.CourseDto;
import kr.blug.tour.dto.FavoritesCheckDto;
import kr.blug.tour.dto.FavoritesDto;
import kr.blug.tour.dto.LikesContentCheckDto;
import kr.blug.tour.dto.SaveContentDto;
import kr.blug.tour.dto.SaveResponseDto;
import kr.blug.tour.entity.ContentsEntity;
import kr.blug.tour.entity.FavoritesEntity;
import kr.blug.tour.entity.UserEntity;
import kr.blug.tour.repository.ContentsRepository;
import kr.blug.tour.repository.FavoritesRepository;
import kr.blug.tour.repository.LikesContentRepository;
import kr.blug.tour.repository.UserRepository;

@Service
public class FavoritesService {
	
	@Autowired
	private FavoritesRepository favoritesRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ContentsRepository contentsRepository;
	
	@Autowired
	private LikesContentRepository likesContentRepository;

	public Page<FavoritesDto> listMyFavorites(Long userId, String contentTypeId, String contentId, String areaCode, String sigunguCode, Pageable pageable) {
		
		
		
//		Page<FavoritesEntity> favoritesPage = favoritesRepository.findByUser_UserIdAndContents_ContentIdAndContents_ContentTypeIdOrderByCrdttmDesc(userId, contentId, contentTypeId, pageable);
		

		Page<FavoritesEntity> favoritesPage = favoritesRepository.listMyFavorites(userId, contentId, contentTypeId, areaCode, sigunguCode, pageable);
		
		
		return favoritesPage.map(record->{
			FavoritesDto dto = new FavoritesDto();
			
			dto.setFavorites_id(record.getFavoritesId());
			dto.setUser_id(record.getUser().getUserId());
			dto.setUser_nickname(record.getUser().getNickname());
			dto.setContentid(record.getContents().getContentId());
			dto.setContenttypeid(record.getContents().getContentTypeId());
			dto.setTitle(record.getContents().getTitle());
			dto.setAddr1(record.getContents().getAddr());
			dto.setAreacode(record.getContents().getAreaCode());
			dto.setSigungucode(record.getContents().getSigunguCode());
			dto.setFirstimage(record.getContents().getFirstimage());
			dto.setMapX(record.getContents().getMapX());
			dto.setMapY(record.getContents().getMapY());
			
			return dto;
		});
		
	}

	public SaveResponseDto saveFavorite(SaveContentDto dto) {
			Long userId = dto.getUser_id();
			String contentId = dto.getContentid();
			
		    // 1. 이미 존재하는지 확인
		    boolean exists = favoritesRepository.existsByUser_UserIdAndContents_ContentId(userId, contentId);
		    if (exists) {
		    	Long likesCount = likesContentRepository.countByContents_ContentId(contentId);
		        return new SaveResponseDto(false, "data already exists", likesCount);
		    }

		    // 2. 사용자 존재 여부 확인
		    Optional<UserEntity> optionalUser = userRepository.findById(userId);
		    if (optionalUser.isEmpty()) {
		    	Long likesCount = likesContentRepository.countByContents_ContentId(contentId);
		        return new SaveResponseDto(false, "user not exists", likesCount);
		    }
		    UserEntity user = optionalUser.get();

		    // 3. 콘텐츠 존재 여부 확인 또는 새로 생성
		    ContentsEntity contents = contentsRepository.findByContentId(contentId)
		            .orElseGet(() -> {
		                ContentsEntity newContents = new ContentsEntity();
		                
		                newContents.setContentId(dto.getContentid());
		                newContents.setContentTypeId(dto.getContenttypeid());
		                newContents.setTitle(dto.getTitle());
		                newContents.setAddr(dto.getAddr1());
		                newContents.setAreaCode(dto.getAreacode());
		                newContents.setSigunguCode(dto.getSigungucode());
		                newContents.setFirstimage(dto.getFirstimage());
		                newContents.setMapX(dto.getMapX());
		                newContents.setMapY(dto.getMapY());
		                newContents.setCrdttm(LocalDateTime.now());
		                return contentsRepository.save(newContents); // 저장 후 반환
		            });

		    // 4. 즐겨찾기 저장
		    FavoritesEntity favorite = new FavoritesEntity();
		    favorite.setUser(user);
		    favorite.setContents(contents);
		    favorite.setCrdttm(LocalDateTime.now());

		    FavoritesEntity saved = favoritesRepository.save(favorite);
		    
		    Long likesCount = likesContentRepository.countByContents_ContentId(contentId);
		    return new SaveResponseDto(true, "saved", "favorites_id", saved.getFavoritesId(), likesCount);

		}

	public SaveResponseDto deleteByUserIdAndContentId(Long userId, String contentId) {
		
		FavoritesEntity entity =  favoritesRepository.findByUser_UserIdAndContents_ContentId(userId, contentId);
		
		if(entity != null) {
			Long favoritesId = entity.getFavoritesId();   //지우기 전에 id를 먼저 저장해 둠
			
			favoritesRepository.delete(entity);	
			Long likesCount = likesContentRepository.countByContents_ContentId(contentId);
	        return new SaveResponseDto(true, "deleted", "favorites_id", favoritesId, likesCount);
		}
		else {
			Long likesCount = likesContentRepository.countByContents_ContentId(contentId);
			return new SaveResponseDto(false, "not_found", likesCount);

	    }
	
	}


	
	// favorites/check
	public FavoritesCheckDto findByUserAndContent(Long userId, String contentId) {
		
		
		FavoritesCheckDto dto = new FavoritesCheckDto();		
		
		Boolean isMine = favoritesRepository.existsByUser_UserIdAndContents_ContentId(userId, contentId);
		
		dto.setUser_id(userId);
		dto.setContentid(contentId);
		dto.setMy_check(isMine);
		
		return dto;

	}

}
