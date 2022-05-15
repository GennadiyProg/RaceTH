package com.lifehouse.raceth.repository;

import com.lifehouse.raceth.model.Start;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface StartRepository extends JpaRepository<Start, Long> {
    @Query(value = "SELECT s FROM Start s WHERE s.competitionDay.competition.id = :id")
    List<Start> findAllByCompetitionId(@Param("id") long id);

    @Query(value = "SELECT s FROM Start s WHERE s.competitionDay.id = :id")
    List<Start> findAllByCompetitionDayId(@Param("id") long id);
}
