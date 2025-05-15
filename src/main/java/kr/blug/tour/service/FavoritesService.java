package kr.blug.tour.service;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import kr.blug.tour.dto.CourseDto;
import kr.blug.tour.dto.FavoritesDto;
import kr.blug.tour.entity.FavoritesEntity;
import kr.blug.tour.repository.FavoritesRepository;

@Service
public class FavoritesService {
	
	@Autowired
	private FavoritesRepository favoritesRepository;

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
			dto.setAddr(record.getContents().getAddr());
			dto.setAreacode(record.getContents().getAreaCode());
			dto.setSigungucode(record.getContents().getSigunguCode());
			dto.setFirstimage(record.getContents().getFirstimage());
			
			return dto;
		});
		
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
