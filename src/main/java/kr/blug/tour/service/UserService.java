package kr.blug.tour.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.blug.tour.dto.UserDto;
import kr.blug.tour.entity.UserEntity;
import kr.blug.tour.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;

	public UserDto userViewById(Long id) {
		
		UserEntity record = userRepository.findById(id).orElseThrow(()->new RuntimeException("not found"));
		
		UserDto dto = new UserDto();
		
		dto.setUserId(record.getUserId());
		dto.setEmail(record.getEmail());
		dto.setPassword(record.getPassword());
		dto.setNickname(record.getNickname());
		dto.setProfileImg(record.getProfileImg());
		dto.setAuthGrade(record.getAuthGrade());
		


		return dto;
	}

}
