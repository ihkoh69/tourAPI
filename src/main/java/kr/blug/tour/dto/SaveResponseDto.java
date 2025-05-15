package kr.blug.tour.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SaveResponseDto {
	private boolean success;
	private String message;
	private String id_name;
	private Long id;
}
