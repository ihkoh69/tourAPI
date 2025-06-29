package kr.blug.tour.repository;

import java.time.LocalDateTime;

public interface ProjectionLikesCotentCount {

     Long  getContentsRowId();
     String getContentId();
     String  getContentTypeId();
     String getAreaCode();
     String getSigunguCode();
     String getAddr1();
     String getAddr2();
     String getTitle();
     String getFirstimage();
     String getMapx();
     String getMapy();
     LocalDateTime getCrdttm();
     Long getLikesCount();
}
