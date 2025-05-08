package kr.blug.tour.dto;

import java.time.LocalDateTime;

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
	private LocalDateTime upDtTm;
}
