package com.lifehouse.raceth.dao;

import com.lifehouse.raceth.Main;
import com.lifehouse.raceth.model.Checkpoint;
import com.lifehouse.raceth.repository.CheckpointRepository;
import org.springframework.stereotype.Component;

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

    public Checkpoint getCheckpointByParticipant(long id){
        return checkpointRepository.findByParticipantId(id);
    }

    public Checkpoint getCheckpoint(long id) {
        return checkpointRepository.findById(id).orElse(null);
    }

    public List<Checkpoint> getAllCheckpoints() {
        return checkpointRepository.findAll();
    }

    public void delete(Checkpoint checkpoint) {
        checkpointRepository.delete(checkpoint);
    }
}
