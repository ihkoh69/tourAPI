package kr.blug.tour.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ItineraryViewDto {

	private Long user_id;
	private Long itinerary_id;	
	private Long course_id;
	
	private String title;
	private String discription;
	private String start_date;
	private String end_date;
	
	private Boolean is_visited;
	private String review;
	private LocalDateTime crdttm;
	
	private List<SaveContentDto> contents;
	
}
