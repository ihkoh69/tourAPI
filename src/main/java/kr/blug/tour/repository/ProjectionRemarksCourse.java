package kr.blug.tour.repository;

import java.time.LocalDateTime;

public interface ProjectionRemarksCourse {

    Long  getRemarksCourseId();
    Long getCourseId();
    Long  getUserId();
    String getUserNickname();
    String  getComment();
    LocalDateTime getCrdttm();
}
