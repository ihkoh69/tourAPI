package kr.blug.tour.service;

import java.time.LocalDateTime;
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
import kr.blug.tour.repository.ProjectionLikesCotentCount;
import kr.blug.tour.repository.ProjectionLikesCourseCount;

@Service
public class LikesContentService {
	
	@Autowired
	private LikesContentRepository likesContentRepository;

	public Optional<LikesContentDto> findByUserAndContent(Long userId, String contentId) {
		
		return likesContentRepository.findByUser_UserIdAndContents_ContentId(userId, contentId).map(myContent->{
			LikesContentDto dto = new LikesContentDto();

			dto.setUser_id(myContent.getUser().getUserId());
			dto.setLikes_content_id(myContent.getLikesContentId());
			dto.setContentid(myContent.getContents().getContentId());
			dto.setContenttypeid(myContent.getContents().getContentTypeId());
			dto.setTitle(myContent.getContents().getTitle());
			dto.setAddr(myContent.getContents().getAddr());
			dto.setAreacode(myContent.getContents().getAreaCode());
			dto.setSigungucode(myContent.getContents().getSigunguCode());
			dto.setFirstimage(myContent.getContents().getFirstimage());
			
			return dto;
		});
	}

	public Page<LikesContentDto> listByUser(Long userId, Pageable pageable) {
	       
			
		return likesContentRepository.findByUser_UserId(userId, pageable).map(myContent->{
			
			LikesContentDto dto = new LikesContentDto();
			
			dto.setUser_id(myContent.getUser().getUserId());
			dto.setLikes_content_id(myContent.getLikesContentId());
			dto.setContentid(myContent.getContents().getContentId());
			dto.setContenttypeid(myContent.getContents().getContentTypeId());
			dto.setTitle(myContent.getContents().getTitle());
			dto.setAddr(myContent.getContents().getAddr());
			dto.setAreacode(myContent.getContents().getAreaCode());
			dto.setSigungucode(myContent.getContents().getSigunguCode());
			dto.setFirstimage(myContent.getContents().getFirstimage());

			return dto;
		});
		
	}

	public Page<LikesContentDto> listLikesContentAll(Pageable pageable, String areaCode, String sigunguCode,
			Long userId) {
		
Page<ProjectionLikesCotentCount> page = likesContentRepository.listContentsOrderByLikesCountDesc(pageable, areaCode, sigunguCode, userId);
		
		page.getContent().forEach(item -> {
		    System.out.println(item.getTitle() + " / 좋아요 수: " + item.getLikesCount());
		});	
		

//		Page<LikesCourseDto> dtoPage = page.map(course -> new LikesCourseDto()
//		);
		
		Page<LikesContentDto> dtoPage = page.map(item -> {
				LikesContentDto dto = new LikesContentDto();
				
				
				
				
				
//				dto.setLikes_content_id();

				dto.setContentid(item.getContentId());
				dto.setContenttypeid(item.getContentTypeId());
				dto.setTitle(item.getTitle());
				dto.setAddr(item.getAddr());
				dto.setAreacode(item.getAreaCode());
				dto.setSigungucode(item.getSigunguCode());
				dto.setFirstimage(item.getFirstimage());
				dto.setLikes_count(item.getLikesCount());
				
	
	//			private LocalDateTime crdttm;
				
				
				return dto;
			}
		);

		
		return dtoPage;

	}


}
