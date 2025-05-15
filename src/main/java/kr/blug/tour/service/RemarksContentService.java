package kr.blug.tour.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import kr.blug.tour.dto.RemarksContentDto;
import kr.blug.tour.dto.RemarksContentResDto;
import kr.blug.tour.entity.RemarksContentEntity;
import kr.blug.tour.repository.RemarksContentRepository;
import kr.blug.tour.repository.UserRepository;
import kr.blug.tour.util.RemarksContentUtil;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class RemarksContentService {
	
	@Autowired
	private RemarksContentRepository remarksContentRepository;

	@Autowired
	private UserRepository userRepository;
		
//	public void remarksContentInsert(RemarksContentDto dto) {
//	        RemarksContentEntity remarks = RemarksContentEntity.builder()
//	                .remarksContent(dto.getRemarks_content())
//	                .user(dto.getUser_id())
//	                .content_id(dto.getContentid())
//	                .build();
//
//		remarksContentRepository.save(remarks);
//	}

//	public Optional<RemarksContentDto> findByUserAndContentid(Long user_id, String contentid) {	
//		return remarksContentRepository.findByUser_User_idAndContents_Contentid(user_id, contentid)
//				.map(item->{
//					RemarksContentDto dto = new RemarksContentDto();	
//					
//					
//					dto.setRemarkscontentid(item.getRemarkscontentid());
//					dto.setRemarksContent(item.getRemarksContent());	
//					dto.setuserid(item.getUser().getuserid());
//
//					
//					return dto;
//				});
//	}

//	public Page<RemarksContentDto> listByUser(Long userid, Pageable pageable) {		
//		return remarksContentRepository.findByUser_userid(userid, pageable)
//				.map(item->{
//					RemarksContentDto dto = new RemarksContentDto();
//					
//					
//			return dto; 
//		});
	
	}			


