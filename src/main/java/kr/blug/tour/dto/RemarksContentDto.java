package kr.blug.tour.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RemarksContentDto {

	private Long remarks_content_id;
	private String contentid;	
	
	private Long user_id;
	private String user_nickname;

	private String comment;

	private String crdttm;
}
