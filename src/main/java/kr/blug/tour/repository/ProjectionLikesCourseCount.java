package kr.blug.tour.repository;

import java.time.LocalDateTime;

public interface ProjectionLikesCourseCount {
	
	
	// 필드명은 SQL 쿼리의 alias (AS)와 정확히 일치해야 
    Long getCourseId();
    Long getWriterUserId();
    String getWriterNickname();
    String getAreaCode();
    String getSigunguCode();
    String getCourseName();
    String getDescription();
    LocalDateTime getCrdttm();
    LocalDateTime getUpdttm();
    Long getLikesCount();
    
}
