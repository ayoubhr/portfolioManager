package com.nttdata.rrss.Repository;

import com.nttdata.rrss.Entity.PortfolioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PortfolioRepository extends JpaRepository<PortfolioEntity, Long> {
    Optional<List<PortfolioEntity>> findByAuthor(Long author);
    @Query(value="DELETE FROM Portfolio WHERE author = ?1", nativeQuery = true)
    void deleteByAuthor(Long author);
}
