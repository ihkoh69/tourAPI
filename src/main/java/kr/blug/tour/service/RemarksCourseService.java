package kr.blug.tour.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.blug.tour.dto.RemarksContentDto;
import kr.blug.tour.dto.RemarksCourseDto;
import kr.blug.tour.entity.RemarksContentEntity;
import kr.blug.tour.entity.RemarksCourseEntity;
import kr.blug.tour.repository.RemarksContentRepository;
import kr.blug.tour.repository.RemarksCourseRepository;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class RemarksCourseService {

	@Autowired
	private RemarksCourseRepository remarksCourseRepository;

//	@Autowired
//	private RemarksCourseUtil remarksCourseUtil;
	
	
	public void remarksCourseInsert(RemarksCourseDto dto) {
		RemarksCourseEntity remarks = RemarksCourseEntity.builder()
//				.contentId(dto.getContentId())
				.build();
		
		remarksCourseRepository.save(remarks);
	}
}
