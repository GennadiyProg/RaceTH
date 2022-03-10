package ru.corporateproduct.raceth.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.corporateproduct.raceth.model.Distance;

public class DistanceDAO implements DAO<Distance> {
    private final Session session;

    public DistanceDAO(final Session session) {
        this.session = session;
    }

    public void create(Distance distance) {

        session.beginTransaction();

        session.save(distance);

        session.getTransaction().commit();
    }
}
