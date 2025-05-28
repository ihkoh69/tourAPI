package kr.blug.tour.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import kr.blug.tour.dto.RemarksCommentUpdateDto;
import kr.blug.tour.dto.RemarksContentDto;
import kr.blug.tour.dto.RemarksCourseDto;
import kr.blug.tour.dto.SaveContentDto;
import kr.blug.tour.dto.SaveResponseDto;
import kr.blug.tour.entity.ContentsEntity;
import kr.blug.tour.entity.RemarksContentEntity;
import kr.blug.tour.entity.RemarksCourseEntity;
import kr.blug.tour.entity.UserEntity;
import kr.blug.tour.repository.ContentsRepository;
import kr.blug.tour.repository.LikesContentRepository;
import kr.blug.tour.repository.ProjectionRemarksContent;
import kr.blug.tour.repository.ProjectionRemarksCourse;
import kr.blug.tour.repository.RemarksContentRepository;
import kr.blug.tour.repository.UserRepository;

@Service
public class RemarksContentService {

		@Autowired
		RemarksContentRepository remarksContentRepository;
		
		@Autowired
		UserRepository userRepository;
		
		@Autowired
		ContentsRepository contentsRepository;
		
		@Autowired
		LikesContentRepository likesContentRepository;

		

		public Page<RemarksContentDto> listRemarksContent(Pageable pageable, Long userId, String contentId) {
			

			Page<ProjectionRemarksContent> page = remarksContentRepository.listRemarksContentOrderByCrdttmDesc(pageable, userId, contentId);
			
			page.getContent().forEach(item -> {
			    System.out.println(item.getUserNickname() + " : " + item.getComment());
			});	
			
			Page<RemarksContentDto> dtoPage = page.map(item -> {
					RemarksContentDto dto = new RemarksContentDto();
					
							
					dto.setRemarks_content_id(item.getRemarksContentId());
					dto.setContentid(item.getContentId());
					dto.setUser_id(item.getUserId());
					dto.setUser_nickname(item.getUserNickname());
					dto.setComment(item.getComment());
					dto.setCrdttm(item.getCrdttm());
					
					return dto;
				}
			);
			
			return dtoPage;

			
		}



		public SaveResponseDto saveRemarksContent(SaveContentDto dto) {
			
			//1. user_id가 유효한지 검사한다.
			
			Optional<UserEntity> user = userRepository.findByUserId(dto.getUser_id());
			if(user.isEmpty()) {
				//user_id가 존재하지 않으면 실패 메시지 리
				Long likesCount = likesContentRepository.countByContents_ContentId(dto.getContentid());
				return new SaveResponseDto(false, "invalid user_id", likesCount);
			}
			
			//2. 여행지가 존재하는지 검사하여 없으면 새로 저장한다.
			
			ContentsEntity content =contentsRepository.findByContentId(dto.getContentid()).orElseGet(()->{
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
				
			//3. 댓글 본문을 저장한다
			RemarksContentEntity remark = new RemarksContentEntity();
			
			

			
			
			remark.setContents(content);
			remark.setUser(user.get());
			remark.setComment(dto.getComment());
			remark.setCrdttm(LocalDateTime.now());
			
			RemarksContentEntity savedRemark= remarksContentRepository.save(remark);
			
			Long likesCount = likesContentRepository.countByContents_ContentId(dto.getContentid());
			return new SaveResponseDto(true, "saved", "remarks_content_id", savedRemark.getRemarksContentId(), likesCount);
		}



		public SaveResponseDto deleteRemarksContent(Long remarksContentId) {
			
			Optional<RemarksContentEntity> remark = remarksContentRepository.findById(remarksContentId);
			if(remark.isEmpty()) return new SaveResponseDto(false, "not_found");
			
			remarksContentRepository.delete(remark.get());
			return new SaveResponseDto(true, "deleted", "remarks_content_id", remark.get().getRemarksContentId());
		}



		public Long countRemarksContent(Long userId, String contentId) {
	
			return remarksContentRepository.countByOptionalUserAndContent(userId, contentId);
		}


		public SaveResponseDto updateCommentById(Long remarksContentId, RemarksCommentUpdateDto dto) {
			
			
			// 1. 해당 id의 레코드가 있는지 검사한다.
			Optional<RemarksContentEntity> remark = remarksContentRepository.findById(remarksContentId);
			if(remark.isEmpty()) return new SaveResponseDto(false, "DB에 해당하는 remarks_content_id가 없습니다.");
			
			RemarksContentEntity comment = remark.get();
			comment.setComment(dto.getComment());
			remarksContentRepository.save(comment);
			
			return new SaveResponseDto(true, "updated", "remarks_content_id", comment.getRemarksContentId());
		}				
		
}
