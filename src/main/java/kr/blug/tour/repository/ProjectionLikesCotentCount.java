package kr.blug.tour.repository;

import java.time.LocalDateTime;

public interface ProjectionLikesCotentCount {

     Long  getContentsRowId();
     String getContentId();
     String  getContentTypeId();
     String getAreaCode();
     String getSigunguCode();
     String getAddr();
     String getTitle();
     String getFirstimage();
     String getMapX();
     String getMapY();
     LocalDateTime getCrdttm();
     Long getLikesCount();
}
