package kr.blug.tour.dto;

import lombok.Data;

@Data
public class ItineraryUpdateDto {

	private Long course_id;
	private String start_date;
	private String end_date;
	private Boolean is_visited;
	private String general_review;
}
