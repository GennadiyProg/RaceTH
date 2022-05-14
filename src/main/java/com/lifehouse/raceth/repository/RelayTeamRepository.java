package com.lifehouse.raceth.repository;

import com.lifehouse.raceth.model.Group;
import com.lifehouse.raceth.model.RelayTeam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface RelayTeamRepository extends JpaRepository<RelayTeam, Long> {
    @Modifying
    @Query("UPDATE RelayTeam r SET r = :relayTeam WHERE r.id = :id")
    void update(@Param("id") long id, @Param("relayTeam") RelayTeam relayTeam);
}
