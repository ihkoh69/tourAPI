package kr.blug.tour.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.jetbrains.exported.JBRApi.Service;

import kr.blug.tour.repository.RemarksContentRepository;

@Service
public class RemarksContentService {
	
	@Autowired
	private RemarksContentRepository remarksContentRepository;

}
