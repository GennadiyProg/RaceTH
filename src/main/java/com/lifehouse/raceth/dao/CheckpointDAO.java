package com.lifehouse.raceth.dao;

import com.lifehouse.raceth.model.Checkpoint;
import com.lifehouse.raceth.tmpstorage.TmpStorage;
import org.hibernate.Session;

import java.util.List;

public class CheckpointDAO implements DAO<Checkpoint> {
//    private final Session session;
//
//    public CheckpointDAO(final Session session) {
//        this.session = session;
//    }

//    public void create(Checkpoint checkpoint) {
//        session.beginTransaction();
//
//        session.save(checkpoint);
//
//        session.getTransaction().commit();
//    }

    public void create(Checkpoint checkpoint) {
        TmpStorage.checkpoints.add(checkpoint);
    }

    public Checkpoint GetCheckpoint(long id) {
        for (Checkpoint item : TmpStorage.checkpoints) {
            if (item.getId() == id) {
                return item;
            }
        }
        return null;
    }

    public List<Checkpoint> GetAllCheckpoints() {
        return TmpStorage.checkpoints;
    }

    public void Delete(Checkpoint checkpoint) {
        TmpStorage.checkpoints.remove(checkpoint);
    }
}
