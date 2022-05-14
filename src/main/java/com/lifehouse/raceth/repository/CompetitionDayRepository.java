package com.lifehouse.raceth.repository;

import com.lifehouse.raceth.model.CompetitionDay;
import com.lifehouse.raceth.model.competition.Competition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface CompetitionDayRepository extends JpaRepository<CompetitionDay, Long> {
    @Modifying
    @Query("UPDATE CompetitionDay c SET c = :competitionDay WHERE c.id = :id")
    void update(@Param("id") long id, @Param("competitionDay") CompetitionDay competitionDay);

    @Modifying
    @Query("DELETE FROM CompetitionDay c WHERE c.competition.id = :id")
    void deleteByCompetitionId(@Param("id") long id);

    @Query(value = "SELECT c FROM CompetitionDay c WHERE c.competition.id = :id")
    List<CompetitionDay> findAllByCompetitionId(@Param("id") long id);
}
