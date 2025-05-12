package kr.blug.tour.service;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import kr.blug.tour.dto.LikesContentDto;
import kr.blug.tour.dto.LikesCourseDto;
import kr.blug.tour.entity.LikesContentEntity;
import kr.blug.tour.repository.LikesContentRepository;

@Service
public class LikesContentService {
	
	@Autowired
	private LikesContentRepository likesContentRepository;

	public Optional<LikesContentDto> findByUserAndContent(Long userId, String contentId) {
		
		return likesContentRepository.findByUser_UserIdAndContentId(userId, contentId).map(myContent->{
			LikesContentDto dto = new LikesContentDto();

			dto.setUserId(myContent.getUser().getUserId());
			dto.setLikesContentId(myContent.getLikesContentId());
			dto.setContentTypeId(myContent.getContentTypeId());
			dto.setTitle(myContent.getTitle());
			dto.setAddr(myContent.getAddr());
			dto.setAreaCode(myContent.getAreaCode());
			dto.setSigunguCode(myContent.getSigunguCode());
			dto.setFirstimage(myContent.getFirstimage());
			dto.setGoodOrHate(myContent.getGoodOrHate());
			
			return dto;
		});
	}

	public Page<LikesContentDto> listByUser(Long userId, Pageable pageable) {
	       
			
		return likesContentRepository.findByUser_UserId(userId, pageable).map(myContent->{
			
			LikesContentDto dto = new LikesContentDto();
			
			dto.setUserId(myContent.getUser().getUserId());
			dto.setLikesContentId(myContent.getLikesContentId());
			dto.setContentTypeId(myContent.getContentTypeId());
			dto.setTitle(myContent.getTitle());
			dto.setAddr(myContent.getAddr());
			dto.setAreaCode(myContent.getAreaCode());
			dto.setSigunguCode(myContent.getSigunguCode());
			dto.setFirstimage(myContent.getFirstimage());
			dto.setGoodOrHate(myContent.getGoodOrHate());
			
			return dto;
		});
		
	}

}
