package kr.blug.tour.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LikesContentCheckDto {
	
	private Long user_id;
	private Boolean my_check;

	private String contentid;
	private Long likes_count;

}
