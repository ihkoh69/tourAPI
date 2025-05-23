package kr.blug.tour.repository;

import java.time.LocalDateTime;

public interface ProjectionRemarksContent {

    Long  getRemarksContentId();
    String getContentId();
    Long  getUserId();
    String getUserNickname();
    String  getComment();
    LocalDateTime getCrdttm();
    
}
