package kr.blug.tour.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="events",
		     uniqueConstraints = {
		    		 @UniqueConstraint(columnNames = {"content_id"})
		     }
		)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventsEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long eventsId; 
	
	@Column(nullable = false)
	private String contentId;
	private String contentTypeId;
	private String areaCode;
	private String sigunguCode;
	private String mapx;
	private String mapy;
	private String startDate;
	private String endDate;
	private String title;
	private String addr1;
	private String addr2;
	private String description;
	private String firstimage;
	private String firstimage2;
	private String cat2;
	private String cat3;
	private String modifiedtime;
	private String crdt;   //최초등록일:관광공사
	private String updt;   //최종수정일:관광공사
}
