package kr.blug.tour.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.blug.tour.entity.FavoritesEntity;

@Repository
public interface FavoritesRepository extends JpaRepository<FavoritesEntity, Long>{



	List<FavoritesEntity> findAllByUser_UserId(Long userId);

	
}
