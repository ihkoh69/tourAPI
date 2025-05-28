package kr.blug.tour.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SaveResponseDto {
	private boolean success;
	private String message;
	
//	private List<ResponseParams>  response_params = new ArrayList<>();
	private String id_name;
	private Long id;
	
	private Long likes_count;

	public SaveResponseDto(Boolean success, String message, String id_name, Long id) {
		super();
		this.success = success;
		this.message = message;
		this.id_name = id_name;
		this.id = id;
	}
	
	public SaveResponseDto(Boolean success, String message, Long likes_count) {
		super();
		this.success = success;
		this.message = message;
		this.likes_count = likes_count;
	}
	
	
	public SaveResponseDto(Boolean success, String message) {
		super();
		this.success = success;
		this.message = message;
	}
	
}
