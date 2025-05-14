package kr.blug.tour.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RemarksContentDto {

	private Long remarks_content_id;
	private Long user_id;
	@Column(length=1000)
	private String remarks_content;
	
	private String contentid;
	private String title;
	

}
