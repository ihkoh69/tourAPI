package kr.blug.tour.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import kr.blug.tour.dto.EventsDto;
import kr.blug.tour.entity.EventsEntity;
import kr.blug.tour.repository.EventSpecification;
import kr.blug.tour.repository.EventsRepository;
import kr.blug.tour.util.GpsUtils;

@Service
public class EventsService {
	
    @Autowired
    private EventsRepository eventsRepository;

    public Page<EventsDto> listEvents(String keyword,
                                      String pStartDate,
                                      String pEndDate,
                                      String areaCode,
                                      String sigunguCode,
                                      String cat3,
                                      String contentTypeId,
                                      String pointX,
                                      String pointY,
                                      double radiusKm,
                                      Pageable pageable) {

        String pxMin = null;
        String pxMax = null;
        String pyMin = null;
        String pyMax = null;

        if (pointX != null && pointY != null) {
            try {
//                double px = Double.parseDouble(pointX);
//                double py = Double.parseDouble(pointY);
//
//                pxMin = formatGps(px - 0.01);
//                pxMax = formatGps(px + 0.01);
//                pyMin = formatGps(py - 0.01);
//                pyMax = formatGps(py + 0.01);
                
                
                GpsUtils.GpsRange gpsRange = GpsUtils.calculateGpsRange(pointX, pointY, radiusKm); // 10km 반경

                if (gpsRange != null) {
                    pxMin = gpsRange.pxMin;
                    pxMax = gpsRange.pxMax;
                    pyMin = gpsRange.pyMin;
                    pyMax = gpsRange.pyMax;
                }
                
                
                
                
            } catch (NumberFormatException e) {
                // 로그 출력 또는 무시
                System.out.println("Invalid GPS coordinate format: " + pointX + ", " + pointY);
            }
        }

        Page<EventsEntity> pageResult = eventsRepository.findAll(
            EventSpecification.withFilters(
                keyword, pStartDate, pEndDate, areaCode, sigunguCode, cat3, contentTypeId,
                pxMin, pxMax, pyMin, pyMax
            ),
            pageable
        );

        // entity → dto 변환
        return pageResult.map((entity)->{
        	EventsDto dto = new EventsDto();
        	
        	dto.setEventsId(entity.getEventsId());
        	dto.setContentId(entity.getContentId());
        	dto.setContentTypeId(entity.getContentTypeId());
        	dto.setAreaCode(entity.getAreaCode());
        	dto.setSigunguCode(entity.getSigunguCode());
        	dto.setMapX(entity.getMapX());
        	dto.setMapY(entity.getMapY());
        	dto.setStartDate(entity.getStartDate());
        	dto.setEndDate(entity.getEndDate());
        	dto.setTitle(entity.getTitle());
        	dto.setAddr1(entity.getAddr1());
        	dto.setAddr2(entity.getAddr2());
        	dto.setDescription(entity.getDescription());
        	dto.setFirstimage(entity.getFirstimage());
        	dto.setFirstimage2(entity.getFirstimage2());
        	dto.setCat2(entity.getCat2());
        	dto.setCat3(entity.getCat3());
        	dto.setModifiedtime(entity.getModifiedtime());
        	dto.setCrdt(entity.getCrdt());
        	dto.setUpdt(entity.getUpdt());
        	
        	return dto;
        });
        	
        	

    }

    private String formatGps(double value) {
        return String.format("%.6f", value);
    }

}
