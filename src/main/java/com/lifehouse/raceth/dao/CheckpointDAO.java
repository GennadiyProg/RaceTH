package com.lifehouse.raceth.dao;

import com.lifehouse.raceth.Main;
import com.lifehouse.raceth.model.Checkpoint;
import com.lifehouse.raceth.model.Participant;
import com.lifehouse.raceth.repository.CheckpointRepository;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.List;

@Component
public class CheckpointDAO {
    private final CheckpointRepository checkpointRepository;

    public CheckpointDAO() {
        this.checkpointRepository = (CheckpointRepository) Main.appContext.getBean("checkpointRepository");
    }

    public void create(Checkpoint checkpoint) {
        checkpointRepository.save(checkpoint);
    }

    public void update(Checkpoint checkpoint) {
        checkpointRepository.save(checkpoint);
    }

    public Checkpoint getCheckpointByParticipant(long id){
        return checkpointRepository.findByParticipantId(id);
    }

    public Checkpoint getCheckpoint(long id) {
        return checkpointRepository.findById(id).orElse(null);
    }

    public List<Checkpoint> getAllCheckpoints() {
        return checkpointRepository.findAll();
    }

    public int getCountCheakpointByParticipiant(Participant participant) {
        return checkpointRepository.countCheckpointByParticipant(participant);
    }

    public int getParticipiantPlace(Participant participant,int lap) {
        return checkpointRepository.countCheckpointByParticipant_StartAndLap(participant.getStart(),lap);
    }

    public void delete(Checkpoint checkpoint) {
        checkpointRepository.delete(checkpoint);
    }

    public LocalTime getlastLapTime(Participant participant, int lap) {
        return checkpointRepository.findCheckpointByParticipantAndLap(participant,lap).get(0).getCrossingTime();
    }

    public Checkpoint getLeader(int lap) {
        return checkpointRepository.getLeader(lap).get(0);
    }

    public Checkpoint getLastCheckpointByParticipant(Participant participant) {return checkpointRepository.findFirstByParticipantOrderByCrossingTimeDesc(participant);}

    public void removeCheckpoint(long id) {
        removeCheckpoint(getCheckpoint(id));
    }

    public void removeCheckpoint(Checkpoint checkpoint) {
        checkpointRepository.delete(checkpoint);
    }
}
