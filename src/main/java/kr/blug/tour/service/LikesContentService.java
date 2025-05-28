package kr.blug.tour.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import kr.blug.tour.dto.LikesContentCheckDto;
import kr.blug.tour.dto.LikesContentDto;
import kr.blug.tour.dto.SaveContentDto;
import kr.blug.tour.dto.SaveResponseDto;
import kr.blug.tour.entity.ContentsEntity;
import kr.blug.tour.entity.LikesContentEntity;
import kr.blug.tour.entity.UserEntity;
import kr.blug.tour.repository.*;

@Service
public class LikesContentService {

	@Autowired
	private LikesContentRepository likesContentRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ContentsRepository contentsRepository;


	// likes/content/check
	public LikesContentCheckDto findByUserAndContent(Long userId, String contentId) {
		
		
		LikesContentCheckDto dto = new LikesContentCheckDto();
		
		
		Long likesCount = likesContentRepository.countByContents_ContentId(contentId);
		Boolean isMine = likesContentRepository.existsByUser_UserIdAndContents_ContentId(userId, contentId);
		
		dto.setUser_id(userId);
		dto.setContentid(contentId);
		dto.setLikes_count(likesCount);
		dto.setMy_check(isMine);
		
		return dto;

	}

	public Page<LikesContentDto> listByUser(Long userId, Pageable pageable) {
	       
			
		return likesContentRepository.findByUser_UserId(userId, pageable).map(myContent->{
			
			LikesContentDto dto = new LikesContentDto();
			
			dto.setUser_id(myContent.getUser().getUserId());
			dto.setLikes_content_id(myContent.getLikesContentId());
			dto.setContentid(myContent.getContents().getContentId());
			dto.setContenttypeid(myContent.getContents().getContentTypeId());
			dto.setTitle(myContent.getContents().getTitle());
			dto.setAddr1(myContent.getContents().getAddr());
			dto.setAreacode(myContent.getContents().getAreaCode());
			dto.setSigungucode(myContent.getContents().getSigunguCode());
			dto.setFirstimage(myContent.getContents().getFirstimage());

			return dto;
		});
		
	}

	public Page<LikesContentDto> listLikesContentAll(Pageable pageable, String areaCode, String sigunguCode,
			String contentTypeId, String contentId, Long userId) {
		
		Page<ProjectionLikesCotentCount> page = likesContentRepository.listContentsOrderByLikesCountDesc(pageable, areaCode, sigunguCode, contentTypeId, contentId, userId);
		
		System.out.println("-------------------------------------- user_id -------------------------------");
		
		page.getContent().forEach(item -> {
		    System.out.println(item.getTitle() + " / 좋아요 수: " + item.getLikesCount());
		});	
		
		Page<LikesContentDto> dtoPage = page.map(item -> {
			
				LikesContentDto dto = new LikesContentDto();				
				
				dto.setLikes_content_id(item.getContentsRowId());

				dto.setContentid(item.getContentId());
				dto.setContenttypeid(item.getContentTypeId());
				dto.setTitle(item.getTitle());
				dto.setAddr1(item.getAddr());
				dto.setAreacode(item.getAreaCode());
				dto.setSigungucode(item.getSigunguCode());
				dto.setFirstimage(item.getFirstimage());
				dto.setLikes_count(item.getLikesCount());
				
				dto.setCrdttm(item.getCrdttm());
					
				return dto;
			}
		);
		
		return dtoPage;

	}

	public SaveResponseDto saveLikesContent(SaveContentDto dto) {
		
	
		// 1. 이미 좋아요 표시한 내용이 있는지 검사 user_id, contentid
		boolean isExists = likesContentRepository.existsByUser_UserIdAndContents_ContentId(dto.getUser_id(), dto.getContentid());
		
		if(isExists) {
			Long likesCount = likesContentRepository.countByContents_ContentId(dto.getContentid());
			return new SaveResponseDto(false, "record already exists", likesCount);
		}
		
		// 2. 유효한(존재하는) 사용자인지 확인 user_id
		
		Optional<UserEntity> optionalUser = userRepository.findById(dto.getUser_id());
		if(optionalUser.isEmpty()) {
			Long likesCount = likesContentRepository.countByContents_ContentId(dto.getContentid());
			return new SaveResponseDto(false, "user not exists", likesCount);
		}
		UserEntity user = optionalUser.get();
		
		
		// 3. 저장되어 있는 컨텐츠 여부 확인 미존재시 컨텐츠를 미리 등록   contentid

		ContentsEntity content = contentsRepository.findByContentId(dto.getContentid())
				.orElseGet(()->{
					ContentsEntity newContent = new ContentsEntity();
					
					newContent.setContentId(dto.getContentid());
					newContent.setContentTypeId(dto.getContenttypeid());
					newContent.setTitle(dto.getTitle());
					newContent.setAddr(dto.getAddr1());
					newContent.setAreaCode(dto.getAreacode());
					newContent.setSigunguCode(dto.getSigungucode());
					newContent.setFirstimage(dto.getFirstimage());
					newContent.setCrdttm(LocalDateTime.now());
					
					return contentsRepository.save(newContent);					
				});			
		
		// 4. likesContent에 좋아요 데이터 저장	
		
		LikesContentEntity likesContentEntity = new LikesContentEntity();
		likesContentEntity.setUser(user);;
		likesContentEntity.setContents(content);
		likesContentEntity.setCrdttm(LocalDateTime.now());
		
		LikesContentEntity saved = likesContentRepository.save(likesContentEntity);
		
		Long likesCount = likesContentRepository.countByContents_ContentId(dto.getContentid());
		
		return new SaveResponseDto(true, "saved", "likes_content_id", saved.getLikesContentId(), likesCount);
	}

	
	
	
	
	public SaveResponseDto deleteByUserIdAndContentId(Long userId, String contentId) {
		
		LikesContentEntity entity = likesContentRepository.findByUser_UserIdAndContents_ContentId(userId, contentId);
		if(entity != null) {
			Long likesContentId = entity.getLikesContentId();
			
			likesContentRepository.delete(entity);
			Long likesCount = likesContentRepository.countByContents_ContentId(contentId);
			return new SaveResponseDto(true, "deleted", "likes_content_id", likesContentId, likesCount);
		}
		else
		{
			Long likesCount = likesContentRepository.countByContents_ContentId(contentId);
			return new SaveResponseDto(false, "not_found",likesCount);
		}

	}


}
