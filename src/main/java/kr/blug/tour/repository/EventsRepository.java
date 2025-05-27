package kr.blug.tour.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import kr.blug.tour.entity.EventsEntity;

public interface EventsRepository extends JpaRepository<EventsEntity, Long>, JpaSpecificationExecutor<EventsEntity>{

}
