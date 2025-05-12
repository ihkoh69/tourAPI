package kr.blug.tour.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.blug.tour.dto.RemarksContentDto;
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
//				.contentTypeId(dto.getContentTypeId())
				.build();
		
		remarksContentRepository.save(remarks);
	}
}
