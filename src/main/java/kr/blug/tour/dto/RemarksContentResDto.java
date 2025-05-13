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
	private Long userId;
	private String contentId;
	private String contentTypeId;
	private String title;
	private String addr;
	private String areaCode;
	private String sigunguCode;
	private String firstimage;
	
	@Column(length=1000)
	private String remarksContent;

}
