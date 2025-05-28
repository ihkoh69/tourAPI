package kr.blug.tour.dto;

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
public class CourseUpdateDto {

	private String course_name;
	private String description;
	
	private boolean contents_update;  // true이면 윗쪽 마스터 데이터만 수정하고 종료, 
	                              // false이면 컨텐츠(SPOT) 리스트 삭제 후 재등록 
	private List<SaveContentDto> contents;	
}
