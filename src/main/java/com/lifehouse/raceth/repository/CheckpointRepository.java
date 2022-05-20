package com.lifehouse.raceth.repository;

import com.lifehouse.raceth.model.Checkpoint;
import com.lifehouse.raceth.model.Participant;
import com.lifehouse.raceth.model.Start;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.query.parser.Part;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
@Transactional
public interface CheckpointRepository extends JpaRepository<Checkpoint, Long> {
    @Query(value = "SELECT c FROM Checkpoint c WHERE c.participant.id = :id")
    Checkpoint findByParticipantId(@Param("id") long id);

    int countCheckpointByParticipant(Participant participant);

    int countCheckpointByParticipant_StartAndLap(Start start, int lap);


}
