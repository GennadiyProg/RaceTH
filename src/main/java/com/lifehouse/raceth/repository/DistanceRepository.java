package com.lifehouse.raceth.repository;

import com.lifehouse.raceth.model.Distance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface DistanceRepository extends JpaRepository<Distance, Long> {
    @Modifying
    @Query("UPDATE Distance d SET d = :dist WHERE d.id = :id")
    void update(@Param("id") long id, @Param("dist") Distance distance);
}
