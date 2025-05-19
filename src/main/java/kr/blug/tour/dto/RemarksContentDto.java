package kr.blug.tour.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="remarks_content",
uniqueConstraints = {
		  @UniqueConstraint(columnNames = {"content_id", "user_id"})
		}
)

public class RemarksContentDto {

	private Long remarks_content_id;
	private String remarks_content;
	
	private Long user_id;

	private String contentid;
	private String contenttypeid;
	private String title;
	
}
