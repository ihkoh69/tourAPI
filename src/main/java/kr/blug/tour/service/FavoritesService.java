package kr.blug.tour.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.blug.tour.dto.CourseDto;
import kr.blug.tour.dto.FavoritesDto;
import kr.blug.tour.entity.FavoritesEntity;
import kr.blug.tour.repository.FavoritesRepository;

@Service
public class FavoritesService {
	
	@Autowired
	private FavoritesRepository favoritesRepository;

	public List<FavoritesDto> listByUserId(Long userId) {
		
		List<FavoritesEntity> favorites = favoritesRepository.findAllByUser_UserId(userId);
		
		List<FavoritesDto> items = new ArrayList<>();
		
		for(FavoritesEntity record : favorites) {
			FavoritesDto dto = new FavoritesDto();
			
			dto.setFavoritesId(record.getFavoritesId());
			dto.setUserId(record.getUser().getUserId());
			dto.setUserNickName(record.getUser().getNickname());
			dto.setContentId(record.getContentId());
			dto.setContentTypeId(record.getContentTypeId());
			dto.setTitle(record.getTitle());
			dto.setAddr(record.getAddr());
			dto.setAreaCode(record.getAreaCode());
			dto.setSigunguCode(record.getSigunguCode());
			dto.setFirstimage(record.getFirstimage());
			
			items.add(dto);
		}
		
		return items;
	}

}
