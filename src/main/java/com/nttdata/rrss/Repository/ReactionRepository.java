package com.nttdata.rrss.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.nttdata.rrss.Entity.ReactionEntity;

@Repository
public interface ReactionRepository extends JpaRepository<ReactionEntity, Long> {
	List<ReactionEntity> findAllByUser(Long user);
	void deleteAllByUser(Long user);
	@Query(value = "SELECT * FROM Reactions WHERE user = ?1 AND publication = ?2", nativeQuery=true)
	List<ReactionEntity> findByUserAndPublication(Long id, Long publication);
}
