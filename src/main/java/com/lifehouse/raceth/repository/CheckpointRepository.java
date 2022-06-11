package com.lifehouse.raceth.repository;

import com.lifehouse.raceth.model.Checkpoint;
import com.lifehouse.raceth.model.Group;
import com.lifehouse.raceth.model.Participant;
import com.lifehouse.raceth.model.Start;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.query.parser.Part;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.List;


@Repository
@Transactional
public interface CheckpointRepository extends JpaRepository<Checkpoint, Long> {
    @Query(value = "SELECT c FROM Checkpoint c WHERE c.participant.id = :id")
    Checkpoint findByParticipantId(@Param("id") long id);

    int countCheckpointByParticipant(Participant participant);

    int countCheckpointByParticipant_StartAndLap(Start start, int lap);

    int countCheckpointByParticipant_Start_GroupAndLap(Group group, int lap);

    List<Checkpoint> findCheckpointByParticipantAndLap(Participant participant, int lap);

    @Query(value = "select Checkpoint.id, Checkpoint.crossingTime, Checkpoint.lap, Checkpoint.participant_id from Checkpoint where Checkpoint.crossingTime = (select min(crossingTime) from Checkpoint join Participant P on Checkpoint.participant_id = P.id join Start S on P.start_id = S.id where lap = :lap and S.group_id = :group_id) and lap = :lap", nativeQuery = true)
    List<Checkpoint> getLeaderOfGroup(@Param("lap") int lap, @Param("group_id") long group_id);

    Checkpoint findFirstByParticipantOrderByCrossingTimeDesc(Participant participant);

    List<Checkpoint> findCheckpointByParticipant(Participant participant);
}
