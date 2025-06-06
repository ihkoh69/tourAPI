package kr.blug.tour.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ItineraryDto {
	
	private Long itinerary_id;
	
	private Long user_id;	
	private Long course_id;
	
	private String title;
	private String start_date;
	private String end_date;
	private Boolean is_visited;
	private String review;
	private LocalDateTime crdttm;
	
	
	

}
