package com.lifehouse.raceth.repository;

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
public interface GroupRepository extends JpaRepository<Group, Long> {
    @Modifying
    @Query(value = "DELETE FROM competition_group AS c WHERE c.group_id = :group_id AND c.competition_id = :competition_id", nativeQuery = true)
    void deleteCompetitionRelation(@Param("group_id") long group_id, @Param("competition_id") long competition_id);

    List<Group> findAllByCompetitions(Competition competition);
}
