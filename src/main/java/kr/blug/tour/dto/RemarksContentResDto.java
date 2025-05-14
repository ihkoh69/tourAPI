package kr.blug.tour.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RemarksContentResDto {
	
//	private Long remarksContentId;
	private Long user_id;
	private String contentid;
	private String contentypeid;
	private String title;
	private String addr;
	private String areacode;
	private String sigungucode;
	private String firstimage;
	
	@Column(length=1000)
	private String remarks_content;

}
