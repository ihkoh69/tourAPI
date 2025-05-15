package kr.blug.tour.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.blug.tour.entity.ContentsEntity;
import kr.blug.tour.entity.UserEntity;

public interface ContentsRepository extends JpaRepository<ContentsEntity, Long> {

	Optional<ContentsEntity> findByContentId(String contentId);



}
