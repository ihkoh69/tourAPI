package kr.blug.tour.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaveResponseDto {
	private boolean success;
	private String message;
	
//	private List<ResponseParams>  response_params = new ArrayList<>();
	private String id_name;
	private Long id;
	
	private Long likes_count;
	
}
