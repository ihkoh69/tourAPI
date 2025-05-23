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
import kr.blug.tour.dto.FavoritesDto;
import kr.blug.tour.dto.SaveContentDto;
import kr.blug.tour.dto.SaveResponseDto;
import kr.blug.tour.entity.ContentsEntity;
import kr.blug.tour.entity.FavoritesEntity;
import kr.blug.tour.entity.UserEntity;
import kr.blug.tour.repository.ContentsRepository;
import kr.blug.tour.repository.FavoritesRepository;
import kr.blug.tour.repository.UserRepository;

@Service
public class FavoritesService {
	
	@Autowired
	private FavoritesRepository favoritesRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ContentsRepository contentsRepository;

	public Page<FavoritesDto> listByUserIdAndContentTypeId(Long userId, String contentTypeId, Pageable pageable) {
		
		Page<FavoritesEntity> favoritesPage = favoritesRepository.findByUser_UserIdAndContents_ContentTypeIdOrderByCrdttmDesc(userId, contentTypeId, pageable);
		
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
			
			return dto;
		});
		
	}

	public SaveResponseDto saveFavorite(SaveContentDto dto) {
			Long userId = dto.getUser_id();
			String contentId = dto.getContentid();
			
		    // 1. 이미 존재하는지 확인
		    boolean exists = favoritesRepository.existsByUser_UserIdAndContents_ContentId(userId, contentId);
		    if (exists) {
		        return new SaveResponseDto(false, "data already exists", null, null);
		    }

		    // 2. 사용자 존재 여부 확인
		    Optional<UserEntity> optionalUser = userRepository.findById(userId);
		    if (optionalUser.isEmpty()) {
		        return new SaveResponseDto(false, "user not exists", null, null);
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
		                newContents.setCrdttm(LocalDateTime.now());
		                return contentsRepository.save(newContents); // 저장 후 반환
		            });

		    // 4. 즐겨찾기 저장
		    FavoritesEntity favorite = new FavoritesEntity();
		    favorite.setUser(user);
		    favorite.setContents(contents);
		    favorite.setCrdttm(LocalDateTime.now());

		    FavoritesEntity saved = favoritesRepository.save(favorite);
		    
		    return new SaveResponseDto(true, "saved", "favorites_id", saved.getFavoritesId());

		}

	public SaveResponseDto deleteByUserIdAndContentId(Long userId, String contentId) {
		
		FavoritesEntity entity =  favoritesRepository.findByUser_UserIdAndContents_ContentId(userId, contentId);
		
		if(entity != null) {
			Long favoritesId = entity.getFavoritesId();   //지우기 전에 id를 먼저 저장해 둠
			
			favoritesRepository.delete(entity);			
	        return new SaveResponseDto(true, "deleted", "favorites_id", favoritesId);
		}
		else {
			return new SaveResponseDto(false, "not_found", null, null);

	    }
	
	}

	
//	public List<FavoritesDto> listByUserId(Long userId) {
//		
//		List<FavoritesEntity> favorites = favoritesRepository.findAllByUser_UserId(userId);
//		
//		List<FavoritesDto> items = new ArrayList<>();
//		
//		for(FavoritesEntity record : favorites) {
//			FavoritesDto dto = new FavoritesDto();
//			
//			dto.setFavoritesId(record.getFavoritesId());
//			dto.setUserId(record.getUser().getUserId());
//			dto.setUserNickName(record.getUser().getNickname());
//			dto.setContentId(record.getContentId());
//			dto.setContentTypeId(record.getContentTypeId());
//			dto.setTitle(record.getTitle());
//			dto.setAddr(record.getAddr());
//			dto.setAreaCode(record.getAreaCode());
//			dto.setSigunguCode(record.getSigunguCode());
//			dto.setFirstimage(record.getFirstimage());
//			
//			items.add(dto);
//		}
//		
//		return items;
//	}
}
