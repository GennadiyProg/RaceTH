package com.lifehouse.raceth.repository;

import com.lifehouse.raceth.model.Group;
import com.lifehouse.raceth.model.competition.Competition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface CompetitionRepository extends JpaRepository<Competition, Long> {
    @Modifying
    @Query("UPDATE Competition c SET c = :competition WHERE c.id = :id")
    void update(@Param("id") long id, @Param("competition") Competition competition);
}
