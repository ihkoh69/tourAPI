 package kr.blug.tour.dto;

import java.time.LocalDateTime;

import kr.blug.tour.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
	
	private Long userId;
	private String email;
	
	private String password;
	private String nickname;
	private String profileImg;
	private String authGrade;
	private LocalDateTime crDtTm;
//	private LocalDateTime upDtTm;
	
//	public UserEntity toEntity() {
//		UserEntity user = UserEntity.builder()
//				.userId(userId)
//				.nickname(nickname)
//				.email(email)
//				.profileImg(profileImg)
//				.build();
//		return user;
//	}
	 
}
