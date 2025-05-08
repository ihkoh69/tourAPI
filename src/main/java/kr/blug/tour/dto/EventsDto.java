package kr.blug.tour.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventsDto {
	private Long eventsId;   
	private String contentId;
	private String areaCode;
	private String sigunguCode;
	private String mapX;
	private String mapY;
	private String startDate;
	private String endDate;
	private String title;
	private String addr1;
	private String addr2;
	private String description;
	private String firstimage;
	private String firstimage2;
	private String crdt;
	private String updt;
}
