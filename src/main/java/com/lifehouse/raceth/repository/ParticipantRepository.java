package com.lifehouse.raceth.repository;

import com.lifehouse.raceth.model.Participant;
import com.lifehouse.raceth.model.Start;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface ParticipantRepository extends JpaRepository<Participant, Long> {
    @Query(value = "SELECT p FROM Participant p WHERE p.start.id IN :id")
    List<Participant> findAllByStartsId(@Param("id") List<Long> id);

    Participant findByChip(String chip);

    List<Participant> getParticipantByStart(Start start);
}
