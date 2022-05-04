package com.lifehouse.raceth.dao;

//import com.lifehouse.raceth.HibernateUtil;
import com.lifehouse.raceth.model.Checkpoint;
import com.lifehouse.raceth.tmpstorage.TmpStorage;
import org.hibernate.Session;

import java.util.List;

public class CheckpointDAO implements DAO<Checkpoint> {
//    private final Session session;

//    public CheckpointDAO() {
//        this.session = HibernateUtil.getSession();
//    }

    public void create(Checkpoint checkpoint) {
//        session.getTransaction().begin();
//        session.save(checkpoint);
//        session.getTransaction().commit();
    }

    public Checkpoint getCheckpointByParticipant(long id){
        return TmpStorage.checkpoints.stream().filter(ch -> ch.getParticipant().getId() == id).findFirst().orElse(null);
    }

    public Checkpoint getCheckpoint(long id) {
        for (Checkpoint item : TmpStorage.checkpoints) {
            if (item.getId() == id) {
                return item;
            }
        }
        return null;
    }

    public List<Checkpoint> getAllCheckpoints() {
        return TmpStorage.checkpoints;
    }

    public void delete(Checkpoint checkpoint) {
        TmpStorage.checkpoints.remove(checkpoint);
    }
}
