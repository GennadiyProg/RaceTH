package com.lifehouse.raceth.repository;

import com.lifehouse.raceth.model.Distance;
import com.lifehouse.raceth.model.Group;
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
public interface DistanceRepository extends JpaRepository<Distance, Long> {
    @Modifying
    @Query(value = "DELETE FROM competition_distance AS c WHERE c.distance_id = :distance_id AND c.competition_id = :competition_id", nativeQuery = true)
    void deleteCompetitionRelation(@Param("distance_id") long distance_id, @Param("competition_id") long competition_id);


    List<Distance> findAllByCompetitions(Competition competition);

    Distance findByLength(int length);
}
