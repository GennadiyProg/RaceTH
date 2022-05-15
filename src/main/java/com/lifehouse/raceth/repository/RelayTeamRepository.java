package com.lifehouse.raceth.repository;

import com.lifehouse.raceth.model.RelayTeam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface RelayTeamRepository extends JpaRepository<RelayTeam, Long> {
}
