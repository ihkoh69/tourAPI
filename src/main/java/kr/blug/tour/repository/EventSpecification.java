package kr.blug.tour.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Predicate;
import kr.blug.tour.entity.EventsEntity;


public class EventSpecification {

    public static Specification<EventsEntity> withFilters(
            String keyword,
            String pStartDate,
            String pEndDate,
            String areaCode,
            String sigunguCode,
            String cat3,
            String contentTypeId,
            String pxMin,
            String pxMax,
            String pyMin,
            String pyMax
    ) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (keyword != null && !keyword.isBlank()) {
                predicates.add(cb.like(root.get("title"), "%" + keyword + "%"));
            }

            if (pStartDate != null && !pStartDate.isBlank() &&
                pEndDate != null && !pEndDate.isBlank()) {
                predicates.add(cb.lessThanOrEqualTo(root.get("startDate"), pEndDate));
                predicates.add(cb.greaterThanOrEqualTo(root.get("endDate"), pStartDate));
            }

            if (areaCode != null && !areaCode.isBlank()) {
                predicates.add(cb.equal(root.get("areaCode"), areaCode));
            }

            if (sigunguCode != null && !sigunguCode.isBlank()) {
                predicates.add(cb.equal(root.get("sigunguCode"), sigunguCode));
            }

            if (cat3 != null && !cat3.isBlank()) {
                predicates.add(cb.equal(root.get("cat3"), cat3));
            }

            if (contentTypeId != null && !contentTypeId.isBlank()) {
                predicates.add(cb.equal(root.get("contentTypeId"), contentTypeId));
            }

            if (pxMin != null && pxMax != null && pyMin != null && pyMax != null &&
                !pxMin.isBlank() && !pxMax.isBlank() && !pyMin.isBlank() && !pyMax.isBlank()) {
                predicates.add(cb.between(root.get("mapX"), pxMin, pxMax));
                predicates.add(cb.between(root.get("mapY"), pyMin, pyMax));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
