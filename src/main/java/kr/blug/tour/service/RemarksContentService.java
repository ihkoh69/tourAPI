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
import kr.blug.tour.util.RemarksContentUtil;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class RemarksContentService {
	
	@Autowired
	private RemarksContentRepository remarksContentRepository;

//	@Autowired
//	private RemarksContentUtil remarksContentUtil;
	
	
	public void remarksContentInsert(RemarksContentDto dto) {
		RemarksContentEntity remarks = RemarksContentEntity.builder()
				.contentId(dto.getContentId())
				.build();		
		remarksContentRepository.save(remarks);
	}

	public Optional<RemarksContentDto> findByUserAndContent(Long userId, String contentId) {	
		return remarksContentRepository.findByUser_UserIdAndContentId(userId, contentId)
				.map(item->{
					RemarksContentDto dto = new RemarksContentDto();	
					
					dto.setRemarksContentId(item.getRemarksContentId());
					dto.setUserId(item.getUser().getUserId());
					dto.setContentId(item.getContentId());	
					dto.setTitle(item.getTitle());
					dto.setAddr(item.getAddr());
					dto.setAreaCode(item.getAreaCode());
					dto.setSigunguCode(item.getSigunguCode());
					dto.setFirstimage(item.getFirstimage());
					dto.setRemarksContent(item.getRemarksContent());	
					
					return dto;
				});
	}

	public Page<RemarksContentDto> listByUser(Long userId, Pageable pageable) {		
		return remarksContentRepository.findByUser_UserId(userId, pageable)
				.map(item->{
					RemarksContentDto dto = new RemarksContentDto();
					
					dto.setRemarksContentId(item.getRemarksContentId());
					dto.setUserId(item.getUser().getUserId());
					dto.setContentId(item.getContentId());	
					dto.setTitle(item.getTitle());
					dto.setAddr(item.getAddr());
					dto.setAreaCode(item.getAreaCode());
					dto.setSigunguCode(item.getSigunguCode());
					dto.setFirstimage(item.getFirstimage());
					dto.setRemarksContent(item.getRemarksContent());	
			return dto; 
		});
	
	}				

}
