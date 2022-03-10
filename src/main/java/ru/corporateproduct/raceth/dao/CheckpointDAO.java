package ru.corporateproduct.raceth.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.corporateproduct.raceth.model.Checkpoint;

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
