package com.lifehouse.raceth.dao;

import com.lifehouse.raceth.model.Checkpoint;
import org.hibernate.Session;

public class CheckpointDAO implements DAO<Checkpoint> {
    private final Session session;

    public CheckpointDAO(final Session session) {
        this.session = session;
    }

    public void create(Checkpoint checkpoint) {
        session.beginTransaction();

        session.save(checkpoint);

        session.getTransaction().commit();
    }
}
