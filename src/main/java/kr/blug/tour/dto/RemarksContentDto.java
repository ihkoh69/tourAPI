package kr.blug.tour.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RemarksContentDto {

	private Long remarks_content_id;
	private String remarks_content;
	
	private Long user_id;

	private String contentid;
	private String contenttypeid;
	private String title;
	
}
